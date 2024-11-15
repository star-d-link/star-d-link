package com.udemy.star_d_link.study.Dto.Request;

import com.udemy.star_d_link.study.Entity.RecurrenceType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyScheduleAllUpdateRequestDto {

    @NotNull
    private Long recurrenceGroupId;

    @NotEmpty(message = "스터디 제목은 필수 입력 항목입니다.")
    private String title;

    @NotEmpty(message = "스터디 내용은 필수 입력 항목입니다.")
    private String content;

    private String location;

    // 반복 일정의 변경을 원할 경우 추가
    private RecurrenceType recurrenceType;
}
