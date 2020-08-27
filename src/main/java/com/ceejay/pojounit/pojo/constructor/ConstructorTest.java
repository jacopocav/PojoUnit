package com.ceejay.pojounit.pojo.constructor;

import com.ceejay.pojounit.pojo.argument.TypedArg;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConstructorTest<T> {
    private final Constructor<T> constructor;
    private final List<? extends TypedArg<?>> params;

    public ConstructorTest(Constructor<T> constructor, List<? extends TypedArg<?>> params) {
        this.constructor = constructor;
        this.params = Collections.unmodifiableList(params);
    }

    public ConstructorTest(Constructor<T> constructor, TypedArg<?>... params) {
        this(constructor, Arrays.asList(Arrays.copyOf(params, params.length)));
    }

    public T newInstance() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Object> list = new ArrayList<Object>();
        for (TypedArg<?> param : params) {
            Object value = param.getValue();
            list.add(value);
        }
        return constructor.newInstance(list.toArray());
    }

    public Constructor<T> getConstructor() {
        return constructor;
    }

    public List<? extends TypedArg<?>> getParams() { // NOSONAR
        return params;
    }
}