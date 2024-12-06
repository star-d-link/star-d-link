package com.udemy.star_d_link.user.dto;

import com.udemy.star_d_link.user.constants.UserRoles;
import com.udemy.star_d_link.user.entity.UserEntity;
import java.util.UUID;
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
public class JoinDTO {

    private String username;
    private String password;
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


