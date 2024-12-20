package com.udemy.star_d_link.domain.groupboard.post.dto.response;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostComment;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupPostCommentUpdateResponseDto {
    private String author;
    private String updatedAt;
    private String content;
    private String createdAt;
    private String nickname;

    public static GroupPostCommentUpdateResponseDto from(GroupPostComment groupPostComment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return GroupPostCommentUpdateResponseDto.builder()
            .content(groupPostComment.getContent())
            .author(groupPostComment.getUserEntity().getUsername())
            .nickname(groupPostComment.getUserEntity().getNickname())
            .updatedAt(groupPostComment.getUpdatedAt().format(formatter))
            .createdAt(groupPostComment.getCreatedAt().format(formatter))
            .build();
    }
}
