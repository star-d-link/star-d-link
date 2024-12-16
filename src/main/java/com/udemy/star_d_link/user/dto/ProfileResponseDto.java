package com.udemy.star_d_link.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.udemy.star_d_link.user.entity.UserEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDto {

    public ProfileResponseDto(UserEntity userEntity) {
        this.nickname = userEntity.getNickname();
        this.profileUrl = userEntity.getProfileUrl();
        this.phoneNumber = userEntity.getPhoneNumber();
        this.region = userEntity.getRegion();
        this.birthDate = userEntity.getBirthDate();
    }

    private String nickname;
    private String profileUrl;
    private String phoneNumber;
    private String region;
    private LocalDate birthDate;
}
