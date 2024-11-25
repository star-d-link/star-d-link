package com.udemy.star_d_link.study.Controller;

import com.udemy.star_d_link.study.Dto.Response.ApiResponse;
import com.udemy.star_d_link.study.Dto.Request.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.Response.StudyListResponseDto;
import com.udemy.star_d_link.study.Dto.Response.StudyMemberResponseDto;
import com.udemy.star_d_link.study.Dto.Response.StudyResponseDto;
import com.udemy.star_d_link.study.Dto.Request.StudyUpdateRequestDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Service.StudyMembersService;
import com.udemy.star_d_link.study.Service.StudyService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/study")
public class StudyController {
    private final StudyService studyService;
    private final StudyMembersService studyMembersService;
    @Autowired
    public StudyController(StudyService studyService, StudyMembersService studyMembersService) {
        this.studyService = studyService;
        this.studyMembersService = studyMembersService;
    }

    @GetMapping(value = "/create")
    public String signForm(){
        return "signup";
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<StudyResponseDto>> createStudy(
        @Valid @RequestBody StudyCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("작성 권한이 없습니다.");
        }
        Study savedStudy = studyService.createStudy(requestDto, currentUser.getUsername());

        StudyResponseDto responseDto = StudyResponseDto.fromEntity(savedStudy);

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
        StudyResponseDto responseDto = StudyResponseDto.fromEntity(study);

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
        Study study = studyService.getStudyForEdit(study_id, currentUser.getUsername());

        StudyUpdateRequestDto responseDto = StudyUpdateRequestDto.fromEntity(study);

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

        Study editStudy = studyService.editStudyByUserId(study_id, currentUser.getUsername(), requestDto);

        StudyResponseDto studyResponseDto = StudyResponseDto.fromEntity(editStudy);

        ApiResponse<StudyResponseDto> response = new ApiResponse<>(
            "success",
            "사용자 정보가 성공적으로 수정되었습니다.",
            studyResponseDto
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{study_id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudy(
        @PathVariable Long study_id,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("인증이 필요합니다.");
        }

        studyService.deleteStudyByUserId(study_id, currentUser.getUsername());

        ApiResponse<Void> response = new ApiResponse<>(
            "success",
            "모집 글 삭제가 완료되었습니다.",
            null
        );

        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<StudyListResponseDto>>> listStudy(
        @PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(value = "isOnline", required = false) Boolean isOnline,
        @RequestParam(value = "region", required = false) String region,
        @RequestParam(value = "isRecruit", required = false) Boolean isRecruit) {

        Page<Study> studyPage = studyService.getStudyList(isOnline, region, isRecruit, pageable);

        Page<StudyListResponseDto> dtoPage = studyPage.map(StudyListResponseDto::fromEntity);

        ApiResponse<Page<StudyListResponseDto>> response = new ApiResponse<>(
            "success",
            "스터디 목록 조회 완료",
            dtoPage
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 참여 신청
    @PostMapping("/{study_id}/apply")
    public ResponseEntity<ApiResponse<StudyMemberResponseDto>> applyStudy(
        @PathVariable("study_id") Long studyId,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        StudyMembers applyMember = studyMembersService.applyStudy(studyId, currentUser.getUsername());

        StudyMemberResponseDto responseDto = StudyMemberResponseDto.fromEntity(applyMember);

        String redirectUrl = "/study/" + studyId;

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));

        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);

    }


    // 제목 또는 내용이 같을 때 결과를 출력하는 일반 검색
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<StudyResponseDto>>> searchStudy(
        @RequestParam("keyword") String keyword,
        @PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Study> studyPage = studyService.searchStudy(keyword, pageable);

        Page<StudyResponseDto> dtoPage = studyPage.map(StudyResponseDto::fromEntity);

        ApiResponse<Page<StudyResponseDto>> response = new ApiResponse<>(
            "success",
            "검색 결과 조회 완료",
            dtoPage
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 상세 검색 (해시 태그 검색 기능만 넣었음 필요에 따라 추가 가능)
    @GetMapping("/detailed-search")
    public ResponseEntity<ApiResponse<Page<StudyResponseDto>>> detailedSearchStudy(
        @RequestParam("hashtag") String hashtag,
        @PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Study> detailedStudyPage = studyService.detailedSearchStudy(hashtag, pageable);

        Page<StudyResponseDto> dtoPage = detailedStudyPage.map(StudyResponseDto::fromEntity);

        ApiResponse<Page<StudyResponseDto>> response = new ApiResponse<>(
            "success",
            "상세 검색 결과 조회 완료",
            dtoPage
        );


        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
