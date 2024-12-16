package com.udemy.star_d_link.user.controller;

import com.udemy.star_d_link.user.dto.EmailRequestDto;
import com.udemy.star_d_link.user.dto.EmailVerifyRequestDto;
import com.udemy.star_d_link.user.dto.EmailVerifyResponseDto;
import com.udemy.star_d_link.user.dto.ResetPasswordDto;
import com.udemy.star_d_link.user.dto.UserRegisterRequestDto;
import com.udemy.star_d_link.user.dto.UserRegisterResponseDto;
import com.udemy.star_d_link.user.entity.UserEntity;
import com.udemy.star_d_link.user.service.UserService;
import com.udemy.star_d_link.user.service.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final VerificationService verificationService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> signup(
        @RequestBody UserRegisterRequestDto requestDto) {
        UserEntity user = userService.registerUser(requestDto);

        UserRegisterResponseDto responseDto = new UserRegisterResponseDto(user.getId(),
            "회원가입이 완료되었습니다.");
        return ResponseEntity.ok(responseDto);
    }

    // 인증 번호 전송
    @PostMapping("/send-email")
    public ResponseEntity<EmailRequestDto> sendEmail(@RequestBody EmailRequestDto requestDto) {
        userService.sendCodeToEmail(requestDto.getEmail());
        return ResponseEntity.ok(requestDto);
    }

    // 이메일 인증
    @PostMapping("/verify-email")
    public ResponseEntity<EmailVerifyResponseDto> verifyEmail(
        @RequestBody EmailVerifyRequestDto requestDto) {
        boolean isVerified = userService.verifyCode(requestDto.getEmail(),
            requestDto.getVerificationCode());

        EmailVerifyResponseDto responseDto = new EmailVerifyResponseDto();
        responseDto.setVerified(isVerified);
        responseDto.setMessage(
            isVerified ? "Email verified successfully." : "Invalid or expired verification code.");
        if (isVerified) {
            verificationService.markEmailAsVerified(requestDto.getEmail());
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    // 비밀번호 재설정
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        userService.resetPassword(resetPasswordDto.getEmail(), resetPasswordDto.getNewPassword());
        return ResponseEntity.ok("비밀번호 변경이 완료 되었습니다.");
    }


}
