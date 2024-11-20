package com.udemy.star_d_link.coursereview.Repository;

import com.udemy.star_d_link.coursereview.Entity.CourseReview;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {
    Page<CourseReview> findAll(Pageable pageable);

    List<CourseReview> findByUserId(Long userId);//나중에 삭제
    /*List<CourseReview> findBySiteUser(SiteUser siteuser);*/

    List<CourseReview> findByTitle(String title);
    List<CourseReview> findByContent(String Content);
    List<CourseReview> findByHashtag(String hashTag);

}
