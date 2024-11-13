package com.udemy.star_d_link.study.Mapper;

import com.udemy.star_d_link.study.Dto.Response.StudyLikesResponseDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyLikes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudyLikesMapper {

    StudyLikesMapper INSTANCE = Mappers.getMapper(StudyLikesMapper.class);

    // StudyLikes 엔티티를 StudyLikesResponseDto로 변환하는 메소드
    StudyLikesResponseDto toDto(StudyLikes studyLikes);

    // StudyLikesResponseDto를 StudyLikes 엔티티로 변환하는 메소드
    @Mapping(target = "likeId", ignore = true) // 새로운 엔티티 생성 시 likeId는 무시
    StudyLikes toEntity(StudyLikesResponseDto dto, Long userId, Study study);
}
