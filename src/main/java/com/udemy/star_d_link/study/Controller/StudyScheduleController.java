package com.udemy.star_d_link.study.Controller;

import com.udemy.star_d_link.study.Dto.Request.StudyScheduleAllUpdateRequestDto;
import com.udemy.star_d_link.study.Dto.Request.StudyScheduleCreateRequestDto;
import com.udemy.star_d_link.study.Dto.Request.StudyScheduleSingleUpdateRequestDto;
import com.udemy.star_d_link.study.Dto.Response.ApiResponse;
import com.udemy.star_d_link.study.Dto.Response.StudyScheduleResponseDto;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Service.StudyMembersService;
import com.udemy.star_d_link.study.Service.StudyScheduleService;
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

        // 실제로 적용할 때는 currentUser의 정보를 바탕으로 userId 사용 후 권한 확인
        Long tempId = 1L;
        boolean hasPermission = studyMembersService.isMemberOfStudy(studyId, tempId);
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

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<StudyScheduleResponseDto>> addSchedule(
        @PathVariable("study_id") Long studyId,
        @Valid @RequestBody StudyScheduleCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        // 실제로 적용할 때는 currentUser의 정보를 바탕으로 userId 사용 후 권한 확인
        Long tempId = 1L;
        boolean hasPermission = studyMembersService.hasPermission(studyId, tempId);
        if (!hasPermission) {
            throw new UnauthorizedException("스터디 일정 관리 권한이 없습니다.");
        }

        StudyScheduleResponseDto responseDto = studyScheduleService.addSchedule(studyId, requestDto);

        String redirectUrl = "/study/" + studyId + "/schedule";

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));

        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).build();
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

        // 실제로 적용할 때는 currentUser의 정보를 바탕으로 userId 사용 후 권한 확인
        Long tempId = 1L;
        boolean hasPermission = studyMembersService.hasPermission(studyId, tempId);
        if (!hasPermission) {
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

        // 실제로 적용할 때는 currentUser의 정보를 바탕으로 userId 사용 후 권한 확인
        Long tempId = 1L;
        boolean hasPermission = studyMembersService.hasPermission(studyId, tempId);
        if (!hasPermission) {
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

        // 실제로 적용할 때는 currentUser의 정보를 바탕으로 userId 사용 후 권한 확인
        Long tempId = 1L;
        boolean hasPermission = studyMembersService.hasPermission(studyId, tempId);
        if (!hasPermission) {
            throw new UnauthorizedException("스터디 일정 관리 권한이 없습니다.");
        }

        studyScheduleService.deleteSchedule(scheduleId);

        String redirectUrl = "/study/" + studyId + "/schedule";

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));

        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).build();
    }
}
