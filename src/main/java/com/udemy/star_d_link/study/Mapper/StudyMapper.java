package com.udemy.star_d_link.study.Mapper;

import com.udemy.star_d_link.study.Dto.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.StudyListDto;
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

    // StudyResponseDto를 StudyUpdateRequestDto로 변환하는 메소드
    public static StudyUpdateRequestDto toUpdateRequestDto(Study study) {
        return new StudyUpdateRequestDto(
            study.getTitle(),
            study.getContent(),
            study.getHashtag(),
            study.getIsRecruit(),
            study.getRegion(),
            study.getIsOnline(),
            study.getHeadCount()
        );
    }

    // StudyUpdateRequestDto를 이용해 기존 Study 엔티티를 업데이트하는 메소드
    public static Study updateStudyFromDto(Study study, StudyUpdateRequestDto requestDto) {
        return study.toBuilder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .hashtag(requestDto.getHashtag())
            .isRecruit(requestDto.getIsRecruit())
            .region(requestDto.getRegion())
            .isOnline(requestDto.getIsOnline())
            .headCount(requestDto.getHeadCount())
            .build();
    }

    // Study 엔티티를 StudyListDto로 변환하는 메소드
    public static StudyListDto toListDto(Study study) {
        return new StudyListDto(
            study.getStudyId(),
            study.getTitle(),
            study.getIsRecruit(),
            study.getRegion(),
            study.getIsOnline(),
            study.getCreateDate()
        );
    }
}