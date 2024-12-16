package com.udemy.star_d_link.study.Service;

import com.querydsl.core.BooleanBuilder;
import com.udemy.star_d_link.study.Dto.Request.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.Request.StudyUpdateRequestDto;
import com.udemy.star_d_link.study.Dto.Response.StudyResponseDto;
import com.udemy.star_d_link.study.Entity.QStudy;
import com.udemy.star_d_link.study.Entity.Role;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Repository.StudyMemberRepository;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import com.udemy.star_d_link.user.repository.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyService {
    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    public StudyService(StudyRepository studyRepository, StudyMemberRepository studyMemberRepository) {
        this.studyRepository = studyRepository;
        this.studyMemberRepository = studyMemberRepository;

    }
    @Transactional
    public Study createStudy(StudyCreateRequestDto requestDto, String username) {
        // 1. Study 엔티티 생성 및 저장
        Study study = requestDto.toEntity(username);
        Study savedStudy = studyRepository.save(study);

        // 2. StudyMembers 엔티티 생성 및 저장
        StudyMembers studyMember = StudyMembers.builder()
            .study(savedStudy) // 연관된 스터디
            .username(username) // 스터디 생성자
            .role(Role.LEADER) // 관리자 역할
            .status("참여중") // 기본 상태
            .build();

        studyMemberRepository.save(studyMember);

        return savedStudy;
    }

    public Study getStudyForEdit(Long studyId, String username) {

        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new RuntimeException("해당 글을 찾을 수 없습니다."));

        if (!study.getUsername().equals(username)) {
            throw new UnauthorizedException(" 권한이 없습니다.");
        }

        return study;
    }

    @Transactional
    public Study editStudyByUserId(Long studyId, String username, StudyUpdateRequestDto requestDto) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new RuntimeException("해당 글을 찾을 수 없습니다."));

        if (!study.getUsername().equals(username)) {
            throw new UnauthorizedException("수정 권한이 없습니다.");
        }

        study = study.toBuilder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .hashtag(requestDto.getHashtag())
            .region(requestDto.getRegion())
            .isOnline(requestDto.getIsOnline())
            .headCount(requestDto.getHeadCount())
            .build();

        return studyRepository.save(study);
    }

    @Transactional
    public void deleteStudyByUserId(Long studyId, String username) {

        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new RuntimeException("해당 글을 찾을 수 없습니다."));

        if (!study.getUsername().equals(username)) {
            throw new UnauthorizedException("해당 글을 삭제할 권한이 없습니다.");
        }

        studyRepository.delete(study);
    }

    public Study findByStudyId(Long studyId) {
        return studyRepository.findByStudyId(studyId)
            .orElseThrow(() -> new NoSuchElementException("스터디 모집글 내용을 찾을 수 없습니다: " + studyId));
    }

    public Page<Study> getStudyList(Boolean isOnline, String region, Boolean isRecruit, Pageable pageable) {
        QStudy study = QStudy.study;
        BooleanBuilder builder = new BooleanBuilder();

        if (isOnline != null) {
            builder.and(study.isOnline.eq(isOnline));
        }
        if (region != null && !region.trim().isEmpty()) {
            builder.and(study.region.eq(region));
        }
        if (isRecruit != null) {
            builder.and(study.isRecruit.eq(isRecruit));
        }

        return studyRepository.findAll(builder, pageable);
    }


    public Page<Study> searchStudy(String keyword, Pageable pageable) {
        QStudy study = QStudy.study;

        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null && !keyword.trim().isEmpty()) {
            builder.or(study.title.containsIgnoreCase(keyword));
            builder.or(study.content.containsIgnoreCase(keyword));
        }

        return studyRepository.findAll(builder, pageable);
    }

    public Page<Study> detailedSearchStudy(String hashtag, Pageable pageable) {
        QStudy study = QStudy.study;
        BooleanBuilder builder = new BooleanBuilder();

        if (hashtag == null || hashtag.trim().isEmpty()) {
            throw new IllegalArgumentException("해시태그는 필수입니다.");
        }

        if (!hashtag.startsWith("#")) {
            hashtag = "#" + hashtag;  // 만약 #이 없으면 #을 추가
        }
        builder.and(study.hashtag.containsIgnoreCase(hashtag));

        return studyRepository.findAll(builder, pageable);
    }
    public Map<String, List<StudyResponseDto>> getStudiesByUserAndRole(String username) {
        // StudyMembers를 통해 역할별 스터디 목록 조회
        List<StudyMembers> allStudyMembers = studyMemberRepository.findByUsername(username);

        // 관리 중인 스터디: Role.LEADER 또는 Role.SUB_LEADER
        List<StudyResponseDto> adminStudies = allStudyMembers.stream()
            .filter(member -> member.getRole() == Role.LEADER || member.getRole() == Role.SUB_LEADER)
            .map(member -> StudyResponseDto.fromEntity(member.getStudy()))
            .collect(Collectors.toList());

        // 가입한 스터디: Role.MEMBER
        List<StudyResponseDto> joinedStudies = allStudyMembers.stream()
            .filter(member -> member.getRole() == Role.MEMBER)
            .map(member -> StudyResponseDto.fromEntity(member.getStudy()))
            .collect(Collectors.toList());

        // 결과를 Map으로 반환
        Map<String, List<StudyResponseDto>> result = new HashMap<>();
        result.put("admin", adminStudies);
        result.put("joined", joinedStudies);

        return result;
    }

}
