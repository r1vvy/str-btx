package com.straujupite.common.util.addcomment;

import static com.straujupite.common.util.formatter.DefaultDateTimeFormatter.DATE_FORMATTER;
import static com.straujupite.common.util.formatter.DefaultDateTimeFormatter.TIME_FORMATTER;

import com.straujupite.common.dto.common.bitrix.BtxComment;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class CommentBuilder {

  public BtxComment buildCommentByTemplate(String template, Object... fields) {
    var parsedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Riga"));
    var date = parsedDateTime.format(DATE_FORMATTER);
    var time = parsedDateTime.format(TIME_FORMATTER);

    var fieldsWithDateAndTime = Stream.concat(Arrays.stream(fields), Stream.of(date, time))
                               .toArray(Object[]::new);

    return new BtxComment(String.format(template, fieldsWithDateAndTime));
  }

}
