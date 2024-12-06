package com.udemy.star_d_link.coursereview.Service;

import com.udemy.star_d_link.coursereview.Entity.CourseLikes;
import com.udemy.star_d_link.coursereview.Entity.CourseReview;
import com.udemy.star_d_link.coursereview.Repository.CourseLikesRepository;
import com.udemy.star_d_link.coursereview.Repository.CourseReviewRepository;
import com.udemy.star_d_link.user.entity.UserEntity;
import com.udemy.star_d_link.user.repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CourseLikesService {

    @Autowired
    private CourseLikesRepository likesRepository;
    @Autowired
    private CourseReviewRepository courseReviewRepository;
    @Autowired
    private UserRepository userRepository;

    public CourseLikes like(Long boardId, String userId) {
        CourseReview courseReview = courseReviewRepository.findById(boardId)
            .orElseThrow(() -> new NoSuchElementException("강의 리뷰글이 존재하지 않습니다."));

        UserEntity userEntity = userRepository.findByUsername(userId)
            .orElseThrow(() -> new NoSuchElementException("유저 정보가 존재하지 않습니다."));

        CourseLikes like = CourseLikes.builder()
            .courseReview(courseReview)
            .userEntity(userEntity)
            .build();
        return likesRepository.save(like);

    }

    public Long getLikesCount(Long boardId) {

        return boardId;
    }
}
