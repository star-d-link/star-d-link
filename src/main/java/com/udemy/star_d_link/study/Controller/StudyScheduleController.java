package com.udemy.star_d_link.study.Controller;

import com.udemy.star_d_link.study.Dto.Request.StudyScheduleAllUpdateRequestDto;
import com.udemy.star_d_link.study.Dto.Request.StudyScheduleCreateRequestDto;
import com.udemy.star_d_link.study.Dto.Request.StudyScheduleSingleUpdateRequestDto;
import com.udemy.star_d_link.study.Dto.Response.ApiResponse;
import com.udemy.star_d_link.study.Dto.Response.StudyScheduleResponseDto;
import com.udemy.star_d_link.study.Entity.StudySchedule;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Service.StudyMembersService;
import com.udemy.star_d_link.study.Service.StudyScheduleService;
import com.udemy.star_d_link.study.Service.StudyService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study/{study_id}/schedule")
public class StudyScheduleController {
    private final StudyScheduleService studyScheduleService;
    private final StudyMembersService studyMembersService;
    @Autowired
    public StudyScheduleController(StudyScheduleService studyScheduleService, StudyMembersService studyMembersService) {
        this.studyScheduleService = studyScheduleService;
        this.studyMembersService = studyMembersService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<StudyScheduleResponseDto>>> getScheduleList (
        @PathVariable("study_id") Long studyId,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        boolean hasPermission = studyMembersService.isMemberOfStudy(studyId, currentUser.getUsername());
        if (!hasPermission) {
            throw new UnauthorizedException("스터디 일정 조회 권한이 없습니다.");
        }

        List<StudyScheduleResponseDto> scheduleList = studyScheduleService.getScheduleList(studyId);
        ApiResponse<List<StudyScheduleResponseDto>> response = new ApiResponse<>(
            "success",
            "스터디 일정 목록 조회 완료",
            scheduleList
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/add")
    public String scheduleForm(
        @PathVariable("study_id") Long studyId,
        @AuthenticationPrincipal UserDetails currentUser){

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        if (!studyMembersService.hasManagementPermission(studyId, currentUser.getUsername())) {
            throw new UnauthorizedException("스터디 일정 관리 권한이 없습니다.");
        }

        return "signup";
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<StudyScheduleResponseDto>> addSchedule(
        @PathVariable("study_id") Long studyId,
        @Valid @RequestBody StudyScheduleCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        if (!studyMembersService.hasManagementPermission(studyId, currentUser.getUsername())) {
            throw new UnauthorizedException("스터디 일정 관리 권한이 없습니다.");
        }

        StudySchedule savedSchedule = studyScheduleService.addSchedule(studyId, requestDto);

        StudyScheduleResponseDto responseDto = StudyScheduleResponseDto.fromEntity(savedSchedule);

        ApiResponse<StudyScheduleResponseDto> response = new ApiResponse<>(
            "success",
            "스터디 일정 추가 완료",
            responseDto
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 반복 일정 전부 수정
    @PutMapping("/update-entire/{recurrence_group_id}")
    public ResponseEntity<ApiResponse<Void>> updateEntireSchedule(
        @PathVariable("study_id") Long studyId,
        @PathVariable("recurrence_group_id") Long recurrenceGroupId,
        @Valid @RequestBody StudyScheduleAllUpdateRequestDto requestDto,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        if (!studyMembersService.hasManagementPermission(studyId, currentUser.getUsername())) {
            throw new UnauthorizedException("스터디 일정 관리 권한이 없습니다.");
        }

        studyScheduleService.updateAllSchedule(recurrenceGroupId, requestDto);
        String redirectUrl = "/study/" + studyId + "/schedule";

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));

        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).build();
    }

    // 특정 일정만 수정
    @PutMapping("/update-single/{schedule_id}")
    public ResponseEntity<ApiResponse<Void>> updateSingleSchedule(
        @PathVariable("study_id") Long studyId,
        @PathVariable("schedule_id") Long scheduleId,
        @Valid @RequestBody StudyScheduleSingleUpdateRequestDto requestDto,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        if (!studyMembersService.hasManagementPermission(studyId, currentUser.getUsername())) {
            throw new UnauthorizedException("스터디 일정 관리 권한이 없습니다.");
        }

        studyScheduleService.updateSingleSchedule(scheduleId, requestDto);
        String redirectUrl = "/study/" + studyId + "/schedule";

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));

        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).build();
    }


    @DeleteMapping("/{schedule_id}/delete") public ResponseEntity<ApiResponse<Void>> deleteSchedule(
        @PathVariable("study_id") Long studyId,
        @PathVariable("schedule_id") Long scheduleId,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        boolean hasManagementPermission = studyMembersService.hasManagementPermission(studyId, currentUser.getUsername());
        if (!hasManagementPermission) {
            throw new UnauthorizedException("스터디 일정 삭제 권한이 없습니다.");
        }

        studyScheduleService.deleteSchedule(scheduleId);

        String redirectUrl = "/study/" + studyId + "/schedule";

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));

        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).build();
    }
}
