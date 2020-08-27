package com.ceejay.pojounit.pojo.argument;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class ArgumentBuilder {

    @NotNull
    @Contract(pure = true)
    public static NullArgument nullArg() {
        return NullArgument.INSTANCE;
    }

    @NotNull
    @Contract(pure = true)
    public static EmptyVarArgs emptyVarArgs() {
        return EmptyVarArgs.INSTANCE;
    }

    @NotNull
    @Contract(pure = true)
    public static NullVarArgs nullVarArgs() {
        return NullVarArgs.INSTANCE;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    @SuppressWarnings("unchecked")
    public static <T> TypedArg<T> arg(@NotNull T value) {
        Validate.notNull(value, "value should not be null. Use nullArg() instead.");

        return new TypedArg<T>((Class<T>) value.getClass(), value);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <T> TypedVarArgs<T> varArgs(@NotNull T... values) {
        Validate.notEmpty(values, "values should have at least 1 element. " +
                "You can use emptyVarArgs() if you need an empty array or nullVarArgs() if you need a null array.");


        return TypedVarArgs.of(values);
    }
}
