package com.udemy.star_d_link.coursereview.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "course_review_comment")
public class CourseReviewComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId; // PK

    @Column(nullable = false)
    private Long boardId; // Board ID (외래 키)

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 본문

    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일시

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 수정일시
}
