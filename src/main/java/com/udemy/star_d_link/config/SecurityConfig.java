package com.udemy.star_d_link.config;

import com.udemy.star_d_link.security.filter.JWTFilter;
import com.udemy.star_d_link.security.filter.LoginFilter;
import com.udemy.star_d_link.security.handler.CustomSuccessHandler;
import com.udemy.star_d_link.security.service.CustomOauth2UserService;
import com.udemy.star_d_link.security.util.JWTUtil;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final CustomOauth2UserService customOauth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                    .userService(customOauth2UserService))
                .successHandler(customSuccessHandler)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/", "/users/**", "/h2-console/**"
                , "/oauth2/**", "/login/oauth2/**", "/uploads/**", "/study/list", "/study/{study_id}").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
            .addFilterAt(
                new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
                UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(sessionManagementConfigurer -> {
                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            // iframe 허용 (h2 console)
            .headers((headers) -> headers.addHeaderWriter(new XFrameOptionsHeaderWriter(
                XFrameOptionsMode.SAMEORIGIN)))
        ;

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /**
     * 애플리케이션의 CORS 설정을 구성한다. 별도의 프로젝트를 생성해 HTML 등을 제작해서 Ajax 호출을 하는 경우에 CORS 설정이 없는 관계로 에러가 발생하는
     * 과정을 방지한다. ------------------------------------------------------------------- 이 메서드는 모든
     * 오리진으로부터의 요청을 허용하는 CORS 구성을 생성한다. 다양한 HTTP 메소드를 허용하고 특정 헤더를 허용한다. 또한 요청에 자격 증명을 포함할 수 있도록
     * 설정한다. 그런다음 구성은 모든 엔드포인트에 적용되도록 등록된다.
     *
     * @return 정의된 CORS 설정이 포함된 CorsConfigurationSource를 반환한다.
     */

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }

}
