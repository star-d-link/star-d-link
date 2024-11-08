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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupPostController {

    private final GroupPostService groupPostService;

    @GetMapping("/{group-id}/post/{last-post-id}")
    public ResponseEntity<?> getPosts(/*시큐리티 객체 받기*/@PathVariable("group-id") Long groupId,
        @PathVariable(value = "last-post-id", required = false) Long lastPostId) {
        List<GroupPostInquiryResponseDto> inquiryResponseDtoList = groupPostService.getSome(groupId,
            lastPostId);
        ResponseDto<?> responseDto = ResponseDto.onSuccess("스터디 그룹 게시글을 성공적으로 조회하였습니다.",
            inquiryResponseDtoList);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{group-id}/post")
    public ResponseEntity<?> createPost(/*시큐리티 객체 받기*/@PathVariable("group-id") Long groupId,
        @RequestBody GroupPostCreateRequestDto createRequestDto) {
        GroupPostCreateResponseDto createResponseDto = groupPostService.save(groupId,
            createRequestDto);
        ResponseDto<?> responseDto = ResponseDto.onSuccess("스터디 그룹 게시글을 성공적으로 생성하였습니다.",
            createResponseDto);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{group-id}/post/{post-id}")
    public ResponseEntity<?> updatePost(/*시큐리티 객체 받기*/@PathVariable("group-id") Long groupId,
        @RequestBody GroupPostUpdateRequestDto updateRequestDto,
        @PathVariable("post-id") Long postId) {
        GroupPostUpdateResponseDto updateResponseDto = groupPostService.update(groupId,
            updateRequestDto, postId);
        ResponseDto<?> responseDto = ResponseDto.onSuccess("스터디 그룹 게시글을 성공적으로 수정하였습니다.",
            updateResponseDto);

        return ResponseEntity.ok(responseDto);
    }
}
