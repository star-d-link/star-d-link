package com.udemy.star_d_link.study.Mapper;

import com.udemy.star_d_link.study.Dto.Request.StudyScheduleCreateRequestDto;
import com.udemy.star_d_link.study.Dto.Request.StudyScheduleAllUpdateRequestDto;
import com.udemy.star_d_link.study.Dto.Request.StudyScheduleSingleUpdateRequestDto;
import com.udemy.star_d_link.study.Dto.Response.StudyScheduleResponseDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudySchedule;
import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudyScheduleMapper {
    StudyScheduleMapper INSTANCE = Mappers.getMapper(StudyScheduleMapper.class);

    @Mapping(target = "scheduleId", ignore = true) // 새로운 엔티티 생성 시 studyManageId는 무시
    StudySchedule toEntity(StudyScheduleCreateRequestDto requestDto, Study study);

    @Mapping(target = "scheduleId", ignore = true)
    StudyScheduleResponseDto toDto(StudySchedule studySchedule);

    List<StudyScheduleResponseDto> toDtoList(List<StudySchedule> studyScheduleList);

    // 전체 업데이트용
    @Mapping(target = "scheduleId", ignore = true) // scheduleId는 수정하지 않음
    @Mapping(target = "scheduleDate", ignore = true) // 전체 수정에서는 개별 날짜 수정하지 않음
    @Mapping(target = "study", ignore = true)
    @Mapping(target = "recurrenceGroup", ignore = true)
    void updateAllScheduleFromDto(@MappingTarget StudySchedule studySchedule, StudyScheduleAllUpdateRequestDto requestDto);

    // 개별 업데이트용
    @Mapping(target = "scheduleId", ignore = true) // scheduleId는 수정하지 않음
    @Mapping(target = "study", ignore = true)
    @Mapping(target = "recurrenceGroup", ignore = true)
    void updateSingleScheduleFromDto(@MappingTarget StudySchedule studySchedule, StudyScheduleSingleUpdateRequestDto requestDto);

    // StudyScheduleCreateRequestDto와 Study를 사용해 StudySchedule 생성
    @Mapping(target = "scheduleId", ignore = true)
    @Mapping(target = "recurrenceGroup", ignore = true)
    StudySchedule toInitialEntity(StudyScheduleCreateRequestDto requestDto, Study study);

    // 반복된 스케줄 생성 (기존 스케줄을 기반으로 recurrenceGroup과 날짜를 설정)
    @Mapping(target = "scheduleId", ignore = true)
    @Mapping(target = "scheduleDate", source = "scheduleDate")
    @Mapping(target = "recurrenceGroup", source = "recurrenceGroup")
    StudySchedule toRecurringEntity(@MappingTarget StudySchedule baseSchedule, LocalDateTime scheduleDate, Long recurrenceGroup);

}
