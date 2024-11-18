package com.udemy.star_d_link.coursereview.Dto;

import com.udemy.star_d_link.coursereview.Entity.CourseReview;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseReviewFileModifyRequestDto {
    CourseReview courseReview;
    String fileUrl;
    LocalDate updatedAt;
}
