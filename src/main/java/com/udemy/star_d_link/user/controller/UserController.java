package com.udemy.star_d_link.user.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/api/v1/users")
public class UserController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> list() {

        log.info("list.................");

        String[] arr = {"aaa", "bbb", "ccc"};

        return ResponseEntity.ok(arr);
    }
}
