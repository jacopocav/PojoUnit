package com.ceejay.pojounit.pojo.argument;

import lombok.val;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TypedVarArgsTest {

    @Test
    public void ofComponentType() {
        val typedVarArgs = TypedVarArgs.ofComponentType(int.class, new Integer[]{1, 2, 3});

        assertArrayEquals(new Integer[]{1,2,3}, typedVarArgs.getValue());
        assertEquals(int.class, typedVarArgs.getComponentType());
    }

    @Test
    public void of() {
        val typedVarArgs = TypedVarArgs.of(new Integer[]{1,2,3});

        assertArrayEquals(new Integer[]{1,2,3}, typedVarArgs.getValue());
        assertEquals(Integer.class, typedVarArgs.getComponentType());
    }

    @Test
    public void withComponentType() {
        val typedVarArgs = TypedVarArgs.of(new Integer[]{1,2,3});
        val primitiveTypedArgs = typedVarArgs.withComponentType(int.class);

        assertArrayEquals(typedVarArgs.getValue(), primitiveTypedArgs.getValue());
        assertEquals(int.class, primitiveTypedArgs.getComponentType());
    }
}