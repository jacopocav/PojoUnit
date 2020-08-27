package com.ceejay.pojounit.pojo.argument;

import java.lang.reflect.Array;

public class TypedVarArgs<T> extends TypedArg<T[]> {

    @SuppressWarnings("unchecked")
    private TypedVarArgs(Class<? super T> componentType, T[] value) {
        super((Class<? super T[]>) Array.newInstance(componentType, 0).getClass(), value);
    }

    static <T> TypedVarArgs<T> ofComponentType(Class<? super T> componentType, T[] array) {
        return new TypedVarArgs<T>(componentType, array);
    }

    @SuppressWarnings("unchecked")
    static <T> TypedVarArgs<T> of(T[] array) {
        return new TypedVarArgs<T>((Class<T>) array.getClass().getComponentType(), array);
    }

    public TypedVarArgs<T> withComponentType(Class<? super T> componentType) {
        return new TypedVarArgs<T>(componentType, getValue());
    }

    @SuppressWarnings("unchecked")
    public Class<? super T> getComponentType() {
        return (Class<? super T>) getType().getComponentType();
    }
}
