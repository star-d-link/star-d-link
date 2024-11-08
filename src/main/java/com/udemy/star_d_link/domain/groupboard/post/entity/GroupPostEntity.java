package com.udemy.star_d_link.domain.groupboard.post.entity;

import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostCreateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostUpdateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private StudyEntity study;

    public static GroupPostEntity of(GroupPostCreateRequestDto createRequestDto, StudyMembers studyMembers) {
        return GroupPostEntity.builder()
            .title(createRequestDto.getTitle())
            .content(createRequestDto.getContent())
            .user(studyMembers.getUser())
            .study(studyMembers.getStudy())
            .groupPostFile(new ArrayList<>())
            .build();
    }

    public void modify(GroupPostUpdateRequestDto updateRequestDto) {
        this.title = updateRequestDto.getTitle();
        this.content = updateRequestDto.getContent();
    }

    public void addFile(GroupPostFileEntity groupPostFile) {
        this.groupPostFile.add(groupPostFile);
    }
}
