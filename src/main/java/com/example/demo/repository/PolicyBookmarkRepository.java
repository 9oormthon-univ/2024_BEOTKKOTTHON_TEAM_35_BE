package com.example.demo.repository;

import com.example.demo.model.PolicyBookmark;
import com.example.demo.model.YouthPolicy;
import com.example.demo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PolicyBookmarkRepository extends JpaRepository<PolicyBookmark, Long> {

    Optional<PolicyBookmark> findByUserAndYouthPolicy(User user, YouthPolicy youthPolicy);

    List<PolicyBookmark> findByUser(User user);

    @Query("SELECT pb FROM PolicyBookmark pb WHERE pb.user.userId = :userId")
    List<PolicyBookmark> findByUserId(@Param("userId") Long userId);

    // 특정 YouthPolicy를 북마크한 모든 사용자 목록을 조회
    List<PolicyBookmark> findByYouthPolicy(YouthPolicy youthPolicy);
}
