package com.udemy.star_d_link.security.filter;

import com.udemy.star_d_link.security.auth.CustomUserPrincipal;
import com.udemy.star_d_link.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
        // h2 console
        if (request.getServletPath().startsWith("/h2-console")) {
            return true;
        }


        // TODO: 현재 개발편의성을 위해 모든경로를 true로 지정했지만 나중에 권한에 따라 조정해야함
        return true;
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

        String accessToken = headerStr.substring(7);

        try {
            Map<String, Object> tokenMap = jwtUtil.validateToken(accessToken);
            // 토큰 검증 결과에 문제가 없으면 tokenMap에서 사용자 정보를 가져와
            // Authentication 객체를 생성한다.
            log.info("tokenMap: " + tokenMap);

            String username = tokenMap.get("username").toString();
            //권한이 여러 개인 경우에는 ,로 구분해서 처리한다
            log.info("test");
            String[] roles = tokenMap.get("role").toString().split(",");
            log.info("roles: " + roles);

            // 토큰 검증 결과를 이용해 Authentication 객체를 생성한다.
            // Access Token을 이용해서 이미 검사가 완료되었으므로 Credentials는 null로 지정
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                    new CustomUserPrincipal(username),
                    null,
                    Arrays.stream(roles)
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList())
                );

            //SecurityContextHolder에 Authentication 객체를 저장
            //나중에 SecurityContextHolder를 이용해서 Authentication 객체를 꺼내서 사용할 수 있다.
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticationToken);

            // 완료 후 다음 필터로 넘어간다. (다음필터가 없으면 컨트롤러 요청으로 넘어간다.
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // 문제가 발생하면
            handleException(response, e);
        }


    }

    /**
     * Access Toekn이 없거나 Beaarer로 시작하지 않는 경우 또는 토큰에
     * 문제가 있을경우에 JwtException이 발생하면 403 Forbidden 에러를 발생시키게 한다.
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
