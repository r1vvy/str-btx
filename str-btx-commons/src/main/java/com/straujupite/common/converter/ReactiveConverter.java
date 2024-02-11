package com.straujupite.common.converter;

import reactor.core.publisher.Mono;

public interface ReactiveConverter<S, T> {
    boolean canConvert(Class<?> sourceClass, Class<?> targetClass);
    Mono<T> convert(S source, Class<T> targetClass);
}