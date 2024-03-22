package com.example.demo.scholarship.controller;

import com.example.demo.scholarship.dto.ScholarshipDto;
import com.example.demo.scholarship.entity.Scholarship;
import com.example.demo.scholarship.service.serviceinterface.ScholarshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="장학금")
@RestController
@RequestMapping("/scholarship")
@Slf4j
@RequiredArgsConstructor
public class ScholarshipController {

    private final ScholarshipService scholarshipService;
    @Operation(summary = "장학금 검색")
    @GetMapping("/search")
    public List<Scholarship> searchScholarship(@RequestParam("keyword") String keyword) {
        return scholarshipService.searchScholarship(keyword);
    }
    @Operation(summary = "장학금 지역별 검색")
    @GetMapping("/region")
    public List<Scholarship> searchScholarshipByRegion(@RequestParam("region") String region) {
        return scholarshipService.searchScholarshipByRegion(region);
    }
    @Operation(summary = "장학금 정보 조회")
    @GetMapping("/{scholarshipId}")
    public ResponseEntity<ScholarshipDto> getScholarship(@PathVariable Long scholarshipId){
        final ScholarshipDto data = scholarshipService.getScholarship(scholarshipId);
        return ResponseEntity.ok(data);
    }
}
