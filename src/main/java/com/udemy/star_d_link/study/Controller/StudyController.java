package com.udemy.star_d_link.study.Controller;

import com.udemy.star_d_link.study.Dto.Response.ApiResponse;
import com.udemy.star_d_link.study.Dto.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Service.StudyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
