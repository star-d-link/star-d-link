package com.udemy.star_d_link.Dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
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
    private List<CourseReviewFileModifyRequestDto> fileDeleteDtoList;
    private List<CourseReviewFileCreateRequestDto> fileCreateDtoList;
}