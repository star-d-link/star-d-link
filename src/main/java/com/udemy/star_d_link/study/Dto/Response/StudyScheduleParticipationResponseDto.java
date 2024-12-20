package com.udemy.star_d_link.study.Dto.Response;

import com.udemy.star_d_link.study.Entity.StudySchedule;
import com.udemy.star_d_link.study.Entity.StudyScheduleParticipation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyScheduleParticipationResponseDto {

    private Long participationId;
    private Long scheduleId;
    private String username;
    private String participationStatus;

    // 엔티티를 DTO로 변환하는 메소드
    public static StudyScheduleParticipationResponseDto fromEntity(
        StudyScheduleParticipation participation) {
        StudySchedule schedule = participation.getStudySchedule();

        return new StudyScheduleParticipationResponseDto(
            participation.getParticipationId(),
            schedule.getScheduleId(),
            participation.getUsername(),
            participation.getStatus().toString()
        );
    }
}
