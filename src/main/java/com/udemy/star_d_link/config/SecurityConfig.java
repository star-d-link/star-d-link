package com.udemy.star_d_link.config;

import com.udemy.star_d_link.security.filter.JWTCheckFilter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTCheckFilter jwtCheckFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            // 폼 로그인 비활성화
            .formLogin(AbstractHttpConfigurer::disable)
            // 로그아웃 비활성화
            .logout(AbstractHttpConfigurer::disable)
            // csrf 토큰 세션 비활성화
            .csrf(AbstractHttpConfigurer::disable)
            // Http session 생성 비활성화
            .sessionManagement(sessionManagementConfigurer -> {
                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.NEVER);
            })
            // jwtCheckFilter를 먼저 실행한다
            .addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class)
            // CORS 구성
            .cors(cors -> {
                cors.configurationSource(corsConfigurationSource());
            })
            // 모든 url 접근허용
//            .authorizeHttpRequests(
//                (auth) -> auth.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
            // iframe 허용 (h2 console)
            .headers((headers) -> headers.addHeaderWriter(new XFrameOptionsHeaderWriter(
                XFrameOptionsMode.SAMEORIGIN)))
        ;

        return httpSecurity.build();
    }

    /**
     * 스프링이 권장하는 PasswordEncoder를 생성한다
     * 현재 기준은 {@link BCryptPasswordEncoder}를 사용하고 있다.
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /**
     * 애플리케이션의 CORS 설정을 구성한다.
     * 별도의 프로젝트를 생성해 HTML 등을 제작해서 Ajax 호출을 하는 경우에
     * CORS 설정이 없는 관계로 에러가 발생하는 과정을 방지한다.
     * -------------------------------------------------------------------
     * 이 메서드는 모든 오리진으로부터의 요청을 허용하는 CORS 구성을 생성한다.
     * 다양한 HTTP 메소드를 허용하고 특정 헤더를 허용한다. 또한
     * 요청에 자격 증명을 포함할 수 있도록 설정한다. 그런다음 구성은
     * 모든 엔드포인트에 적용되도록 등록된다.
     *
     * @return 정의된 CORS 설정이 포함된 CorsConfigurationSource를 반환한다.
     */

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(List.of("*")); // 어디서든 허락
        corsConfiguration.setAllowedMethods(
            List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(
            List.of("Authorization", "Content-Type", "Cache-Control"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }


}
