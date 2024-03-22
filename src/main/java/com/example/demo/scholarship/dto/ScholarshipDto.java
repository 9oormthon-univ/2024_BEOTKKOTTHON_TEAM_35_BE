package com.example.demo.scholarship.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "장학금 정보")
public class ScholarshipDto {
    private Long scholarshipId;
    private String organizerName;
    private String productName;
    private String region;
    private String startDate;
    private String endDate;
    private String selectionQuota;
    private String websiteUrl;
    private String detial1;
    private String detial2;
}
