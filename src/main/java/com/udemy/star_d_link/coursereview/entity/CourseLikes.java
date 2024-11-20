package com.udemy.star_d_link.coursereview.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CourseLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesId; // Primary Key

    @Column(nullable = false)
    private Long boardId; // 좋아요를 누른 게시글 ID

    @Column(nullable = false)
    private Long userId; // 좋아요를 누른 사용자 ID
}
