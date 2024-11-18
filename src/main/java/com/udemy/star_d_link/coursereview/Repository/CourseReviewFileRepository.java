package com.udemy.star_d_link.coursereview.Repository;

import com.udemy.star_d_link.coursereview.Entity.CourseReview;
import com.udemy.star_d_link.coursereview.Entity.CourseReviewFile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseReviewFileRepository extends JpaRepository<CourseReviewFile, Integer> {
    List<CourseReviewFile> findByCourseReivew(CourseReview courseReview);
}
