package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.StudyCreateRequestDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import org.springframework.stereotype.Service;

@Service
public class StudyService {
    private final StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public Study createStudy(StudyCreateRequestDto studyCreateRequestDto, String username){

        // 임시로 유저 아이디 적용함. 실제 적용할 때는
        // User user = userRepository.findByUsername(username) 같이 받아온 username으로 userId를 찾기
        Long id = 1L;


        Study newStudy = Study.builder()
            .userId(id)
            .title(studyCreateRequestDto.getTitle())
            .content(studyCreateRequestDto.getContent())
            .hashtag(studyCreateRequestDto.getHashtag())
            .isRecruit(false)
            .region(studyCreateRequestDto.getRegion())
            .isOnline(studyCreateRequestDto.isOnline())
            .headCount(studyCreateRequestDto.getHeedCount())
            .build();

        return studyRepository.save(newStudy);
    }
}
