package com.udemy.star_d_link.domain.groupboard.post.repository;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import java.util.List;

public interface CustomGroupPostRepository {

    List<GroupPostEntity> findSomeByGroupIdAndLastPostId(Long groupId, Long lastPostId);
}
