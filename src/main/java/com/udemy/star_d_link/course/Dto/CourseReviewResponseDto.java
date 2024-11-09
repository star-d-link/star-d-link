package com.udemy.star_d_link.course.Dto;

import com.udemy.star_d_link.Entity.CourseReview;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseReviewResponseDto {
    private Integer boardId;
    private Integer userId;
    private Integer likeCount;
    private String title;
    private String content;
    private String hashtag;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Integer postType;
    private String name;
    private Integer rating;

    public CourseReviewResponseDto(CourseReview courseReview){
        this.boardId = courseReview.getBoardId();
        this.userId = courseReview.getUserId();
        this.likeCount = courseReview.getLikeCount();
        this.title = courseReview.getTitle();
        this.content = courseReview.getContent();
        this.hashtag = courseReview.getHashtag();
        this.createdAt = courseReview.getCreatedAt();
        this.updatedAt = courseReview.getUpdatedAt();
        this.postType = courseReview.getPostType();
        this.name = courseReview.getName();;
        this.rating = courseReview.getRating();
    }
}


