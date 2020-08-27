package com.ceejay.pojounit.util.reflect;

import lombok.val;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

public class ClassesTest {

    @Test
    public void getDefaultValueForClass1() {
        val booleanDef = Classes.getDefaultValueForClass(boolean.class);
        assertThat(booleanDef).isFalse();

        val intDef = Classes.getDefaultValueForClass(int.class);
        assertThat(intDef).isZero();

        val longDef = Classes.getDefaultValueForClass(long.class);
        assertThat(longDef).isZero();

        val shortDef = Classes.getDefaultValueForClass(short.class);
        assertThat(shortDef).isZero();

        val doubleDef = Classes.getDefaultValueForClass(double.class);
        assertThat(doubleDef).isZero();

        val floatDef = Classes.getDefaultValueForClass(float.class);
        assertThat(floatDef).isZero();

        val charDef = Classes.getDefaultValueForClass(char.class);
        assertThat(charDef).isEqualTo('\u0000');

        val byteDef = Classes.getDefaultValueForClass(byte.class);
        assertThat(byteDef).isZero();

        val wBooleanDef = Classes.getDefaultValueForClass(Boolean.class);
        assertNull(wBooleanDef);

        val integerDef = Classes.getDefaultValueForClass(Integer.class);
        assertNull(integerDef);

        val wLongDef = Classes.getDefaultValueForClass(Long.class);
        assertNull(wLongDef);

        val wShortDef = Classes.getDefaultValueForClass(Short.class);
        assertNull(wShortDef);

        val wDoubleDef = Classes.getDefaultValueForClass(Double.class);
        assertNull(wDoubleDef);

        val wFloatDef = Classes.getDefaultValueForClass(Float.class);
        assertNull(wFloatDef);

        val characterDef = Classes.getDefaultValueForClass(Character.class);
        assertNull(characterDef);

        val wByteDef = Classes.getDefaultValueForClass(Byte.class);
        assertNull(wByteDef);
    }

    @Test
    public void testGetDefaultValueForClass2() {
        val wBooleanDef = Classes.getDefaultValueForClass(Boolean.class, true);
        assertThat(wBooleanDef).isFalse();

        val integerDef = Classes.getDefaultValueForClass(Integer.class, true);
        assertThat(integerDef).isZero();

        val wLongDef = Classes.getDefaultValueForClass(Long.class, true);
        assertThat(wLongDef).isZero();

        val wShortDef = Classes.getDefaultValueForClass(Short.class, true);
        assertThat(wShortDef).isZero();

        val wDoubleDef = Classes.getDefaultValueForClass(Double.class, true);
        assertThat(wDoubleDef).isZero();

        val wFloatDef = Classes.getDefaultValueForClass(Float.class, true);
        assertThat(wFloatDef).isZero();

        val characterDef = Classes.getDefaultValueForClass(Character.class, true);
        assertThat(characterDef).isEqualTo('\u0000');

        val wByteDef = Classes.getDefaultValueForClass(Byte.class, true);
        assertThat(wByteDef).isZero();
    }
}