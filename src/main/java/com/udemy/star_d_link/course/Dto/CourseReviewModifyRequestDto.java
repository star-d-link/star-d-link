package com.udemy.star_d_link.course.Dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseReviewModifyRequestDto {
    //private SiteUser siteUser;
    private Integer userId;
    private Integer likeCount;
    private String title;
    private String content;
    private String hashtag;
    private LocalDate updatedAt;
    private Integer postType;
    private String name;
    private Integer rating;

}
