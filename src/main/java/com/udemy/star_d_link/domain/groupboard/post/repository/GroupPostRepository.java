package com.udemy.star_d_link.domain.groupboard.post.repository;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupPostRepository extends JpaRepository<GroupPostEntity, Long>,
    CustomGroupPostRepository {

}
