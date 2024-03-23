package com.example.demo.users.service.serviceimpl;

import com.example.demo.component.JwtTokenProvider;
import com.example.demo.users.service.serviceinterface.KakaoService;
import com.example.demo.vo.response.user.ResponseKakaoUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

@Service
@Transactional
public class KakaoServiceImpl implements KakaoService {

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.redirect.uri}")
    private String redirectUri;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public KakaoServiceImpl(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                String.class
        );

        // 여기에서 JSON 응답을 구문 분석하여 액세스 토큰을 추출하십시오.
        // 예를 들어, Jackson 라이브러리를 사용하여 구문 분석할 수 있습니다.
        // 이 예에서는 응답 문자열을 반환합니다.
        return response.getBody();
    }

    public JsonNode getUserProfile(String accessToken) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                String.class
        );

        // 응답 JSON 문자열을 JsonNode 객체로 변환합니다.
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(response.getBody());
    }

    public JsonNode getUserFromCode(String code) throws Exception{
        RestTemplate restTemplate = new RestTemplate();

        // 액세스 토큰 요청
        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id="
                        + clientId + "&redirect_uri="
                        + redirectUri + "&code=" + code,
                null,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode accessTokenNode = objectMapper.readTree(response.getBody());

        // 사용자 프로필 정보 요청
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessTokenNode.get("access_token").asText());

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> userInfoResponse = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                String.class
        );

        return objectMapper.readTree(userInfoResponse.getBody());
    }

    public ResponseEntity<String> logout(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "https://kapi.kakao.com/v1/user/logout",
                HttpMethod.POST,
                request,
                String.class
        );
    }


    public ResponseKakaoUser toResponse(JsonNode userProfile){
        JsonNode kakaoAccountNode = userProfile.get("kakao_account");
        JsonNode profileNode = kakaoAccountNode.get("profile");

        String profileNickname = profileNode.get("nickname").asText();
        String profileEmail = kakaoAccountNode.get("email").asText();

        ResponseKakaoUser responseKakaoUser = new ResponseKakaoUser();
        responseKakaoUser.setNickname(profileNickname);
        responseKakaoUser.setEmail(profileEmail);

        return responseKakaoUser;
    }

    public ResponseKakaoUser setToken(ResponseKakaoUser responseKakaoUser){
        String jwtToken = jwtTokenProvider.generateToken(responseKakaoUser.getNickname());
        responseKakaoUser.setJwtToken(jwtToken);

        return responseKakaoUser;
    }
}