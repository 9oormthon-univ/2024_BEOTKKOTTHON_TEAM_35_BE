package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.FinancialProduct;
import com.example.demo.model.ProductBookmark;
import com.example.demo.users.entity.User;

import java.util.List;
import java.util.Optional;

public interface ProductBookmarkRepostiory extends JpaRepository<ProductBookmark, Long> {
    Optional<ProductBookmark> findByUserAndFinancialProduct(User user, FinancialProduct financialProduct);

    // username을 사용하여 사용자의 모든 북마크 조회
    @Query("SELECT ub FROM ProductBookmark ub WHERE ub.user.nickname = :username")
    List<ProductBookmark> findByUsername(@Param("username") String username);

    // username과 금융 상품 ID를 사용하여 특정 북마크 조회
    @Query("SELECT ub FROM ProductBookmark ub WHERE ub.user.nickname = :username AND ub.financialProduct.id = :productId")
    Optional<ProductBookmark> findByUsernameAndFinancialProductId(@Param("username") String username, @Param("productId") Long productId);

    // 특정 금융상품에 대한 북마크 수를 카운트
    @Query("SELECT COUNT(ub) FROM ProductBookmark ub WHERE ub.financialProduct.id = :productId")
    long countByFinancialProductId(@Param("productId") Long productId);
}
