package com.ceejay.pojounit.pojo.argument;

import lombok.NonNull;

public class NullArgument {
    static final NullArgument INSTANCE = new NullArgument();

    private NullArgument() {}

    @NonNull
    public <T> TypedArg<T> withType(@NonNull Class<T> type) {
        return new TypedArg<T>(type, null);
    }
}
