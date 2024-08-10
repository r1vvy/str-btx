package com.straujupite.common.util.uriformatter;

import com.straujupite.common.dto.common.PhoneNumber;
import com.straujupite.common.dto.common.bitrix.CompanyId;
import com.straujupite.common.dto.out.request.AddActivityOutRequest;
import com.straujupite.common.dto.out.request.GetActivityOutRequest;
import com.straujupite.common.dto.out.request.UpdateActivityDeadlineOutRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UriBuilderConfig {

  @Bean
  public UriBuilderRegistry uriBuilderRegistry() {
    var uriBuilderRegistry = new UriBuilderRegistry();
    uriBuilderRegistry.registerUriBuilder(AddActivityOutRequest.class,
                                          new AddActivityToDealUriBuilder());
    uriBuilderRegistry.registerUriBuilder(PhoneNumber.class,
                                          new GetCompanyInfoByPhoneNumberUriBuilder());
    uriBuilderRegistry.registerUriBuilder(GetActivityOutRequest.class,
                                          new GetNotCompletedActivitiesUriBuilder());
    uriBuilderRegistry.registerUriBuilder(CompanyId.class,
                                          new GetNotCompletedDealsByCompanyIdUriBuilder());
    uriBuilderRegistry.registerUriBuilder(UpdateActivityDeadlineOutRequest.class,
                                          new UpdateActivityDeadlineUriBuilder());

    return uriBuilderRegistry;
  }
}
