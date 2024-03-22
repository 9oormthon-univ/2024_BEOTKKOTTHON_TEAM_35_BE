package com.example.demo.users.entity;

import com.example.demo.model.Quiz; // Quiz 모델 import
import com.example.demo.model.TodayQuiz;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "password1", nullable = false)
    private String password1;

    @Column(name = "password2", nullable = false)
    private String password2;

    @Column(name = "point", nullable = false)
    private Long point;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "flag_notification", nullable = false)
    private Boolean flagNotification;

    // User와 Quiz 사이의 OneToMany 관계를 정의하는 새로운 필드
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz> quizzes;

    // User와 TodayQuiz 사이의 OneToMany 관계를 정의하는 새로운 필드
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodayQuiz> todayQuizzes;

    // 기존 메서드들
    public void updateEmail(String email) {this.email = email;}
    public void updateNickname(String nickname) {this.nickname = nickname;}
    public void updatePassword1(String password1) {this.password1 = password1;}
    public void updatePassword2(String password2) {this.password2 = password2;}
    public void updatePoint(Long point) {this.point = point;}
    public void updateflagNotification(Boolean flagNotification) {this.flagNotification = flagNotification;}
}
