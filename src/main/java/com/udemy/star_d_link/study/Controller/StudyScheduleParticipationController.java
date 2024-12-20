package com.udemy.star_d_link.study.Controller;

import com.udemy.star_d_link.study.Dto.Request.ParticipationRequestDto;
import com.udemy.star_d_link.study.Dto.Response.ApiResponse;
import com.udemy.star_d_link.study.Dto.Response.StudyScheduleParticipationResponseDto;
import com.udemy.star_d_link.study.Entity.StudyScheduleParticipation;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Service.StudyScheduleParticipationService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study/{study_id}/schedule/{schedule_id}/participation")
public class StudyScheduleParticipationController {
    private final StudyScheduleParticipationService participationService;
    @Autowired
    public StudyScheduleParticipationController(
        StudyScheduleParticipationService participationService) {
        this.participationService = participationService;
    }

    @PutMapping("/respond")
    public ResponseEntity<ApiResponse<StudyScheduleParticipationResponseDto>> respondToSchedule(
        @PathVariable("schedule_id") Long scheduleId,
        @RequestBody ParticipationRequestDto requestDto,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        StudyScheduleParticipation participation = participationService.updateParticipation(scheduleId, currentUser.getUsername(), requestDto);

        StudyScheduleParticipationResponseDto responseDto = StudyScheduleParticipationResponseDto.fromEntity(participation);

        ApiResponse<StudyScheduleParticipationResponseDto> response = new ApiResponse<>(
            "success",
            "스케쥴 참여 여부 수정 완료.",
            responseDto
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudyScheduleParticipationResponseDto>>> getParticipant(
        @PathVariable("schedule_id") Long scheduleId) {

        List<StudyScheduleParticipation> participation = participationService.getParticipation(scheduleId);
        List<StudyScheduleParticipationResponseDto> responseDto = participation.stream()
            .map(StudyScheduleParticipationResponseDto::fromEntity)
            .collect(Collectors.toList());

        ApiResponse<List<StudyScheduleParticipationResponseDto>> response = new ApiResponse<>(
            "success",
            "스케줄 참여자 목록 조회 성공",
            responseDto
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
