package com.ceejay.pojounit.pojo.argument;

public class NullVarArgs {
    static final NullVarArgs INSTANCE = new NullVarArgs();

    private NullVarArgs() {}

    public <T> TypedVarArgs<T> withComponentType(Class<? super T> componentType) {
        return TypedVarArgs.ofComponentType(componentType, null);
    }
}
