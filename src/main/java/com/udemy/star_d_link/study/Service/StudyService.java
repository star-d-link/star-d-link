package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.StudyCreateRequestDto;
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

@Service
public class StudyService {

    private final StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public Study createStudy(StudyCreateRequestDto requestDto, String username){

        // 임시로 유저 아이디 적용함. 실제 적용할 때는
        // User user = userRepository.findByUsername(username) 같이 받아온 username으로 userId를 찾기
        Long id = 1L;

        Study study = StudyMapper.toEntity(requestDto);
        return studyRepository.save(study);
    }

    public Study editStudyByUserId(Long studyId, StudyUpdateRequestDto requestDto) {

        // 임시로 유저 아이디 적용함. 실제 적용할 때는
        // User user = userRepository.findByUsername(username) 같이 받아온 username으로 userId를 찾기
        Long id = 1L;
        Optional<Study> optionalStudy = studyRepository.findByUserId(id);

        if (optionalStudy.isEmpty()) {
            throw new RuntimeException("해당 글을 찾을 수 없습니다.");
        }
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new RuntimeException("해당 글을 찾을 수 없습니다."));

        Study editStudy = study.toBuilder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .hashtag(requestDto.getHashtag())
            .isRecruit(requestDto.getIsRecruit())
            .region(requestDto.getRegion())
            .isOnline(requestDto.getIsOnline())
            .headCount(requestDto.getHeadCount())
            .build();

        return studyRepository.save(editStudy);
    }

    public StudyResponseDto findByStudyId(Long studyId){
        Study study =  studyRepository.findByStudyId(studyId)
            .orElseThrow(() -> new NoSuchElementException("스터디 모집글 내용을 찾을 수 없습니다: " + studyId));

        return StudyMapper.toResponseDto(study);
    }

    public Page<Study> getStudyList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        return studyRepository.findAll(pageable);
    }


}
