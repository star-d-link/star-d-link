package com.udemy.star_d_link.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVerifyRequestDto {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message =  "유효한 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "인증 코드는 필수 입력값입니다.")
    private String verificationCode;

}
