package com.udemy.star_d_link.coursereview.Controller;

import com.udemy.star_d_link.coursereview.Dto.ApiResponse;
import com.udemy.star_d_link.coursereview.Dto.CourseReviewCommentDto;
import com.udemy.star_d_link.coursereview.Dto.CourseReviewCreateRequestDto;
import com.udemy.star_d_link.coursereview.Entity.CourseReview;
import com.udemy.star_d_link.coursereview.Entity.CourseReviewComment;
import com.udemy.star_d_link.coursereview.Service.CourseReviewCommentService;

import com.udemy.star_d_link.coursereview.Service.CourseReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CourseReviewCommentController {

    private final CourseReviewCommentService commentService;
    private final CourseReviewService courseReviewService;

    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponse<List<CourseReviewComment>>> getComments(
        @PathVariable Long boardId) {
        CourseReview courseReview = courseReviewService.getCourseReviewDetail(boardId);
        List<CourseReviewComment> commentList = commentService.getCommentsList(courseReview);
        ApiResponse apiResponse = new ApiResponse(
            "success",
            "강의리뷰 댓글",
            commentList
        );
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    @PostMapping("/{boardId}")
    public ResponseEntity<ApiResponse<CourseReviewComment>> addComment(@PathVariable Long boardId,
        @Valid @RequestBody CourseReviewCommentDto courseReviewCommentDto,
        @AuthenticationPrincipal UserDetails currentUser) {
        if (currentUser == null) {
            ApiResponse<CourseReviewComment> apiResponse = new ApiResponse<>(
                "error",
                "비회원은 작성 권한이 없습니다.",
                null
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        }

        try {
            CourseReviewComment newComment = commentService.addComment(boardId,
                courseReviewCommentDto);
            ApiResponse<CourseReviewComment> apiResponse = new ApiResponse<>(
                "success",
                "글이 작성되었습니다.",
                newComment
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CourseReviewComment> apiResponse = new ApiResponse<>(
                "error",
                "글이 작성되지 않았습니다",
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }

    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
