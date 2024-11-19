package com.udemy.star_d_link.study.Dto.Response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyScheduleResponseDto {
    private Long scheduleId;
    private Long studyId;
    private String title;
    private String content;
    private LocalDateTime scheduleDate;
    private String location;
}
