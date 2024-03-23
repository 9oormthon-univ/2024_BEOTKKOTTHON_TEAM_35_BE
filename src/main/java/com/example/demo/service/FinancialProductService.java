package com.example.demo.service;

import com.example.demo.dto.FinancialProductsResponse;
import com.example.demo.model.FinancialProduct;
import com.example.demo.model.ProductBookmark;
import com.example.demo.repository.FinancialProductRepository;
import com.example.demo.repository.ProductBookmarkRepostiory;
import com.example.demo.users.repository.UserRepository;
import com.example.demo.users.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class FinancialProductService {

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private FinancialProductRepository financialProductRepository;

    @Autowired
    private ProductBookmarkRepostiory productBookmarkRepostiory;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public void fetchAndSaveFinancialProducts() {
        String encodedApiKey = URLEncoder.encode(apiKey, StandardCharsets.UTF_8);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("serviceKey", encodedApiKey)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "100")
                .queryParam("resultType", "json");

        String urlTemplate = builder.build(true).toUriString();

        try {
            String response = restTemplate.getForObject(urlTemplate, String.class);
            FinancialProductsResponse productsResponse = objectMapper.readValue(response, FinancialProductsResponse.class);

            if (productsResponse != null && productsResponse.getGetProductList() != null) {
                productsResponse.getGetProductList().getItem().forEach(item -> {
                    FinancialProduct financialProduct = FinancialProduct.builder()
                            .fncIstNm(item.getFncIstNm())
                            .prdNm(item.getPrdNm())
                            .prdSalDscnDt(item.getPrdSalDscnDt())
                            .regDate(item.getRegDate())
                            .viewCount(0) // 기본값 설정 가능
                            .bookmarkCount(0) // 기본값 설정 가능
                            .homepageLink("") // 필요하다면 기본값 설정
                            .build();
                    financialProductRepository.save(financialProduct);
                });
            } else {
                System.out.println("No products found or error in response format.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching financial products: " + e.getMessage());
        }
    }

    public List<FinancialProduct> searchFinancialProducts(String keyword) {
        String redisKey = "search:" + keyword;

        // Redis에서 검색 결과 조회
        String cachedProducts = stringRedisTemplate.opsForValue().get(redisKey);

        if (cachedProducts != null) {
            try {
                return objectMapper.readValue(cachedProducts, new TypeReference<List<FinancialProduct>>() {});
            } catch (Exception e) {
                throw new RuntimeException("Error parsing cached financial products", e);
            }
        } else {
            List<FinancialProduct> products = financialProductRepository.findByFncIstNmOrPrdNmContaining(keyword);

            if (!products.isEmpty()) {
                try {
                    // 검색 결과를 Redis에 캐싱
                    stringRedisTemplate.opsForValue().set(redisKey, objectMapper.writeValueAsString(products), 60, TimeUnit.MINUTES);
                } catch (Exception e) {
                    throw new RuntimeException("Error caching financial products", e);
                }
            }

            return products;
        }
    }

    // 특정 ID를 가진 금융상품 찾기
    public Optional<FinancialProduct> findById(Long id) {
        return financialProductRepository.findById(id);
    }

    // 북마크 토글 기능: 사용자가 특정 금융상품에 대해 북마크를 추가하거나 제거 (북마크수 증감 기능)
    public void toggleBookmarkByUsername(String username, Long productId) {
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        FinancialProduct product = financialProductRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        Optional<ProductBookmark> existingBookmark = productBookmarkRepostiory.findByUserAndFinancialProduct(user, product);

        if (existingBookmark.isPresent()) {
            // 북마크가 이미 존재하면 제거하고, 북마크 수 감소
            productBookmarkRepostiory.delete(existingBookmark.get());
            product.setBookmarkCount(product.getBookmarkCount() - 1); // 북마크 수 감소
        } else {
            // 북마크가 없으면 추가하고, 북마크 수 증가
            ProductBookmark newBookmark = new ProductBookmark(user, product);
            productBookmarkRepostiory.save(newBookmark);
            product.setBookmarkCount(product.getBookmarkCount() + 1); // 북마크 수 증가
        }
        financialProductRepository.save(product); // 업데이트된 북마크 수를 저장
    }


    // 사용자별 북마크한 금융상품 목록 조회
    public List<FinancialProduct> getBookmarkedFinancialProductsByUsername(String username) {
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        return productBookmarkRepostiory.findByUsername(username).stream()
                .map(ProductBookmark::getFinancialProduct)
                .collect(Collectors.toList());
    }

    // 특정 금융상품 북마크 수 조회
    public long getBookmarkCountForProduct(Long productId) {
        return productBookmarkRepostiory.countByFinancialProductId(productId);
    }

    // 조회수 증가 메소드
    public void incrementViewCount(Long productId) {
        FinancialProduct product = financialProductRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
        product.setViewCount(product.getViewCount() + 1);
        financialProductRepository.save(product);
    }

}
