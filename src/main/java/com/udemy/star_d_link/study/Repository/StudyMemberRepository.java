package com.udemy.star_d_link.study.Repository;

import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyLikes;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMemberRepository extends JpaRepository<StudyMembers, Long> {
    boolean existsByUserIdAndStudy(Long userId, Study study);
    Optional<StudyMembers> findByUserIdAndStudy(Long userId, Study study);
    Optional<StudyMembers> findByStudyAndUserIdAndStatusNot(Study study, Long userId, String status);
}
