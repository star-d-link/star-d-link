package com.udemy.star_d_link.study.Dto.Response;

import com.udemy.star_d_link.study.Entity.StudyLikes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyLikesResponseDto {
    private Long likesId;
    private Long userId;
    private Long studyId;

    public static StudyLikesResponseDto fromEntity(StudyLikes studyLikes) {
        return new StudyLikesResponseDto(
            studyLikes.getLikeId(),
            studyLikes.getUser().getUserId(),
            studyLikes.getStudy().getStudyId()
        );
    }
}
