package com.example.demo.controller;

import com.example.demo.service.FinancialProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
