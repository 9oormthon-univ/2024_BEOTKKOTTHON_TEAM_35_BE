package com.example.demo.service;

import com.example.demo.dto.FinancialProductsResponse;
import com.example.demo.model.FinancialProduct;
import com.example.demo.repository.FinancialProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class FinancialProductService {

    private String apiUrl = "http://apis.data.go.kr/B190017/service/GetInsuredProductService202008/getProductList202008";

    private String apiKey = "UoPPz0gcgUkxIQpfIB1qcfifH27WFRiEYG3Ov+M4dQ1CDzJ4660agnXtojbsBvFlsS2+RS1uo2c0lIz+Iao/Mw==";

    @Autowired
    private FinancialProductRepository financialProductRepository;

    public void fetchAndSaveFinancialProducts() {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("serviceKey", apiKey)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "1")
                .queryParam("resultType", "json")
                .queryParam("prdNm", "청년")
                .toUriString();

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
}
