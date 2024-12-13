package com.udemy.star_d_link.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVerifyResponseDto {

    private boolean isVerified; // 인증 여부
    private String message; //인증 결과 메시지


}
