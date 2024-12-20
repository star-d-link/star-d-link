package com.udemy.star_d_link.domain.groupboard.post.repository;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostComment;
import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupPostCommentRepository extends JpaRepository<GroupPostComment, Long> {
    List<GroupPostComment> findByGroupPostEntity(GroupPostEntity groupPostEntity);
}
