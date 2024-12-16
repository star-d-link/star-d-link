package com.udemy.star_d_link.user.service;

import com.udemy.star_d_link.user.dto.UserRegisterRequestDto;
import com.udemy.star_d_link.user.dto.UserDTO;
import com.udemy.star_d_link.user.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public interface UserService {

    UserEntity registerUser(UserRegisterRequestDto user);

    UserDTO read(String username, String password);

    UserDTO getByUsername(String username);

    void sendCodeToEmail(
        @NotBlank(message = "이메일은 필수 입력값입니다.") @Email(message = "유효한 이메일 주소를 입력하세요.") String email);

    boolean verifyCode(
        @NotBlank(message = "이메일은 필수 입력값입니다.") @Email(message = "유효한 이메일 주소를 입력해주세요.") String email,
        @NotBlank(message = "인증 코드는 필수 입력값입니다.") String code);

    void sendCodeToEmailForPassword(String email);

    UserEntity resetPassword(String email, String password);

}
