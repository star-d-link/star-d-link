package com.udemy.star_d_link.study.Dto.Response;

import com.udemy.star_d_link.study.Entity.StudySchedule;
import com.udemy.star_d_link.study.Entity.StudyScheduleParticipation;
import com.udemy.star_d_link.study.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyScheduleParticipationResponseDto {

    private Long participationId;
    private Long scheduleId;
    private Long userId;
    private String participationStatus;

    // 엔티티를 DTO로 변환하는 메소드
    public static StudyScheduleParticipationResponseDto fromEntity(
        StudyScheduleParticipation participation) {
        User user = participation.getUser();
        StudySchedule schedule = participation.getStudySchedule();

        return new StudyScheduleParticipationResponseDto(
            participation.getParticipationId(),
            schedule.getScheduleId(),
            user.getUserId(),
            participation.getStatus().toString()
        );
    }
}
