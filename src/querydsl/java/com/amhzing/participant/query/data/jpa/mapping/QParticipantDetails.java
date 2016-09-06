package com.amhzing.participant.query.data.jpa.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParticipantDetails is a Querydsl query type for ParticipantDetails
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QParticipantDetails extends EntityPathBase<ParticipantDetails> {

    private static final long serialVersionUID = 2034530606L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParticipantDetails participantDetails = new QParticipantDetails("participantDetails");

    public final StringPath addedBy = createString("addedBy");

    public final DateTimePath<java.util.Date> addedDate = createDateTime("addedDate", java.util.Date.class);

    public final QAddress address;

    public final StringPath contactNumber = createString("contactNumber");

    public final StringPath email = createString("email");

    public final QName name;

    public final StringPath participantId = createString("participantId");

    public final StringPath updatedBy = createString("updatedBy");

    public final DateTimePath<java.util.Date> updatedDate = createDateTime("updatedDate", java.util.Date.class);

    public QParticipantDetails(String variable) {
        this(ParticipantDetails.class, forVariable(variable), INITS);
    }

    public QParticipantDetails(Path<? extends ParticipantDetails> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QParticipantDetails(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QParticipantDetails(PathMetadata metadata, PathInits inits) {
        this(ParticipantDetails.class, metadata, inits);
    }

    public QParticipantDetails(Class<? extends ParticipantDetails> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
        this.name = inits.isInitialized("name") ? new QName(forProperty("name")) : null;
    }

}

