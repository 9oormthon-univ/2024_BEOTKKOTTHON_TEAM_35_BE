package com.example.demo.service;

import com.example.demo.model.Quiz;
import com.example.demo.repository.QuizRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public List<Quiz> getQuizzesByCategory(String category) {
        return quizRepository.findByCategory(category);
    }

    // 사용자의 답변을 기반으로 답변 상태를 업데이트하는 메소드
    public Optional<Quiz> updateAnswerStatus(Long quizId, int userResponse) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        quizOptional.ifPresent(quiz -> {
            if (userResponse == 1) {
                quiz.setAnswerStatus("CORRECT");
            } else if (userResponse == 0) {
                quiz.setAnswerStatus("WRONG");
            }
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