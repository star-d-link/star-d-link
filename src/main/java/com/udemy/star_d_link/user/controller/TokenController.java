package com.udemy.star_d_link.user.controller;

import com.udemy.star_d_link.security.util.JWTUtil;
import com.udemy.star_d_link.user.dto.UserDTO;
import com.udemy.star_d_link.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 실제 사용자의 정보를 확인해서 토큰을 발행하는 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/token")
@Log4j2
@RequiredArgsConstructor
public class TokenController {

    private final UserService userService;
    private final JWTUtil jwtUtil;

    /**
     * 새로운 토큰 발행
     * @param userDTO
     * @return
     */
    @PostMapping("/make")
    public ResponseEntity<Map<String, String>> makeToken(@RequestBody UserDTO userDTO) {

        log.info("make token................");

        UserDTO userDTOResult = userService.read(userDTO.getUsername(), userDTO.getPassword());

        log.info(userDTOResult);

        String username = userDTOResult.getUsername();
        Map<String, Object> dataMap = userDTOResult.getDataMap();

        // 액세스토큰 1일, 리프레쉬토큰 30일
        String accessToken = jwtUtil.createToken(dataMap, 2);
        String refreshToken = jwtUtil.createToken(Map.of("username", username)
            , 60 * 24 * 30);

        log.info("accessToken: " + accessToken);
        log.info("refreshToken: " + refreshToken);

        return ResponseEntity.ok(
            Map.of("accessToken", accessToken, "refreshToken", refreshToken)
        );
    }

    /**
     * Access 토큰이 만료되었을 때 새로운 토큰 발행
     * @param accessTokenStr
     * @param refreshToken
     * @param username
     * @return
     */
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> makeToken(
        @RequestHeader("Authorization") String accessTokenStr,
        @RequestParam("refreshToken") String refreshToken,
        @RequestParam("username") String username
    ) {

        // 토큰 존재 확인
        log.info("access token with Bearer............." + accessTokenStr);

        if (accessTokenStr == null || !accessTokenStr.startsWith("Bearer ")) {
            return handleException("No Access Token", 400);
        }

        if (refreshToken == null) {
            return handleException("No Refresh Token", 400);
        }

        log.info("refresh token................." + refreshToken);

        if (username == null) {
            return handleException("No username", 400);
        }

        //Access Token이 만료되었는지 확인
        String accessToken = accessTokenStr.substring(7); // Bearer를 제거

        try {
            jwtUtil.validateToken(accessToken);

            //아직 만료 기한이 남아있는 상황
            Map<String, String> data = makeData(username, accessToken, refreshToken);
            log.info("Access Token is not expired / 액세스 토큰이 만료되지 않았습니다................");
            return ResponseEntity.ok(data);
        } catch (ExpiredJwtException expiredJwtException) {
            try {
                // Refresh가 필요한 상황
                Map<String, String> newTokenMap = makeNewToken(username, refreshToken);
                return ResponseEntity.ok(newTokenMap);
            } catch (Exception e) {
                return handleException("REFRESH " + e.getMessage(), 400);
            }
        } catch (Exception e) {
            e.printStackTrace(); //디버깅용
            return handleException(e.getMessage(), 400);
        }

    }

    /**
     * refreshToken을 검증한 후 username을 이용해 사용자 정보를 다시 확인한 후
     * 새로운 토큰을 생성해 반환한다.
     * @param username
     * @param refreshToken
     * @return
     */
    private Map<String, String> makeNewToken(String username, String refreshToken) {
        Map<String, Object> claims = jwtUtil.validateToken(refreshToken);

        log.info("refresh token claims: " + claims);

        if (!username.equals(claims.get("username").toString())) {
            throw new RuntimeException("Invalid Refresh Token Host");
        }
        // 사용자의 정보가 수정이 되었을수도 있기 때문에 다시 가져온다
        UserDTO userDTO = userService.getByUsername(username);

        Map<String, Object> newClaims = userDTO.getDataMap();

        String newAccessToken = jwtUtil.createToken(newClaims, 2);

        String newRefreshToken = jwtUtil.createToken(Map.of("username", username),
            60 * 24 * 30);

        return makeData(username, newAccessToken, newRefreshToken);
    }

    /**
     * Access Token이 만료되지 않았는데 refresh가 호출 되었을 때 새로운 토큰을 발행할 필요가 없으므로 파라미터로 전달받은 정보들을 그대로 전송한다.
     *
     * @param username
     * @param accessToken
     * @param refreshToken
     * @return
     */
    private Map<String, String> makeData(String username, String accessToken,
        String refreshToken) {

        return Map.of("username", username, "accessToken", accessToken,
            "refreshToken", refreshToken);
    }

    /**
     * refresh 동작 중에 잘못된 상황은 예외 메시지를 전송할 수 있도록 한다.
     *
     * @param msg
     * @param status
     * @return
     */
    private ResponseEntity<Map<String, String>> handleException(String msg, int status) {
        return ResponseEntity.status(status).body(Map.of("error", msg));
    }
}
