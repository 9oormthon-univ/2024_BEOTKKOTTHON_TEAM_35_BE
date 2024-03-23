package com.example.demo.vo.request.user;

import lombok.Data;

@Data
public class RequestUser {
    private Long userId;
    private String email;
    private String password;
    private Long point;
    private String nickname;
    private Boolean flagNotification;
    private String oauthProvider;
}
