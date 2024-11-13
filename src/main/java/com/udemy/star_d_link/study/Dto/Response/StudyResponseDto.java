package com.udemy.star_d_link.study.Dto.Response;

import java.time.LocalDate;
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
    private Boolean isRecruit;
    private String region;
    private Boolean isOnline;
    private Integer headCount;
    private LocalDate createDate;
}
