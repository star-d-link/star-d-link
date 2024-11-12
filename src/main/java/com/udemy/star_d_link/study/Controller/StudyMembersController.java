package com.udemy.star_d_link.study.Controller;

import com.udemy.star_d_link.study.Dto.Response.ApiResponse;
import com.udemy.star_d_link.study.Dto.Response.StudyMemberResponseDto;
import com.udemy.star_d_link.study.Entity.Role;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Mapper.StudyMembersMapper;
import com.udemy.star_d_link.study.Service.StudyMembersService;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study/{study_id}/manage")
public class StudyMembersController {
    private final StudyMembersService studyMembersService;

    public StudyMembersController(StudyMembersService studyMembersService) {
        this.studyMembersService = studyMembersService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<Page<StudyMemberResponseDto>>> getMemberList(
        @PathVariable("study_id") Long studyId,
        @AuthenticationPrincipal UserDetails currentUser,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        // 실제로 적용할 때는 currentUser의 정보를 바탕으로 userId 사용 후 권한 확인
        Long tempId = 1L;
        boolean hasPermission = studyMembersService.hasPermission(studyId, tempId);
        if (!hasPermission) {
            throw new UnauthorizedException("스터디 멤버 관리 권한이 없습니다.");
        }

        Page<StudyMemberResponseDto> dtoPage = studyMembersService.getMemberList(page, size);

        ApiResponse<Page<StudyMemberResponseDto>> response = new ApiResponse<>(
            "success",
            "스터디 목록 조회 완료",
            dtoPage
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{member_id}/accept")
    public ResponseEntity<ApiResponse<StudyMemberResponseDto>> acceptMember(
        @PathVariable("study_id") Long studyId,
        @PathVariable("member_id") Long memberId,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        // 실제로 적용할 때는 currentUser의 정보를 바탕으로 userId 사용 후 권한 확인
        Long tempId = 1L;
        boolean hasPermission = studyMembersService.hasPermission(studyId, tempId);

        if (!hasPermission) {
            throw new UnauthorizedException("스터디 멤버 관리 권한이 없습니다.");
        }

        StudyMemberResponseDto responseDto = studyMembersService.acceptMember(studyId, memberId);

        String redirectUrl = "/study/" + studyId + "/manage";

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));

        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }

    @DeleteMapping("/{member_id}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectMember(
        @PathVariable("study_id") Long studyId,
        @PathVariable("member_id") Long memberId,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        // 실제로 적용할 때는 currentUser의 정보를 바탕으로 userId 사용 후 권한 확인
        Long tempId = 1L;
        boolean hasPermission = studyMembersService.hasPermission(studyId, tempId);

        if (!hasPermission) {
            throw new UnauthorizedException("스터디 멤버 관리 권한이 없습니다.");
        }

        studyMembersService.rejectMember(studyId, tempId);

        String redirectUrl = "/study/" + studyId + "/manage";

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));

        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }

    @PutMapping("/role")
    public ResponseEntity<ApiResponse<StudyMemberResponseDto>> changeMemberRole(
        @PathVariable("study_id") Long studyId,
        @RequestParam("user_id") Long userId,
        @RequestParam("role") Role newRole,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        // 현재 사용자의 userId 확인
        Long currentUserId = 1L; // 임시로 userId를 설정합니다. 실제로는 currentUser에서 가져옵니다.

        StudyMembers updatedMember = studyMembersService.changeMemberRole(studyId, userId, newRole, currentUserId);

        StudyMemberResponseDto responseDto = StudyMembersMapper.toResponseDto(updatedMember);

        ApiResponse<StudyMemberResponseDto> response = new ApiResponse<>(
            "success",
            "멤버 역할 변경이 완료되었습니다.",
            responseDto
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
