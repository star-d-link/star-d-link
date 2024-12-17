package com.udemy.star_d_link.domain.groupboard.post.repository;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import com.udemy.star_d_link.study.Entity.Study;
import java.util.List;

public interface CustomGroupPostRepository {
    List<GroupPostEntity> findByStudy(Study study);
}
