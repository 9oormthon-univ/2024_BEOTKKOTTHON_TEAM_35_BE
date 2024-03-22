package com.example.demo.model;

import com.example.demo.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String category; // 예: 금융 개념, 금융 상품 등
}
