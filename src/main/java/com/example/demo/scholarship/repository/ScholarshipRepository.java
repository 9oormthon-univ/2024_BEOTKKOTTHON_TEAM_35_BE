package com.example.demo.scholarship.repository;

import com.example.demo.scholarship.entity.Scholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
    @Query("SELECT s FROM Scholarship s WHERE s.productName LIKE %:keyword% OR s.organizerName LIKE %:keyword%")
    List<Scholarship> findByKeyword(@Param("keyword") String keyword);
    @Query("SELECT s FROM Scholarship s WHERE s.region = :region")
    List<Scholarship> findByRegion(@Param("region") String region);

}
