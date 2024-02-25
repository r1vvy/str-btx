package com.straujupite.common.converter;

import java.util.List;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ConversionService {
  private final List<ReactiveConverter<?, ?>> converters;

  public ConversionService(List<ReactiveConverter<?, ?>> converters) {
    this.converters = converters;
  }

  @SuppressWarnings("unchecked")
  public <S, T> Mono<T> convert(S source, Class<T> targetClass) {
    return Flux.fromIterable(converters)
               .filter(converter -> converter.canConvert(source.getClass(), targetClass))
               .next()
               .flatMap(converter -> ((ReactiveConverter<S, T>)converter).convert(source, targetClass))
               .switchIfEmpty(Mono.error(new IllegalStateException("No suitable converter found")));
  }
}
