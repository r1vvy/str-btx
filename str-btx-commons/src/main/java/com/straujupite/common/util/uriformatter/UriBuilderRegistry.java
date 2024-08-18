package com.straujupite.common.util.uriformatter;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UriBuilderRegistry {

  private final Map<Class<?>, UriBuilder<?>> uriBuilders = new HashMap<>();

  public <T> void registerUriBuilder(Class<T> requestType, UriBuilder<T> uriBuilder) {
    uriBuilders.put(requestType, uriBuilder);
  }

  @SuppressWarnings("unchecked")
  public <T> UriBuilder<T> getUriBuilder(Class<?> requestType) {
    UriBuilder<?> builder = uriBuilders.get(requestType);

    if (builder == null) {
      throw new IllegalArgumentException("No UriBuilder registered for " + requestType.getName());
    }

    try {
      return (UriBuilder<T>) builder;
    } catch (ClassCastException exc) {
      throw new IllegalArgumentException(
          "UriBuilder found, but type mismatch for " + requestType.getName(), exc);
    }
  }
}
