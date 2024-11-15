package com.udemy.star_d_link.study.Controller;

import com.udemy.star_d_link.study.Dto.Request.ParticipationRequestDto;
import com.udemy.star_d_link.study.Dto.Request.StudyUpdateRequestDto;
import com.udemy.star_d_link.study.Dto.Response.ApiResponse;
import com.udemy.star_d_link.study.Entity.StudyScheduleParticipation;
import com.udemy.star_d_link.study.Entity.User;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Service.StudyScheduleParticipationService;
import com.udemy.star_d_link.study.Service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study/{study_id}/schedule/{schedule_id}/participation")
public class StudyScheduleParticipationController {
    private final StudyScheduleParticipationService participationService;
    private final StudyService studyService;
    @Autowired
    public StudyScheduleParticipationController(
        StudyScheduleParticipationService participationService, StudyService studyService) {
        this.participationService = participationService;
        this.studyService = studyService;
    }

    @PutMapping("/respond")
    public ResponseEntity<ApiResponse<StudyScheduleParticipation>> respondToSchedule(
        @PathVariable("schedule_id") Long scheduleId,
        @RequestBody ParticipationRequestDto requestDto,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        User user = studyService.findUserByUsername(currentUser.getUsername());
        StudyScheduleParticipation responseDto = participationService.addParticipation(user, requestDto);

        ApiResponse<StudyScheduleParticipation> response = new ApiResponse<>(
            "success",
            "스케쥴 참여 여부 등록 완료.",
            responseDto
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
