package com.udemy.star_d_link.study.Controller;

import com.udemy.star_d_link.study.Dto.Response.ApiResponse;
import com.udemy.star_d_link.study.Dto.Response.StudyMemberResponseDto;
import com.udemy.star_d_link.study.Entity.Role;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Service.StudyMembersService;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, List<StudyMemberResponseDto>>>> getMemberLists(
        @PathVariable("study_id") Long studyId,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        // 서비스 계층 호출
        Map<String, List<StudyMembers>> memberLists = studyMembersService.getMemberList(studyId);

        // 각 리스트를 DTO로 변환
        Map<String, List<StudyMemberResponseDto>> responseData = new HashMap<>();
        responseData.put("admin", memberLists.get("admin").stream()
            .map(StudyMemberResponseDto::fromEntity)
            .collect(Collectors.toList()));
        responseData.put("joined", memberLists.get("joined").stream()
            .map(StudyMemberResponseDto::fromEntity)
            .collect(Collectors.toList()));

        ApiResponse<Map<String, List<StudyMemberResponseDto>>> response = new ApiResponse<>(
            "success",
            "스터디 멤버 목록 조회 성공",
            responseData
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{member_id}/accept")
    public ResponseEntity<ApiResponse<StudyMemberResponseDto>> acceptMember(
        @PathVariable("study_id") Long studyId,
        @PathVariable("member_id") Long memberId,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        boolean hasPermission = studyMembersService.hasPermission(studyId, currentUser.getUsername());

        if (!hasPermission) {
            throw new UnauthorizedException("스터디 멤버 관리 권한이 없습니다.");
        }

        StudyMembers acceptedMember = studyMembersService.acceptMember(studyId, memberId);

        StudyMemberResponseDto responseDto = StudyMemberResponseDto.fromEntity(acceptedMember);

        return ResponseEntity.ok(new ApiResponse<>("success", "참여가 승인되었습니다.", responseDto));
    }

    @DeleteMapping("/{member_id}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectMember(
        @PathVariable("study_id") Long studyId,
        @PathVariable("member_id") Long memberId,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        boolean hasPermission = studyMembersService.hasPermission(studyId, currentUser.getUsername());

        if (!hasPermission) {
            throw new UnauthorizedException("스터디 멤버 관리 권한이 없습니다.");
        }

        // memberId를 사용하여 삭제 처리
        studyMembersService.rejectMember(studyId, memberId);

        return ResponseEntity.ok(new ApiResponse<>("success", "멤버가 거절되었습니다.", null));
    }
    @PutMapping("/role")
    public ResponseEntity<ApiResponse<StudyMemberResponseDto>> changeMemberRole(
        @PathVariable("study_id") Long studyId,
        @RequestParam("memberName") String memberName,
        @RequestParam("role") Role newRole,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        StudyMembers updatedMember = studyMembersService.changeMemberRole(studyId, currentUser.getUsername(), newRole, memberName);

        StudyMemberResponseDto responseDto = StudyMemberResponseDto.fromEntity(updatedMember);

        ApiResponse<StudyMemberResponseDto> response = new ApiResponse<>(
            "success",
            "멤버 역할 변경이 완료되었습니다.",
            responseDto
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
