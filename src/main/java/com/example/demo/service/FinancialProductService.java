package com.example.demo.service;

import com.example.demo.dto.FinancialProductsResponse;
import com.example.demo.model.FinancialProduct;
import com.example.demo.repository.FinancialProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class FinancialProductService {

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private FinancialProductRepository financialProductRepository;

    public void fetchAndSaveFinancialProducts() {
        // 인코딩된 API 키
        String encodedApiKey = URLEncoder.encode(apiKey, StandardCharsets.UTF_8);
        // "청년"을 인코딩
//        String encodedProductName = URLEncoder.encode("청년", StandardCharsets.UTF_8);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("serviceKey", encodedApiKey) // 인코딩된 키 사용
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "2")
                .queryParam("resultType", "json")
                .queryParam("prdNm", "KB");

        // .build(true)를 호출하여 이미 인코딩된 상태임을 명시
        String urlTemplate = builder.build(true).toUriString();

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

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
                // 로그 남기기 또는 사용자 정의 예외 처리
                System.out.println("No products found or error in response format.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 로그 남기기 또는 사용자 정의 예외 처리
            System.out.println("Error fetching financial products: " + e.getMessage());
            // 필요에 따라 사용자 정의 예외를 던지거나 다른 방식으로 처리
        }
    }
    public List<FinancialProduct> searchFinancialProducts(String keyword) {
        return financialProductRepository.findByFncIstNmOrPrdNmContaining(keyword);
    }
}
