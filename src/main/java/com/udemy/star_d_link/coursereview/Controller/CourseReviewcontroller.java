package com.udemy.star_d_link.coursereview.Controller;

import com.udemy.star_d_link.coursereview.Entity.CourseReview;
import com.udemy.star_d_link.coursereview.Service.CourseReviewService;
import com.udemy.star_d_link.coursereview.Dto.ApiResponse;
import com.udemy.star_d_link.coursereview.Dto.CourseReviewCreateRequestDto;
import com.udemy.star_d_link.coursereview.Dto.CourseReviewModifyRequestDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/courseReview")
public class CourseReviewcontroller {
    private final CourseReviewService courseReviewService;

    @GetMapping(value = "/create")
    public String createReview(){
        return "createReview";
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<CourseReview>> createCourseReview(
        @Valid @RequestBody CourseReviewCreateRequestDto courseReviewCreateRequestDto,
        @AuthenticationPrincipal UserDetails currentUser){
        if(currentUser == null ){
            ApiResponse<CourseReview> apiResponse = new ApiResponse<>(
                "error",
                "비회원은 작성 권한이 없습니다.",
                null
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        }

        try{
            CourseReview newCourseReview = courseReviewService.createCourseReview(courseReviewCreateRequestDto, Long.parseLong(
                currentUser.getUsername()));
                ApiResponse<CourseReview> apiResponse = new ApiResponse<>(
                    "success",
                    "글이 작성되었습니다.",
                    newCourseReview
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
            }
        catch (Exception e){
            ApiResponse<CourseReview> apiResponse = new ApiResponse<>(
                "error",
                "글이 작성되지 않았습니다",
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    @GetMapping("/modify/{boardId}")
    public ResponseEntity<ApiResponse<CourseReview>> getModifyCourseReview(@PathVariable Long boardId,
        @AuthenticationPrincipal UserDetails currentUser){
        CourseReview courseReview = courseReviewService.getCourseReviewDetail(boardId);
        if(currentUser == null || !currentUser.getUsername().equals(courseReview.getUserId())){
            ApiResponse<CourseReview> apiResponse = new ApiResponse<>(
                "error",
                "작성자만 수정 가능합니다",
                null
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        }
        ApiResponse apiResponse = new ApiResponse(
            "success",
            "리뷰글을 수정합니다.",
            courseReview
        );
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("/modify/{boardId}")
    public ResponseEntity<ApiResponse<CourseReview>> putModifyCourseReview(@PathVariable Long boardId,
        @RequestBody CourseReviewModifyRequestDto courseReviewModifyRequestDto,
        @AuthenticationPrincipal UserDetails currentUser){
        CourseReview courseReview = courseReviewService.getCourseReviewDetail(boardId);

        if(currentUser == null/* || !userDetails.getUsername().equals(courseReview.getUserId())*/){
            ApiResponse<CourseReview> apiResponse = new ApiResponse<>(
                "error",
                "작성자만 수정 가능합니다",
                null
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        }
        CourseReview modifiedReview = courseReviewService.modifyCourseReview(courseReviewModifyRequestDto, boardId, Long.parseLong(
            currentUser.getUsername()));

        ApiResponse<CourseReview> apiResponse = new ApiResponse<>(
            "success",
            "글이 수정되었습니다.",
            modifiedReview
        );
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<CourseReview>> getCourseReview(@RequestParam(defaultValue = "0") Integer page){
        Page<CourseReview> courseReviewList = courseReviewService.getList(page);
        ApiResponse apiResponse = new ApiResponse(
            "success",
            "목록 조회",
            courseReviewList
        );
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponse<CourseReview>> getReview(
        @PathVariable Long boardId){
        CourseReview courseReview = courseReviewService.getCourseReviewDetail(boardId);
        ApiResponse apiResponse = new ApiResponse(
            "success",
            "강의리뷰 상세내용",
            courseReview
        );
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @GetMapping("/delete/{boardId}")
    public ResponseEntity<ApiResponse<CourseReview>> deleteCourseReview(@PathVariable Long boardId,
        @AuthenticationPrincipal UserDetails userDetails){
        CourseReview courseReview = courseReviewService.getCourseReviewDetail(boardId);
        if(userDetails == null || !userDetails.getUsername().equals(courseReview.getUserId())){
            ApiResponse<CourseReview> apiResponse = new ApiResponse<>(
                "error",
                "작성자만 삭제 가능합니다",
                null
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        }


        courseReviewService.deleteCourseReview(boardId, Long.parseLong(
            userDetails.getUsername()));
        ApiResponse<CourseReview> apiResponse = new ApiResponse<>(
            "success",
            "삭제 완료되었습니다.",
            null
        );
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }




}
