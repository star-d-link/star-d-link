package com.udemy.star_d_link.domain.groupboard.post.repository;

import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupPostFileRepository extends JpaRepository<GroupPostFileEntity, Long> {

}
