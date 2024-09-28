package com.straujupite.itest;

import com.straujupite.itest.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.common.ContentTypes.APPLICATION_JSON;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    classes = TestApplication.class,
    properties = "classpath=application-itest.yml"
)
@ActiveProfiles("itest")
public abstract class BaseIntegrationTest {

  protected static final String JSON_PATH = "/json/";

  @LocalServerPort
  protected Integer port;

  @Autowired
  private ApplicationContext appContext;

  protected String getJson(String filePathAndName) {
    return FileUtils.read(JSON_PATH + filePathAndName);
  }

  protected WebTestClient webTestClient() {
    return WebTestClient.bindToApplicationContext(appContext)
                        .configureClient()
                        .baseUrl("http://localhost:" + port)
                        .defaultHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .build();
  }

  protected void retrieveCallInfo(String request) {
    webTestClient().post()
                   .uri("/api/retrieveCallInfo")
                   .bodyValue(request)
                   .exchange()
                   .expectStatus()
                   .is2xxSuccessful()
                   .expectBody()
                   .isEmpty();
  }

    private static void setGoogleCredentialsSystemProperty() {
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "");
    }
}
