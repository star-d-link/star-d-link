package com.udemy.star_d_link.domain.groupboard.post.dto.response;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupPostUpdateResponseDto {
    private LocalDateTime updatedAt;

    public static GroupPostUpdateResponseDto from(GroupPostEntity groupPost) {
        return GroupPostUpdateResponseDto.builder()
            .updatedAt(groupPost.getUpdatedAt())
            .build();
    }
}
