package com.straujupite.itest.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.straujupite.itest.BaseIntegrationTest;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

public class FileUtils {

  public static String read(String filePath) {
    try {
      var resource = BaseIntegrationTest.class.getResourceAsStream(filePath);
      assertNotNull(resource, String.format("File '%s' not found", filePath));

      return IOUtils.toString(resource, StandardCharsets.UTF_8);
    } catch (IOException exc) {
      throw new UncheckedIOException(exc);
    }
  }
}
