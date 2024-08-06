package com.straujupite.common.util.addcomment;

import com.straujupite.common.dto.common.bitrix.BtxComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

import static com.straujupite.common.util.formatter.DefaultDateTimeFormatter.DATE_FORMATTER;
import static com.straujupite.common.util.formatter.DefaultDateTimeFormatter.TIME_FORMATTER;

@Component
@RequiredArgsConstructor
public class CommentBuilder {

  private final Clock clock;

  public BtxComment buildCommentByTemplate(String template, Object... fields) {
    var parsedDateTime = ZonedDateTime.now(clock);
    var date = parsedDateTime.format(DATE_FORMATTER);
    var time = parsedDateTime.format(TIME_FORMATTER);

    var fieldsWithDateAndTime = Stream.concat(Arrays.stream(fields), Stream.of(date, time))
            .toArray(Object[]::new);

    return new BtxComment(String.format(template, fieldsWithDateAndTime));
  }
}
