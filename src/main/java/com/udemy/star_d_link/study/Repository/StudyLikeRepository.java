package com.udemy.star_d_link.study.Repository;

import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyLikes;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyLikeRepository extends JpaRepository<StudyLikes, Long> {
    Optional<StudyLikes> findByStudy(Study study);
    Optional<StudyLikes> findByUserId(Long userId);
    Optional<StudyLikes> findByStudyAndUserId(Study study, Long userId);
    boolean existsByUserIdAndStudy(Long userId, Study study);
}
