package com.udemy.star_d_link.study.Dto.Response;

import com.udemy.star_d_link.study.Entity.Study;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyListResponseDto {
    private Long studyId;
    private String username;
    private String title;
    private Boolean isRecruit;
    private String region;
    private Boolean isOnline;
    private LocalDate createDate;

    public static StudyListResponseDto fromEntity(Study study) {
        return new StudyListResponseDto(
            study.getStudyId(),
            study.getUsername(),
            study.getTitle(),
            study.getIsRecruit(),
            study.getRegion(),
            study.getIsOnline(),
            study.getCreateDate()
        );
    }
}
