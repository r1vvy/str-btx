package com.straujupite.common.dto.out.command;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.straujupite.common.dto.common.bitrix.EntityType;
import com.straujupite.common.util.AddCommentOutSerializer;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = AddCommentOutSerializer.class)
public class AddCommentOutCommand {

  @NotBlank
  private Integer entityId;

  @NotBlank
  private EntityType entityType;

  @NotBlank
  private String comment;
}
