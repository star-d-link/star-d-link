package com.udemy.star_d_link.study.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Table(name = "study_members")
public class StudyMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyManageId;

    private Long userId;

    private Long studyId;

    @Column(length = 20, nullable = false)
    private String role;

    @Column(length = 20, nullable = false)
    private String status;
}
