package com.udemy.star_d_link.study.Repository;

import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudyLikes;
import com.udemy.star_d_link.study.Entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyLikeRepository extends JpaRepository<StudyLikes, Long> {
    Optional<StudyLikes> findByStudy(Study study);
    Optional<StudyLikes> findByUser(User user);
    Optional<StudyLikes> findByUserAndStudy(User user, Study study);
    boolean existsByUserAndStudy(User user, Study study);
}
