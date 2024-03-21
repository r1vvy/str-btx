package com.straujupite.itest;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    classes = MockApplication.class,
    properties = "classpath:application-itest.yml"
)
@ActiveProfiles("itest")
public abstract class BaseIntegrationTest {

  @LocalServerPort
  protected Integer port;

  @BeforeAll
  public static void setup() {

  }
}
