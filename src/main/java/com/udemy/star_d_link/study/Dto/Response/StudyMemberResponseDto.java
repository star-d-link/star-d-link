package com.udemy.star_d_link.study.Dto.Response;

import com.udemy.star_d_link.study.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyMemberResponseDto {
    private Long studyManageId;
    private Long userId;
    private Long StudyId;
    private Role role;
    private String status;
}
