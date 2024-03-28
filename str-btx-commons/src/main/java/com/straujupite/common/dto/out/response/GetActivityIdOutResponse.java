package com.straujupite.common.dto.out.response;


import com.straujupite.common.dto.result.TodoActivityResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetActivityIdOutResponse {

    private TodoActivityResult result;
}
