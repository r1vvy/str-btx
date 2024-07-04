package com.straujupite.in.util;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RequestResponseLogUtils {

  public static Mono<StringBuilder> getRequestBody(ServerHttpRequest request) {
    return DataBufferUtils.join(request.getBody())
                          .map(dataBuffer -> {
                            StringBuilder sb = new StringBuilder();
                            byte[] bytes = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(bytes);
                            DataBufferUtils.release(dataBuffer);
                            String body = new String(bytes, StandardCharsets.UTF_8);
                            sb.append(body);

                            return sb;
                          });
  }

  public static String buildRequestLog(StringBuilder body, HttpMethod requestMethod,
      URI requestURI) {
    return String.format("\n"
            + "----------------INCOMING REQUEST----------------\n"
            + "URL: %s\n"
            + "METHOD: %s\n"
            + "BODY:\n"
            + "%s",
        requestURI, requestMethod, body.toString()
    );
  }

  public static String buildResponseLog(HttpStatusCode status) {
    return String.format("\n"
            + "----------------INCOMING RESPONSE----------------\n"
            + "STATUS: %s\n"
            + "%s",
        status.value()
    );
  }
}
