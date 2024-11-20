package com.udemy.star_d_link.study.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.udemy.star_d_link.study.Dto.Request.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.Response.StudyListResponseDto;
import com.udemy.star_d_link.study.Dto.Response.StudyResponseDto;
import com.udemy.star_d_link.study.Dto.Request.StudyUpdateRequestDto;
import com.udemy.star_d_link.study.Entity.QStudy;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.User;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import com.udemy.star_d_link.study.Repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final JPAQueryFactory queryFactory;
    private final UserRepository userRepository;
    public StudyService(StudyRepository studyRepository, JPAQueryFactory queryFactory,
        UserRepository userRepository) {
        this.studyRepository = studyRepository;
        this.queryFactory = queryFactory;
        this.userRepository = userRepository;
    }

    @Transactional
    public StudyResponseDto createStudy(StudyCreateRequestDto requestDto, User user) {
        Study study = requestDto.toEntity(user);
        Study savedStudy = studyRepository.save(study);
        return StudyResponseDto.fromEntity(savedStudy);
    }

    public Study getStudyForEdit(Long studyId, User user) {

        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new RuntimeException("해당 글을 찾을 수 없습니다."));

        if (!study.getUser().equals(user)) {
            throw new UnauthorizedException("수정 권한이 없습니다.");
        }

        return study;
    }

    @Transactional
    public Study editStudyByUserId(Long studyId, User user, StudyUpdateRequestDto requestDto) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new RuntimeException("해당 글을 찾을 수 없습니다."));

        if (!study.getUser().equals(user)) {
            throw new UnauthorizedException("수정 권한이 없습니다.");
        }

        study = study.toBuilder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .hashtag(requestDto.getHashtag())
            .isRecruit(requestDto.getIsRecruit())
            .region(requestDto.getRegion())
            .isOnline(requestDto.getIsOnline())
            .headCount(requestDto.getHeadCount())
            .build();

        return studyRepository.save(study);
    }

    @Transactional
    public void deleteStudyByUserId(Long studyId, User user) {

        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new RuntimeException("해당 글을 찾을 수 없습니다."));

        if (!study.getUser().equals(user)) {
            throw new UnauthorizedException("해당 글을 삭제할 권한이 없습니다.");
        }

        studyRepository.delete(study);
    }

    public Study findByStudyId(Long studyId) {
        return studyRepository.findByStudyId(studyId)
            .orElseThrow(() -> new NoSuchElementException("스터디 모집글 내용을 찾을 수 없습니다: " + studyId));
    }

    public Page<StudyListResponseDto> getStudyList(Boolean isOnline, String region, Boolean isRecruit, Pageable pageable) {
        QStudy study = QStudy.study;
        BooleanBuilder builder = new BooleanBuilder();

        if (isOnline != null) {
            builder.and(study.isOnline.eq(isOnline));
        }
        if (region != null) {
            builder.and(study.region.eq(region));
        }
        if (isRecruit != null) {
            builder.and(study.isRecruit.eq(isRecruit));
        }

        Page<Study> studyPage = studyRepository.findAll(builder, pageable);
        return studyPage.map(StudyListResponseDto::fromEntity);
    }


    public Page<StudyResponseDto> searchStudy(String keyword, Pageable pageable) {
        QStudy study = QStudy.study;

        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null && !keyword.trim().isEmpty()) {
            builder.or(study.title.containsIgnoreCase(keyword));
            builder.or(study.content.containsIgnoreCase(keyword));
        }

        Page<Study> studyPage = studyRepository.findAll(builder, pageable);
        return studyPage.map(StudyResponseDto::fromEntity);
    }

    public Page<StudyResponseDto> detailedSearchStudy(String hashtag, Pageable pageable) {
        QStudy study = QStudy.study;

        BooleanBuilder builder = new BooleanBuilder();

        if (hashtag != null && !hashtag.trim().isEmpty()) {
            builder.and(study.hashtag.containsIgnoreCase(hashtag));
        }

        Page<Study> studyPage = studyRepository.findAll(builder, pageable);
        return studyPage.map(StudyResponseDto::fromEntity);
    }

    // 임시로 User를 조회하는 메서드들 추후 변경
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다"));
    }
    public User findUserByUserId(Long userId) {
        return userRepository.findByUserId(userId)
            .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다"));
    }
}
