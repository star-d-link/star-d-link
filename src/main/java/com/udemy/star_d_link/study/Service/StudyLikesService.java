package com.udemy.star_d_link.study.Service;

import com.udemy.star_d_link.study.Dto.Response.StudyLikesResponseDto;
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
    private final StudyLikesMapper studyLikesMapper;

    public StudyLikesService(StudyLikeRepository studyLikeRepository, StudyLikesMapper studyLikesMapper) {
        this.studyLikeRepository = studyLikeRepository;
        this.studyLikesMapper = studyLikesMapper;

    }

    @Transactional
    public StudyLikesResponseDto addLikes(Long studyId, Long userId) {

        // studyId를 통해 Study 엔티티를 조회
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다: "));

        boolean alreadyLike = studyLikeRepository.existsByUserIdAndStudy(userId, study);
        if (alreadyLike) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다");
        }
        StudyLikes studyLikes = studyLikesMapper.toEntity(null, userId, study);
        StudyLikes savedStudyLikes = studyLikeRepository.save(studyLikes);

        study.incrementLikes();
        studyRepository.save(study);

        return studyLikesMapper.toDto(savedStudyLikes);
    }

    @Transactional
    public void deleteLikes(Long studyId, Long userId) {
        // studyId를 통해 Study 엔티티를 조회
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NoSuchElementException("해당 스터디를 찾을 수 없습니다: "));

        StudyLikes studyLikes = studyLikeRepository.findByStudyAndUserId(study, userId)
            .orElseThrow(() -> new NoSuchElementException("좋아요를 찾을 수 없습니다."));

        studyLikeRepository.delete(studyLikes);

        study.decrementLikes();
        studyRepository.save(study);
    }
}
