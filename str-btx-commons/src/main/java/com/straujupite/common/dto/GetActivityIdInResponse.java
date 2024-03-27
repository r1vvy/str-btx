package com.straujupite.common.dto;


import com.straujupite.common.dto.result.TodoActivityResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetActivityIdInResponse {

    private TodoActivityResult result;
}
