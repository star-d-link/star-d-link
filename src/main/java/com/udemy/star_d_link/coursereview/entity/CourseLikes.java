package com.udemy.star_d_link.coursereview.Entity;

import com.udemy.star_d_link.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class CourseLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesId; // Primary Key

    @ManyToOne
    private CourseReview courseReview; // 좋아요를 누른 게시글 ID

    @ManyToOne
    private UserEntity userEntity; // 좋아요를 누른 사용자 ID
}
