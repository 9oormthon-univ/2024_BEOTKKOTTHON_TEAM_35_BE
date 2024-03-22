package com.example.demo.scholarship.service.serviceinterface;

import com.example.demo.scholarship.entity.Scholarship;

import java.util.List;

public interface ScholarshipService {
    List<Scholarship> searchScholarship(String keyword);
}
