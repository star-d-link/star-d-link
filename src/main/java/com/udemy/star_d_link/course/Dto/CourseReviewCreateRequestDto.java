package com.udemy.star_d_link.course.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseReviewCreateRequestDto {
    //private SiteUser siteUser;
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


}
