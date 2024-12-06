package com.udemy.star_d_link.user.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping()
    public String admin(Authentication authentication){
        return "admin" + authentication.getName();
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
