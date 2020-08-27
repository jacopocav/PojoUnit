package com.ceejay.pojounit.util.reflect;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Modifier;

/**
 * Classe di utilità per effettuare operazioni tramite reflection
 */
@UtilityClass
public class Fields {
    /**
     * Cambia il valore di campo static final (può essere private)
     * @param clazz classe in cui cambiare il campo
     * @param fieldName nome del campo
     * @param fieldValue valore da assegnare al campo
     * @throws IllegalAccessException se è attivo un SecurityManager che impedisce l'accesso ai campi private e/o final
     */
    public void setStaticFinalField(Class<?> clazz, String fieldName, Object fieldValue)
            throws IllegalAccessException {
        val field = FieldUtils.getDeclaredField(clazz, fieldName, true);

        val modifiers = FieldUtils.getDeclaredField(field.getClass(), "modifiers", true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL); // NOSONAR

        field.set(null, fieldValue); // NOSONAR
    }

    /**
     * Cambia il valore di campo final (può essere private)
     * @param obj oggetto in cui cambiare il campo
     * @param fieldName nome del campo
     * @param fieldValue valore da assegnare al campo
     * @throws NoSuchFieldException se il campo non esiste
     * @throws IllegalAccessException se è attivo un SecurityManager che impedisce l'accesso ai campi private e/o final
     */
    public void setFinalField(Object obj, String fieldName, Object fieldValue)
            throws NoSuchFieldException, IllegalAccessException {
        val field = FieldUtils.getDeclaredField(obj.getClass(), fieldName, true);

        val modifiers = FieldUtils.getDeclaredField(field.getClass(), "modifiers", true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL); // NOSONAR

        field.set(obj, fieldValue); // NOSONAR
    }
}
