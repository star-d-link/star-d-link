package com.udemy.star_d_link.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class JWTUtil {

    private static final String key = "1348342572893738473540958238572893342346";

    /**
     * JWT 토큰을 생성해 반환한다
     * typ JWT, alg HS256 설정
     * 발행일, 만료일 설정
     * valueMap의 모든 key, value 값을 페이로드의 JSON 클레임에 추가한 후
     * key를  HMAC-SHA 암호화 하여 서명한다.
     *
     * @param valueMap
     * @param min
     * @return
     */
    public String createToken(Map<String, Object> valueMap, int min) {

        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return Jwts.builder().header()
            .add("typ", "JWT")
            .add("alg", "HS256")
            .and()
            .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
            .expiration((Date.from(ZonedDateTime.now().plusMinutes(min).toInstant())))
            .claims(valueMap)
            .signWith(key)
            .compact()
            ;
    }

    /**
     * JWT 토큰을 검증하고 페이로드 클레임 즉 사용자의 정보가 담긴
     * Map을 반환한다
     * @param token
     * @return
     */
    public Map<String, Object> validateToken(String token) {

        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        Claims claims = Jwts.parser().verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        log.info("claims: " + claims);

        return claims;
    }

}
