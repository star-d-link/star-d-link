package com.udemy.star_d_link.domain.groupboard.post.dto.response;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupPostCreateResponseDto {
    private Long id;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private LocalDateTime createdAt;

    public static GroupPostCreateResponseDto from(GroupPostEntity groupPost) {
        return GroupPostCreateResponseDto.builder()
            .id(groupPost.getId())
            .createdAt(groupPost.getCreatedAt())
            .build();
    }
}
