package com.example.demo.service;

import com.example.demo.model.TodayQuiz;
import com.example.demo.repository.TodayQuizRepository;
import com.example.demo.users.entity.User;
import com.example.demo.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TodayQuizService {

    private final TodayQuizRepository todayQuizRepository;
    private final UserRepository userRepository;

    public TodayQuizService(TodayQuizRepository todayQuizRepository, UserRepository userRepository) {
        this.todayQuizRepository = todayQuizRepository;
        this.userRepository = userRepository;
    }

    public Optional<TodayQuiz> getTodayQuizById(Long id) {
        return todayQuizRepository.findById(id);
    }

    // 정답 확인 및 포인트 업데이트 메서드
    public boolean checkTodayQuizAnswer(Long quizId, String answer, String username) {
        Optional<TodayQuiz> quizOpt = todayQuizRepository.findById(quizId);
        if (!quizOpt.isPresent()) {
            return false; // 퀴즈가 존재하지 않음
        }
        TodayQuiz quiz = quizOpt.get();
        boolean isCorrect = quiz.getCorrectAnswer().equals(answer);
        if (isCorrect) {
            Optional<User> userOpt = userRepository.findByNickname(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.updatePoint(user.getPoint() + 1); // 포인트 1 증가
                userRepository.save(user);
            }
        }
        return isCorrect;
    }

}
