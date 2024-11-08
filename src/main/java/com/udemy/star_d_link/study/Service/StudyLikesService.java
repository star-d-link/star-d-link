package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.StudyLikesDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyLikes;
import com.udemy.star_d_link.study.Mapper.StudyLikesMapper;
import com.udemy.star_d_link.study.Repository.StudyLikeRepository;
import com.udemy.star_d_link.study.Repository.StudyRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyLikesService {
    @Autowired
    private StudyRepository studyRepository;

    private final StudyLikeRepository studyLikeRepository;

    public StudyLikesService(StudyLikeRepository studyLikeRepository) {
        this.studyLikeRepository = studyLikeRepository;
    }

    @Transactional
    public StudyLikesDto addLikes(Long studyId, Long userId) {

        // studyId를 통해 Study 엔티티를 조회
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다: "));

        StudyLikes studyLikes = StudyLikesMapper.toEntity(null, userId, study);

        StudyLikes savedStudyLikes = studyLikeRepository.save(studyLikes);

        return StudyLikesMapper.toDto(savedStudyLikes);
    }
}
