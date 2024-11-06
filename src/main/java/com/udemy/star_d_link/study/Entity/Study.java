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
@Table(name = "study")
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;

    private Long userId;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String hashtag;

    @Column(nullable = false)
    private boolean isRecruit;

    @Column(length = 100)
    private String region;

    @Column(nullable = false)
    private boolean isOnline;

    @Column(nullable = false)
    private int headcount;
}
