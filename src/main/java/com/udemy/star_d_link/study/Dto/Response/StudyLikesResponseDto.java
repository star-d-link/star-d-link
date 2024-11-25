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
    private String username;
    private Long studyId;

    public static StudyLikesResponseDto fromEntity(StudyLikes studyLikes) {
        return new StudyLikesResponseDto(
            studyLikes.getLikeId(),
            studyLikes.getUsername(),
            studyLikes.getStudy().getStudyId()
        );
    }
}
