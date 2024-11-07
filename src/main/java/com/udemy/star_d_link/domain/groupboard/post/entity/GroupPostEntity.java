package com.udemy.star_d_link.domain.groupboard.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Builder
@Table(name = "group_post")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 20)
    private String content;

    @Column(nullable = false)
    private int numberOfComments;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "groupPost")
    private List<GroupPostFileEntity> groupPostFile;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private UserEntity user;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private StudyEntity study;
}
