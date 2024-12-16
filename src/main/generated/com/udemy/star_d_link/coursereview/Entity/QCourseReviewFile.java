package com.udemy.star_d_link.coursereview.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourseReviewFile is a Querydsl query type for CourseReviewFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCourseReviewFile extends EntityPathBase<CourseReviewFile> {

    private static final long serialVersionUID = -1269031708L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCourseReviewFile courseReviewFile = new QCourseReviewFile("courseReviewFile");

    public final QCourseReview courseReview;

    public final DatePath<java.time.LocalDate> createdAt = createDate("createdAt", java.time.LocalDate.class);

    public final StringPath fileUrl = createString("fileUrl");

    public final NumberPath<Long> imageId = createNumber("imageId", Long.class);

    public final DatePath<java.time.LocalDate> updatedAt = createDate("updatedAt", java.time.LocalDate.class);

    public QCourseReviewFile(String variable) {
        this(CourseReviewFile.class, forVariable(variable), INITS);
    }

    public QCourseReviewFile(Path<? extends CourseReviewFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCourseReviewFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCourseReviewFile(PathMetadata metadata, PathInits inits) {
        this(CourseReviewFile.class, metadata, inits);
    }

    public QCourseReviewFile(Class<? extends CourseReviewFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.courseReview = inits.isInitialized("courseReview") ? new QCourseReview(forProperty("courseReview")) : null;
    }

}

