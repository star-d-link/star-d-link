package com.udemy.star_d_link.study.Mapper;

import com.udemy.star_d_link.study.Dto.StudyLikesDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyLikes;

public class StudyLikesMapper {
    public static StudyLikesDto toDto(StudyLikes studyLikes) {
        return new StudyLikesDto(
            studyLikes.getLikeId(),
            studyLikes.getUserId(),
            studyLikes.getStudy().getUserId()
        );
    }

    public static StudyLikes toEntity(StudyLikesDto Dto, Long userId, Study study) {
        return StudyLikes.builder()
            .likeId(Dto.getLikesId())
            .userId(userId)
            .study(study)
            .build();
    }
}
