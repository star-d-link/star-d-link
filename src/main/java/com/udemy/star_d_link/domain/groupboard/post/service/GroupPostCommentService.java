package com.udemy.star_d_link.domain.groupboard.post.service;

import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostCommentCreateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostCommentUpdateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostCommentCreateResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostCommentInquiryResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostCommentUpdateResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostComment;
import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import com.udemy.star_d_link.domain.groupboard.post.repository.GroupPostCommentRepository;
import com.udemy.star_d_link.domain.groupboard.post.repository.GroupPostRepository;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.user.entity.UserEntity;
import com.udemy.star_d_link.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupPostCommentService {
    private final GroupPostRepository groupPostRepository;
    private final GroupPostCommentRepository groupPostCommentRepository;
    private final UserRepository userRepository;

    public List<GroupPostCommentInquiryResponseDto> getList(Long postId) {
        GroupPostEntity groupPostEntity = groupPostRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        List<GroupPostComment> commentList = groupPostCommentRepository.findByGroupPostEntity(groupPostEntity);

        return commentList.stream()
            .map(GroupPostCommentInquiryResponseDto::from)
            .toList();
    }
    @Transactional
    public GroupPostCommentCreateResponseDto save(Long postId,
        String username,
        GroupPostCommentCreateRequestDto createRequestDto) {

        GroupPostEntity groupPost = groupPostRepository.findById(postId)
            .orElseThrow(RuntimeException::new);
        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        GroupPostComment groupPostComment = GroupPostComment.of(createRequestDto, groupPost, userEntity);
        groupPostCommentRepository.save(groupPostComment);
        return GroupPostCommentCreateResponseDto.from(groupPostComment);
    }
    @Transactional
    public GroupPostCommentUpdateResponseDto update(Long postId,
        String username,
        GroupPostCommentUpdateRequestDto updateRequestDto,
        Long commentId) {
        GroupPostEntity groupPost = groupPostRepository.findById(postId)
            .orElseThrow(RuntimeException::new);
        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(RuntimeException::new);
        GroupPostComment groupPostComment = groupPostCommentRepository.findById(commentId)
            .orElseThrow(RuntimeException::new);
        if(userEntity != groupPostComment.getUserEntity())
            throw new UnauthorizedException("수정 권한이 없습니다.");

        groupPostComment.modify(updateRequestDto);
        groupPostCommentRepository.save(groupPostComment);
        return GroupPostCommentUpdateResponseDto.from(groupPostComment);
    }

    @Transactional
    public void delete(Long commentId, String username) {
        GroupPostComment groupPostComment = groupPostCommentRepository.findById(commentId)
            .orElseThrow(RuntimeException::new);
        //유저 검증
        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(RuntimeException::new);;
        if(userEntity != groupPostComment.getUserEntity())
            throw new UnauthorizedException("삭제 권한이 없습니다.");
        groupPostCommentRepository.delete(groupPostComment);
    }

}
