package com.udemy.star_d_link.user.service;

import com.udemy.star_d_link.user.entity.VerificationCode;
import com.udemy.star_d_link.user.repository.EmailRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final EmailRepository emailRepository;

    public boolean isEmailVerified(String email) {
        return emailRepository.findFirstByEmailOrderByIdDesc(email)
            .filter(vc -> vc.getExpiresTime().isAfter(LocalDateTime.now()))
            .map(VerificationCode::isVerified)// 인증 완료 여부 화인
            .orElse(false); // 인증 실패시 false
    }

    public void markEmailAsVerified(String email) {
        emailRepository.findFirstByEmailOrderByIdDesc(email).ifPresent(vc -> {
            vc.changeVerified(true); // 인증 상태를 truw로 변경
            emailRepository.save(vc);
        });
    }
}
