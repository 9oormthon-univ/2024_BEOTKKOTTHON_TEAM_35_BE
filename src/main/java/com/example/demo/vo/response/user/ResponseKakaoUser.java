package com.example.demo.vo.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseKakaoUser {
    private String nickname;
    private String profile;
    private String jwtToken;
    private String oauthProvider = "Kakao";
}

