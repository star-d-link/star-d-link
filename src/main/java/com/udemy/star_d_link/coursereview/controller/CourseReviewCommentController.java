package com.udemy.star_d_link.coursereview.controller;

import com.udemy.star_d_link.coursereview.entity.CourseReviewComment;
import com.udemy.star_d_link.coursereview.service.CourseReviewCommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CourseReviewCommentController {

    private final CourseReviewCommentService commentService;

    public CourseReviewCommentController(CourseReviewCommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{boardId}")
    public List<CourseReviewComment> getComments(@PathVariable Long boardId) {
        return commentService.getCommentsByBoardId(boardId);
    }

    @PostMapping
    public CourseReviewComment addComment(@RequestParam Long boardId, @RequestParam String content) {
        return commentService.addComment(boardId, content);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
