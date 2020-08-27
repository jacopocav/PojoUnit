package com.ceejay.pojounit.util.reflect;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.apache.commons.lang3.ClassUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Utility class for {@link Method} objects.
 */
@UtilityClass
public class Methods {

    /**
     * Returns {@literal true} if the method {@code m} could be a getter.
     * <p>
     * A method can be a getter under the following conditions:
     * <ul>
     *     <li>Its name starts with "get" (if it returns a Boolean/boolean value it can also start with "is"/"has")</li>
     *     <li>It must take no arguments (<em>nullary</em> method)</li>
     * </ul>
     *
     * @param m method to check
     * @return {@literal true} if {@code m} satisfies all conditions to be a getter
     */
    @Contract(pure = true)
    public boolean isGetterLike(@NotNull Method m) {
        val name = m.getName();

        val isNullary = m.getParameterTypes().length == 0;

        val returnsBoolean = ClassUtils.isAssignable(m.getReturnType(), Boolean.class, true);

        val hasBooleanGetterPrefix = name.startsWith("is");

        return isNullary && (name.startsWith("get") || (returnsBoolean && hasBooleanGetterPrefix));
    }

    /**
     * Returns {@literal true} if the method {@code m} could be a setter.
     * <p>
     * A method can be a setter under the following conditions:
     * <ul>
     *     <li>Its name starts with "set"</li>
     *     <li>It must take exactly one argument (<em>unary</em> method)</li>
     *     <li>It must have a return type of {@code void} or of its declaring class (if it adopts fluent syntax)</li>
     * </ul>
     *
     * @param m method to check
     * @return {@literal true} if {@code m} satisfies all conditions to be a setter
     */
    @Contract(pure = true)
    public boolean isSetterLike(@NotNull Method m) {
        val name = m.getName();

        val isUnary = m.getParameterTypes().length == 1;

        val returnsVoidOrThis = m.getReturnType().equals(void.class) || m.getReturnType().isAssignableFrom(m.getDeclaringClass());

        return isUnary && returnsVoidOrThis && name.startsWith("set");
    }

    /**
     * Returns {@literal true} if method {@code m} can be a getter for field {@code f}.
     * <p>
     * It does all the checks of {@link Methods#isGetterLike(Method)} and adds the following requirements:
     * <ul>
     *     <li>the method's declaring class must be the same or a supertype of the field's declaring class</li>
     *     <li>the field type must be assignable to the return type of the method.</li>
     * </ul>
     *
     * @param m method to check
     * @param f field to check
     * @return {@literal true} if {@code m} satisfies all conditions to be a getter for {@code f}
     */
    @Contract(pure = true)
    public boolean isGetterForField(@NotNull Method m, @NotNull Field f) {
        if (isGetterLike(m) && m.getDeclaringClass().equals(f.getDeclaringClass())) {
            return ClassUtils.isAssignable(f.getType(), m.getReturnType(), true);
        }
        return false;
    }

    /**
     * Returns {@literal true} if method {@code m} can be a setter for field {@code f}.
     * <p>
     * It does all the checks of {@link Methods#isSetterLike(Method)} and adds the requirement
     * that the method's only argument type must be assignable to the field type.
     *
     * @param m method to check
     * @param f field to check
     * @return {@literal true} if {@code m} satisfies all conditions to be a setter for {@code f}
     */
    @Contract(pure = true)
    public boolean isSetterForField(@NotNull Method m, @NotNull Field f) {
        if (isSetterLike(m) && m.getDeclaringClass().equals(f.getDeclaringClass())) {
            return ClassUtils.isAssignable(m.getParameterTypes()[0], f.getType(), true);
        }
        return false;
    }
}
