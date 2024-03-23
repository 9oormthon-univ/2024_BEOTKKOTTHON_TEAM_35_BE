package com.example.demo.controller;

import com.example.demo.dto.FinancialProductDetailsResponse;
import com.example.demo.model.FinancialProduct;
import com.example.demo.service.FinancialProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FinancialProductController {

    @Autowired
    private FinancialProductService financialProductService;

    @GetMapping("/fetch")
    public String fetchFinancialProducts() {
        financialProductService.fetchAndSaveFinancialProducts();
        return "저장 성공!";
    }

    @GetMapping("/search")
    public List<FinancialProduct> searchFinancialProducts(@RequestParam(value = "keyword", required = false) String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // 키워드가 제공되지 않았거나 비어있는 경우 모든 금융 상품 반환
            return financialProductService.getAllFinancialProducts();
        }
        // 그렇지 않으면, 키워드에 따라 필터링된 목록 반환
        return financialProductService.searchFinancialProducts(keyword);
    }

    // 북마크 토글 엔드포인트 수정
    @GetMapping("/toggle-bookmark")
    public ResponseEntity<String> toggleBookmark(@RequestParam("productId") Long productId) {
        String username = getCurrentUsername();
        // financialProductService에 username과 productId를 전달하여 북마크를 토글
        financialProductService.toggleBookmarkByUsername(username, productId);
        return ResponseEntity.ok("북마크 상태가 변경되었습니다.");
    }

    @GetMapping("/bookmarked-products")
    public ResponseEntity<List<FinancialProduct>> getBookmarkedFinancialProducts() {
        String username = getCurrentUsername();
        // financialProductService에 username을 전달하여 북마크된 상품 조회
        List<FinancialProduct> bookmarkedProducts = financialProductService.getBookmarkedFinancialProductsByUsername(username);
        return ResponseEntity.ok(bookmarkedProducts);
    }

    @GetMapping("/financial-products/{id}")
    public ResponseEntity<FinancialProductDetailsResponse> getFinancialProductDetails(@PathVariable Long id) {
        FinancialProduct product = financialProductService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        long bookmarkCount = financialProductService.getBookmarkCountForProduct(id);

        FinancialProductDetailsResponse response = new FinancialProductDetailsResponse(product, (int) bookmarkCount);
        return ResponseEntity.ok(response);
    }


    // Username 가져옴
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null; // 또는 적절한 예외 처리
    }
}
