package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.StudyListDto;
import com.udemy.star_d_link.study.Dto.StudyResponseDto;
import com.udemy.star_d_link.study.Dto.StudyUpdateRequestDto;
import com.udemy.star_d_link.study.Entity.Study;
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
    public StudyResponseDto  createStudy(StudyCreateRequestDto requestDto){

        // DTO를 엔티티로 변환
        Study study = StudyMapper.toEntity(requestDto);

        // 엔티티 저장
        Study savedStudy = studyRepository.save(study);

        // 저장된 엔티티를 응답 DTO로 변환
        return StudyMapper.toResponseDto(savedStudy);
    }

    public Study editStudyByUserId(Long studyId, StudyUpdateRequestDto requestDto) {

        // 임시로 유저 아이디 적용함. 실제 적용할 때는
        // User user = userRepository.findByUsername(username) 같이 받아온 username으로 userId를 찾기
        Long id = 1L;
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new RuntimeException("해당 글을 찾을 수 없습니다."));

        Study editStudy = StudyMapper.updateStudyFromDto(study, requestDto);

        return studyRepository.save(editStudy);
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
