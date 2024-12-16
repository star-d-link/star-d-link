package com.udemy.star_d_link.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.udemy.star_d_link.user.entity.UserEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class ProfileRequestDto {

    private String nickname;
    private String password;
    private MultipartFile profileImage;
    private String phoneNumber;
    private String region;
    private LocalDate birthDate;

}
