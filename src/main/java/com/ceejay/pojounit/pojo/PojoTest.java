package com.ceejay.pojounit.pojo;

import com.ceejay.pojounit.pojo.constructor.ConstructorTest;
import lombok.Value;
import lombok.val;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@Value
public class PojoTest<T> {
    Map<String, PojoProperty<T>> propertyMap;
    List<ConstructorTest<T>> constructorTests;
    Class<T> clazz;
    T instance;


    public PojoTest<T> testConstructors() {
        for (val ct : constructorTests) {
            try {
                val instance = ct.newInstance(); // NOSONAR
                assertTrue(true);
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }

        return this;
    }

    public PojoTest<T> testGetters() {
        for (Map.Entry<String, PojoProperty<T>> e : propertyMap.entrySet()) {
            val prop = e.getValue();

            if (prop.getGetter() != null) {
                try {
                    assertEquals(prop.getField().get(instance), prop.getGetter().invoke(instance));
                } catch (Exception ex) {
                    fail(ex.getMessage());
                }
            }
        }
        return this;
    }

    public PojoTest<T> testSetters() {
        for (Map.Entry<String, PojoProperty<T>> e : propertyMap.entrySet()) {
            val prop = e.getValue();

            if (prop.getSetter() != null) {
                try {
                    val fieldBefore = prop.getField().get(instance);
                    prop.getSetter().invoke(instance, fieldBefore);
                    assertEquals(fieldBefore, prop.getField().get(instance));
                } catch (Exception ex) {
                    fail(ex.getMessage());
                }
            }
        }
        return this;
    }

    public PojoTest<T> testAll() {
        testConstructors();
        testGetters();
        testSetters();
        return this;
    }
}
