package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fncIstNm; // 금융회사명
    private String prdNm; // 금융상품명
    private String prdSalDscnDt; // 상품판매중단일자
    private String regDate; // 등록일

    // 추가할 생성자
    public FinancialProduct(String fncIstNm, String prdNm, String prdSalDscnDt, String regDate) {
        this.fncIstNm = fncIstNm;
        this.prdNm = prdNm;
        this.prdSalDscnDt = prdSalDscnDt;
        this.regDate = regDate;
    }
}
