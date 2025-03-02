package io.api.carrent.infra.repositories.queries.mapper;

import lombok.RequiredArgsConstructor;
import org.hibernate.MappingException;
import org.hibernate.query.TupleTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.Instant;

@RequiredArgsConstructor
public class AliasToDtoMapper<T> implements TupleTransformer<T> {
    private final Class<T> dtoClass;

    @Override
    public T transformTuple(Object[] result, String[] aliases) {
        int i = 0;
        try {
            Constructor<T> constructor = dtoClass.getDeclaredConstructor();;

            constructor.setAccessible(true);
            T dtoInstance = constructor.newInstance();

            for (i = 0; i < aliases.length; i++) {
                String alias = aliases[i];
                Object value = result[i];

                if (alias != null) {
                    var field = getField(dtoClass, alias);

                    if (!field.getType().isAssignableFrom(value.getClass())) {
                        value = equivalentTypeMapping(value, field.getType());
                    }

                    var fieldSetter = getSetterMethod(dtoClass, alias, value.getClass());
                    fieldSetter.invoke(dtoInstance, value);
                }
            }

            return dtoInstance;
        } catch (Exception e) {
            if (e instanceof MappingException) {
                throw new MappingException(e.getMessage());
            }

            throw new MappingException("Houve um error ao mappear DTO com o campo '%s'.".formatted(aliases[i]));
        }
    }

    private Field getField(Class<T> dtoClass, String alias) {
        try {
            return dtoClass.getDeclaredField(alias);
        } catch (NoSuchFieldException e) {
            throw new MappingException("Não foi encontrado propriedade com o alias '%s'.".formatted(alias));
        }
    }

    private Method getSetterMethod(Class<T> dtoClass, String alias, Class<?> parameterType) {
        try {
            var setMethodName = "set" + alias.substring(0, 1).toUpperCase() + alias.substring(1);
            var setterMethod = dtoClass.getMethod(setMethodName, parameterType);
            setterMethod.setAccessible(true);
            return setterMethod;
        } catch (NoSuchMethodException e) {
            throw new MappingException("Não foi encontrado metódo setter da propriedade '%s'.".formatted(alias));
        }
    }

    private Object equivalentTypeMapping(Object value, Class fieldType) {
        if (shouldCastToInstant(value, fieldType)) {
            return ((Timestamp) value).toInstant();
        } else if (shouldCastToFloat(value, fieldType)) {
            return ((Double) value).floatValue();
        } else if (shouldCastToDouble(value, fieldType)) {
            return ((Float) value).doubleValue();
        } else

        return value;
    }

    private boolean shouldCastToInstant(Object value, Class fieldType) {
        return (
            value.getClass().isAssignableFrom(Timestamp.class)
            && Instant.class.isAssignableFrom(fieldType)
        );
    }

    private boolean shouldCastToFloat(Object value, Class fieldType) {
        return (
            value.getClass().isAssignableFrom(Double.class)
            && Float.class.isAssignableFrom(fieldType)
        );
    }

    private boolean shouldCastToDouble(Object value, Class fieldType) {
        return (
            value.getClass().isAssignableFrom(Float.class)
            && Double.class.isAssignableFrom(fieldType)
        );
    }
}
