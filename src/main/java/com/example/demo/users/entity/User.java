package com.example.demo.users.entity;

import com.example.demo.model.ProductBookmark;
import com.example.demo.model.Quiz; // Quiz 모델 import
import com.example.demo.model.TodayQuiz;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Getter
@AllArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "point", nullable = false)
    private Long point;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "oauth_provider")
    private String oauthProvider;

    @Column(name = "flag_notification", nullable = false)
    private Boolean flagNotification;

    // User와 ProductBookmark 사이의 OneToMany 관계를 정의
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductBookmark> productBookmarks = new ArrayList<>();

    // 기존 메서드들
    public void updateEmail(String email) {this.email = email;}
    public void updateNickname(String nickname) {this.nickname = nickname;}
    public void updatePassword(String password) {this.password = password;}
    public void updatePoint(Long point) {this.point = point;}
    public void updateflagNotification(Boolean flagNotification) {this.flagNotification = flagNotification;}
}
