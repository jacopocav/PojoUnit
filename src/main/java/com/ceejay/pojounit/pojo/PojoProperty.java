package com.ceejay.pojounit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Data
@AllArgsConstructor
class PojoProperty<T> {
    Class<T> clazz;
    Field field;
    Method getter;
    Method setter;
}
