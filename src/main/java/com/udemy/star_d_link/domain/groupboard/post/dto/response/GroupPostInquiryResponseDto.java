package com.udemy.star_d_link.domain.groupboard.post.dto.response;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupPostInquiryResponseDto {
    private Long id;
    private String author;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String nickname;

    public static GroupPostInquiryResponseDto from(GroupPostEntity groupPost){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return GroupPostInquiryResponseDto.builder()
            .id(groupPost.getId())
            .author(groupPost.getUser().getUsername())
            .nickname(groupPost.getUser().getNickname())
            .title(groupPost.getTitle())
            .content(groupPost.getContent())
            .createdAt(groupPost.getCreatedAt().format(formatter))
            .updatedAt(groupPost.getUpdatedAt() != null ? groupPost.getUpdatedAt().format(formatter) : null)
            .build();
    }
}
