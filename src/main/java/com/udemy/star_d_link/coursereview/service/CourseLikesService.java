package com.udemy.star_d_link.coursereview.service;

import com.udemy.star_d_link.coursereview.entity.CourseLikes;
import com.udemy.star_d_link.coursereview.repository.CourseLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseLikesService {

    @Autowired
    private CourseLikesRepository likesRepository;

    public void toggleLike(Long boardId, Long userId) {
        if (likesRepository.existsByBoardIdAndUserId(boardId, userId)) {
            likesRepository.deleteByBoardIdAndUserId(boardId, userId);
        } else {
            CourseLikes like = new CourseLikes();
            like.setBoardId(boardId);
            like.setUserId(userId);
            likesRepository.save(like);
        }
    }

    public Long getLikesCount(Long boardId) {
        return boardId;
    }
}
