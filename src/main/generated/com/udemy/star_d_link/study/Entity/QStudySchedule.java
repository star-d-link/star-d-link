package com.udemy.star_d_link.study.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudySchedule is a Querydsl query type for StudySchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudySchedule extends EntityPathBase<StudySchedule> {

    private static final long serialVersionUID = -1721317761L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudySchedule studySchedule = new QStudySchedule("studySchedule");

    public final StringPath location = createString("location");

    public final NumberPath<Long> recurrenceGroup = createNumber("recurrenceGroup", Long.class);

    public final StringPath scheduleContent = createString("scheduleContent");

    public final DateTimePath<java.time.LocalDateTime> scheduleDate = createDateTime("scheduleDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> scheduleId = createNumber("scheduleId", Long.class);

    public final StringPath scheduleTitle = createString("scheduleTitle");

    public final QStudy study;

    public final StringPath username = createString("username");

    public QStudySchedule(String variable) {
        this(StudySchedule.class, forVariable(variable), INITS);
    }

    public QStudySchedule(Path<? extends StudySchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudySchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudySchedule(PathMetadata metadata, PathInits inits) {
        this(StudySchedule.class, metadata, inits);
    }

    public QStudySchedule(Class<? extends StudySchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.study = inits.isInitialized("study") ? new QStudy(forProperty("study")) : null;
    }

}

