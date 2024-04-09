package com.straujupite.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@SpringBootApplication(scanBasePackages = "com.straujupite")
public class StrBtxApplication {
    public static void main(String[] args) {
        SpringApplication.run(StrBtxApplication.class, args);
    }
}