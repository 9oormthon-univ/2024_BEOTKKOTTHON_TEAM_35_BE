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

    public TodayQuizService(TodayQuizRepository todayQuizRepository) {
        this.todayQuizRepository = todayQuizRepository;
    }

    public Optional<TodayQuiz> getTodayQuizById(Long id) {
        return todayQuizRepository.findById(id);
    }

}
