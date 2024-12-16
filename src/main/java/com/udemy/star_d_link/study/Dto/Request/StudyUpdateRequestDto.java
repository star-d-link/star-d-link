package com.udemy.star_d_link.study.Dto.Request;

import com.udemy.star_d_link.study.Dto.Response.StudyResponseDto;
import com.udemy.star_d_link.study.Entity.Study;
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
    private String region;
    private Boolean isOnline;
    private Integer headCount;

    private Double latitude;
    private Double longitude;

    public static StudyUpdateRequestDto fromEntity(Study study) {
        return new StudyUpdateRequestDto(
            study.getTitle(),
            study.getContent(),
            study.getHashtag(),
            study.getRegion(),
            study.getIsOnline(),
            study.getHeadCount(),
            study.getLatitude(),
            study.getLongitude()
        );
    }
}
