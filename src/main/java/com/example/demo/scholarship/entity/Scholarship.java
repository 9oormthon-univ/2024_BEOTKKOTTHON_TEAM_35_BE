package com.example.demo.scholarship.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "scholarship")
@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scholarship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scholarship_id", nullable = false, unique = true)
    private Long scholarshipId;
    @Column(name = "organizer_name", nullable = false)
    private String organizerName;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "region", nullable = false)
    private String region;
    @Column(name = "start_date", nullable = false)
    private String startDate;
    @Column(name = "end_date", nullable = false)
    private String endDate;
    @Column(name = "selection_quota", nullable = false)
    private String selectionQuota;
    @Column(name = "website_url", nullable = false)
    private String websiteUrl;
    @Column(name = "detial1", nullable = false)
    private String detial1;
    @Column(name = "detial2", nullable = false)
    private String detial2;
}
