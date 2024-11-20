package com.udemy.star_d_link.study.Dto.Response;

import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudySchedule;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyScheduleResponseDto {
    private Long scheduleId;
    private Study study;
    private String title;
    private String content;
    private LocalDateTime scheduleDate;
    private String location;

    public static StudyScheduleResponseDto fromEntity(StudySchedule studySchedule) {
        return new StudyScheduleResponseDto(
            studySchedule.getScheduleId(),
            studySchedule.getStudy(),
            studySchedule.getScheduleTitle(),
            studySchedule.getScheduleContent(),
            studySchedule.getScheduleDate(),
            studySchedule.getLocation()
        );
    }
}
