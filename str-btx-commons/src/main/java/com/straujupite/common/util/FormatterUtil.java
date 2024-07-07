package com.straujupite.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormatterUtil {

  public static String formatJson(String json) {
    var objectMapper = new ObjectMapper();
    try {
      var jsonObject = objectMapper.readValue(json, Object.class);
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    } catch (Exception e) {
      log.error("Failed to format JSON", e);
      return json;
    }
  }
}
