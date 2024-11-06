package com.udemy.star_d_link.Service;

import com.udemy.star_d_link.Entity.CourseReview;
import com.udemy.star_d_link.Repository.CourseReviewRepository;
import com.udemy.star_d_link.course.Dto.CourseReviewCreateRequestDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CourseReviewService {
    private final CourseReviewRepository courseReviewRepository;

    public CourseReviewService(CourseReviewRepository courseReviewRepository){
        this.courseReviewRepository = courseReviewRepository;
    }
    public List<CourseReview> findAll(){
        return courseReviewRepository.findAll();
    }
    public CourseReview createCourseReview(CourseReviewCreateRequestDto courseReviewCreateRequestDto, SiteUser siteUser){
        CourseReview newCourseReview = CourseReview.builder()
            .siteUser(siteUser)
            .likeCount(0)
            .title(courseReviewCreateRequestDto.getTitle())
            .content(courseReviewCreateRequestDto.getContent())
            .hashtag(courseReviewCreateRequestDto.getHashtag())
            .createdAt(LocalDate.now())
            .updatedAt(LocalDate.now())
            .postType(courseReviewCreateRequestDto.getPostType())
            .name(courseReviewCreateRequestDto.getName())
            .rating(0)
            .build();

        return courseReviewRepository.save(newCourseReview);
    }
}
