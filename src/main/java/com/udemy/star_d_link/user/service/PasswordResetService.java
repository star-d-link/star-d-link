//package com.udemy.star_d_link.user.service;
//
//import com.udemy.star_d_link.user.entity.VerificationCode;
//import com.udemy.star_d_link.user.repository.EmailRepository;
//import com.udemy.star_d_link.user.repository.UserRepository;
//import java.time.LocalDateTime;
//import java.util.concurrent.ThreadLocalRandom;
//import java.util.stream.Collectors;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class PasswordResetService {
//
//    private final EmailService emailService;
//    private final EmailRepository emailRepository;
//    private final UserRepository userRepository;
//
//    // 인증 코드 전송
//    public void sendResetCode(String email) {
//        String code = generateRandomCode(6);
//        VerificationCode verificationCode = VerificationCode.builder()
//            .email(email)
//            .code(code)
//            .expiresTime(LocalDateTime.now().plusMinutes(15))
//            .build();
//    }
//
//    private String generateRandomCode(int length) {
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        return ThreadLocalRandom.current().ints(length, 0, characters.length())
//            .mapToObj(i -> String.valueOf(characters.charAt(i)))
//            .collect(Collectors.joining());
//    }
//
//}
