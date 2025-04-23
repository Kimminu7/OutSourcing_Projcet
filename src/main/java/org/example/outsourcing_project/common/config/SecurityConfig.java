package org.example.outsourcing_project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 의존성을 추가하면, 사용 권한을 받아야하기 때문에 적용시킨 클래스
 * (GPT가 알려준 방식)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ✅ 람다 방식으로 csrf 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**").permitAll() //  인증 없이 허용하는 URL ( WHITE LIST )
                        .anyRequest().authenticated()
                ); // 그 외는 인증 필요

        return http.build();
    }
}
