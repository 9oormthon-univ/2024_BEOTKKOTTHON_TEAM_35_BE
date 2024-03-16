package com.example.demo.repository;

import com.example.demo.model.FinancialProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FinancialProductRepository extends JpaRepository<FinancialProduct, Long> {
    @Query("SELECT fp FROM FinancialProduct fp WHERE fp.fncIstNm LIKE %:keyword% OR fp.prdNm LIKE %:keyword%")
    List<FinancialProduct> findByFncIstNmOrPrdNmContaining(@Param("keyword") String keyword);
}

