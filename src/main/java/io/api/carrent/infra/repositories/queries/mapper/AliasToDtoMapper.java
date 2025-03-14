package io.api.carrent.infra.repositories.queries.mapper;

import io.api.carrent.infra.repositories.queries.mapper.validations.CastingValidationChain;
import io.api.carrent.infra.repositories.queries.mapper.validations.DoubleToFloat;
import io.api.carrent.infra.repositories.queries.mapper.validations.FloatToDouble;
import io.api.carrent.infra.repositories.queries.mapper.validations.TimestampToInstant;
import lombok.RequiredArgsConstructor;
import org.hibernate.MappingException;
import org.hibernate.query.TupleTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

@RequiredArgsConstructor
public class AliasToDtoMapper<T> implements TupleTransformer<T> {
    private final Class<T> dtoClass;
    private final CastingValidationChain<?> castingValidationChain;

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

                    if (value != null && !field.getType().isAssignableFrom(value.getClass())) {
                        value = equivalentTypeMapping(value, field.getType());
                    }

                    var fieldSetter = getSetterMethod(dtoClass, alias);
                    var parameterType = fieldSetter.getParameterTypes()[0];

                    if (value == null && parameterType.isPrimitive()) {
                        continue;
                    }

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

    private Method getSetterMethod(Class<T> dtoClass, String alias) {
        try {
            var setMethodName = "set" + alias.substring(0, 1).toUpperCase() + alias.substring(1);
            var methods = dtoClass.getMethods();

            var setterMethod = Arrays.stream(methods)
                    .filter(method -> method.getName().equals(setMethodName)).findFirst();

            if (setterMethod.isEmpty()) {
                throw new NoSuchMethodException();
            }

            return setterMethod.get();
        } catch (NoSuchMethodException e) {
            throw new MappingException("Não foi encontrado metódo setter da propriedade '%s'.".formatted(alias));
        }
    }

    private Object equivalentTypeMapping(Object value, Class fieldType) {
        return this.castingValidationChain.execute(value, fieldType);
    }

    public static <T> AliasToDtoMapper<T> getInstance(Class<T> dtoClass) {
        CastingValidationChain castingValidationChain = new DoubleToFloat()
                .setNext(new FloatToDouble())
                .setNext(new TimestampToInstant())
                .setNext(null);

        return new AliasToDtoMapper<>(dtoClass, castingValidationChain);
    }
}
