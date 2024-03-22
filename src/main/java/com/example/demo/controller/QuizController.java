package com.example.demo.controller;

import com.example.demo.model.Quiz;
import com.example.demo.service.QuizService;
import com.example.demo.users.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    // 퀴즈 풀기
    @PostMapping("/{quizId}/solve")
    public ResponseEntity<String> solveQuiz(@PathVariable Long quizId, @RequestBody String answer) {
        String username = getCurrentUsername();
        boolean isCorrect = quizService.checkQuizAnswer(quizId, answer, username);
        return ResponseEntity.ok(isCorrect ? "정답입니다!" : "틀렸습니다. 다시 시도해 보세요.");
    }

    // 틀린 퀴즈 다시 보기
    @GetMapping("/review")
    public ResponseEntity<List<Quiz>> reviewIncorrectQuizzes() {
        String username = getCurrentUsername();
        List<Quiz> incorrectQuizzes = quizService.getIncorrectQuizzesByUsername(username);
        return ResponseEntity.ok(incorrectQuizzes);
    }

    // Username 가져오는 메소드
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null; // 또는 적절한 예외 처리
    }

}
