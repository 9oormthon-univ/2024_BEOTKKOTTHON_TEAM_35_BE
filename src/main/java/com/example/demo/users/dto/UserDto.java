package com.example.demo.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "계정 정보")
public class UserDto {
    private Long userId;
    private String email;
    private String password;
    private Long point;
    private String nickname;
    private Boolean flagNotification;
    private String oauthProvider;
}
