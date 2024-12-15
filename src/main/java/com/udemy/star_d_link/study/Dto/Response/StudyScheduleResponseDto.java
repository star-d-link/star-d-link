package com.udemy.star_d_link.study.Dto.Response;

import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudySchedule;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyScheduleResponseDto {
    private Long scheduleId;
    private String username;
    private String title;
    private String content;
    private String scheduleDate;
    private String location;

    public static StudyScheduleResponseDto fromEntity(StudySchedule studySchedule) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return new StudyScheduleResponseDto(
            studySchedule.getScheduleId(),
            studySchedule.getUsername(),
            studySchedule.getScheduleTitle(),
            studySchedule.getScheduleContent(),
            studySchedule.getScheduleDate().format(formatter),
            studySchedule.getLocation()
        );
    }
}
