package io.api.carrent.infra.repositories.queries.mapper.validations;

public class FloatToDouble extends CastingValidationChain<Double> {
    @Override
    protected Double cast(Object value) {
        return ((Float) value).doubleValue();
    }

    @Override
    protected boolean validate(Object value, Class fieldType) {
        return (
                value.getClass().isAssignableFrom(Float.class)
                        && Double.class.isAssignableFrom(fieldType)
        );
    }
}
