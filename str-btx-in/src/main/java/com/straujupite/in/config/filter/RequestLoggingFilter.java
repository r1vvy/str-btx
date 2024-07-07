package com.straujupite.in.config.filter;

import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;
import static com.straujupite.in.util.RequestResponseLogUtils.buildRequestLog;
import static com.straujupite.in.util.RequestResponseLogUtils.getRequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class RequestLoggingFilter implements WebFilter, Ordered {

  private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    return Mono.deferContextual(ctx -> logRequest(exchange))
               .then(chain.filter(exchange));
  }

  private Mono<String> logRequest(ServerWebExchange exchange) {
    var requestMethod = exchange.getRequest().getMethod();
    var requestURI = exchange.getRequest().getURI();

    return getRequestBody(exchange.getRequest()).map(
                                                    body -> buildRequestLog(body, requestMethod, requestURI))
                                                .doOnEach(logOnNext(log::debug));
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }
}