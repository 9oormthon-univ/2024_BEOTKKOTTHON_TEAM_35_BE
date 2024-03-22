package com.example.demo.controller;

import com.example.demo.model.Quiz;
import com.example.demo.service.QuizService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuizController {

    private final QuizService quizService;

    @Value("${jwt.secret}")
    private String jwtSecret; // JWT Secret 키를 application.properties에서 주입

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quizzes/{id}") // ID로 퀴즈 조회
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // SecurityContext에서 사용자 이름(여기서는 사용자 ID로 가정) 추출

        // 퀴즈 답변 업데이트 시 사용자 정보도 함께 업데이트
        return quizService.updateAnswerStatus(id, answer, Long.parseLong(username)) // username을 Long 타입으로 변환하여 메서드 호출
                .map(ResponseEntity::ok)
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
