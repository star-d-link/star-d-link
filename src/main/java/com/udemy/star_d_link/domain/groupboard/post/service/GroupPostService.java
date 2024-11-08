package com.udemy.star_d_link.domain.groupboard.post.service;

import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostCreateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostUpdateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostCreateResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostInquiryResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostUpdateResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostFileEntity;
import com.udemy.star_d_link.domain.groupboard.post.repository.GroupPostFileRepository;
import com.udemy.star_d_link.domain.groupboard.post.repository.GroupPostRepository;
import com.udemy.star_d_link.global.common.service.ImageUploadService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupPostService {

    private final GroupPostRepository groupPostRepository;
    private final GroupPostFileRepository groupPostFileRepository;
    private final ImageUploadService imageUploadService;

    public List<GroupPostInquiryResponseDto> getSome(Long groupId, Long lastPostId) {
        List<GroupPostEntity> groupPostEntities = groupPostRepository.findSomeByGroupIdAndLastPostId(
            groupId, lastPostId);
        return groupPostEntities.stream().map(GroupPostInquiryResponseDto::from).toList();
    }

    @Transactional
    public GroupPostCreateResponseDto save(Long groupId,
        GroupPostCreateRequestDto createRequestDto) {
        StudyMember studyMember = StudyMemberRepository.find();
        GroupPostEntity groupPost = GroupPostEntity.of(createRequestDto, studyMembers);
        List<GroupPostFileEntity> groupPostFiles = createRequestDto.getFileUrls().stream()
            .map(fileUrl -> GroupPostFileEntity.of(fileUrl, groupPost)).toList();
        groupPostFileRepository.saveAll(groupPostFiles);
        groupPostRepository.save(groupPost);
        return GroupPostCreateResponseDto.from(groupPost);
    }

    @Transactional
    public GroupPostUpdateResponseDto update(Long groupId,
        GroupPostUpdateRequestDto updateRequestDto, Long postId) {
        GroupPostEntity groupPost = groupPostRepository.findById(postId)
            .orElseThrow(RuntimeException::new);
        //유저 검증
        groupPost.modify(updateRequestDto);
        List<GroupPostFileEntity> groupPostFiles = updateRequestDto.getAddFileUrls().stream()
            .map(fileUrl -> GroupPostFileEntity.of(fileUrl, groupPost)).toList();
        groupPostFileRepository.saveAll(groupPostFiles);
        imageUploadService.deleteFiles(updateRequestDto.getDeleteFileUrls());
        groupPostRepository.save(groupPost);
        return GroupPostUpdateResponseDto.from(groupPost);
    }

    @Transactional
    public void delete(Long groupId, Long postId) {
        GroupPostEntity groupPost = groupPostRepository.findById(postId)
            .orElseThrow(RuntimeException::new);
        //유저 검증
        groupPostRepository.delete(groupPost);
    }
}
