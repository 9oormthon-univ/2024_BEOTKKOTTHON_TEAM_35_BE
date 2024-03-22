package com.example.demo.repository;

import com.example.demo.model.YouthPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YouthPolicyRepository extends JpaRepository<YouthPolicy, Long> {
    // 기본적인 CRUD 메소드가 JpaRepository를 통해 제공됩니다.
    List<YouthPolicy> findByPolyBizSjnmContaining(String polyBizSjnm);

    // 필요에 따라 추가적인 쿼리 메소드를 여기에 정의할 수 있습니다.
}
