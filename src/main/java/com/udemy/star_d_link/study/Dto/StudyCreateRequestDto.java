package com.udemy.star_d_link.study.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyCreateRequestDto {
    @NotNull
    private Long userId;

    @NotEmpty(message = "스터디 제목은 필수 입력 항목입니다.")
    private String title;

    @NotEmpty(message = "스터디 내용은 필수 입력 항목입니다.")
    private String content;
    private String hashtag;
    private Boolean isRecruit;
    private String region;
    private Boolean isOnline;
    private Integer headCount;
    private Date createDate;
}
