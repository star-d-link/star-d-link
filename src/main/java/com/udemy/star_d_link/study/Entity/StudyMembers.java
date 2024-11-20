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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "study_members")
public class StudyMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyManageId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String status;

    public StudyMembers(User user, Study study, Role role, String status) {
        this.user = user;
        this.study = study;
        this.role = role;
        this.status = status;
    }

}
