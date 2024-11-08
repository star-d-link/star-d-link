package com.udemy.star_d_link.study.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.udemy.star_d_link.study.Entity.Study;
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
    private Date createDate;
}
