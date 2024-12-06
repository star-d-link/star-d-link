package com.udemy.star_d_link.coursereview.Entity;

import com.udemy.star_d_link.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "course_review_comment")
public class CourseReviewComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId; // PK

    @ManyToOne
    private CourseReview courseReview; // Board ID (외래 키)

    @ManyToOne
    private UserEntity userEntity;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 본문

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
