package com.udemy.star_d_link.study.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyLikesResponseDto {
    private Long likesId;
    private Long userId;
    private Long studyId;
}
