package com.straujupite.common.dto.out.command;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.straujupite.common.dto.common.bitrix.BtxComment;
import com.straujupite.common.dto.common.bitrix.EntityType;
import com.straujupite.common.util.AddCommentOutSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = AddCommentOutSerializer.class)
public class AddCommentOutCommand {

  @NotNull
  private Integer entityId;

  @NotNull
  private EntityType entityType;

  @NotNull
  private BtxComment comment;
}
