package com.example.demo.controller;

import com.example.demo.service.YouthCenterService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
