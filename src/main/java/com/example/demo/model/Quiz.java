package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor  // Lombok이 기본 생성자를 생성하도록 합니다.
@AllArgsConstructor // Lombok이 모든 필드를 포함하는 생성자를 생성하도록 합니다.
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String category; // 금융 개념, 금융 상품, 금융 지표, 금융 기관
    private String answerStatus; // 답변 상태: NULL (미응답), CORRECT (정답), WRONG (오답)

    // Lombok이 제공하는 어노테이션을 사용하여 추가적인 생성자나 getter/setter를 명시적으로 작성할 필요가 없습니다.
}
