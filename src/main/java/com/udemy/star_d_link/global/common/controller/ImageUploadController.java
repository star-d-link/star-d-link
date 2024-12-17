/*
package com.udemy.star_d_link.global.common.controller;

import com.udemy.star_d_link.global.common.dto.PreSignedUrlResponseDto;
import com.udemy.star_d_link.global.common.dto.ResponseDto;
import com.udemy.star_d_link.global.common.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @GetMapping
    public ResponseEntity<?> generateGroupPostPreSignedUrl(@RequestParam String imageName) {
        PreSignedUrlResponseDto preSignedUrlResponseDto = imageUploadService.getPreSignedUrl(
            "group_post", imageName);
        ResponseDto<?> responseDto = ResponseDto.onSuccess("성공적으로 프리사인드 만듦",
            preSignedUrlResponseDto);

        return ResponseEntity.ok(responseDto);
    }
}
*/
