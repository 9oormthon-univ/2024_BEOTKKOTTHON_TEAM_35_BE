package com.example.demo.model;

import com.example.demo.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodayQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;

}
