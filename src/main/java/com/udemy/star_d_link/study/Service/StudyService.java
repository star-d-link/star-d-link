package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Dto.StudyResponseDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import java.util.NoSuchElementException;
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

        Study newStudy = Study.builder()
            .userId(id)
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .hashtag(requestDto.getHashtag())
            .isRecruit(false)
            .region(requestDto.getRegion())
            .isOnline(requestDto.getIsOnline())
            .headCount(requestDto.getHeedCount())
            .build();

        return studyRepository.save(newStudy);
    }

    public StudyResponseDto findByStudyId(Long studyId){
        Study study =  studyRepository.findByStudyId(studyId)
            .orElseThrow(() -> new NoSuchElementException("스터디 모집글 내용을 찾을 수 없습니다: " + studyId));

        return new StudyResponseDto(
            study.getStudyId(),
            study.getUserId(),
            study.getTitle(),
            study.getContent(),
            study.getHashtag(),
            study.getIsRecruit(),
            study.getRegion(),
            study.getIsOnline(),
            study.getHeadCount(),
            study.getCreateDate()
        );
    }
}
