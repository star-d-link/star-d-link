package com.udemy.star_d_link.Repository;

import com.udemy.star_d_link.Entity.CourseReview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseReviewRepository extends JpaRepository {
    List<CourseReview> findAll();
    List<CourseReview> findBySiteUser(SiteUser siteuser);
    List<CourseReview> findByTitle(String title);
    List<CourseReview> findByContent(String Content);
    List<CourseReview> findByHashtag(String hashTag);
    List<CourseReview> findByTitleAndContentAndHashtag(String search);
}
