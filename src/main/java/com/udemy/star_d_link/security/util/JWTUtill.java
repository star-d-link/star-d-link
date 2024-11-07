package com.udemy.star_d_link.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class JWTUtill {

    private static String key = "1348342572893738473540958238572893342346";

    public String createToken(Map<String, Object> valueMap, int min) {

        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JWTUtill.key.getBytes("UTF-8"));
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

    public Map<String, Object> validateToken(String token) {

        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JWTUtill.key.getBytes("UTF-8"))
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
