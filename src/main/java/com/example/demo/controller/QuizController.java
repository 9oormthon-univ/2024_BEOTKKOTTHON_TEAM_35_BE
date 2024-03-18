package com.example.demo.controller;

import com.example.demo.model.Quiz;
import com.example.demo.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quizzes/{id}") // id 퀴즈 조회
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/quizzes") // 카테고리별 퀴즈 조회
    public ResponseEntity<List<Quiz>> getQuizzesByCategory(@RequestParam String category) {
        List<Quiz> quizzes = quizService.getQuizzesByCategory(category);
        if (quizzes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    // 사용자의 답변을 제출받아 처리하는 엔드포인트 (0을 보내면 오답, 1을 보내면 정답 처리)
    @PutMapping("/quizzes/{id}/answer")
    public ResponseEntity<Quiz> submitAnswer(@PathVariable Long id, @RequestParam int answer) {
        return quizService.updateAnswerStatus(id, answer)
                .map(updatedQuiz -> ResponseEntity.ok(updatedQuiz))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // AnswerStatus에 따라 문제 필터링
    @GetMapping("/quizzes/filter")
    public ResponseEntity<List<Quiz>> getQuizzesByAnswerStatus(@RequestParam(required = false) String answerStatus) {
        List<Quiz> filteredQuizzes = quizService.getQuizzesByAnswerStatus(answerStatus);
        if (filteredQuizzes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(filteredQuizzes);
    }
}