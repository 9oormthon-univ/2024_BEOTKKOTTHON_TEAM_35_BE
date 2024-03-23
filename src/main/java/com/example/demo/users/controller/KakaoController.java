package com.example.demo.users.controller;

import com.example.demo.users.service.serviceimpl.KakaoServiceImpl;
import com.example.demo.users.service.serviceinterface.KakaoService;
import com.example.demo.vo.response.user.ResponseKakaoUser;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Tag(name="카카오")
@RestController
@RequestMapping("/kakao")
@Slf4j
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;

    @Autowired
    public KakaoController(KakaoServiceImpl kakaoService) {
        this.kakaoService = kakaoService;
    }

    @Operation(summary = "카카오 로그인")
    @PostMapping("/login")
    public ResponseEntity<ResponseKakaoUser> kakaoCallback(@RequestParam String code) throws Exception {
        JsonNode userProfile = kakaoService.getUserFromCode(code);
        ResponseKakaoUser responseKakaoUser = kakaoService.setToken(kakaoService.toResponse(userProfile));

        return ResponseEntity.ok().body(responseKakaoUser);
    }

    @Operation(summary = "카카오 유저 정보 조회")
    @GetMapping("/user")
    public String kakaoGetUser(@RequestParam String code) throws Exception {
        JsonNode userProfile = kakaoService.getUserFromCode(code);

        return userProfile.toString();
    }
    @Operation(summary = "카카오 유저 토큰 조회")
    @GetMapping("/oauth/callback")
    public ResponseEntity<String> getKakaoAccessToken(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessToken(code);
        return ResponseEntity.ok(accessToken);
    }

    @Operation(summary = "카카오 로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String accessToken) throws Exception {
        return kakaoService.logout(accessToken);
    }
}