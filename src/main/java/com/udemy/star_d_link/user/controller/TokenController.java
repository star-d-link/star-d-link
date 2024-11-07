package com.udemy.star_d_link.user.controller;

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

    @PostMapping("/make")
    public ResponseEntity<Map<String, String>> makeToken(@RequestBody UserDTO userDTO) {

        log.info("make token................");

        UserDTO userDTOResult = userService.read(userDTO.getUsername(), userDTO.getPassword());

        log.info(userDTOResult);
        // TODO: 토큰 생성해야함
        return null;
    }
}
