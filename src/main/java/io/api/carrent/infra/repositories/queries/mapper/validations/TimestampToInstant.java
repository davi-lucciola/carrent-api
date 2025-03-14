package io.api.carrent.infra.repositories.queries.mapper.validations;

import java.sql.Timestamp;
import java.time.Instant;

public class TimestampToInstant extends CastingValidationChain<Instant> {
    @Override
    protected Instant cast(Object value) {
        return ((Timestamp) value).toInstant();
    }

    @Override
    protected boolean validate(Object value, Class fieldType) {
        return (
                value.getClass().isAssignableFrom(Timestamp.class)
                && Instant.class.isAssignableFrom(fieldType)
        );
    }
}
