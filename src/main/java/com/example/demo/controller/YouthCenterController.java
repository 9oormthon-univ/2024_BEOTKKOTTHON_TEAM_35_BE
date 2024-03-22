package com.example.demo.controller;

import com.example.demo.service.YouthCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class YouthCenterController {

    @Autowired
    private YouthCenterService youthCenterService;

    @GetMapping("/policies")
    public String getPolicies(@RequestParam(required = false) Integer display,
                              @RequestParam(required = false) Integer pageIndex,
                              @RequestParam(required = false) String query,
                              @RequestParam(required = false) String bizTycdSel,
                              @RequestParam(required = false) String srchPolyBizSecd,
                              @RequestParam(required = false) String keyword) {
        return youthCenterService.fetchPolicies(display, pageIndex, query, bizTycdSel, srchPolyBizSecd, keyword);
    }

    // 상세 조회 (파라미터에 출력결과 중 <bizId> 넣어야 함)
    @GetMapping("/policy-details")
    public String getPolicyDetails(@RequestParam String srchPolicyId) {
        return youthCenterService.fetchPolicyDetails(srchPolicyId);
    }

    // 정책 북마크 토글 메소드 업데이트
    @GetMapping("/toggle-policy-bookmark")
    public ResponseEntity<?> togglePolicyBookmark(@RequestParam Long policyId) { // srchPolicyId를 policyId로 변경
        String username = getCurrentUsername();
        youthCenterService.togglePolicyBookmark(username, policyId); // policyId를 Long 타입으로 받음
        return ResponseEntity.ok("북마크 상태가 변경되었습니다.");
    }

    // 유저별 북마크된 정책 목록 조회 메소드 업데이트
    @GetMapping("/user-policy-bookmarks")
    public ResponseEntity<?> getUserPolicyBookmarks() {
        String username = getCurrentUsername();
        // 반환 타입을 YouthPolicy 목록이 포함된 형태로 변경해야 함
        return ResponseEntity.ok(youthCenterService.getPolicyBookmarksByUsername(username));
    }

    // 현재 로그인한 유저의 username을 가져오는 메소드
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null; // 적절한 예외 처리 필요
    }
}
