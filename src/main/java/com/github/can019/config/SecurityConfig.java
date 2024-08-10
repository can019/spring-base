package com.github.can019.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // REST API에서는 CSRF 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않음 (stateless)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/register", "/actuator/**", "/api/v0/hello").permitAll() // 로그인 및 회원가입은 허용
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .httpBasic(withDefaults()); // 최신 방식의 HTTP Basic 인증 설정

        return http.build();
    }
}
