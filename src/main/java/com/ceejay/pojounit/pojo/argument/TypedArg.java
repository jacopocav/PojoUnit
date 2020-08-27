package com.ceejay.pojounit.pojo.argument;


import lombok.*;
import lombok.experimental.Wither;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TypedArg<T> {
    @Wither
    @NonNull
    private final Class<? super T> type;
    private final T value;
}
