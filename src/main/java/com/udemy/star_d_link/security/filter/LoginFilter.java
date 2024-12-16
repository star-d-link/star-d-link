package com.udemy.star_d_link.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.star_d_link.security.util.JWTUtil;
import com.udemy.star_d_link.user.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 로그인 로직
 */
@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    /**
     * @param request  from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a redirect
     *                 as part of a multi-stage authentication process (such as OIDC).
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        String username = null;
        String password = null;
        if ("application/json".equals(request.getContentType())) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map<String, String> requestBody = objectMapper.readValue(request.getInputStream(),
                    Map.class);
                username = requestBody.get("username");
                password = requestBody.get("password");
            } catch (IOException e) {
                throw new AuthenticationServiceException(
                    "Failed to parse authentication request body", e);
            }
        } else {
            username = obtainUsername(request);
            password = obtainPassword(request);
        }

        log.info("username: {}", username);
        log.info("password: {}", password);

        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }

    /**
     * @param request
     * @param response
     * @param chain
     * @param authResult the object returned from the <tt>attemptAuthentication</tt> method.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {
        log.info("로그인 성공");

        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        String username = customUserDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority next = iterator.next();

        String role = next.getAuthority();

        String token = jwtUtil.createJwt(username, role, 60 * 60 * 10L);

        response.addHeader("Authorization", "Bearer " + token);

        // 응답 본문에 토큰 추가 (JSON 형태로 반환)
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("token", token);
        new ObjectMapper().writeValue(response.getWriter(), responseBody);

        log.info("JWT Token: {}", token);
    }

    /**
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed)
        throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
