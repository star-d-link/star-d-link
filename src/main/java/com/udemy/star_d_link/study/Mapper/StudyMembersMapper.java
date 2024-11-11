package com.udemy.star_d_link.study.Mapper;

import com.udemy.star_d_link.study.Dto.Response.StudyMemberResponseDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyMembers;

public class StudyMembersMapper {
    public static StudyMembers toEntity(Long userId, Study study, String role) {
        return StudyMembers.builder()
            .userId(userId)
            .study(study)
            .role(role)
            .status("대기중") // 기본 상태 설정
            .build();
    }

    public static StudyMemberResponseDto toResponseDto(StudyMembers studyMembers) {
        return new StudyMemberResponseDto(
            studyMembers.getStudyManageId(),
            studyMembers.getUserId(),
            studyMembers.getStudy().getStudyId(),
            studyMembers.getRole(),
            studyMembers.getStatus()
        );
    }

    public static StudyMembers updateStatusToActive(StudyMembers member) {
        return StudyMembers.builder()
            .studyManageId(member.getStudyManageId())
            .userId(member.getUserId())
            .study(member.getStudy())
            .role(member.getRole())
            .status("참여중")
            .build();
    }
}
