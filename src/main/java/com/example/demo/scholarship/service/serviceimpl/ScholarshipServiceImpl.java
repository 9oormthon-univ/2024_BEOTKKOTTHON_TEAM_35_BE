package com.example.demo.scholarship.service.serviceimpl;

import com.example.demo.error.ErrorCode;
import com.example.demo.scholarship.dto.ScholarshipDto;
import com.example.demo.scholarship.entity.Scholarship;
import com.example.demo.scholarship.exception.ScholarshipNotExist;
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
    @Transactional(readOnly = true)
    public List<Scholarship> searchScholarship(String keyword) {
        return scholarshipRepository.findByKeyword(keyword);
    }
    @Transactional(readOnly = true)
    public List<Scholarship> searchScholarshipByRegion(String region) {
        return scholarshipRepository.findByRegion(region);
    }
    @Transactional(readOnly = true)
    public ScholarshipDto getScholarship(Long scholarshipId){
        final Scholarship scholarship= scholarshipRepository.findById(scholarshipId)
                .orElseThrow(()-> new ScholarshipNotExist("scholarship not exist", ErrorCode.SCHOLARSHIP_NOT_EXIST));

        return ScholarshipDto.builder()
                .scholarshipId(scholarshipId)
                .startDate(scholarship.getStartDate())
                .endDate(scholarship.getEndDate())
                .organizerName(scholarship.getOrganizerName())
                .productName(scholarship.getProductName())
                .detial1(scholarship.getDetial1())
                .detial2(scholarship.getDetial2())
                .region(scholarship.getRegion())
                .build();
    }
}
