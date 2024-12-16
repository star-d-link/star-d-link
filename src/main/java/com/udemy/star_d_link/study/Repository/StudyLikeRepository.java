package com.udemy.star_d_link.study.Repository;

import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyLikes;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyLikeRepository extends JpaRepository<StudyLikes, Long> {
    Optional<StudyLikes> findByStudy(Study study);
    Optional<StudyLikes> findByUsername(String username);
    Optional<StudyLikes> findByUsernameAndStudy(String username, Study study);
    boolean existsByUsernameAndStudy(String username, Study study);
}
