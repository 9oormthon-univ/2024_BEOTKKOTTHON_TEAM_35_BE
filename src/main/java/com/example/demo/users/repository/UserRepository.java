package com.example.demo.users.repository;

import com.example.demo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.oauthId = :oauthId")
    User findByOauthId(@Param("oauthId") String oauthId);
}
