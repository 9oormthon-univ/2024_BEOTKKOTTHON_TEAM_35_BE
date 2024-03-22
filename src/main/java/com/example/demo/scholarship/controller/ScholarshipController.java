package com.example.demo.scholarship.controller;

import com.example.demo.scholarship.entity.Scholarship;
import com.example.demo.scholarship.service.serviceinterface.ScholarshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
