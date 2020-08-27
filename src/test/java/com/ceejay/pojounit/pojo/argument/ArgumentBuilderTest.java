package com.ceejay.pojounit.pojo.argument;

import lombok.val;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArgumentBuilderTest {

    @Test
    public void nullArg() throws IllegalAccessException {
        val instance = FieldUtils.readDeclaredStaticField(NullArgument.class, "INSTANCE", true);

        assertSame(instance, ArgumentBuilder.nullArg());
    }

    @Test
    public void emptyVarArgs() throws IllegalAccessException {
        val instance = FieldUtils.readDeclaredStaticField(EmptyVarArgs.class, "INSTANCE", true);

        assertSame(instance, ArgumentBuilder.emptyVarArgs());
    }

    @Test
    public void nullVarArgs() throws IllegalAccessException {
        val instance = FieldUtils.readDeclaredStaticField(NullVarArgs.class, "INSTANCE", true);

        assertSame(instance, ArgumentBuilder.nullVarArgs());
    }

    @Test
    public void arg() {
        val arg = new TypedArg<Integer>(Integer.class, 1);

        assertEquals(arg, ArgumentBuilder.arg(1));
    }

    @Test
    public void varArgs() {
        val args = TypedVarArgs.of(new Integer[]{1,2,3});

        val varArgs = ArgumentBuilder.varArgs(1, 2, 3);

        assertArrayEquals(args.getValue(), varArgs.getValue());
        assertEquals(args.getType(), args.getType());
    }
}