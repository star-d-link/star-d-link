package com.udemy.star_d_link.study.Dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyResponseDto {
    private Long StudyId;
    private Long userId;
    private String title;
    private String content;
    private String hashtag;
    private boolean isRecruit;
    private String region;
    private boolean isOnline;
    private int heedCount;
    private Date createDate;
}
