package com.udemy.star_d_link.study.Dto.Request;

import com.udemy.star_d_link.study.Entity.RecurrenceType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyScheduleSingleUpdateRequestDto {

    @NotNull
    private Long scheduleId;

    @NotEmpty(message = "스터디 제목은 필수 입력 항목입니다.")
    private String title;

    @NotEmpty(message = "스터디 내용은 필수 입력 항목입니다.")
    private String content;

    private LocalDateTime scheduleDate;

    private String location;
}
