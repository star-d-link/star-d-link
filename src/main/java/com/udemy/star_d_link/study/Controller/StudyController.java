package com.udemy.star_d_link.study.Controller;

import com.udemy.star_d_link.study.Dto.Response.ApiResponse;
import com.udemy.star_d_link.study.Dto.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.StudyListDto;
import com.udemy.star_d_link.study.Dto.StudyResponseDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Service.StudyService;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/study")
public class StudyController {
    private final StudyService studyService;

    @Autowired
    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    @GetMapping(value = "create")
    public String signForm(){
        return "signup";
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<Study>> createStudy(
        @Valid @RequestBody StudyCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            ApiResponse<Study> response = new ApiResponse<>(
                "error",
                "작성 권한이 없습니다.",
                null
            );
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        try {
            Study newStudy = studyService.createStudy(requestDto, currentUser.getUsername());

            ApiResponse<Study> response = new ApiResponse<>(
                "success",
                "작성이 완료되었습니다.",
                newStudy
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<Study> response = new ApiResponse<>(
                "error",
                "작성 형식이 올바르지 않습니다.",
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{study_id}")
    public ResponseEntity<ApiResponse<StudyResponseDto>> getStudy(
        @PathVariable Long study_id) {
        try {

            StudyResponseDto studyDto = studyService.findByStudyId(study_id);

            ApiResponse<StudyResponseDto> response = new ApiResponse<>(
                "success",
                "스터디 모집 글 조회 완료.",
                studyDto
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NoSuchElementException e) {
            ApiResponse<StudyResponseDto> response = new ApiResponse<>(
                "error",
                "글이 존재하지 않습니다.",
                null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<StudyListDto>>> listStudy(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        Page<Study> studyPage = studyService.getStudyList(page, size);
        Page<StudyListDto> dtoPage = studyPage.map(study -> new StudyListDto(
            study.getStudyId(),
            study.getTitle(),
            study.getIsRecruit(),
            study.getRegion(),
            study.getIsOnline(),
            study.getCreateDate()
        ));

        ApiResponse<Page<StudyListDto>> response = new ApiResponse<>(
            "success",
            "스터디 목록 조회 완료",
            dtoPage
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
