package com.udemy.star_d_link.study.Repository;

import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyMembers;
import com.udemy.star_d_link.study.Entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMemberRepository extends JpaRepository<StudyMembers, Long> {
    boolean existsByUserAndStudy(User user, Study study);
    List<StudyMembers> findByStudy(Study study);
    Optional<StudyMembers> findByUser(User user);
    Optional<StudyMembers> findByUserAndStudy(User user, Study study);
    Optional<StudyMembers> findByStudyAndUserAndStatusNot(Study study, User user, String status);
}
