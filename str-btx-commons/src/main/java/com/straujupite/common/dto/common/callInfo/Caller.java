package com.straujupite.common.dto.common.callInfo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor_ = {@JsonCreator})
public class Caller {

  @JsonValue
  @JsonAlias({"callerid", "caller"})
  @NotBlank
  private final String number;

}
