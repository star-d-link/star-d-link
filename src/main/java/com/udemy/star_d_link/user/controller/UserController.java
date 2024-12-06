package com.udemy.star_d_link.user.controller;

import com.udemy.star_d_link.user.dto.JoinDTO;
import com.udemy.star_d_link.user.dto.UserDTO;
import com.udemy.star_d_link.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> list() {

        log.info("list.................");

        String[] arr = {"aaa", "bbb", "ccc"};

        return ResponseEntity.ok(arr);
    }

    @PostMapping("/signup")
    public String signup(@RequestBody JoinDTO joinDTO) {
        userService.saveUser(joinDTO);
        return "success";
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
//        if ()
//    }

//    @GetMapping("/signup")
}
