package com.udemy.star_d_link.study.Dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyListDto {
    private Long StudyId;
    private String title;
    private Boolean isRecruit;
    private String region;
    private Boolean isOnline;
    private Date createDate;
}
