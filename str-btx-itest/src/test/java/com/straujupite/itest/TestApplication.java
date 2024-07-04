package com.straujupite.itest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.straujupite")
public class TestApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(TestApplication.class).run(args);
  }
}
