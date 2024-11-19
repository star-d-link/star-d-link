package com.udemy.star_d_link.study.Dto.Response;

import com.udemy.star_d_link.study.Entity.Role;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyMemberResponseDto {
    private Long studyManageId;
    private Long userId;
    private Long StudyId;
    private String  role;
    private String status;

    public static StudyMemberResponseDto fromEntity(StudyMembers studyMember) {
        return new StudyMemberResponseDto(
            studyMember.getStudyManageId(),
            studyMember.getUser().getUserId(),
            studyMember.getStudy().getStudyId(),
            studyMember.getRole().name(),
            studyMember.getStatus()
        );
    }
}
