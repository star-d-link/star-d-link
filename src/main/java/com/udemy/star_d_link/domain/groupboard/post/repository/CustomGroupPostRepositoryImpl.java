package com.udemy.star_d_link.domain.groupboard.post.repository;


import static com.udemy.star_d_link.domain.groupboard.post.entity.QGroupPostEntity.groupPostEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.udemy.star_d_link.domain.groupboard.post.entity.GroupPostEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomGroupPostRepositoryImpl implements CustomGroupPostRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<GroupPostEntity> findSomeByGroupIdAndLastPostId(Long groupId, Long lastPostId) {
        return jpaQueryFactory
            .selectFrom(groupPostEntity)
            .where(groupPostEntity.id.lt(lastPostId)/*, groupPostEntity.study.id.eq(groupId)*/)
            .join(groupPostEntity.user).fetchJoin()
            .leftJoin(groupPostEntity.groupPostFile).fetchJoin()
            .distinct()
            .fetch();
    }
}
