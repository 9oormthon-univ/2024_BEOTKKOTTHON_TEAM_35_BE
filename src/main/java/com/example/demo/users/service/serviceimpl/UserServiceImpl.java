package com.example.demo.users.service.serviceimpl;

import com.example.demo.component.JwtTokenProvider;
import com.example.demo.error.ErrorCode;
import com.example.demo.users.dto.UserDto;
import com.example.demo.users.entity.User;
import com.example.demo.users.exception.UserNotExist;
import com.example.demo.users.exception.UserPasswordIncorrect;
import com.example.demo.users.repository.UserRepository;
import com.example.demo.users.service.serviceinterface.UserService;
import com.example.demo.vo.request.user.RequestLogin;
import com.example.demo.vo.request.user.RequestUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void createUser(RequestUser request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .point(request.getPoint())
                .nickname(request.getNickname())
                .flagNotification(request.getFlagNotification())
                .oauthProvider(request.getOauthProvider())
                .build();

        userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public UserDto getUser(Long userId){
        final User user= userRepository.findById(userId)
                .orElseThrow(()-> new UserNotExist("user not exist", ErrorCode.USER_NOT_EXIST));

        return UserDto.builder()
                .userId(userId)
                .email(user.getEmail())
                .password(user.getPassword())
                .point(user.getPoint())
                .nickname(user.getNickname())
                .flagNotification(user.getFlagNotification())
                .build();
    }

    @Transactional
    public void updateUser(Long userId, RequestUser request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExist("user not exist", ErrorCode.USER_NOT_EXIST));

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        // 계정 정보 업데이트
        user.updateEmail(request.getEmail());
        user.updateNickname(request.getNickname());
        user.updatePassword(encodedPassword);
        user.updatePoint(request.getPoint());
        user.updateflagNotification(request.getFlagNotification());

        userRepository.save(user);
    }

    @Transactional
    public Map<String, Object> login(RequestLogin requestLogin) {
        User user = userRepository.findByEmail(requestLogin.getEmail())
                .orElseThrow(()-> new UserNotExist("user not exist", ErrorCode.USER_NOT_EXIST));

        if (!passwordEncoder.matches(requestLogin.getPassword(), user.getPassword())) {
            throw new UserPasswordIncorrect("user password incorrect", ErrorCode.USER_PASSWORD_INCORRECT);
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getUserId());

        return response;
    }
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExist("user not exist", ErrorCode.USER_NOT_EXIST));

        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public boolean isNicknameExists(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }
}