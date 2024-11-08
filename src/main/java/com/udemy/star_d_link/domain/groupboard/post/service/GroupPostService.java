package com.udemy.star_d_link.domain.groupboard.post.service;

import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostCreateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostCreateResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostInquiryResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import com.udemy.star_d_link.domain.groupboard.post.repository.GroupPostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupPostService {

    private final GroupPostRepository groupPostRepository;

    public List<GroupPostInquiryResponseDto> getSome(Long groupId, Long lastPostId) {
        List<GroupPostEntity> groupPostEntities = groupPostRepository.findSomeByGroupIdAndLastPostId(
            groupId, lastPostId);
        return groupPostEntities.stream().map(GroupPostInquiryResponseDto::from).toList();
    }

    public GroupPostCreateResponseDto save(Long groupId,
        GroupPostCreateRequestDto createRequestDto) {
        StudyMember studyMember = StudyMemberRepository.find();
        GroupPostEntity groupPost = GroupPostEntity.of(createRequestDto, studyMembers);
        groupPostRepository.save(groupPost);
    }
}
