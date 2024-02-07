package com.straujupite.in.controller;


import com.straujupite.core.service.CallInfoService;
import com.straujupite.in.config.PathConfiguration;
import lombok.RequiredArgsConstructor;
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
  public Mono<Object> retrieveCallInfo(@RequestBody Object object) {
    return callInfoService.retrieveCallInfo(object);
  }

}
