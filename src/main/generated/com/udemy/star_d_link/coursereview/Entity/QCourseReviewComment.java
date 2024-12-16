package com.udemy.star_d_link.coursereview.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourseReviewComment is a Querydsl query type for CourseReviewComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCourseReviewComment extends EntityPathBase<CourseReviewComment> {

    private static final long serialVersionUID = 384019959L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCourseReviewComment courseReviewComment = new QCourseReviewComment("courseReviewComment");

    public final NumberPath<Long> commentId = createNumber("commentId", Long.class);

    public final StringPath content = createString("content");

    public final QCourseReview courseReview;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final com.udemy.star_d_link.user.entity.QUserEntity userEntity;

    public QCourseReviewComment(String variable) {
        this(CourseReviewComment.class, forVariable(variable), INITS);
    }

    public QCourseReviewComment(Path<? extends CourseReviewComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCourseReviewComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCourseReviewComment(PathMetadata metadata, PathInits inits) {
        this(CourseReviewComment.class, metadata, inits);
    }

    public QCourseReviewComment(Class<? extends CourseReviewComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.courseReview = inits.isInitialized("courseReview") ? new QCourseReview(forProperty("courseReview")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new com.udemy.star_d_link.user.entity.QUserEntity(forProperty("userEntity")) : null;
    }

}

