package com.udemy.star_d_link.domain.groupboard.post.dto.response;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupPostCreateResponseDto {
    private Long id;
    private LocalDate createdAt;

    public static GroupPostCreateResponseDto from(GroupPostEntity groupPost) {
        return GroupPostCreateResponseDto.builder()
            .id(groupPost.getId())
            .createdAt(groupPost.getCreatedAt())
            .build();
    }
}
