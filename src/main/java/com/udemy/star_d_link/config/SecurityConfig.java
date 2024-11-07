package com.udemy.star_d_link.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Log4j2
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            // 폼 로그인 비활성화
            .formLogin(AbstractHttpConfigurer::disable)
            // 로그아웃 비활성화
            .logout(AbstractHttpConfigurer::disable)
            // csrf 토큰 세션 비활성화
            .csrf(AbstractHttpConfigurer::disable)
            // Http session 생성 비활성화
            .sessionManagement(AbstractHttpConfigurer::disable)
            // 모든 url 접근허용
//            .authorizeHttpRequests(
//                (auth) -> auth.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
            // iframe 허용 (h2 console)
            .headers((headers) -> headers.addHeaderWriter(new XFrameOptionsHeaderWriter(
                XFrameOptionsMode.SAMEORIGIN)))
        ;

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
