package com.example.demo.scholarship.service.serviceinterface;

import com.example.demo.scholarship.dto.ScholarshipDto;
import com.example.demo.scholarship.entity.Scholarship;

import java.util.List;

public interface ScholarshipService {
    List<Scholarship> searchScholarship(String keyword);
    List<Scholarship> searchScholarshipByRegion(String region);
    ScholarshipDto getScholarship(Long scholarshipId);
}
