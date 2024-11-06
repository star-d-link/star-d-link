package com.udemy.star_d_link.Controller;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.udemy.star_d_link.Entity.CourseReview;
import com.udemy.star_d_link.Service.CourseReviewService;
import com.udemy.star_d_link.course.Dto.ApiResponse;
import com.udemy.star_d_link.course.Dto.CourseReviewCreateRequestDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/courseReview")
public class CourseReviewcontroller {
    private final CourseReviewService courseReviewService;

    @Autowired
    public CourseReviewcontroller(CourseReviewService courseReviewService){
        this.courseReviewService = courseReviewService;
    }
    @GetMapping(value = "create")
    public String createReview(){
        return "createReview";
    }
    @PostMapping(value = "create")
    public ResponseEntity<ApiResponse<CourseReview>> createCourseReview(
        @Valid @RequestBody CourseReviewCreateRequestDto courseReviewCreateRequestDto,
        @AuthenticationPrincipal UserDetails currentUser){
        if(currentUser == null){
            ApiResponse<CourseReview> apiResponse = new ApiResponse<>(
                "error",
                "비회원은 작성 권한이 없습니다.",
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }

        try{
            CourseReview newCourseReview = courseReviewService.createCourseReview(courseReviewCreateRequestDto, currentUser){
                ApiResponse<CourseReview> apiResponse = new ApiResponse<>(
                    "success",
                    "글이 작성되었습니다.",
                    newCourseReview
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
            }
        } catch (Exception e){
            ApiResponse<CourseReview> apiResponse = new ApiResponse<>(
                "error",
                "글이 작성되지 않았습니다",
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("")
    public ResponseEntity<ApiResponse<CourseReview>> getCourseReview(){
        List<CourseReview> courseReviewList = courseReviewService.findAll();
        return new ApiResponse<>(courseReviewList, HttpStatus.OK);
    }

}
