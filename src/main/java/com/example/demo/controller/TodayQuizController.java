package com.example.demo.controller;

import com.example.demo.model.TodayQuiz;
import com.example.demo.service.TodayQuizService;
import com.example.demo.users.entity.User;
import com.example.demo.users.repository.UserRepository;
import com.example.demo.users.service.serviceinterface.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodayQuizController {

    private final TodayQuizService todayQuizService;
    private final UserService userService; // UserService 인터페이스 참조 추가
    private final UserRepository userRepository; // UserRepository 참조 추가

    public TodayQuizController(TodayQuizService todayQuizService, UserService userService, UserRepository userRepository) {
        this.todayQuizService = todayQuizService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/today-quizzes/{id}")
    public ResponseEntity<TodayQuiz> getTodayQuizById(@PathVariable Long id) {
        return todayQuizService.getTodayQuizById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/today-quizzes/{id}/solve")
    public ResponseEntity<String> solveTodayQuiz(@PathVariable Long id, @RequestBody String answer) {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.badRequest().body("인증되지 않은 사용자입니다.");
        }

        // UserRepository를 사용하여 현재 사용자의 엔티티 가져오기
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        boolean isCorrect = todayQuizService.checkTodayQuizAnswer(id, answer, username);

        if (isCorrect) {
            user.updatePoint(user.getPoint() + 1); // 포인트 1 증가
            userRepository.save(user); // UserRepository를 통해 사용자 정보 업데이트
            return ResponseEntity.ok("정답입니다! 포인트가 업데이트되었습니다.");
        } else {
            return ResponseEntity.ok("오답입니다. 다시 시도해보세요.");
        }
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }
}
