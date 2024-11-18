package com.udemy.star_d_link.user.dto;

import com.udemy.star_d_link.user.entity.UserEntity;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private String profileUrl;
    private String phoneNumber;
    private String birthDate;
    private String region;
    private String role;
    private String createdAt;
    private String updatedAt;

    public UserDTO(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.nickname = userEntity.getNickname();
        this.email = userEntity.getEmail();
        this.profileUrl = userEntity.getProfileUrl();
        this.phoneNumber = userEntity.getPhoneNumber();
        this.birthDate = userEntity.getBirthDate();
        this.region = userEntity.getRegion();
        this.role = userEntity.getRole();
    }

    public Map<String, Object> getDataMap() {
        return Map.of(
            "username", username,
            "password", password,
            "nickname", nickname,
            "email", email,
            "phoneNumber", phoneNumber,
            "birthDate", birthDate,
            "region", region,
            "role", role
        );
    }
}
