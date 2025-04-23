package org.example.outsourcing_project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ✅ 람다 방식으로 csrf 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/signup").permitAll() // 회원가입은 인증 없이 허용
                        .anyRequest().authenticated()
                ); // 그 외는 인증 필요

        return http.build();
    }
}
