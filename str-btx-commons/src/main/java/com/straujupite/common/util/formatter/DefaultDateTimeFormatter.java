package com.straujupite.common.util.formatter;

import java.time.format.DateTimeFormatter;

public class DefaultDateTimeFormatter {

  public static final DateTimeFormatter FORMATTER_WITH_TIMEZONE = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd'T'HH:mm:ssZ");
  public static final DateTimeFormatter FORMATTER_WITHOUT_TIMEZONE = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd'T'HH:mm:ss");
  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

}
