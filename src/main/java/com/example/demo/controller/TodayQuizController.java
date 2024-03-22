package com.example.demo.controller;

import com.example.demo.model.TodayQuiz;
import com.example.demo.service.TodayQuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodayQuizController {

    private final TodayQuizService todayQuizService;

    public TodayQuizController(TodayQuizService todayQuizService) {
        this.todayQuizService = todayQuizService;
    }

    @GetMapping("/today-quizzes/{id}")
    public ResponseEntity<TodayQuiz> getTodayQuizById(@PathVariable Long id) {
        return todayQuizService.getTodayQuizById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/today-quizzes/{id}/answer")
    public ResponseEntity<TodayQuiz> submitTodayQuizAnswer(@PathVariable Long id, @RequestParam int answer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName()); // 이 예제에서는 사용자 이름이 사용자 ID로 사용됩니다.

        return todayQuizService.updateAnswerAndUserScore(id, answer, userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
