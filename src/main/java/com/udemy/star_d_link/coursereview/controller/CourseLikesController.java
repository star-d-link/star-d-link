package com.udemy.star_d_link.coursereview.controller;

import com.udemy.star_d_link.coursereview.service.CourseLikesService;
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

    @PostMapping
    public void toggleLike(@RequestParam Long boardId, @RequestParam Long userId) {
        likesService.toggleLike(boardId, userId);
    }
}
