package com.udemy.star_d_link.study.Dto.Request;

import com.udemy.star_d_link.study.Entity.RecurrenceType;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.study.Entity.StudySchedule;
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
public class StudyScheduleCreateRequestDto {

    @NotEmpty(message = "스터디 제목은 필수 입력 항목입니다.")
    private String scheduleTitle;

    @NotEmpty(message = "스터디 내용은 필수 입력 항목입니다.")
    private String scheduleContent;

    @NotNull(message = "스터디 날짜는 필수 입력 항목입니다.")
    @Future(message = "스터디 날짜는 미래 시간이어야합니다.")
    private LocalDateTime scheduleDate;

    private String location;

    private Integer recurrenceGroup;

    // 반복 일정 관련 필드
    private Boolean isRecurring;
    private RecurrenceType recurrenceType;
    private Integer recurrenceCount;

    public StudySchedule toEntity(Study study) {
        return StudySchedule.builder()
            .study(study)
            .scheduleTitle(scheduleTitle)
            .scheduleContent(scheduleContent)
            .scheduleDate(scheduleDate)
            .location(location)
            .recurrenceGroup(null)  // 처음 생성할 때는 null, 나중에 업데이트 가능
            .build();
    }
}
