package com.example.demo.service;

import com.example.demo.dto.FinancialProductsResponse;
import com.example.demo.model.FinancialProduct;
import com.example.demo.repository.FinancialProductRepository;
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
import java.util.concurrent.TimeUnit;

@Service
public class FinancialProductService {

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private FinancialProductRepository financialProductRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void fetchAndSaveFinancialProducts() {
        String encodedApiKey = URLEncoder.encode(apiKey, StandardCharsets.UTF_8);
        String encodedPrdNm = URLEncoder.encode("교보", StandardCharsets.UTF_8);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("serviceKey", encodedApiKey)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "10")
                .queryParam("resultType", "json")
                .queryParam("prdNm", encodedPrdNm);

        String urlTemplate = builder.build(true).toUriString();

        RestTemplate restTemplate = new RestTemplate();

        try {
            String response = restTemplate.getForObject(urlTemplate, String.class);
            FinancialProductsResponse productsResponse = objectMapper.readValue(response, FinancialProductsResponse.class);

            if (productsResponse != null && productsResponse.getGetProductList() != null && productsResponse.getGetProductList().getItem() != null) {
                List<FinancialProductsResponse.ProductList.Item> items = productsResponse.getGetProductList().getItem();
                items.forEach(item -> {
                    FinancialProduct financialProduct = new FinancialProduct(item.getFncIstNm(), item.getPrdNm(), item.getPrdSalDscnDt(), item.getRegDate());
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
                    stringRedisTemplate.opsForValue().set(redisKey, objectMapper.writeValueAsString(products), 60, TimeUnit.MINUTES); // 60분 동안 캐시 유지
                } catch (Exception e) {
                    throw new RuntimeException("Error caching financial products", e);
                }
            }

            return products;
        }
    }
}
