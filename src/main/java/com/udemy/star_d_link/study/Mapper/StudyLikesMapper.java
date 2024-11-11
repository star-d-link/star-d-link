package com.udemy.star_d_link.study.Mapper;

import com.udemy.star_d_link.study.Dto.Response.StudyLikesResponseDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyLikes;

public class StudyLikesMapper {
    public static StudyLikesResponseDto toDto(StudyLikes studyLikes) {
        return new StudyLikesResponseDto(
            studyLikes.getLikeId(),
            studyLikes.getUserId(),
            studyLikes.getStudy().getUserId()
        );
    }

    public static StudyLikes toEntity(StudyLikesResponseDto Dto, Long userId, Study study) {
        return StudyLikes.builder()
            .likeId(Dto.getLikesId())
            .userId(userId)
            .study(study)
            .build();
    }
}
