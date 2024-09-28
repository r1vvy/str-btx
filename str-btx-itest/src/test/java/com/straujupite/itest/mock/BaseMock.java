package com.straujupite.itest.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.straujupite.itest.util.FileUtils.read;

public abstract class BaseMock {

  public static final WireMockServer wireMockServer = new WireMockServer(options().port(1337));
  protected static final String OUTGOING_JSON_PATH = "/json/out";


  public static int getPort() {
    return wireMockServer.port();
  }

  public void reset() {
    wireMockServer.resetAll();
  }

  protected void stubPost(String url, int statusCode, String requestBodyPath, String responseBodyPath) {
    stubFor(post(urlEqualTo(url))
            .withRequestBody(equalToJson(read(requestBodyPath), true, true))
            .willReturn(aResponse()
                    .withStatus(statusCode)
                    .withHeader("Content-Type", "application/json")
                    .withBody(read(responseBodyPath))));
  }

  protected void stubGet(String urlPrefix, int statusCode, String responseBodyPath) {
    stubFor(get(urlPathMatching(urlPrefix + "*"))
            .willReturn(aResponse()
                    .withStatus(statusCode)
                    .withHeader("Content-Type", "application/json")
                    .withBody(read(responseBodyPath))));
  }

  protected WireMockServer getWireMockServer() {
    return wireMockServer;
  }

  @PostConstruct
  private void start() {
    wireMockServer.start();
    WireMock.configureFor("localhost", wireMockServer.port());
  }

  @PreDestroy
  private void stop() {
    wireMockServer.stop();
  }
}
