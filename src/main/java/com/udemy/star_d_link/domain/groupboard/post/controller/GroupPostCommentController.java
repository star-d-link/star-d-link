package com.udemy.star_d_link.domain.groupboard.post.controller;

import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostCommentCreateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostCommentUpdateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostCommentCreateResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostCommentUpdateResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.service.GroupPostCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
@Slf4j
public class GroupPostCommentController {
    private final GroupPostCommentService groupPostCommentService;

    @GetMapping("/{studyId}/post/{postId}/comment")
    public ResponseEntity<?> getComment(
        @PathVariable("postId") Long postId) {
        var inquiryResponseDtoList = groupPostCommentService.getList(postId);
        return ResponseEntity.ok(inquiryResponseDtoList);
    }
    @PostMapping("/{studyId}/post/{postId}/comment")
    public ResponseEntity<?> createPost(
        @PathVariable("postId") Long postId,
        @RequestBody GroupPostCommentCreateRequestDto createRequestDto,
        @AuthenticationPrincipal UserDetails currentUser) {

        GroupPostCommentCreateResponseDto createResponseDto = groupPostCommentService
            .save(postId, currentUser.getUsername(),
            createRequestDto);

        return ResponseEntity.ok(createResponseDto);
    }

    @PutMapping("/{studyId}/post/{postId}/comment/{commentId}")
    public ResponseEntity<?> updatePost(
        @PathVariable("commentId") Long commentId,
        @PathVariable("postId") Long postId,
        @RequestBody GroupPostCommentUpdateRequestDto updateRequestDto,
        @AuthenticationPrincipal UserDetails currentUser) {
        GroupPostCommentUpdateResponseDto updateResponseDto = groupPostCommentService
            .update(postId, currentUser.getUsername(),
            updateRequestDto, commentId);

        return ResponseEntity.ok(updateResponseDto);
    }

    @DeleteMapping("/{studyId}/post/{postId}/comment/{commentId}")
    public ResponseEntity<?> deletePost(
        @PathVariable("commentId") Long commentId,
        @AuthenticationPrincipal UserDetails currentUser) {

        groupPostCommentService.delete(commentId, currentUser.getUsername());
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
