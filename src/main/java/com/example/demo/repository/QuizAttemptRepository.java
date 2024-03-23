package com.example.demo.repository;

import com.example.demo.model.QuizAttempt;
import com.example.demo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByUserAndCorrect(User user, boolean correct);
}