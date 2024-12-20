package com.udemy.star_d_link.domain.groupboard.post.dto.response;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostComment;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupPostCommentInquiryResponseDto {
    private Long id;
    private String author;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String nickname;


    public static GroupPostCommentInquiryResponseDto from(GroupPostComment groupPostComment){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return GroupPostCommentInquiryResponseDto.builder()
            .id(groupPostComment.getCommentId())
            .author(groupPostComment.getUserEntity().getUsername())
            .nickname(groupPostComment.getUserEntity().getNickname())
            .content(groupPostComment.getContent())
            .createdAt(groupPostComment.getCreatedAt().format(formatter))
            .updatedAt(groupPostComment.getUpdatedAt() != null ? groupPostComment.getUpdatedAt().format(formatter) : null)
            .build();
    }
}
