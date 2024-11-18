package com.udemy.star_d_link.coursereview.repository;

import com.udemy.star_d_link.coursereview.entity.CourseReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseReviewCommentRepository extends JpaRepository<CourseReviewComment, Long> {
    List<CourseReviewComment> findByBoardId(Long boardId);
}
