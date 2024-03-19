package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class YouthCenterService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "https://www.youthcenter.go.kr/opi/wantedSpace.do";

    @Value("${openapi.key}")
    private String apiKey;

    public String fetchSpaces(Integer display, Integer pageIndex, String srchSpnm, String srchAreaCpvn) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("openApiVlak", apiKey)
                .queryParam("display", display != null ? display : 10)
                .queryParam("pageIndex", pageIndex != null ? pageIndex : 1)
                .queryParam("pageType", 1)
                .queryParam("srchSpnm", srchSpnm)
                .queryParam("srchAreaCpvn", convertAreaNameToCode(srchAreaCpvn));

        String response = restTemplate.getForObject(builder.toUriString(), String.class);
        return response;
    }

    private String convertAreaNameToCode(String areaName) {
        if (areaName == null) {
            return null; // 또는 적절한 기본값
        }
        switch (areaName) {
            case "서울": return "003002001";
            case "부산": return "003002002";
            case "대구": return "003002003";
            case "인천": return "003002004";
            case "광주": return "003002005";
            case "대전": return "003002006";
            case "울산": return "003002007";
            case "경기": return "003002008";
            case "강원": return "003002009";
            case "충북": return "003002010";
            case "충남": return "003002011";
            case "전북": return "003002012";
            case "전남": return "003002013";
            case "경북": return "003002014";
            case "경남": return "003002015";
            case "제주": return "003002016";
            case "세종": return "003002017";
            default: return null;
        }
    }

}
