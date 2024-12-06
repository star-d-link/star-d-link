package com.udemy.star_d_link.coursereview.Repository;

import com.udemy.star_d_link.coursereview.Entity.CourseLikes;
import com.udemy.star_d_link.coursereview.Entity.CourseReview;
import com.udemy.star_d_link.user.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 상속받아 기본 CRUD 메서드와 추가 커스텀 메서드를 정의
public interface CourseLikesRepository extends JpaRepository<CourseLikes, Long>{
    List<CourseLikes> findByCourseReview(CourseReview courseReview);
    boolean existsByCourseReviewAndUserEntity(CourseReview courseReview, UserEntity userEntity);

    /*
    // 특정 Board ID와 User ID로 좋아요를 삭제하는 메서드
    void deleteByBoardIdAndUserId(Long boardId, Long userId);

    // 특정 Board ID와 User ID로 좋아요가 이미 존재하는지 확인하는 메서드
    boolean existsByBoardIdAndUserId(Long boardId, Long userId);*/
}
