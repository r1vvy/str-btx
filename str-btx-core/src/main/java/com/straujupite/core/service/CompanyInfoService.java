package com.straujupite.core.service;

import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.out.response.GetCompanyOutResponse;
import com.straujupite.out.adapter.RetrieveCompanyInfoAdapter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class CompanyInfoService {

    private static final String COUNTRY_CODE = "+371";

    private final RetrieveCompanyInfoAdapter retrieveCompanyInfoAdapter;

    public Mono<RetrieveCallInfoContext> getCompanyIdByPhoneNumber(RetrieveCallInfoContext context,
        String phoneNumber) {
        return Mono.justOrEmpty(phoneNumber)
                   .filter(this::hasCountryCode)
                   .switchIfEmpty(addCountryCode(phoneNumber, COUNTRY_CODE))
                   .doOnNext(
                       num -> log.info("About to call retrieveCompanyIdByPhoneNumber: {}", num))
                   .flatMap(retrieveCompanyInfoAdapter::retrieveCompanyIdByPhoneNumber)
                   .doOnNext(response -> log.debug("Response: {}", response))
                   .map(this::getCompanyId)
                   .filter(Optional::isPresent)
                   .map(Optional::get)
                   .doOnNext(companyId -> log.debug("Retrieved companyId: {}", companyId))
                   .map(context::withCompanyId);
    }

    private Optional<Integer> getCompanyId(GetCompanyOutResponse response) {
        return response.getCompanies()
                       .stream()
                       .findFirst();
    }

    private boolean hasCountryCode(String phoneNumber) {
        return phoneNumber.startsWith("+");
    }

    private Mono<String> addCountryCode(String phoneNumber, String countryCode) {
        return Mono.justOrEmpty(phoneNumber)
                   .map(num -> countryCode + num);
    }
}
