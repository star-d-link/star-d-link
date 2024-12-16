package com.udemy.star_d_link.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udemy.star_d_link.user.constants.UserRoles;
import com.udemy.star_d_link.user.dto.ProfileRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @JsonIgnore
    @Column(nullable = false, length = 250)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, length = 40, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String role;

    @Column(length = 250)
    private String profileUrl;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 20)
    private LocalDate birthDate;

    @Column(length = 100)
    private String region;



    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void updateProfile(ProfileRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.region = requestDto.getRegion();
        this.birthDate = requestDto.getBirthDate();
    }

    public void deactivateUser() {
        this.role = UserRoles.ROLE_DEACTIVATED.name();
    }

    public void activateUser() {
        this.role = UserRoles.ROLE_USER.name();
    }


}
