package com.udemy.star_d_link.domain.groupboard.post.controller;

import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostCreateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostUpdateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostCreateResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostInquiryResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostUpdateResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.service.GroupPostService;
import com.udemy.star_d_link.global.common.dto.ResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class GroupPostController {

    private final GroupPostService groupPostService;

    @GetMapping("/{studyId}")
    public ResponseEntity<?> getPosts(
        @PathVariable("studyId") Long studyId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        // 게시글 리스트 조회
        var inquiryResponseDtoList = groupPostService.getList(studyId, page, size);

        ResponseDto<?> responseDto = ResponseDto.onSuccess(
            "스터디 그룹 게시글 목록을 성공적으로 조회하였습니다.",
            inquiryResponseDtoList
        );

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{studyId}/post/{postId}")
    public ResponseEntity<?> getPostDetail(
        @PathVariable("studyId") Long studyId,
        @PathVariable("postId") Long postId) {

        // 단일 게시글 조회
        GroupPostInquiryResponseDto postDetail = groupPostService.getPostDetail(studyId, postId);

        // 성공 응답 생성
        ResponseDto<?> responseDto = ResponseDto.onSuccess(
            "스터디 그룹 게시글을 성공적으로 조회하였습니다.",
            postDetail
        );

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{studyId}/board")
    public ResponseEntity<?> createPost(
        @PathVariable("studyId") Long studyId,
        @RequestBody GroupPostCreateRequestDto createRequestDto,
        @AuthenticationPrincipal UserDetails currentUser) {
        System.out.println("Received Study ID: " + studyId);
        System.out.println("User: " + currentUser.getUsername());
        System.out.println("Request DTO: " + createRequestDto);
        GroupPostCreateResponseDto createResponseDto = groupPostService.save(studyId, currentUser.getUsername(),
            createRequestDto);
        ResponseDto<?> responseDto = ResponseDto.onSuccess(
            "스터디 그룹 게시글을 성공적으로 생성하였습니다.",
            createResponseDto);

        return ResponseEntity.ok(responseDto);
    }

/*    @PutMapping("/{studyId}/post/{postId}")
    public ResponseEntity<?> updatePost(
        @PathVariable("studyId") Long studyId,
        @PathVariable("postId") Long postId,
        @RequestBody GroupPostUpdateRequestDto updateRequestDto) {
        GroupPostUpdateResponseDto updateResponseDto = groupPostService.update(studyId, updateRequestDto, postId);
        ResponseDto<?> responseDto = ResponseDto.onSuccess(
            "스터디 그룹 게시글을 성공적으로 수정하였습니다.",
            updateResponseDto);

        return ResponseEntity.ok(responseDto);
    }*/

    @DeleteMapping("/{studyId}/post/{postId}")
    public ResponseEntity<?> deletePost(
        @PathVariable("studyId") Long studyId,
        @PathVariable("postId") Long postId) {

        System.out.println("Received studyId: ");
        groupPostService.delete(studyId, postId);
        ResponseDto<?> responseDto = ResponseDto.onSuccess(
            "스터디 그룹 게시글을 성공적으로 삭제하였습니다.",
            null);

        return ResponseEntity.ok(responseDto);
    }
}
