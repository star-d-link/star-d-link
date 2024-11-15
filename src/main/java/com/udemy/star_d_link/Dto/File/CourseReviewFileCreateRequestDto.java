package com.udemy.star_d_link.Dto.File;

import com.udemy.star_d_link.Entity.CourseReview;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseReviewFileCreateRequestDto {

    CourseReview courseReview;
    String fileUrl;
    LocalDate createdAt;
    LocalDate updatedAt;
}
