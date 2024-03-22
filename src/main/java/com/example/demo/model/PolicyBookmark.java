package com.example.demo.model;

import com.example.demo.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true) // Builder 패턴 사용 설정
@AllArgsConstructor(access = AccessLevel.PACKAGE) // 모든 필드를 포함한 생성자는 패키지 전용으로 생성
public class PolicyBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id")
    private YouthPolicy youthPolicy;

    // Lombok을 사용하지 않고 직접 생성자를 추가
    public PolicyBookmark(User user, YouthPolicy youthPolicy) {
        this.user = user;
        this.youthPolicy = youthPolicy;
    }
}
