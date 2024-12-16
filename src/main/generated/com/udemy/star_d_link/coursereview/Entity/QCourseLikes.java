package com.udemy.star_d_link.coursereview.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourseLikes is a Querydsl query type for CourseLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCourseLikes extends EntityPathBase<CourseLikes> {

    private static final long serialVersionUID = 503244236L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCourseLikes courseLikes = new QCourseLikes("courseLikes");

    public final QCourseReview courseReview;

    public final NumberPath<Long> likesId = createNumber("likesId", Long.class);

    public final com.udemy.star_d_link.user.entity.QUserEntity userEntity;

    public QCourseLikes(String variable) {
        this(CourseLikes.class, forVariable(variable), INITS);
    }

    public QCourseLikes(Path<? extends CourseLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCourseLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCourseLikes(PathMetadata metadata, PathInits inits) {
        this(CourseLikes.class, metadata, inits);
    }

    public QCourseLikes(Class<? extends CourseLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.courseReview = inits.isInitialized("courseReview") ? new QCourseReview(forProperty("courseReview")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new com.udemy.star_d_link.user.entity.QUserEntity(forProperty("userEntity")) : null;
    }

}

