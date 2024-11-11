package com.udemy.star_d_link.study.Dto.Response;

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
    private String role;
    private String status;
}
