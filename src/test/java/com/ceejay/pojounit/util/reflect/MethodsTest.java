package com.ceejay.pojounit.util.reflect;

import org.junit.Test;
import testutil.AnotherMethodsTestClass;
import testutil.MethodsTestClass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class MethodsTest {

    private static final Class<?> cls = MethodsTestClass.class;
    private static final Class<?> anotherCls = AnotherMethodsTestClass.class;

    @Test
    public void isGetterLike() {
        try {
            assertThat(Methods.isGetterLike(cls.getDeclaredMethod("getRightProperty"))).isTrue();
            assertThat(Methods.isGetterLike(cls.getDeclaredMethod("isRightBooleanProperty"))).isTrue();
            assertThat(Methods.isGetterLike(cls.getDeclaredMethod("getWrongProperty"))).isTrue();

            assertThat(Methods.isGetterLike(cls.getDeclaredMethod("isWrongBooleanProperty"))).isFalse();
            assertThat(Methods.isGetterLike(cls.getDeclaredMethod("getNotNullary", Object.class))).isFalse();
            assertThat(Methods.isGetterLike(cls.getDeclaredMethod("hasWrongName"))).isFalse();
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void isSetterLike() {
        try {
            assertThat(Methods.isSetterLike(cls.getDeclaredMethod("setRightProperty", Integer.class))).isTrue();
            assertThat(Methods.isSetterLike(cls.getDeclaredMethod("setRightBooleanProperty", boolean.class))).isTrue();
            assertThat(Methods.isSetterLike(cls.getDeclaredMethod("setWrongProperty", int.class))).isTrue();
            assertThat(Methods.isSetterLike(cls.getDeclaredMethod("setFluentBooleanProperty", boolean.class))).isTrue();

            assertThat(Methods.isSetterLike(cls.getDeclaredMethod("setNotUnary"))).isFalse();
            assertThat(Methods.isSetterLike(cls.getDeclaredMethod("setWrongReturnType", int.class))).isFalse();
            assertThat(Methods.isSetterLike(cls.getDeclaredMethod("notASetter", boolean.class))).isFalse();
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void isGetterForField() {
        try {
            assertThat(Methods.isGetterForField(cls.getDeclaredMethod("getRightProperty"),
                    cls.getDeclaredField("rightProperty"))).isTrue();
            assertThat(Methods.isGetterForField(cls.getDeclaredMethod("isRightBooleanProperty"),
                    cls.getDeclaredField("rightBooleanProperty"))).isTrue();

            assertThat(Methods.isGetterForField(cls.getDeclaredMethod("getWrongProperty"),
                    cls.getDeclaredField("wrongProperty"))).isFalse();
            assertThat(Methods.isGetterForField(cls.getDeclaredMethod("notAGetter"),
                    cls.getDeclaredField("notAProperty"))).isFalse();
            assertThat(Methods.isGetterForField(cls.getDeclaredMethod("getWrongProperty"),
                    anotherCls.getDeclaredField("notInSameClass"))).isFalse();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void isSetterForField() {
        try {
            assertThat(Methods.isSetterForField(cls.getDeclaredMethod("setRightProperty", Integer.class),
                    cls.getDeclaredField("rightProperty"))).isTrue();
            assertThat(Methods.isSetterForField(cls.getDeclaredMethod("setRightBooleanProperty", boolean.class),
                    cls.getDeclaredField("rightBooleanProperty"))).isTrue();

            assertThat(Methods.isSetterForField(cls.getDeclaredMethod("setWrongProperty", int.class),
                    cls.getDeclaredField("wrongProperty"))).isFalse();
            assertThat(Methods.isSetterForField(cls.getDeclaredMethod("notASetter", boolean.class),
                    cls.getDeclaredField("notAProperty"))).isFalse();
            assertThat(Methods.isSetterForField(cls.getDeclaredMethod("setWrongProperty", int.class),
                    anotherCls.getDeclaredField("notInSameClass"))).isFalse();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}