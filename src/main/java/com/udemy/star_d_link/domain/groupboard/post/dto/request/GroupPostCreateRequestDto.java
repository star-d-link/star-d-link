package com.udemy.star_d_link.domain.groupboard.post.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupPostCreateRequestDto {
    private String title;
    private String content;
}
