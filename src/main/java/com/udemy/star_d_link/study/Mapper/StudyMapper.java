package com.udemy.star_d_link.study.Mapper;

import com.udemy.star_d_link.study.Dto.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.StudyResponseDto;
import com.udemy.star_d_link.study.Dto.StudyUpdateRequestDto;
import com.udemy.star_d_link.study.Entity.Study;

public class StudyMapper {

    // Study 엔티티를 StudyResponseDto로 변환하는 메소드
    public static StudyResponseDto toResponseDto(Study study) {
        return new StudyResponseDto(
            study.getStudyId(),
            study.getUserId(),
            study.getTitle(),
            study.getContent(),
            study.getHashtag(),
            study.getIsRecruit(),
            study.getRegion(),
            study.getIsOnline(),
            study.getHeadCount(),
            study.getCreateDate()
        );
    }

    // StudyCreateRequestDto를 Study 엔티티로 변환하는 메소드
    public static Study toEntity(StudyCreateRequestDto requestDto) {
        return Study.builder()
            .userId(requestDto.getUserId())
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .hashtag(requestDto.getHashtag())
            .isRecruit(requestDto.getIsRecruit())
            .region(requestDto.getRegion())
            .isOnline(requestDto.getIsOnline())
            .headCount(requestDto.getHeadCount())
            .createDate(requestDto.getCreateDate()) // 만약 엔티티에서 자동 생성되길 원한다면 제외 가능
            .build();
    }

    // StudyResponseDto를 StudyCreateRequestDto로 변환하는 메소드 (필요한 경우)
    public static StudyCreateRequestDto toCreateRequestDto(StudyResponseDto responseDto) {
        return new StudyCreateRequestDto(
            responseDto.getUserId(),
            responseDto.getTitle(),
            responseDto.getContent(),
            responseDto.getHashtag(),
            responseDto.getIsRecruit(),
            responseDto.getRegion(),
            responseDto.getIsOnline(),
            responseDto.getHeadCount(),
            responseDto.getCreateDate()
        );
    }
    // StudyResponseDto를 StudyUpdateRequestDto로 변환하는 메소드
    public static StudyUpdateRequestDto toUpdateRequestDto(StudyResponseDto responseDto) {
        return new StudyUpdateRequestDto(
            responseDto.getTitle(),
            responseDto.getContent(),
            responseDto.getHashtag(),
            responseDto.getIsRecruit(),
            responseDto.getRegion(),
            responseDto.getIsOnline(),
            responseDto.getHeadCount()
        );
    }
}