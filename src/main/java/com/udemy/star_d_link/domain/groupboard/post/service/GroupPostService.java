package com.udemy.star_d_link.domain.groupboard.post.service;

import com.udemy.star_d_link.coursereview.Entity.CourseReview;
import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostCreateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostUpdateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostCreateResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostInquiryResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.response.GroupPostUpdateResponseDto;
import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostFileEntity;
import com.udemy.star_d_link.domain.groupboard.post.repository.GroupPostFileRepository;
import com.udemy.star_d_link.domain.groupboard.post.repository.GroupPostRepository;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import com.udemy.star_d_link.study.Repository.StudyMemberRepository;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import com.udemy.star_d_link.study.Service.StudyMembersService;
import com.udemy.star_d_link.user.entity.UserEntity;
import com.udemy.star_d_link.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupPostService {

    private final GroupPostRepository groupPostRepository;
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;

    public Page<GroupPostInquiryResponseDto> getList(Long groupId, int page, int size) {
        Study study = studyRepository.findByStudyId(groupId)
            .orElseThrow(() -> new RuntimeException("스터디를 찾을 수 없습니다."));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<GroupPostEntity> groupPostEntities = groupPostRepository.findByStudy(study, pageable);

        return groupPostEntities.map(GroupPostInquiryResponseDto::from);
    }

    @Transactional
    public GroupPostCreateResponseDto save(Long groupId,
        String username,
        GroupPostCreateRequestDto createRequestDto) {

       Study study = studyRepository.findByStudyId(groupId)
           .orElseThrow(RuntimeException::new);
       UserEntity userEntity = userRepository.findByUsername(username)
           .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        GroupPostEntity groupPost = GroupPostEntity.of(createRequestDto, study, userEntity);
        groupPostRepository.save(groupPost);
        return GroupPostCreateResponseDto.from(groupPost);
    }

/*    @Transactional
    public GroupPostUpdateResponseDto update(Long groupId,
        Long userid,
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
    }*/

    @Transactional
    public void delete(Long groupId, Long postId) {
        GroupPostEntity groupPost = groupPostRepository.findById(postId)
            .orElseThrow(RuntimeException::new);
        //유저 검증
        groupPostRepository.delete(groupPost);
    }

    @Transactional
    public GroupPostInquiryResponseDto getPostDetail(Long groupId, Long postId) {
        // 스터디 조회
        Study study = studyRepository.findByStudyId(groupId)
            .orElseThrow(() -> new RuntimeException("스터디를 찾을 수 없습니다."));

        // 게시글 조회
        GroupPostEntity post = groupPostRepository.findByIdAndStudy(postId, study)
            .orElseThrow(() -> new RuntimeException("해당 스터디에 게시글이 존재하지 않습니다."));

        // DTO 변환 후 반환
        return GroupPostInquiryResponseDto.from(post);
    }
}
