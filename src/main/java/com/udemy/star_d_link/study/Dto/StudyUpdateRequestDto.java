package com.udemy.star_d_link.study.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyUpdateRequestDto {
    private String title;
    private String content;
    private String hashtag;
    private Boolean isRecruit;
    private String region;
    private Boolean isOnline;
    private Integer headCount;

    private StudyUpdateRequestDto createStudyUpdateRequestDto(StudyResponseDto studyResponseDto) {
        return new StudyUpdateRequestDto(
            studyResponseDto.getTitle(),
            studyResponseDto.getContent(),
            studyResponseDto.getHashtag(),
            studyResponseDto.getIsRecruit(),
            studyResponseDto.getRegion(),
            studyResponseDto.getIsOnline(),
            studyResponseDto.getHeadCount()
        );
    }
}
