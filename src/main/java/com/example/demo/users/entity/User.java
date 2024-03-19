package com.example.demo.users.entity;

import com.example.demo.model.Quiz;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "oauth_id", nullable = false)
    private String oauthId;

    @Column(name = "oauth_provider", nullable = false)
    private String oauthProvider;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password1", nullable = false)
    private String password1;

    @Column(name = "password2", nullable = false)
    private String password2;

    @Column(name = "point", nullable = false)
    private Long point;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "flag_notification", nullable = false)
    private Boolean flagNotification;

    @Column(name = "device_token", nullable = false)
    private String deviceToken;

    // User와 Quiz 사이의 OneToMany 관계를 정의
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz> quizzes = new ArrayList<>();

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
        quiz.setUser(this);
    }

    public void removeQuiz(Quiz quiz) {
        quizzes.remove(quiz);
        quiz.setUser(null);
    }
}
