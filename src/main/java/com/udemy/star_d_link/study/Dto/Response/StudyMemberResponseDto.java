package com.udemy.star_d_link.study.Dto.Response;

import com.udemy.star_d_link.study.Entity.StudyMembers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyMemberResponseDto {
    private Long studyManageId;
    private String username;
    private Long StudyId;
    private String  role;
    private String status;

    public static StudyMemberResponseDto fromEntity(StudyMembers studyMember) {
        return new StudyMemberResponseDto(
            studyMember.getStudyManageId(),
            studyMember.getUsername(),
            studyMember.getStudy().getStudyId(),
            studyMember.getRole().name(),
            studyMember.getStatus()
        );
    }
}
