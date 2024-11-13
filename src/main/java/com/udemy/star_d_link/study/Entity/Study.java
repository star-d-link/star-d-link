package com.udemy.star_d_link.study.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

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

    @Column
    private Long userId;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String hashtag;

    @Column(nullable = false)
    private Boolean isRecruit;

    @Column(length = 100)
    private String region;

    @Column(nullable = false)
    private Boolean isOnline;

    @Column(nullable = false)
    private Integer headCount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate createDate;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyLikes> likes = new ArrayList<>();

    @Column(nullable = false)
    private Integer likesCount = 0;

    public void incrementLikes() {
        this.likesCount++;
    }

    public void decrementLikes() {
        if (this.likesCount > 0) {
            this.likesCount--;
        }  else {
            throw new IllegalStateException("좋아요 수는 0보다 작을 수 없습니다.");
        }
    }

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudySchedule> schedules = new ArrayList<>();
}
