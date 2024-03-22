package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.FinancialProduct;
import com.example.demo.model.UserBookmark;
import com.example.demo.users.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserBookmarkRepository extends JpaRepository<UserBookmark, Long> {
    Optional<UserBookmark> findByUserAndFinancialProduct(User user, FinancialProduct financialProduct);

    // username을 사용하여 사용자의 모든 북마크 조회
    @Query("SELECT ub FROM UserBookmark ub WHERE ub.user.nickname = :username")
    List<UserBookmark> findByUsername(@Param("username") String username);

    // username과 금융 상품 ID를 사용하여 특정 북마크 조회
    @Query("SELECT ub FROM UserBookmark ub WHERE ub.user.nickname = :username AND ub.financialProduct.id = :productId")
    Optional<UserBookmark> findByUsernameAndFinancialProductId(@Param("username") String username, @Param("productId") Long productId);

    // 특정 금융상품에 대한 북마크 수를 카운트
    @Query("SELECT COUNT(ub) FROM UserBookmark ub WHERE ub.financialProduct.id = :productId")
    long countByFinancialProductId(@Param("productId") Long productId);
}
