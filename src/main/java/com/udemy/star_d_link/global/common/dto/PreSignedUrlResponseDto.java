package com.udemy.star_d_link.global.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PreSignedUrlResponseDto {
    private String preSignedUrl;
    private String staticUrl;

    public static PreSignedUrlResponseDto of(String preSignedUrl, String staticUrl) {
        return PreSignedUrlResponseDto.builder()
            .preSignedUrl(preSignedUrl)
            .staticUrl(staticUrl)
            .build();
    }
}
