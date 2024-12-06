package com.udemy.star_d_link.study.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyMembers is a Querydsl query type for StudyMembers
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyMembers extends EntityPathBase<StudyMembers> {

    private static final long serialVersionUID = -331057103L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyMembers studyMembers = new QStudyMembers("studyMembers");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath status = createString("status");

    public final QStudy study;

    public final NumberPath<Long> studyManageId = createNumber("studyManageId", Long.class);

    public final StringPath username = createString("username");

    public QStudyMembers(String variable) {
        this(StudyMembers.class, forVariable(variable), INITS);
    }

    public QStudyMembers(Path<? extends StudyMembers> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyMembers(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyMembers(PathMetadata metadata, PathInits inits) {
        this(StudyMembers.class, metadata, inits);
    }

    public QStudyMembers(Class<? extends StudyMembers> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.study = inits.isInitialized("study") ? new QStudy(forProperty("study")) : null;
    }

}

