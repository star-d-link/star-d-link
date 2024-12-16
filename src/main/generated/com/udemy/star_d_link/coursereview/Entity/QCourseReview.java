package com.udemy.star_d_link.coursereview.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourseReview is a Querydsl query type for CourseReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCourseReview extends EntityPathBase<CourseReview> {

    private static final long serialVersionUID = -1410885816L;

    public static final QCourseReview courseReview = new QCourseReview("courseReview");

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final ListPath<CourseReviewComment, QCourseReviewComment> comments = this.<CourseReviewComment, QCourseReviewComment>createList("comments", CourseReviewComment.class, QCourseReviewComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final ListPath<CourseReviewFile, QCourseReviewFile> courseReviewFileList = this.<CourseReviewFile, QCourseReviewFile>createList("courseReviewFileList", CourseReviewFile.class, QCourseReviewFile.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> createdAt = createDate("createdAt", java.time.LocalDate.class);

    public final StringPath hashtag = createString("hashtag");

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> postType = createNumber("postType", Integer.class);

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public final StringPath title = createString("title");

    public final DatePath<java.time.LocalDate> updatedAt = createDate("updatedAt", java.time.LocalDate.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QCourseReview(String variable) {
        super(CourseReview.class, forVariable(variable));
    }

    public QCourseReview(Path<? extends CourseReview> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourseReview(PathMetadata metadata) {
        super(CourseReview.class, metadata);
    }

}

