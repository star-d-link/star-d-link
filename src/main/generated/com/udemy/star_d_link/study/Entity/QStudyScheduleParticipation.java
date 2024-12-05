package com.udemy.star_d_link.study.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyScheduleParticipation is a Querydsl query type for StudyScheduleParticipation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyScheduleParticipation extends EntityPathBase<StudyScheduleParticipation> {

    private static final long serialVersionUID = -655180542L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyScheduleParticipation studyScheduleParticipation = new QStudyScheduleParticipation("studyScheduleParticipation");

    public final NumberPath<Long> participationId = createNumber("participationId", Long.class);

    public final EnumPath<ParticipationStatus> status = createEnum("status", ParticipationStatus.class);

    public final QStudySchedule studySchedule;

    public final StringPath username = createString("username");

    public QStudyScheduleParticipation(String variable) {
        this(StudyScheduleParticipation.class, forVariable(variable), INITS);
    }

    public QStudyScheduleParticipation(Path<? extends StudyScheduleParticipation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyScheduleParticipation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyScheduleParticipation(PathMetadata metadata, PathInits inits) {
        this(StudyScheduleParticipation.class, metadata, inits);
    }

    public QStudyScheduleParticipation(Class<? extends StudyScheduleParticipation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studySchedule = inits.isInitialized("studySchedule") ? new QStudySchedule(forProperty("studySchedule"), inits.get("studySchedule")) : null;
    }

}

