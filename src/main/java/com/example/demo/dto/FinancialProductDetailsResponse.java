package com.example.demo.dto;

import com.example.demo.model.FinancialProduct;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinancialProductDetailsResponse {
    private Long id;
    private String fncIstNm; // 금융회사명
    private String prdNm; // 금융상품명
    private String prdSalDscnDt; // 상품판매중단일자
    private String regDate; // 등록일
    private int viewCount; // 조회수
    private int bookmarkCount; // 북마크 수
    private String homepageLink; // 홈페이지 링크

    public FinancialProductDetailsResponse(FinancialProduct product, int bookmarkCount) {
        this.id = product.getId();
        this.fncIstNm = product.getFncIstNm();
        this.prdNm = product.getPrdNm();
        this.prdSalDscnDt = product.getPrdSalDscnDt();
        this.regDate = product.getRegDate();
        this.viewCount = product.getViewCount();
        this.bookmarkCount = bookmarkCount;
        this.homepageLink = product.getHomepageLink();
    }
}
