package com.straujupite.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.straujupite")
public class StrBtxApplication {
    private static final Logger logger = LoggerFactory.getLogger(StrBtxApplication.class);

    public static void main(String[] args) {
        logger.info("Application started");
        SpringApplication.run(StrBtxApplication.class, args);
        logger.info("Application finished");
    }
}