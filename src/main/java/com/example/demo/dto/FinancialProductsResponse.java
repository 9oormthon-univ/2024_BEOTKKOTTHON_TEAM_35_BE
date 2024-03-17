package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinancialProductsResponse {
    @JsonProperty("getProductList")
    private ProductList getProductList;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductList {
        private Header header;
        private List<Item> item;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Item {
            private String prdSalDscnDt;
            private String num;
            private String fncIstNm;
            private String regDate;
            private String prdNm;
        }
    }
}
