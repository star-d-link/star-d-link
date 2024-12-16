package com.udemy.star_d_link.user.controller;

import com.udemy.star_d_link.security.dto.UserDetailDto;
import com.udemy.star_d_link.user.dto.CustomUserDetails;
import com.udemy.star_d_link.user.dto.ProfileRequestDto;
import com.udemy.star_d_link.user.dto.ProfileResponseDto;
import com.udemy.star_d_link.user.entity.UserEntity;
import com.udemy.star_d_link.user.service.ProfileService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping
    public ResponseEntity<?> updateProfile(@ModelAttribute ProfileRequestDto requestDto,
        @AuthenticationPrincipal CustomUserDetails authentication) {
        log.info(requestDto.toString());
        try {
            UserEntity user = profileService.updateProfile(requestDto,
                authentication.getUsername());
            return ResponseEntity.ok(user);
        } catch (IOException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(500).body("프로필 업데이트 실패: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getUserProfile(
        @AuthenticationPrincipal CustomUserDetails authentication) {
        ProfileResponseDto profile = profileService.getUserProfile(authentication.getUsername());
        return ResponseEntity.ok(profile);
    }
}
