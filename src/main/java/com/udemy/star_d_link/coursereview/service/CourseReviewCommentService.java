package com.udemy.star_d_link.coursereview.service;

import com.udemy.star_d_link.coursereview.entity.CourseReviewComment;
import com.udemy.star_d_link.coursereview.repository.CourseReviewCommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseReviewCommentService {

    private final CourseReviewCommentRepository commentRepository;

    public CourseReviewCommentService(CourseReviewCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CourseReviewComment> getCommentsByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }

    public CourseReviewComment addComment(Long boardId, String content) {
        CourseReviewComment comment = new CourseReviewComment();
        comment.setBoardId(boardId);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
