package com.straujupite.core.service;

import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.out.response.GetCompanyOutResponse;
import com.straujupite.out.adapter.RetrieveCompanyInfoAdapter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CompanyInfoService {

    private final RetrieveCompanyInfoAdapter retrieveCompanyInfoAdapter;

    public Mono<RetrieveCallInfoContext> getCompanyIdByPhoneNumber(RetrieveCallInfoContext context,
        String phoneNumber) {
        return retrieveCompanyInfoAdapter.retrieveCompanyIdByPhoneNumber(phoneNumber)
                                         .map(this::getCompanyId)
                                         .filter(Optional::isPresent)
                                         .map(Optional::get)
                                         .map(context::withCompanyId)
                                         .switchIfEmpty(Mono.error(new RuntimeException("Add some error here")));
    }

    private Optional<Integer> getCompanyId(GetCompanyOutResponse response) {
        return response.getCompanies()
                       .stream()
                       .findFirst();
    }
}
