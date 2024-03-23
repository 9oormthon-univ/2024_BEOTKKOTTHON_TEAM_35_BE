package com.example.demo.model;

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
    private String category;

    // 난이도(stage) 컬럼 추가
    private String stage; // 이 필드의 데이터 타입은 예시로 String을 사용했지만, 필요에 따라 다른 타입으로 변경할 수 있습니다.
}
