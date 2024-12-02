package com.udemy.star_d_link.study.Dto.Response;

import com.udemy.star_d_link.study.Entity.Study;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyResponseDto {
    private Long StudyId;
    private String username;
    private String title;
    private String content;
    private String hashtag;
    private Boolean isRecruit;
    private String region;
    private Double latitude;
    private Double longitude;
    private Boolean isOnline;
    private Integer headCount;
    private Integer LikesCount;
    private LocalDate createDate;

    public static StudyResponseDto fromEntity(Study study) {
        return new StudyResponseDto(
            study.getStudyId(),
            study.getUsername(),
            study.getTitle(),
            study.getContent(),
            study.getHashtag(),
            study.getIsRecruit(),
            study.getRegion(),
            study.getLatitude(),
            study.getLongitude(),
            study.getIsOnline(),
            study.getHeadCount(),
            study.getLikesCount(),
            study.getCreateDate()
        );
    }
}
