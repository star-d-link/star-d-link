package com.udemy.star_d_link.coursereview.Controller;

import com.udemy.star_d_link.coursereview.Service.CourseLikesService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class CourseLikesController {

    private final CourseLikesService likesService;

    public CourseLikesController(CourseLikesService likesService) {
        this.likesService = likesService;
    }

    @GetMapping("/{boardId}")
    public Long getLikesCount(@PathVariable Long boardId) {
        return likesService.getLikesCount(boardId);
    }

    @PostMapping("/like/{boardId}")
    public void toggleLike(@PathVariable Long boardId,
        @AuthenticationPrincipal UserDetails currentUser) {
        likesService.like(boardId, currentUser.getUsername());
    }
}
