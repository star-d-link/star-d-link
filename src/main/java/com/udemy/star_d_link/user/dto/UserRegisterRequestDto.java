package com.udemy.star_d_link.user.dto;

import com.udemy.star_d_link.user.constants.UserRoles;
import com.udemy.star_d_link.user.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDto {

    @NotBlank(message = "id는 필수 입력값입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message =  "유효한 이메일 주소를 입력해주세요.")
    private String email;

    private String nickname;

    public UserEntity toEntity() {
        return UserEntity.builder()
            .username(username)
            .password(password)
            .email(email)
            .nickname(nickname)
            .role(UserRoles.ROLE_USER.name())
            .build();
    }

}


