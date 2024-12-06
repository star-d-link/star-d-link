package com.udemy.star_d_link.coursereview.Dto;

import com.udemy.star_d_link.coursereview.Entity.CourseReview;
import com.udemy.star_d_link.coursereview.Entity.CourseReviewComment;
import com.udemy.star_d_link.user.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseReviewCommentDto {

    private CourseReview courseReview; // Board ID (외래 키)
    private UserEntity userEntity;
    private String content; // 본문
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CourseReviewCommentDto(CourseReviewComment courseReviewComment)
    {
        this.courseReview = courseReviewComment.getCourseReview();
        this.userEntity = courseReviewComment.getUserEntity();
        this.content = courseReviewComment.getContent();
        this.createdAt = courseReviewComment.getCreatedAt();
        this.updatedAt = courseReviewComment.getUpdatedAt();
    }

    public Map<String, Object> getDataMap() {
        return Map.of(
            "courseReview", courseReview,
            "userEntity", userEntity,
            "content", content,
            "createdAt", createdAt,
            "updatedAt", updatedAt
        );
    }
}
