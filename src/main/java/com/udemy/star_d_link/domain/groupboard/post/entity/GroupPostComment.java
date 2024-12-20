package com.udemy.star_d_link.domain.groupboard.post.entity;

import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostCommentCreateRequestDto;
import com.udemy.star_d_link.domain.groupboard.post.dto.request.GroupPostCommentUpdateRequestDto;
import com.udemy.star_d_link.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "group_post_comment")
public class GroupPostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId; // PK

    @ManyToOne
    private GroupPostEntity groupPostEntity ; // Board ID (외래 키)

    @ManyToOne
    private UserEntity userEntity;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 본문

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public static GroupPostComment of(GroupPostCommentCreateRequestDto createRequestDto, GroupPostEntity groupPostEntity,
        UserEntity userEntity) {
        return GroupPostComment.builder()
            .groupPostEntity(groupPostEntity)
            .content(createRequestDto.getContent())
            .userEntity(userEntity)
            .build();
    }
    public void modify(GroupPostCommentUpdateRequestDto updateRequestDto) {
        this.content = updateRequestDto.getContent();
    }
}
