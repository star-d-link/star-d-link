package com.udemy.star_d_link.study.Repository;

import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMemberRepository extends JpaRepository<StudyMembers, Long> {
    boolean existsByUsernameAndStudy(String username, Study study);
    List<StudyMembers> findByStudy(Study study);
    Optional<StudyMembers> findByUsername(String username);
    Optional<StudyMembers> findByUsernameAndStudy(String username, Study study);
    Optional<StudyMembers> findByStudyAndUsernameAndStatusNot(Study study, String username, String status);
}
