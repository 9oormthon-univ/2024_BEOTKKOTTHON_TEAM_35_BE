package com.example.demo.controller;
import com.example.demo.model.FinancialProduct;
import com.example.demo.service.FinancialProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/financial-products")
public class FinancialProductController {

    @Autowired
    private FinancialProductService financialProductService;

    @GetMapping("/fetch")
    public String fetchFinancialProducts() {
        financialProductService.fetchAndSaveFinancialProducts();
        return "저장 성공!";
    }

    @GetMapping("/search")
    public List<FinancialProduct> searchFinancialProducts(@RequestParam("keyword") String keyword) {
        return financialProductService.searchFinancialProducts(keyword);
    }
}
