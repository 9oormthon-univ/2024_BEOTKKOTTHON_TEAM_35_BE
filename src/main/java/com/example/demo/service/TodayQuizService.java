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

    public Optional<TodayQuiz> updateAnswerAndUserScore(Long quizId, int userResponse, Long userId) {
        Optional<TodayQuiz> quizOptional = todayQuizRepository.findById(quizId);
        Optional<User> userOptional = userRepository.findById(userId);

        quizOptional.ifPresent(quiz -> {
            if (userResponse == 1) {
                quiz.setAnswerStatus("CORRECT");
            } else if (userResponse == 0) {
                quiz.setAnswerStatus("WRONG");
            }
            userOptional.ifPresent(user -> {
                quiz.setUser(user);
                user.getTodayQuizzes().add(quiz);
                userRepository.save(user);
            });
            todayQuizRepository.save(quiz);
        });
        return quizOptional;
    }
}
