package com.udemy.star_d_link.domain.groupboard.post.dto.response;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupPostCreateResponseDto {
    private Long id;
    private String content;
    private String nickname;
    private String createdAt;

    public static GroupPostCreateResponseDto from(GroupPostEntity groupPost) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return GroupPostCreateResponseDto.builder()
            .id(groupPost.getId())
            .content(groupPost.getContent())
            .nickname(groupPost.getUser().getNickname())
            .createdAt(groupPost.getCreatedAt().format(formatter))
            .build();
    }
}
