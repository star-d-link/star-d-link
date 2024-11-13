package com.udemy.star_d_link.study.Dto.Response;

import java.time.LocalDate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyListResponseDto {
    private Long studyId;
    private String title;
    private Boolean isRecruit;
    private String region;
    private Boolean isOnline;
    private LocalDate createDate;
}
