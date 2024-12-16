package com.udemy.star_d_link.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestDto {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message =  "유효한 이메일 주소를 입력하세요.")
    private String email;



}
