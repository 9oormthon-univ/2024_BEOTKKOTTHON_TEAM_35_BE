package com.example.demo.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .cors(cors -> cors // CORS 설정 활성화
//                        .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
//                )
                .csrf((csrfConfig) ->
                        csrfConfig.disable()
                ) // 1번
                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
                )// 2번
                .authorizeHttpRequests((authorizeRequests) ->
                                authorizeRequests
                                        .requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**", "/webjars/**", "/swagger-resources/**").permitAll()
//                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                        .requestMatchers("/**", "/login/**", "/users/**", "/kakao/**", "/kauth/**", "/kakao/oauth/**").permitAll()
//                                .requestMatchers("/posts/**", "/api/v1/posts/**").hasRole(Role.USER.name())
//                                .requestMatchers("/admins/**", "/api/v1/admins/**").hasRole(Role.ADMIN.name())
                                        .anyRequest().authenticated()
                )// 3번
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        List<String> permitAllEndpoints = Arrays.asList(
                "/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**", "/swagger-resources/**",
                "/login/**", "/users/**", "/kakao/**", "/kauth/**", "/kakao/oauth/**"
        );
        return new JwtTokenFilter(jwtSecret, permitAllEndpoints);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
