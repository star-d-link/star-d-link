package com.udemy.star_d_link.study.Controller;

import com.udemy.star_d_link.study.Dto.Response.ApiResponse;
import com.udemy.star_d_link.study.Dto.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.StudyLikesDto;
import com.udemy.star_d_link.study.Dto.StudyResponseDto;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Service.StudyLikesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study/{study_id}/like")
public class StudyLikesController {
    private final StudyLikesService studyLikesService;

    @Autowired
    public StudyLikesController(StudyLikesService studyLikesService) {
        this.studyLikesService = studyLikesService;
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<StudyLikesDto>> addLikes(
        @PathVariable("study_id") Long studyId,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        // 서비스 계층으로 study_id와 currentUser 전달하여 권한 확인 및 데이터 조회
        Long tempId = 1L;
        StudyLikesDto responseDto = studyLikesService.addLikes(studyId, tempId);

        ApiResponse<StudyLikesDto> response = new ApiResponse<>(
            "success",
            "작성이 완료되었습니다.",
            responseDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private Long getUserIdFromUserDetails(UserDetails currentUser) {
        // userRepository나 UserService 등을 통해 userId를 조회하는 로직 필요
        // 여기서는 임시로 하드코딩된 예시 사용
        return 1L;  // 실제로는 currentUser.getUsername()을 통해 User 엔티티에서 조회해야 합니다.
    }

}
