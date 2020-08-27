package com.ceejay.pojounit.pojo;

import com.ceejay.pojounit.util.exception.PojoTestBuildException;
import com.ceejay.pojounit.pojo.argument.TypedArg;
import com.ceejay.pojounit.pojo.constructor.ConstructorTest;
import com.ceejay.pojounit.util.reflect.Classes;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.*;

import static com.ceejay.pojounit.pojo.argument.ArgumentBuilder.*;

public class PojoTestBuilder<T> {
    public final Class<T> classToTest;
    private T instance = null;
    private ConstructorTest<T> constructor = null;
    private boolean testGetters = false;
    private boolean testSetters = false;
    private final Map<String, PojoProperty<T>> propertyMap = new HashMap<String, PojoProperty<T>>();
    private final List<ConstructorTest<T>> constructorTests = new ArrayList<ConstructorTest<T>>();


    private PojoTestBuilder(Class<T> classToTest,
                            Map<String, PojoProperty<T>> propertyMap) {
        this.classToTest = classToTest;
        this.propertyMap.putAll(propertyMap);
    }

    @NotNull
    @Contract("_ -> new")
    public static PojoMultipleTestBuilder testForAll(Class<?>... classesToTest) {
        return new PojoMultipleTestBuilder(classesToTest);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <T> PojoTestBuilder<T> testFor(@NotNull Class<T> classToTest) {

        final Map<String, PojoProperty<T>> propertyMap = new HashMap<String, PojoProperty<T>>();


        for (Method m : classToTest.getDeclaredMethods()) {
            val isGetter = m.getName().startsWith("get");
            val isSetter = m.getName().startsWith("set");

            if (Modifier.isPublic(m.getModifiers()) && (isGetter || isSetter)) {
                final String fieldName = StringUtils.uncapitalize(m.getName().substring(3));

                final PojoProperty<T> property;
                if (!propertyMap.containsKey(fieldName)) {
                    property = new PojoProperty<T>(classToTest, null, null, null);
                } else {
                    property = propertyMap.get(fieldName);
                }

                if (property.getField() == null) {
                    final Field f = FieldUtils.getDeclaredField(classToTest, fieldName, true);

                    if (f != null && (isGetter || !Modifier.isFinal(f.getModifiers()))) {
                        property.setField(f);
                    }
                }

                if (property.getField() != null) {
                    final Class<?>[] parameterTypes = m.getParameterTypes();

                    if (isGetter && parameterTypes.length == 0) {
                        property.setGetter(m);
                    }
                    if (isSetter && parameterTypes.length == 1 && parameterTypes[0] == property.getField().getType()) {
                        property.setSetter(m);
                    }
                    if (!propertyMap.containsKey(fieldName)) {
                        propertyMap.put(fieldName, property);
                    }
                }

            }
        }

        return new PojoTestBuilder<T>(classToTest, propertyMap);
    }

    @Contract("_ -> this")
    public PojoTestBuilder<T> excludeGetters(String... exclusions) {
        return exclude(true, false, exclusions);
    }

    @Contract("_ -> this")
    public PojoTestBuilder<T> excludeSetters(String... exclusions) {
        return exclude(false, true, exclusions);
    }

    @Contract("_ -> this")
    public PojoTestBuilder<T> excludeSetterAndGetter(String... exclusions) {
        return exclude(true, true, exclusions);
    }

    @Contract("-> this")
    public PojoTestBuilder<T> excludeDefaultConstructor() {
        return excludeConstructor();
    }

    @Contract(value = "_ -> this")
    public PojoTestBuilder<T> excludeConstructor(Class<?>... paramTypes) {
        val ctx = ConstructorUtils.getMatchingAccessibleConstructor(classToTest, paramTypes);

        val it = constructorTests.iterator();
        while (it.hasNext()) {
            if (it.next().getConstructor().equals(ctx)) {
                it.remove();
                break;
            }
        }

        if (constructor != null && constructor.getConstructor().equals(ctx)) {
            constructor = null;
            selectDefaultConstructor();
        }

        return this;
    }

    @Contract("_, _, _ -> this")
    private PojoTestBuilder<T> exclude(boolean getter, boolean setter, String... exclusions) {
        if (getter || setter) {
            for (val ex : exclusions) {
                final String fieldName;
                if (ex.startsWith("set") || ex.startsWith("get")) {
                    fieldName = StringUtils.uncapitalize(ex.substring(3));
                } else {
                    fieldName = StringUtils.uncapitalize(ex);
                }

                if (propertyMap.containsKey(fieldName)) {
                    val property = propertyMap.get(fieldName);
                    if (getter) {
                        property.setGetter(null);
                    }
                    if (setter) {
                        property.setSetter(null);
                    }

                    if (property.getGetter() == null && property.getSetter() == null) {
                        propertyMap.remove(fieldName);
                    }
                }
            }
        }
        return this;
    }

    @Contract("_, _ -> this")
    public PojoTestBuilder<T> mapFieldToGetter(String fieldName, String getterName) throws NoSuchFieldException,
            NoSuchMethodException {
        if (!("get" + StringUtils.capitalize(fieldName)).equals(getterName)) {
            val field = classToTest.getDeclaredField(fieldName);
            val getter = classToTest.getDeclaredMethod(getterName);

            if (propertyMap.containsKey(fieldName)) {
                propertyMap.get(fieldName).setField(field).setGetter(getter);
            } else {
                propertyMap.put(fieldName, new PojoProperty<T>(classToTest, field, getter, null));
            }
        }

        return this;
    }

    @Contract("_, _ -> this")
    public PojoTestBuilder<T> mapFieldToSetter(String fieldName, String setterName) throws NoSuchFieldException,
            NoSuchMethodException {
        if (!("set" + StringUtils.capitalize(fieldName)).equals(setterName)) {
            final Field field = classToTest.getDeclaredField(fieldName);
            final Method setter = classToTest.getDeclaredMethod(setterName);

            if (propertyMap.containsKey(fieldName)) {
                propertyMap.get(fieldName).setField(field).setSetter(setter);
            } else {
                propertyMap.put(fieldName, new PojoProperty<T>(classToTest, field, null, setter));
            }
        }

        return this;
    }

    @Contract("_, _, _ -> this")
    public PojoTestBuilder<T> mapFieldToGetterAndSetter(String fieldName, String getterName, String setterName)
            throws NoSuchFieldException, NoSuchMethodException {
        mapFieldToGetter(fieldName, getterName);
        return mapFieldToSetter(fieldName, setterName);
    }

    public PojoTestBuilder<T> withGetterTest() {
        testGetters = true;
        return this;
    }

    public PojoTestBuilder<T> withSetterTest() {
        testSetters = true;
        return this;
    }

    private PojoTestBuilder<T> withConstructor(boolean main, TypedArg<?>... params) throws NoSuchMethodException {
        final Constructor<T> ctx = findMatchingConstructor(params);

        final ConstructorTest<T> ct = new ConstructorTest<T>(ctx, params);

        if (main) {
            this.constructor = ct;
        } else {
            constructorTests.add(ct);
        }

        return this;
    }

    public PojoTestBuilder<T> withConstructor(TypedArg<?>... params) throws NoSuchMethodException {
        return withConstructor(constructor == null, params);
    }

    public PojoTestBuilder<T> withDefaultConstructor() throws NoSuchMethodException {
        return withConstructor(constructor == null);
    }

    public PojoTestBuilder<T> withMainConstructor(TypedArg<?>... params) throws NoSuchMethodException {
        return withConstructor(true, params);
    }

    public PojoTestBuilder<T> withMainDefaultConstructor() throws NoSuchMethodException {
        return withConstructor(true);
    }

    private PojoTestBuilder<T> onInstance(T instance) {
        if (constructor != null) {
            throw new IllegalStateException("È già stato specificato l'uso di un costruttore");
        }

        this.instance = instance;
        return this;
    }

    private Constructor<T> findMatchingConstructor(TypedArg<?>... params) throws NoSuchMethodException {
        final Class<?>[] paramClasses = new Class<?>[params.length];

        for (int i = 0; i < params.length; i++) {
            paramClasses[i] = params[i].getType();
        }

        final Constructor<T> ctx =
                ConstructorUtils.getMatchingAccessibleConstructor(classToTest, paramClasses);

        if (ctx == null) {
            StringBuilder str = new StringBuilder("(");
            for (int i = 0; i < paramClasses.length; i++) {
                if (i != 0) {
                    str.append(", ");
                }
                str.append(paramClasses[i].getSimpleName());
            }
            str.append(")");

            throw new NoSuchMethodException("La classe " + classToTest.getSimpleName() + " non ha un costruttore che " +
                    "accetta i seguenti parametri: " + str.toString());
        }

        return ctx;
    }

    @SuppressWarnings("unchecked")
    public PojoTestBuilder<T> withConstructorTest() {
        final Constructor<?>[] constructors = classToTest.getDeclaredConstructors();

        for (Constructor<?> ctx : constructors) {
            val paramTypes = ctx.getParameterTypes();
            val typedParams = new ArrayList<TypedArg<?>>();

            for(int i = 0; i < paramTypes.length; ++i) {
                final Class<?> cls = paramTypes[i];
                final Object defVal = Classes.getDefaultValueForClass(cls);

                if (ctx.isVarArgs() && i == paramTypes.length - 1) {
                    typedParams.add(emptyVarArgs().withComponentType(cls.getComponentType()));
                } else if (defVal != null) {
                    typedParams.add(arg(defVal));
                } else {
                    typedParams.add(nullArg().withType(cls));
                }
            }

            constructorTests.add(new ConstructorTest<T>((Constructor<T>) ctx, typedParams));
        }

        selectDefaultConstructor();

        return this;
    }

    private void selectDefaultConstructor() {
        Collections.sort(constructorTests, new Comparator<ConstructorTest<T>>() {
            @Override
            public int compare(ConstructorTest<T> o1, ConstructorTest<T> o2) {
                return NumberUtils.compare(o1.getParams().size(), o2.getParams().size());
            }
        });

        constructor = constructorTests.remove(0);
    }

    public PojoTest<T> build() {
        if (instance == null && constructor == null) {
            try {
                // Prova a prendere il costruttore di default
                withDefaultConstructor();
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("È necessario fornire almeno un costruttore o un istanza su cui " +
                        "effettuare i test nel caso in cui non sia presente un costruttore a 0 argomenti");
            }
        }

        if (instance == null) {
            try {
                instance = constructor.newInstance();
            } catch (IllegalAccessException e) {
                throw new PojoTestBuildException(e);
            } catch (InvocationTargetException e) {
                throw new PojoTestBuildException(e);
            } catch (InstantiationException e) {
                throw new PojoTestBuildException(e);
            }
        }

        if (!(testGetters && testSetters)) {
            for (Map.Entry<String, PojoProperty<T>> entry : propertyMap.entrySet()) {
                final PojoProperty<T> property = entry.getValue();
                if (!testGetters) {
                    property.setGetter(null);
                }
                if (!testSetters) {
                    property.setSetter(null);
                }
            }
        }

        return new PojoTest<T>(propertyMap, constructorTests, classToTest, instance);
    }
}
