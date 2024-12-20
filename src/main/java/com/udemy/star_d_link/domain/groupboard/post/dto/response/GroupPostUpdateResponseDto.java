package com.udemy.star_d_link.domain.groupboard.post.dto.response;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupPostUpdateResponseDto {
    private LocalDate updatedAt;

    public static GroupPostUpdateResponseDto from(GroupPostEntity groupPost) {
        return GroupPostUpdateResponseDto.builder()
            .updatedAt(groupPost.getUpdatedAt())
            .build();
    }
}