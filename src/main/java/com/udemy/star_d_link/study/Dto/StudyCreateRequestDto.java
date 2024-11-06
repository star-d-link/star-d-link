package com.udemy.star_d_link.study.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudyCreateRequestDto {
    private Long userId;
    private String title;
    private String content;
    private String hashtag;
    private boolean isRecruit;
    private String region;
    private boolean isOnline;
    private int heedCount;
}
