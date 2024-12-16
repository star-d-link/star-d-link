package com.udemy.star_d_link.study.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.udemy.star_d_link.study.Dto.Request.StudyScheduleCreateRequestDto;
import com.udemy.star_d_link.study.Dto.Response.StudyScheduleResponseDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 스터디 일정 관리에 대한 Entity
 PK인 ID, 외래키인 study, 제목, 내용, 날짜, 장소로 이루어짐
 */

@Entity
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "study_schedule")
public class StudySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(nullable = false, length = 20)
    private String username;

    @ManyToOne
    @JoinColumn(name = "study_id", nullable = false)
    @JsonBackReference // 순환 참조 방지
    private Study study;

    @Column(nullable = false)
    private String scheduleTitle;

    @Column(nullable = false)
    private String scheduleContent;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime scheduleDate;

    @Column(nullable = false)
    private String location;

    // 반복 일정 관련 필드
    private Long recurrenceGroup; // 반복 그룹


    public StudyScheduleResponseDto toResponseDto(String username) {
        return new StudyScheduleResponseDto(
            scheduleId,
            username,
            scheduleTitle,
            scheduleContent,
            scheduleDate.toString(),
            location,
            recurrenceGroup
        );
    }

    @OneToMany(mappedBy = "studySchedule", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StudyScheduleParticipation> participations = new ArrayList<>();


}
