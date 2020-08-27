package com.ceejay.pojounit.pojo.argument;

import java.lang.reflect.Array;

public class EmptyVarArgs {
    static final EmptyVarArgs INSTANCE = new EmptyVarArgs();

    private EmptyVarArgs() {
    }

    @SuppressWarnings("unchecked")
    public <T> TypedVarArgs<T> withComponentType(Class<T> componentType) {
        final Object arr = Array.newInstance(componentType, 0);
        return TypedVarArgs.ofComponentType(componentType, (T[]) arr);
    }
}
