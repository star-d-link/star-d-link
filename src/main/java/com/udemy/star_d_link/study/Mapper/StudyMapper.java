package com.udemy.star_d_link.study.Mapper;

import com.udemy.star_d_link.study.Dto.Request.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.Response.StudyListResponseDto;
import com.udemy.star_d_link.study.Dto.Response.StudyResponseDto;
import com.udemy.star_d_link.study.Dto.Request.StudyUpdateRequestDto;
import com.udemy.star_d_link.study.Entity.Study;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/*
코드의 가독성을 위해 Mapper 클래스 작성
Entity -> DTO나 DTO -> Entity 변환 작업시 사용
 */

@Mapper(componentModel = "spring")
public interface StudyMapper {

    StudyMapper INSTANCE = Mappers.getMapper(StudyMapper.class);

    // Study 엔티티를 StudyResponseDto로 변환하는 메소드
    StudyResponseDto toResponseDto(Study study);

    // StudyCreateRequestDto를 Study 엔티티로 변환하는 메소드
    Study toEntity(StudyCreateRequestDto requestDto);

    // Study 엔티티를 StudyUpdateRequestDto로 변환하는 메소드
    StudyUpdateRequestDto toUpdateRequestDto(Study study);

    // StudyUpdateRequestDto를 이용해 기존 Study 엔티티를 업데이트하는 메소드
    @Mapping(target = "studyId", ignore = true) // 엔티티의 고유 ID는 업데이트하지 않도록 설정
    void updateStudyFromDto(StudyUpdateRequestDto requestDto, @MappingTarget Study study);

    // Study 엔티티를 StudyListResponseDto로 변환하는 메소드
    StudyListResponseDto toListDto(Study study);
}