package com.udemy.star_d_link.domain.groupboard.post.dto.response;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
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
    private List<String> groupPostFileUrls;
    private int numberOfComments;

    public static GroupPostInquiryResponseDto from(GroupPostEntity groupPost){
        return GroupPostInquiryResponseDto.builder()
            .id(groupPost.getId())
            .author(groupPost.getUser().getNickname())
            .title(groupPost.getTitle())
            .content(groupPost.getContent())
            .numberOfComments(groupPost.getNumberOfComments())
            .groupPostFileUrls(groupPost.getGroupPostFile())
            .build();
    }
}
