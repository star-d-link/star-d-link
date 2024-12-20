package com.udemy.star_d_link.domain.groupboard.post.controller;

import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostCreateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostUpdateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostCreateResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostInquiryResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostUpdateResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.service.GroupPostService;
import com.udemy.star_d_link.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study/group")
@Slf4j
public class GroupPostController {

    private final GroupPostService groupPostService;

    @GetMapping("/{studyId}")
    public ResponseEntity<?> getPosts(
        @PathVariable("studyId") Long studyId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        // 게시글 리스트 조회
        var inquiryResponseDtoList = groupPostService.getList(studyId, page, size);


        return ResponseEntity.ok(inquiryResponseDtoList);
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

        return ResponseEntity.ok(postDetail);
    }

    @PostMapping("/{studyId}/board")
    public ResponseEntity<?> createPost(
        @PathVariable("studyId") Long studyId,
        @RequestBody GroupPostCreateRequestDto createRequestDto,
        @AuthenticationPrincipal UserDetails currentUser) {

        GroupPostCreateResponseDto createResponseDto = groupPostService.save(studyId, currentUser.getUsername(),
            createRequestDto);

        return ResponseEntity.ok(createResponseDto);
    }

    @PutMapping("/{studyId}/post/{postId}")
    public ResponseEntity<?> updatePost(
        @PathVariable("studyId") Long studyId,
        @PathVariable("postId") Long postId,
        @RequestBody GroupPostUpdateRequestDto updateRequestDto,
        @AuthenticationPrincipal UserDetails currentUser) {
        GroupPostUpdateResponseDto updateResponseDto = groupPostService.update(studyId, currentUser.getUsername(),
            updateRequestDto, postId);

        return ResponseEntity.ok(updateResponseDto);
    }

    @DeleteMapping("/{studyId}/post/{postId}")
    public ResponseEntity<?> deletePost(
        @PathVariable("studyId") Long studyId,
        @PathVariable("postId") Long postId,
        @AuthenticationPrincipal UserDetails currentUser) {

        groupPostService.delete(studyId, postId, currentUser.getUsername());
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
