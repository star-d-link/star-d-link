package com.udemy.star_d_link.user.controller;

import com.udemy.star_d_link.user.service.AdminService;
import com.udemy.star_d_link.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping()
    public String admin(Authentication authentication) {
        return "admin" + authentication.getName();
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PutMapping("/users/{userId}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable Long userId) {

        try {
            adminService.deactivateUser(userId);
            return ResponseEntity.ok("사용자가 탈퇴 상태로 변경되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("사용자 탈퇴 처리 중 오류가 발생했습니다.");
        }
    }
}
