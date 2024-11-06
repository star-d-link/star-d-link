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

    public Study createStudy(StudyCreateRequestDto studyCreateRequestDto){
        Study newStudy = Study.builder()
            .userId(studyCreateRequestDto.getUserId())
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
