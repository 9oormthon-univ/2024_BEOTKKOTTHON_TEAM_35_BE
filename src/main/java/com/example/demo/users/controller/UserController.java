package com.example.demo.users.controller;

import com.example.demo.users.service.serviceinterface.UserService;
import com.example.demo.vo.request.user.RequestLogin;
import com.example.demo.vo.request.user.RequestUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.demo.users.dto.UserDto;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name="유저")
@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "유저 회원가입")
    @PostMapping("/register")
    public ResponseEntity<Long> createUser(@RequestBody RequestUser request) {
        Long userId = userService.createUser(request);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @Operation(summary = "유저 정보 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId){
        final UserDto data = userService.getUser(userId);
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "유저 로그인")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody RequestLogin request) {
        Map<String, Object> ResponseLogin = userService.login(request);
        return ResponseEntity.ok(ResponseLogin);
    }

    @Operation(summary = "유저 정보 변경")
    @PutMapping("/{userId}")
    public void updateUser(@PathVariable Long userId, @RequestBody RequestUser request) {
        userService.updateUser(userId, request);
    }

    @Operation(summary = "닉네임 체크")
    @GetMapping("/check-nickname/{nickname}")
    public ResponseEntity<String> checkNicknameAvailability(@PathVariable String nickname) {
        boolean exists = userService.isNicknameExists(nickname);
        if (exists) {
            return ResponseEntity.ok("중복된 닉네임입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 닉네임입니다.");
        }
    }

    @Operation(summary = "이메일 체크")
    @GetMapping("/check-email/{email}")
    public ResponseEntity<String> checkEmailAvailability(@PathVariable String email) {
        boolean exists = userService.isEmailExists(email);
        if (exists) {
            return ResponseEntity.ok("중복된 이메일입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        }
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}

