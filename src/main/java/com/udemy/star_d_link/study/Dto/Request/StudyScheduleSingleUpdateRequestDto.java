package com.udemy.star_d_link.study.Dto.Request;

import jakarta.validation.constraints.Future;
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
    private String scheduleTitle;

    @NotEmpty(message = "스터디 내용은 필수 입력 항목입니다.")
    private String scheduleContent;

    @NotNull(message = "스터디 날짜는 필수 입력 항목입니다.")
    @Future(message = "스터디 날짜는 미래 시간이어야합니다.")
    private LocalDateTime scheduleDate;

    @NotEmpty(message = "스터디 장소는 필수 입력 항목입니다.")
    private String location;

}
