package com.udemy.star_d_link.study.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyMemberRequestDto {
    private Long userId;
    private Long studyId;
    private String role;
}
