package com.udemy.star_d_link.study.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.udemy.star_d_link.study.Dto.Request.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.Response.StudyListResponseDto;
import com.udemy.star_d_link.study.Dto.Response.StudyResponseDto;
import com.udemy.star_d_link.study.Dto.Request.StudyUpdateRequestDto;
import com.udemy.star_d_link.study.Entity.QStudy;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Mapper.StudyMapper;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final JPAQueryFactory queryFactory;

    public StudyService(StudyRepository studyRepository, JPAQueryFactory queryFactory) {
        this.studyRepository = studyRepository;
        this.queryFactory = queryFactory;
    }

    @Transactional
    public StudyResponseDto createStudy(StudyCreateRequestDto requestDto){

        Study study = StudyMapper.toEntity(requestDto);

        Study savedStudy = studyRepository.save(study);

        return StudyMapper.toResponseDto(savedStudy);
    }

    public Study getStudyForEdit(Long studyId, Long userId) {

        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new RuntimeException("해당 글을 찾을 수 없습니다."));

        if (!study.getUserId().equals(userId)) {
            throw new UnauthorizedException("수정 권한이 없습니다.");
        }

        return study;
    }

    public Study editStudyByUserId(Long studyId, Long userId, StudyUpdateRequestDto requestDto) {
        Study study = studyRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("해당 글을 찾을 수 없습니다."));

        if (!study.getUserId().equals(userId)) {
            throw new UnauthorizedException("수정 권한이 없습니다.");
        }

        Study editStudy = StudyMapper.updateStudyFromDto(study, requestDto);

        return studyRepository.save(editStudy);
    }

    @Transactional
    public void deleteStudyByUserId(Long studyId, Long userId) {

        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new RuntimeException("해당 글을 찾을 수 없습니다."));

        if (!study.getUserId().equals(userId)) {
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


        // 각 조건이 null이 아니면 맞는 조건을 builder에 추가
        if (isOnline != null) {
            builder.and(study.isOnline.eq(isOnline));
        }
        if (region != null) {
            builder.and(study.region.eq(region));
        }
        if (isRecruit != null) {
            builder.and(study.isRecruit.eq(isRecruit));
        }

        List<Study> studyList = queryFactory
            .selectFrom(study)
            .where(builder)
            .offset(pageable.getOffset()) // 페이지 시작점
            .limit(pageable.getPageSize()) // 페이지 크기(default : 10)
            .fetch();

        long total = studyList.size();



        return new PageImpl<>(studyList, pageable, total)
            .map(StudyMapper::toListDto);
    }


    public Page<StudyListResponseDto> searchStudyTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<Study> studyPage = studyRepository.findByTitleContainingIgnoreCase(title, pageable);

        return studyPage.map(StudyMapper::toListDto);
    }


}
