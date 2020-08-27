package com.ceejay.pojounit.pojo;

import java.util.ArrayList;
import java.util.List;

public class PojoMultipleTestBuilder {
    private final List<PojoTestBuilder<?>> pojoTesters = new ArrayList<PojoTestBuilder<?>>();


    public PojoMultipleTestBuilder(Class<?>... classes) {
        for (Class<?> aClass : classes) {
            pojoTesters.add(PojoTestBuilder.testFor(aClass));
        }
    }

    public PojoMultipleTestBuilder withGetterTest() {
        for (PojoTestBuilder<?> pojoTester : pojoTesters) {
            pojoTester.withGetterTest();
        }
        return this;
    }

    public PojoMultipleTestBuilder withSetterTest() {
        for (PojoTestBuilder<?> pojoTester : pojoTesters) {
            pojoTester.withSetterTest();
        }
        return this;
    }

    public PojoMultipleTestBuilder withConstructorTest() {
        for (PojoTestBuilder<?> pojoTester : pojoTesters) {
            pojoTester.withConstructorTest();
        }
        return this;
    }

    public PojoMultipleTestBuilder testAll() {
        for (PojoTestBuilder<?> pt : pojoTesters) {
            pt.build().testAll();
        }
        return this;
    }
}
