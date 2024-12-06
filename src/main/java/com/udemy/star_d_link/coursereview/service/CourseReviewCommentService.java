package com.udemy.star_d_link.coursereview.Service;

import com.udemy.star_d_link.coursereview.Dto.CourseReviewCommentDto;
import com.udemy.star_d_link.coursereview.Entity.CourseReview;
import com.udemy.star_d_link.coursereview.Entity.CourseReviewComment;
import com.udemy.star_d_link.coursereview.Repository.CourseReviewCommentRepository;
import com.udemy.star_d_link.coursereview.Repository.CourseReviewRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseReviewCommentService {

    private final CourseReviewCommentRepository commentRepository;
    private final CourseReviewRepository courseReviewRepository;

    public List<CourseReviewComment> getCommentsList(CourseReview courseReview) {
        return commentRepository.findByCourseReview(courseReview);
    }

    public CourseReviewComment addComment(Long boardId,
        CourseReviewCommentDto courseReviewCommentDto) {
        CourseReview courseReview = courseReviewRepository.findById(boardId).
            orElseThrow(() -> new NoSuchElementException("글이 존재하지 않습니다."));
        CourseReviewComment comment = CourseReviewComment.builder()
            .courseReview(courseReview)
            .userEntity(courseReviewCommentDto.getUserEntity())
            .content(courseReviewCommentDto.getContent())
            .build();

        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
