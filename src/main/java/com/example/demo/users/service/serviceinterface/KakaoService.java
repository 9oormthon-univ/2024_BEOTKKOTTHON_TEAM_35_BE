package com.example.demo.users.service.serviceinterface;

import com.example.demo.vo.response.user.ResponseKakaoUser;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface KakaoService {
    String getAccessToken(String code);

    JsonNode getUserProfile(String accessToken) throws IOException;

    ResponseEntity<String> logout(String accessToken);

    JsonNode getUserFromCode(String code) throws Exception;

    ResponseKakaoUser toResponse(JsonNode userProfile);

    ResponseKakaoUser setToken(ResponseKakaoUser responseKakaoUser);
}
