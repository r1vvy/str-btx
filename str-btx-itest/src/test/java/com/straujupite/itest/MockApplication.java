package com.straujupite.itest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.straujupite")
public class MockApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(MockApplication.class).run(args);
  }
}
