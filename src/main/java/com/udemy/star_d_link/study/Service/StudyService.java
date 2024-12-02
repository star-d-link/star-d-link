package com.udemy.star_d_link.study.Service;

import com.querydsl.core.BooleanBuilder;
import com.udemy.star_d_link.study.Dto.Request.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.Request.StudyUpdateRequestDto;
import com.udemy.star_d_link.study.Entity.QStudy;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import com.udemy.star_d_link.user.repository.UserRepository;
import java.util.NoSuchElementException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyService {
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    public StudyService(StudyRepository studyRepository, UserRepository userRepository) {
        this.studyRepository = studyRepository;
        this.userRepository = userRepository;

    }
    @Transactional
    public Study createStudy(StudyCreateRequestDto requestDto, String username) {

        Study study = requestDto.toEntity(username);
        return studyRepository.save(study);
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
            .isRecruit(requestDto.getIsRecruit())
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

    // 임시로 User를 조회하는 메서드들 추후 변경
}
