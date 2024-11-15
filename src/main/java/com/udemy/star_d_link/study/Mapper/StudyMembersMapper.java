package com.udemy.star_d_link.study.Mapper;

import com.udemy.star_d_link.study.Dto.Response.StudyMemberResponseDto;
import com.udemy.star_d_link.study.Entity.Role;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import com.udemy.star_d_link.study.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudyMembersMapper {

    StudyMembersMapper INSTANCE = Mappers.getMapper(StudyMembersMapper.class);

    StudyMembers toEntity(User user, Study study, Role role);

    @Mapping(target = "studyManageId", ignore = true) // 새로운 엔티티 생성 시 studyManageId는 무시
    StudyMemberResponseDto toDto(StudyMembers studyMembers);

    @Mapping(target = "status", constant = "참여중")
    void updateStatusToActive(@MappingTarget StudyMembers member);

}
