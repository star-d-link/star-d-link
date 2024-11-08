package com.udemy.star_d_link.study.Controller;

import com.udemy.star_d_link.study.Dto.Response.ApiResponse;
import com.udemy.star_d_link.study.Dto.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.StudyListDto;
import com.udemy.star_d_link.study.Dto.StudyResponseDto;
import com.udemy.star_d_link.study.Dto.StudyUpdateRequestDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Mapper.StudyMapper;
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
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<ApiResponse<StudyResponseDto>> createStudy(
        @Valid @RequestBody StudyCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("작성 권한이 없습니다.");
        }

        StudyResponseDto responseDto = studyService.createStudy(requestDto);

        ApiResponse<StudyResponseDto> response = new ApiResponse<>(
            "success",
            "작성이 완료되었습니다.",
            responseDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{study_id}")
    public ResponseEntity<ApiResponse<StudyResponseDto>> getStudy(
        @PathVariable Long study_id) {
        // 서비스 계층에서 Study 엔티티 조회
        Study study = studyService.findByStudyId(study_id);

        // Study 엔티티를 StudyResponseDto로 변환
        StudyResponseDto responseDto = StudyMapper.toResponseDto(study);

        ApiResponse<StudyResponseDto> response = new ApiResponse<>(
            "success",
            "스터디 모집 글 조회 완료.",
            responseDto
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/{study_id}/edit")
    public ResponseEntity<ApiResponse<StudyUpdateRequestDto>> getStudyEditForm(
        @PathVariable Long study_id,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("인증이 필요합니다.");
        }

        Study study = studyService.findByStudyId(study_id);

        // studyResponseDto의 getUserId로 user의 닉네임을 받아와야 함
        // 임시로 nickname 설정
        String tempName = "test";

        if(!tempName.equals(currentUser.getUsername())) {
            throw new UnauthorizedException("접근 권한이 없습니다.");
        }

        StudyUpdateRequestDto responseDto = StudyMapper.toUpdateRequestDto(study);

        ApiResponse<StudyUpdateRequestDto> response = new ApiResponse<>(
            "success",
            "수정할 내용을 표시합니다.",
            responseDto
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{study_id}/edit")
    public ResponseEntity<ApiResponse<StudyResponseDto>> putStudyEditForm(
        @PathVariable Long study_id,
        @RequestBody StudyUpdateRequestDto requestDto,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null /*|| !currentUser.getUsername().equals(nickname)*/) {
            throw new UnauthorizedException("수정 권한이 없습니다.");
        }

        Study editStudy = studyService.editStudyByUserId(study_id, requestDto);

        // 실제로 적용할 때는 getUserId로 유저 정보를 찾아서 나온 nickname이나 다른 유저 정보로 수정
        if (!currentUser.getUsername().equals(editStudy.getUserId())) {
            throw new UnauthorizedException("수정 권한이 없습니다.");
        }

        StudyResponseDto studyResponseDto = StudyMapper.toResponseDto(editStudy);



        ApiResponse<StudyResponseDto> response = new ApiResponse<>(
            "success",
            "사용자 정보가 성공적으로 수정되었습니다.",
            studyResponseDto
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<StudyListDto>>> listStudy(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        Page<StudyListDto> dtoPage = studyService.getStudyList(page, size);

        ApiResponse<Page<StudyListDto>> response = new ApiResponse<>(
            "success",
            "스터디 목록 조회 완료",
            dtoPage
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
