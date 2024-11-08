package com.udemy.star_d_link.study.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyUpdateRequestDto {

    @NotEmpty(message = "스터디 제목은 필수 입력 항목입니다.")
    private String title;

    @NotEmpty(message = "스터디 내용은 필수 입력 항목입니다.")
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
