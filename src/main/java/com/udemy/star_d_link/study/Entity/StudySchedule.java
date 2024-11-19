package com.udemy.star_d_link.study.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 스터디 일정 관리에 대한 Entity
 PK인 ID, 외래키인 study, 제목, 내용, 날짜, 장소로 이루어짐
 */

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "study_schedule")
public class StudySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @Column(nullable = false)
    private String scheduleTitle;

    @Column(nullable = false)
    private String scheduleContent;

    @Column(nullable = false)
    private LocalDateTime scheduleDate;

    @Column(nullable = false)
    private String location;

    // 반복 일정 관련 필드
    private Long recurrenceGroup; // 반복 그룹
}
