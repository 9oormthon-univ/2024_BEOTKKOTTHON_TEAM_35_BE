package com.example.demo.service;

import com.example.demo.model.Quiz;
import com.example.demo.repository.QuizRepository;
import com.example.demo.users.entity.User;
import com.example.demo.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository; // UserRepository 주입

    public QuizService(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public List<Quiz> getQuizzesByCategory(String category) {
        return quizRepository.findByCategory(category);
    }

    // 사용자의 답변을 기반으로 답변 상태와 사용자 정보를 업데이트하는 메소드
    public Optional<Quiz> updateAnswerStatus(Long quizId, int userResponse, Long userId) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        Optional<User> userOptional = userRepository.findById(userId);

        quizOptional.ifPresent(quiz -> {
            if (userResponse == 1) {
                quiz.setAnswerStatus("CORRECT");
            } else if (userResponse == 0) {
                quiz.setAnswerStatus("WRONG");
            }
            userOptional.ifPresent(quiz::setUser); // 사용자 정보를 퀴즈와 연결
            quizRepository.save(quiz);
        });

        return quizOptional;
    }

    // AnswerStatus에 따라 문제 필터링
    public List<Quiz> getQuizzesByAnswerStatus(String answerStatus) {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream()
                .filter(quiz -> answerStatus == null ? quiz.getAnswerStatus() == null : answerStatus.equals(quiz.getAnswerStatus()))
                .collect(Collectors.toList());
    }

}
