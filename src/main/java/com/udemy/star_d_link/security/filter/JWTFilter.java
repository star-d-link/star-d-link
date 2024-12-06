package com.udemy.star_d_link.security.filter;

import com.udemy.star_d_link.security.util.JWTUtil;
import com.udemy.star_d_link.user.dto.CustomUserDetails;
import com.udemy.star_d_link.user.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Log4j2
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        log.info("authorization: " + authorization);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("ACCESS TOKEN NOT FOUND");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("authorization: " + authorization);
        String token = "";

        if (authorization != null) {
            token = authorization.split(" ")[1];

        }

        if (jwtUtil.isExpired(token)) {
            log.info("ACCESS TOKEN IS EXPIRED");
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        UserEntity userEntity = UserEntity.builder()
            .username(username)
            .role(role)
            .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
            customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);


    }
}
