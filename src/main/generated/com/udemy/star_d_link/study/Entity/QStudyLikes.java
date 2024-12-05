package com.udemy.star_d_link.study.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyLikes is a Querydsl query type for StudyLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyLikes extends EntityPathBase<StudyLikes> {

    private static final long serialVersionUID = 1630132436L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyLikes studyLikes = new QStudyLikes("studyLikes");

    public final NumberPath<Long> likeId = createNumber("likeId", Long.class);

    public final QStudy study;

    public final StringPath username = createString("username");

    public QStudyLikes(String variable) {
        this(StudyLikes.class, forVariable(variable), INITS);
    }

    public QStudyLikes(Path<? extends StudyLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyLikes(PathMetadata metadata, PathInits inits) {
        this(StudyLikes.class, metadata, inits);
    }

    public QStudyLikes(Class<? extends StudyLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.study = inits.isInitialized("study") ? new QStudy(forProperty("study")) : null;
    }

}

