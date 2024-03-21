package com.example.demo.repository;

import com.example.demo.model.TodayQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayQuizRepository extends JpaRepository<TodayQuiz, Long> {
    // 필요한 메소드들을 여기에 정의할 수 있습니다.
}
