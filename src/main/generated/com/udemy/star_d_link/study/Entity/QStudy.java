package com.udemy.star_d_link.study.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudy is a Querydsl query type for Study
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudy extends EntityPathBase<Study> {

    private static final long serialVersionUID = -642377784L;

    public static final QStudy study = new QStudy("study");

    public final StringPath content = createString("content");

    public final DatePath<java.time.LocalDate> createDate = createDate("createDate", java.time.LocalDate.class);

    public final StringPath hashtag = createString("hashtag");

    public final NumberPath<Integer> headCount = createNumber("headCount", Integer.class);

    public final BooleanPath isOnline = createBoolean("isOnline");

    public final BooleanPath isRecruit = createBoolean("isRecruit");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final ListPath<StudyLikes, QStudyLikes> likes = this.<StudyLikes, QStudyLikes>createList("likes", StudyLikes.class, QStudyLikes.class, PathInits.DIRECT2);

    public final NumberPath<Integer> likesCount = createNumber("likesCount", Integer.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final ListPath<StudyMembers, QStudyMembers> members = this.<StudyMembers, QStudyMembers>createList("members", StudyMembers.class, QStudyMembers.class, PathInits.DIRECT2);

    public final StringPath region = createString("region");

    public final ListPath<StudySchedule, QStudySchedule> schedules = this.<StudySchedule, QStudySchedule>createList("schedules", StudySchedule.class, QStudySchedule.class, PathInits.DIRECT2);

    public final NumberPath<Long> studyId = createNumber("studyId", Long.class);

    public final StringPath title = createString("title");

    public final StringPath username = createString("username");

    public QStudy(String variable) {
        super(Study.class, forVariable(variable));
    }

    public QStudy(Path<? extends Study> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudy(PathMetadata metadata) {
        super(Study.class, metadata);
    }

}

