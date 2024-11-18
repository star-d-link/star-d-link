package com.udemy.star_d_link.Repository;

import com.udemy.star_d_link.Entity.CourseReview;
import com.udemy.star_d_link.Entity.CourseReviewFile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseReviewFileRepository extends JpaRepository<CourseReviewFile, Integer> {
    List<CourseReviewFile> findByCourseReivew(CourseReview courseReview);
}
