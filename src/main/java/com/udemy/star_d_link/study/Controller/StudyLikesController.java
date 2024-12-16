package com.udemy.star_d_link.study.Controller;

import com.udemy.star_d_link.study.Dto.Response.ApiResponse;
import com.udemy.star_d_link.study.Dto.Response.StudyLikesResponseDto;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Service.StudyLikesService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study/{study_id}")
public class StudyLikesController {
    private final StudyLikesService studyLikesService;
    @Autowired
    public StudyLikesController(StudyLikesService studyLikesService) {
        this.studyLikesService = studyLikesService;
    }

    @PostMapping("/like")
    public ResponseEntity<ApiResponse<StudyLikesResponseDto>> addLikes(
        @PathVariable("study_id") Long studyId,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        studyLikesService.addLikes(studyId, currentUser.getUsername());

        String redirectUrl = "/study/" + studyId;

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }

    @DeleteMapping("/like")
    public ResponseEntity<ApiResponse<Void>> deleteLikes(
        @PathVariable("study_id") Long studyId,
        @AuthenticationPrincipal UserDetails currentUser) {

        if (currentUser == null) {
            throw new UnauthorizedException("권한이 없습니다.");
        }

        studyLikesService.deleteLikes(studyId, currentUser.getUsername());

        ApiResponse<Void> response = new ApiResponse<>(
            "success",
            "모집 글 삭제가 완료되었습니다.",
            null
        );

        String redirectUrl = "/study/" + studyId;

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
}
