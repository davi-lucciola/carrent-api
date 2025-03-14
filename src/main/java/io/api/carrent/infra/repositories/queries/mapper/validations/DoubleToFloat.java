package io.api.carrent.infra.repositories.queries.mapper.validations;

public class DoubleToFloat extends CastingValidationChain<Float> {
    @Override
    protected Float cast(Object value) {
        return ((Double) value).floatValue();
    }

    @Override
    protected boolean validate(Object value, Class fieldType) {
        return (
                value.getClass().isAssignableFrom(Double.class)
                        && Float.class.isAssignableFrom(fieldType)
        );
    }
}
