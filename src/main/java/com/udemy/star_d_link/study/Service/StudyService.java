package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.StudyListDto;
import com.udemy.star_d_link.study.Dto.StudyResponseDto;
import com.udemy.star_d_link.study.Dto.StudyUpdateRequestDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Exception.UnauthorizedException;
import com.udemy.star_d_link.study.Mapper.StudyMapper;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyService {

    private final StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    @Transactional
    public StudyResponseDto createStudy(StudyCreateRequestDto requestDto){

        // DTO를 엔티티로 변환
        Study study = StudyMapper.toEntity(requestDto);

        // 엔티티 저장
        Study savedStudy = studyRepository.save(study);

        // 저장된 엔티티를 응답 DTO로 변환
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

    public Page<StudyListDto> getStudyList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<Study> studyPage = studyRepository.findAll(pageable);

        return studyPage.map(StudyMapper::toListDto);
    }


}
