package com.straujupite.itest.mock;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.straujupite.itest.util.FileUtils.read;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

abstract class BaseMock {

  public static final WireMockServer wireMockServer = new WireMockServer(options().port(1337));

  @PostConstruct
  private void start() {
    wireMockServer.start();
  }

  @PreDestroy
  private void stop() {
    wireMockServer.stop();
  }

  public void reset() {
    wireMockServer.resetAll();
  }

  protected void stub(String requestJsonPath, String responseJsonPath) {
    wireMockServer.resetAll();
    var requestStub = StubMapping.buildFrom(read(requestJsonPath));
    var responseStub = StubMapping.buildFrom(read(responseJsonPath));

    requestStub.setResponse(responseStub.getResponse());
    wireMockServer.addStubMapping(requestStub);
  }

  protected void stubMore(String requestJsonPath, String responseJsonPath) {
    var requestStub = StubMapping.buildFrom(read(requestJsonPath));
    var responseStub = StubMapping.buildFrom(read(responseJsonPath));

    requestStub.setResponse(responseStub.getResponse());
    wireMockServer.addStubMapping(requestStub);
  }

}
