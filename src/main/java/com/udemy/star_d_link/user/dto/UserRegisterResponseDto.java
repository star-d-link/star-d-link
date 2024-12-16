package com.udemy.star_d_link.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegisterResponseDto {

    private Long userId;
    private String message;

}
