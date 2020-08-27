package com.ceejay.pojounit.util.reflect;

import lombok.Value;
import lombok.val;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class FieldsTest {

    @Value
    static class FieldTestClass {
        private static final List<Integer> PSF_FIELD = Collections.singletonList(0);
        int pfField;
    }

    @Test
    public void setStaticFinalField() {
        assertThat(FieldTestClass.PSF_FIELD.get(0)).isZero();

        try {
            Fields.setStaticFinalField(FieldTestClass.class, "PSF_FIELD", Collections.singletonList(1));
        } catch (IllegalAccessException e) {
            fail("setStaticFinalField failed! Exception: ", e);
        }

        assertThat(FieldTestClass.PSF_FIELD.get(0)).isEqualTo(1);
    }

    @Test
    public void setFinalField() {
        val instance = new FieldTestClass(0);

        assertThat(instance.pfField).isZero();

        try {
            Fields.setFinalField(instance, "pfField", 1);
        } catch (NoSuchFieldException e) {
            fail("setFinalField failed! Exception: ", e);
        } catch (IllegalAccessException e) {
            fail("setFinalField failed! Exception: ", e);
        }

        assertThat(instance.getPfField()).isEqualTo(1);
    }
}