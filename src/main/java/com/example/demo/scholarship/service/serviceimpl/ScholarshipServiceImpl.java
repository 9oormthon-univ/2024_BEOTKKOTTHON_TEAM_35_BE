package com.example.demo.scholarship.service.serviceimpl;

import com.example.demo.scholarship.entity.Scholarship;
import com.example.demo.scholarship.repository.ScholarshipRepository;
import com.example.demo.scholarship.service.serviceinterface.ScholarshipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ScholarshipServiceImpl implements ScholarshipService {
    private final ScholarshipRepository scholarshipRepository;
    public List<Scholarship> searchScholarship(String keyword) {
        return scholarshipRepository.findByKeyword(keyword);
    }
}
