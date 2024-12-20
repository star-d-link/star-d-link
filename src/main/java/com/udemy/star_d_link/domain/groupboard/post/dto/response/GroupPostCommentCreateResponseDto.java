package com.udemy.star_d_link.domain.groupboard.post.dto.response;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostComment;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupPostCommentCreateResponseDto {
    private Long id;
    private String author;
    private String content;
    private String createdAt;
    private String nickname;

    public static GroupPostCommentCreateResponseDto from(GroupPostComment groupPostComment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return GroupPostCommentCreateResponseDto.builder()
            .id(groupPostComment.getCommentId())
            .author(groupPostComment.getUserEntity().getUsername())
            .nickname(groupPostComment.getUserEntity().getNickname())
            .content(groupPostComment.getContent())
            .createdAt(groupPostComment.getCreatedAt().format(formatter))
            .build();
    }
}
