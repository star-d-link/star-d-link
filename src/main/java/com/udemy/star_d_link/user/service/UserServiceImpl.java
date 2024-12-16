package com.udemy.star_d_link.user.service;

import com.udemy.star_d_link.user.dto.UserRegisterRequestDto;
import com.udemy.star_d_link.user.dto.UserDTO;
import com.udemy.star_d_link.user.entity.UserEntity;
import com.udemy.star_d_link.user.entity.VerificationCode;
import com.udemy.star_d_link.user.exception.UserExceptions;
import com.udemy.star_d_link.user.repository.EmailRepository;
import com.udemy.star_d_link.user.repository.UserRepository;
import com.udemy.star_d_link.user.util.RandomNickname;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RandomNickname randomNickname;
    private final EmailRepository emailRepository;
    private final EmailService emailService;
    private final VerificationService verificationService;


    /**
     * 회원가입
     *
     * @param user
     * @return
     */
    @Override
    public UserEntity registerUser(UserRegisterRequestDto user) {
        // 이메일 인증 여부 확인
        String username = user.getUsername();
        String email = user.getEmail();

        // 이메일 인증 여부 확인
        if (!verificationService.isEmailVerified(email)) {
            throw UserExceptions.BAD_REQUEST.get();
        }

        // 중복 아이디 확인
        if (userRepository.existsByUsername(username)) {
            throw UserExceptions.DUPLICATE_NICKNAME.get();
        }

        // 중복 이메일 확인
        if (userRepository.existsByEmail(email)) {
            throw UserExceptions.DUPLICATE_EMAIL.get();
        }

        user.setNickname(randomNickname.generate());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user.toEntity());
    }

    @Override
    public UserDTO read(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(UserExceptions.BAD_CREDENTIALS::get);

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw UserExceptions.BAD_CREDENTIALS.get();
        }

        return new UserDTO(userEntity);
    }

    @Override
    public UserDTO getByUsername(String username) {
        Optional<UserEntity> result = userRepository.findByUsername(username);

        UserEntity userEntity = result.orElseThrow(UserExceptions.NOT_FOUND::get);

        return new UserDTO(userEntity);
    }

    /**
     * 인증코드가 담긴 이메일을 보낸다
     *
     * @param email
     */
    @Override
    public void sendCodeToEmail(String email) {
        VerificationCode createdCode = createVerificationCode(email);
        String title = "Star-D-Link 이메일 인증 번호";
        String content = "<html>"
            + "<body>"
            + "<h1>Star-D-Link 인증 코드: " + createdCode.getCode() + "</h1>"
            + "<p>해당 코드를 홈페이지에 입력하세요.</p>"
            + "<footer style='color: grey; font-size: small;'>"
            + "<p>※본 메일은 자동응답 메일이므로 본 메일에 회신하지 마시기 바랍니다.</p>"
            + "</footer>"
            + "</body>"
            + "</html>";

        try {
            emailService.sendEmail(email, title, content);
        } catch (RuntimeException | MessagingException e) {
            log.info(e.getMessage());
            throw new RuntimeException("Unable to send email in sendCodeToEmail", e);
        }

    }

    /**
     * 비밀번호 찾기를 위해 이메일이 담긴 이메일을 보낸다
     *
     * @param email
     */
    @Override
    public void sendCodeToEmailForPassword(String email) {
        VerificationCode createdCode = createVerificationCode(email);
        String title = "Star-D-Link 비밀번호 재설정을 위한 이메일 인증 번호";
        String content = "<html>"
            + "<body>"
            + "<h1>Star-D-Link 인증 코드: " + createdCode.getCode() + "</h1>"
            + "<p>해당 코드를 비밀번호 재설정 인증 코드에 입력하세요.</p>"
            + "<footer style='color: grey; font-size: small;'>"
            + "<p>※본 메일은 자동응답 메일이므로 본 메일에 회신하지 마시기 바랍니다.</p>"
            + "</footer>"
            + "</body>"
            + "</html>";

        try {
            emailService.sendEmail(email, title, content);
        } catch (RuntimeException | MessagingException e) {
            log.info(e.getMessage());
            throw new RuntimeException("Unable to send email in sendCodeToEmail", e);
        }

    }

    /**
     * 인증 코드 생성 및 저장
     *
     * @param email
     * @return
     */
    private VerificationCode createVerificationCode(String email) {
        String randomCode = generateRandomCode(6);
        VerificationCode code = VerificationCode.builder()
            .email(email)
            .code(randomCode)
            .expiresTime(LocalDateTime.now().plusDays(1)) // 1일 후 만료
            .build();
        return emailRepository.save(code);
    }

    /**
     * 랜덤  코드 생성
     *
     * @param length
     * @return
     */
    private String generateRandomCode(int length) {
        // 숫자 + 대문자 + 소문자
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    /**
     * 인증 코드 유효성 검사 후 만료시간 이전이면 true, 이후면 false
     *
     * @param email
     * @param code
     * @return
     */
    @Override
    public boolean verifyCode(String email, String code) {
        return emailRepository.findByEmailAndCode(email, code)
            .map(vc -> vc.getExpiresTime().isAfter(LocalDateTime.now()))
            .orElse(false);
    }

    @Override
    public UserEntity resetPassword(String email, String password) {
        UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(UserExceptions.NOT_FOUND::get);
        user.changePassword(passwordEncoder.encode(password));
        return user;
    }

    /**
     * 매일 정오에 해당 만료 코드 삭제
     */
    @Transactional
    @Scheduled(cron = "0 0 12 * * ?")
    public void deleteExpiredVerificationCodes() {
        emailRepository.deleteByExpiresTimeBefore(LocalDateTime.now());
    }


}
