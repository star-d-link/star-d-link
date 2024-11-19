package com.udemy.star_d_link.study.Mapper;

import com.udemy.star_d_link.study.Dto.Request.ParticipationRequestDto;
import com.udemy.star_d_link.study.Entity.ParticipationStatus;
import com.udemy.star_d_link.study.Entity.StudySchedule;
import com.udemy.star_d_link.study.Entity.StudyScheduleParticipation;
import com.udemy.star_d_link.study.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudyScheduleParticipationMapper {

    // 스케줄 참여 정보를 생성하기 위한 메서드
    @Mapping(target = "participationId", ignore = true) // ID는 자동 생성
    StudyScheduleParticipation toEntity(StudySchedule studySchedule, User user, ParticipationStatus status);

    @Mapping(target = "participationId", ignore = true) // ID는 자동 생성
    StudyScheduleParticipation toEntity(ParticipationRequestDto requestDto, StudySchedule studySchedule, User user);

    @Mapping(target = "participationId", ignore = true)
    StudyScheduleParticipation toDto(StudyScheduleParticipation studyScheduleParticipation);
}
