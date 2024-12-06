package com.udemy.star_d_link.coursereview.Repository;

import com.udemy.star_d_link.coursereview.Entity.CourseReview;
import com.udemy.star_d_link.coursereview.Entity.CourseReviewComment;
import com.udemy.star_d_link.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseReviewCommentRepository extends JpaRepository<CourseReviewComment, Long> {
    List<CourseReviewComment> findByCourseReview(CourseReview courseReview);
    List<UserEntity> findByUserEntity(UserEntity userEntity);
}
