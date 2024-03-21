package com.straujupite.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.straujupite.common.dto.out.command.AddCommentOutCommand;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class AddCommentOutSerializer extends StdSerializer<AddCommentOutCommand> {

  public AddCommentOutSerializer() {
    this(null);
  }

  public AddCommentOutSerializer(Class<AddCommentOutCommand> t) {
    super(t);
  }

  @Override
  public void serialize(AddCommentOutCommand addCommentOutCommand, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeObjectFieldStart("fields");

    jsonGenerator.writeNumberField("ENTITY_ID", addCommentOutCommand.getEntityId());
    jsonGenerator.writeStringField("ENTITY_TYPE", addCommentOutCommand.getEntityType().toString());
    jsonGenerator.writeStringField("COMMENT", addCommentOutCommand.getComment().getValue());

    jsonGenerator.writeEndObject();
    jsonGenerator.writeEndObject();
  }
}
