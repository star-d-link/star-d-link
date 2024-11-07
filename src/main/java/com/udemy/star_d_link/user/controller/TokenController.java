package com.udemy.star_d_link.user.controller;

import com.udemy.star_d_link.security.util.JWTUtil;
import com.udemy.star_d_link.user.dto.UserDTO;
import com.udemy.star_d_link.user.service.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
@Log4j2
@RequiredArgsConstructor
public class TokenController {

    private final UserService userService;
    private final JWTUtil jwtUtil;

    @PostMapping("/make")
    public ResponseEntity<Map<String, String>> makeToken(@RequestBody UserDTO userDTO) {

        log.info("make token................");

        UserDTO userDTOResult = userService.read(userDTO.getUsername(), userDTO.getPassword());

        log.info(userDTOResult);

        String username = userDTOResult.getUsername();
        Map<String, Object> dataMap = userDTOResult.getDataMap();

        // TODO: 개발 진행 중 액세스 10분, 리프레쉬 7일로 설정 나중에 1일, 30일로 변경해야함
        String accessToken = jwtUtil.createToken(dataMap, 10);
        String refreshToken = jwtUtil.createToken(Map.of("username", username)
            , 60 * 24 * 7);

        log.info("accessToken: " + accessToken);
        log.info("refreshToken: " + refreshToken);

        return ResponseEntity.ok(
            Map.of("accessToken", accessToken, "refreshToken", refreshToken)
        );
    }
}
