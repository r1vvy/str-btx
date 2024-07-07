package com.straujupite.in.config;

import com.straujupite.in.config.filter.RequestLoggingFilter;
import com.straujupite.in.config.filter.TraceIdFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

  @Bean
  public TraceIdFilter traceIdFilter() {
    return new TraceIdFilter();
  }

  @Bean
  public RequestLoggingFilter loggingFilter() {
    return new RequestLoggingFilter();
  }

}
