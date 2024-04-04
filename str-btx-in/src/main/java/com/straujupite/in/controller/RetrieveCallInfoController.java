package com.straujupite.in.controller;


import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.in.command.RetrieveCallInfoCommand;
import com.straujupite.core.service.CallInfoService;
import com.straujupite.in.config.PathConfiguration;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(PathConfiguration.RETRIEVE_CALL_INFO)
@RequiredArgsConstructor
public class RetrieveCallInfoController {

  private final CallInfoService callInfoService;

  @PostMapping
  public Mono<ResponseEntity<Void>> retrieveCallInfo(
      @RequestBody @Valid RetrieveCallInfoCommand command) {
    return Mono.just(createContextFromCommand(command))
               .flatMap(callInfoService::retrieveCallInfo)
               .thenReturn(ResponseEntity.ok().build());
  }

  private RetrieveCallInfoContext createContextFromCommand(RetrieveCallInfoCommand command) {
    return RetrieveCallInfoContext.builder()
                                  .retrieveCallInfoCommand(command)
                                  .build();
  }
}
