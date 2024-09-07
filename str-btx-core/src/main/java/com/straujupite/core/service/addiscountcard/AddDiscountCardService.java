package com.straujupite.core.service.addiscountcard;

import com.straujupite.common.dto.in.command.AddDiscountCardCommand;
import com.straujupite.common.dto.result.AddDiscountCardResult;
import reactor.core.publisher.Mono;

public interface AddDiscountCardService {
    Mono<AddDiscountCardResult> addDiscountCard(AddDiscountCardCommand command);
}
