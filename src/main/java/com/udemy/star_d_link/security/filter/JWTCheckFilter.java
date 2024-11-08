package com.udemy.star_d_link.security.filter;

import com.udemy.star_d_link.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    /**
     * JWTCheckFilter가 동작하지 않아야 하는 경로를 지정한다.
     * @param request current HTTP request
     * @return
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        // 토큰의 생성, 재생성 경로
        if (request.getServletPath().startsWith("/api/v1/token/")) {
            return true;
        }

        // TODO: 경로 지정 필요
        return false;
    }

    /**
     * Access Token을 꺼내서 검증해 문제가 없는 경우에는 컨트롤러 혹은 다음 필터들이
     * 동작하도록 구성한다. 만일 Access Token에 문제가 있는 경우 JwtException이 발생한다.
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        log.info("JWTCheckFilter doFilter......................");

        log.info("requestURI: " + request.getRequestURI());

        String headerStr = request.getHeader("Authorization");
        log.info("headerStr: " + headerStr);

        //Access Token이 없는 경우
        if (headerStr == null || !headerStr.startsWith("Bearer ")) {
            handleException(response, new Exception("ACCESS TOKEN NOT FOUND"));
            return;
        }
    }

    /**
     * Access Toekn이 없거나 Beaarer로 시작하지 않는 경우라 JwtException이 발생하면
     * 403 Forbidden 에러를 발생시키게 한다.
     * @param response
     * @param e
     * @throws IOException
     */
    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
    }


}
