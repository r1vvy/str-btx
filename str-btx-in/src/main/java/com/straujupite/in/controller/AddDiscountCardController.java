package com.straujupite.in.controller;

import com.straujupite.common.dto.in.command.AddDiscountCardCommand;
import com.straujupite.core.service.addiscountcard.AddDiscountCardService;
import com.straujupite.in.config.PathConfiguration;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;

@RestController
@RequestMapping(PathConfiguration.ADD_DISCOUNT_CARD)
@RequiredArgsConstructor
@Slf4j
public class AddDiscountCardController {

    private final AddDiscountCardService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Void>> addDiscountCard(@Valid @RequestBody AddDiscountCardCommand command) {
        return Mono.just(command)
                   .doOnEach(logOnNext(cmd -> log.info("Received command: {}", cmd)))
                   .flatMap(service::addDiscountCard)
                   .thenReturn(ResponseEntity.ok().build());
    }
}
