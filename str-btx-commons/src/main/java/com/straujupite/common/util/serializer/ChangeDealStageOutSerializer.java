package com.straujupite.common.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.straujupite.common.dto.out.request.ChangeDealStageOutRequest;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class ChangeDealStageOutSerializer extends StdSerializer<ChangeDealStageOutRequest> {

  public ChangeDealStageOutSerializer() {
    this(null);
  }

  public ChangeDealStageOutSerializer(Class<ChangeDealStageOutRequest> t) {
    super(t);
  }

  @Override
  public void serialize(ChangeDealStageOutRequest value, JsonGenerator jsonGenerator,
      SerializerProvider provider) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("id", value.getDealId());

    jsonGenerator.writeObjectFieldStart("fields");
    jsonGenerator.writeStringField("STAGE_ID", value.getDealStage().getValue());
    jsonGenerator.writeEndObject();

    jsonGenerator.writeEndObject();
  }
}
