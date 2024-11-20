package com.udemy.star_d_link.study.Dto.Request;

import com.udemy.star_d_link.study.Entity.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyMemberCreateRequestDto {
    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @NotNull(message = "스터디 ID는 필수입니다.")
    private Long studyId;

    @NotNull(message = "역할은 필수입니다.")
    private Role role;

    @NotEmpty(message = "상태는 필수입니다.")
    private String status;
}
