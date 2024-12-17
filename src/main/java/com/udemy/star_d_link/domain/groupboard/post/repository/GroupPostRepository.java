package com.udemy.star_d_link.domain.groupboard.post.repository;

import com.udemy.star_d_link.coursereview.Entity.CourseReview;
import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import com.udemy.star_d_link.study.Entity.Study;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupPostRepository extends JpaRepository<GroupPostEntity, Long>,
    CustomGroupPostRepository {
    Page<GroupPostEntity> findByStudy(Study study, Pageable pageable);
    Optional<GroupPostEntity> findByIdAndStudy(Long postId, Study study);


}
