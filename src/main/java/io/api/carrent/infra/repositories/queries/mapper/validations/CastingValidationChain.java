package io.api.carrent.infra.repositories.queries.mapper.validations;

public abstract class CastingValidationChain<T> {
    private CastingValidationChain<?> next;

    protected abstract T cast(Object value);
    protected abstract boolean validate(Object value, Class fieldType);

    public Object execute(Object value, Class fieldType) {
        if (this.validate(value, fieldType)) {
            return this.cast(value);
        }

        return this.next == null ? value : this.next.execute(value, fieldType);
    }

    public CastingValidationChain<?> setNext(CastingValidationChain<?> castingValidationChain) {
        if (this.next == null) {
            this.next = castingValidationChain;
        } else {
            this.next.setNext(castingValidationChain);
        }

        return this;
    }
}
