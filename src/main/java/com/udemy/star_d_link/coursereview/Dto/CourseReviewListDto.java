package com.udemy.star_d_link.coursereview.Dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseReviewListDto {
    private Integer boardId;
    private Integer userId;
    private Integer likeCount;
    private String title;
    private LocalDate createdAt;
    private Integer postType;
    private String name;
}
