package com.example.demo.model;

import com.example.demo.users.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class UserBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore // 순환 참조 방지
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_product_id")
    @JsonIgnore // 순환 참조 방지
    private FinancialProduct financialProduct;

    // 생성자
    public UserBookmark(User user, FinancialProduct financialProduct) {
        this.user = user;
        this.financialProduct = financialProduct;
    }

    // 기본 생성자
    public UserBookmark() {
    }

    // 게터 및 세터
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FinancialProduct getFinancialProduct() {
        return financialProduct;
    }

    public void setFinancialProduct(FinancialProduct financialProduct) {
        this.financialProduct = financialProduct;
    }
}
