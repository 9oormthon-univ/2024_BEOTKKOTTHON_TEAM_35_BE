package com.example.demo.repository;

import com.example.demo.model.FinancialProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialProductRepository extends JpaRepository<FinancialProduct, Long> {
    // 필요한 쿼리 메소드를 여기에 정의
}
