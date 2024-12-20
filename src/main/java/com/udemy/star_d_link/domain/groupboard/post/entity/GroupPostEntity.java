package com.udemy.star_d_link.domain.groupboard.post.entity;

import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostCreateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostUpdateRequestDto;
import com.udemy.star_d_link.study.Entity.Study;
import com.udemy.star_d_link.user.entity.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Study study;

    @OneToMany(mappedBy = "groupPostEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPostComment> comments;

    public static GroupPostEntity of(GroupPostCreateRequestDto createRequestDto, Study study,
        UserEntity userEntity) {
        return GroupPostEntity.builder()
            .title(createRequestDto.getTitle())
            .content(createRequestDto.getContent())
            .user(userEntity)
            .study(study)
            .build();
    }

    public void modify(GroupPostUpdateRequestDto updateRequestDto) {
        this.title = updateRequestDto.getTitle();
        this.content = updateRequestDto.getContent();
    }

}
