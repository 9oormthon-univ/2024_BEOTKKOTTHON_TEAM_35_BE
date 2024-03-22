package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class YouthCenterService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "https://www.youthcenter.go.kr/opi/youthPlcyList.do";

    @Value("${openapi.key}")
    private String apiKey;

    public String fetchPolicies(Integer display, Integer pageIndex, String query, String bizTycdSel, String srchPolyBizSecd, String keyword) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("openApiVlak", apiKey)
                .queryParam("display", display != null ? display : 10)
                .queryParam("pageIndex", pageIndex != null ? pageIndex : 1)
                .queryParam("query", query)
                .queryParam("bizTycdSel", bizTycdSel)
                .queryParam("srchPolyBizSecd", srchPolyBizSecd)
                .queryParam("keyword", keyword);

        return restTemplate.getForObject(builder.toUriString(), String.class);
    }

    public String fetchPolicyDetails(String srchPolicyId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("openApiVlak", apiKey)
                .queryParam("srchPolicyId", srchPolicyId);

        return restTemplate.getForObject(builder.toUriString(), String.class);
    }
}
