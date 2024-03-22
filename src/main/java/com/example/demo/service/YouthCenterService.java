package com.example.demo.service;

import com.example.demo.model.PolicyBookmark;
import com.example.demo.model.YouthPolicy;
import com.example.demo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.example.demo.users.repository.UserRepository;
import com.example.demo.repository.PolicyBookmarkRepository;
import com.example.demo.repository.YouthPolicyRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class YouthCenterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PolicyBookmarkRepository policyBookmarkRepository;

    @Autowired
    private YouthPolicyRepository youthPolicyRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "https://www.youthcenter.go.kr/opi/youthPlcyList.do";

    @Value("${openapi.key}")
    private String apiKey;

    public String fetchPolicies(Integer display, Integer pageIndex, String query, String bizTycdSel, String srchPolyBizSecd, String keyword) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("openApiVlak", apiKey)
                .queryParam("display", display != null ? display : 10)
                .queryParam("pageIndex", pageIndex != null ? pageIndex : 1)
                .queryParam("query", query)
                .queryParam("bizTycdSel", bizTycdSel)
                .queryParam("srchPolyBizSecd", srchPolyBizSecd)
                .queryParam("keyword", keyword);

        return restTemplate.getForObject(builder.toUriString(), String.class);
    }

    public String fetchPolicyDetails(String srchPolicyId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("openApiVlak", apiKey)
                .queryParam("srchPolicyId", srchPolicyId);

        return restTemplate.getForObject(builder.toUriString(), String.class);
    }

    public void togglePolicyBookmark(String nickname, Long policyId) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("User not found with nickname: " + nickname));
        YouthPolicy youthPolicy = youthPolicyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("Policy not found with ID: " + policyId));

        Optional<PolicyBookmark> bookmark = policyBookmarkRepository.findByUserAndYouthPolicy(user, youthPolicy);
        if (!bookmark.isPresent()) {
            policyBookmarkRepository.save(new PolicyBookmark(user, youthPolicy));
        } else {
            policyBookmarkRepository.delete(bookmark.get());
        }
    }

    public List<YouthPolicy> getPolicyBookmarksByUsername(String username) {
        User user = userRepository.findByNickname(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        List<PolicyBookmark> bookmarks = policyBookmarkRepository.findByUser(user);

        return bookmarks.stream()
                .map(PolicyBookmark::getYouthPolicy)
                .collect(Collectors.toList());
    }

}
