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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class StudyScheduleParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participationId;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private StudySchedule studySchedule;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private UserEntity user;

    @Column(nullable = false, length = 20)
    private String username;

    @Enumerated(EnumType.STRING)
    private ParticipationStatus status;

}
