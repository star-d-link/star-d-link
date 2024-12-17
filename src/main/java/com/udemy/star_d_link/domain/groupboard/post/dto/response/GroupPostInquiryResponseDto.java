package com.udemy.star_d_link.domain.groupboard.post.dto.response;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupPostInquiryResponseDto {
    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDate createdAt;
    private LocalDate updatedAt;


    public static GroupPostInquiryResponseDto from(GroupPostEntity groupPost){
        return GroupPostInquiryResponseDto.builder()
            .id(groupPost.getId())
            .author(groupPost.getUser().getUsername())
            .title(groupPost.getTitle())
            .content(groupPost.getContent())
            .createdAt(groupPost.getCreatedAt())
            .updatedAt(groupPost.getUpdatedAt())
            .build();
    }
}
