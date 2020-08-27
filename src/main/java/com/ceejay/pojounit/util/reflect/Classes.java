package com.ceejay.pojounit.util.reflect;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class Classes {
    /**
     * Returns the default value for the given class. Wrapper types default to {@literal null}.
     * <p>
     * This method is equivalent to calling {@link Classes#getDefaultValueForClass(Class, boolean)} with arguments (cls, false).
     *
     * @param cls the class
     * @return the default value for the given class
     * @see Classes#getDefaultValueForClass(Class, boolean)
     */
    public <T> T getDefaultValueForClass(Class<T> cls) {
        return getDefaultValueForClass(cls, false);
    }

    /**
     * Returns the default value for the given class. Wrapper types can default to {@literal null} or to their
     * respective primitive default value.
     * <p>
     * The default value for all reference types is {@literal null}.
     * The default values for primitive types are:
     * <ul>
     *     <li>{@code int: 0}</li>
     *     <li>{@code long: 0L}</li>
     *     <li>{@code short: (short) 0}</li>
     *     <li>{@code double: 0.0d}</li>
     *     <li>{@code float: 0.0f}</li>
     *     <li>{@code char:} '&#92;u0000'</li>
     *     <li>{@code byte: (byte) 0}</li>
     * </ul>
     *
     * @param cls                        the class
     * @param primitiveDefaultForWrapper {@literal true} if wrapper types should default to their corresponding primitive default value.
     *                                   {@literal false} if they should default to {@literal null}.
     * @return the default value for the given class
     */
    @SuppressWarnings("unchecked")
    public <T> T getDefaultValueForClass(Class<T> cls, boolean primitiveDefaultForWrapper) {
        if (cls.isPrimitive() || primitiveDefaultForWrapper) {
            return (T) primitiveDefaults.get(cls);
        }

        return null;
    }

    /**
     * Thread-safe cache for primitive default values.
     * <p>
     * Default values are computed only once for every primitive type when
     * {@link Classes#getDefaultValueForClass(Class, boolean)} is called with a primitive type or
     * with a wrapper type and with argument {@code primitiveDefaultForWrapper = true};
     * On subsequent calls they are taken from this map.
     */
    private final Map<Class<?>, Object> primitiveDefaults;

    static {
        // Initializing primitiveDefaults

        val tmp = new HashMap<Class<?>, Object>();

        // Trick: in order to avoid hard-coding default values for every primitive type, they can be obtained
        // dynamically by creating a singleton array of the given primitive type and returning its only element.
        tmp.put(boolean.class, false);
        tmp.put(Boolean.class, false);

        tmp.put(int.class, 0);
        tmp.put(Integer.class, 0);

        tmp.put(long.class, 0L);
        tmp.put(Long.class, 0L);

        tmp.put(short.class, (short) 0);
        tmp.put(Short.class, (short) 0);

        tmp.put(double.class, 0.0d);
        tmp.put(Double.class, 0.0d);

        tmp.put(float.class, 0.0f);
        tmp.put(Float.class, 0.0f);

        tmp.put(char.class, '\0');
        tmp.put(Character.class, '\0');

        tmp.put(byte.class, (byte) 0);
        tmp.put(Byte.class, (byte) 0);

        primitiveDefaults = Collections.unmodifiableMap(tmp);
    }
}
