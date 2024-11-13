package com.udemy.star_d_link.study.Mapper;

import com.udemy.star_d_link.study.Dto.Request.StudyScheduleRequestDto;
import com.udemy.star_d_link.study.Dto.Response.StudyScheduleResponseDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudySchedule;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudyScheduleMapper {
    StudyScheduleMapper INSTANCE = Mappers.getMapper(StudyScheduleMapper.class);

    @Mapping(target = "scheduleId", ignore = true) // 새로운 엔티티 생성 시 studyManageId는 무시
    StudySchedule toEntity(StudyScheduleRequestDto requestDto, Study study);

    StudyScheduleResponseDto toDto(StudySchedule studySchedule);

    List<StudyScheduleResponseDto> toDtoList(List<StudySchedule> studyScheduleList);
}
