package com.example.demo.service;

import com.example.demo.model.Quiz;
import com.example.demo.model.QuizAttempt;
import com.example.demo.repository.QuizAttemptRepository;
import com.example.demo.repository.QuizRepository;
import com.example.demo.users.repository.UserRepository;
import com.example.demo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    public QuizService(QuizRepository quizRepository, UserRepository userRepository, QuizAttemptRepository quizAttemptRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.quizAttemptRepository = quizAttemptRepository;
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public List<Quiz> getQuizzesByCategory(String category) {
        return quizRepository.findByCategory(category);
    }

    public boolean checkQuizAnswer(Long quizId, String answer, String username) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID: " + quizId));
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username: " + username));

        boolean isCorrect = quiz.getCorrectAnswer().equals(answer);
        QuizAttempt attempt = new QuizAttempt(null, user, quiz, answer, isCorrect);
        quizAttemptRepository.save(attempt);

        return isCorrect;
    }

    public List<Quiz> getIncorrectQuizzesByUsername(String username) {
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username: " + username));
        List<QuizAttempt> incorrectAttempts = quizAttemptRepository.findByUserAndCorrect(user, false);

        return incorrectAttempts.stream()
                .map(QuizAttempt::getQuiz)
                .distinct()
                .collect(Collectors.toList());
    }
}
