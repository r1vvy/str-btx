package com.straujupite.in.config.filter;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@Slf4j
public class TraceIdFilter implements WebFilter, Ordered {

  private static final String TRACE_ID = "traceId";

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    var traceId = UUID.randomUUID().toString();
    return chain.filter(exchange)
                .contextWrite(Context.of(TRACE_ID, traceId));
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }
}
