package com.udemy.star_d_link.user.repository;

import com.udemy.star_d_link.user.entity.VerificationCode;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByEmailAndCode(String email, String code);

    Optional<VerificationCode> findFirstByEmailOrderByIdDesc(String email);




    void deleteByExpiresTimeBefore(LocalDateTime currentTime);

}
