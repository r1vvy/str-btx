package com.straujupite.core.service;

import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;

import com.straujupite.common.dto.common.PhoneNumber;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.out.response.GetCompanyOutResponse;
import com.straujupite.out.adapter.BitrixAdapter;
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

    private final BitrixAdapter bitrixAdapter;

    public Mono<RetrieveCallInfoContext> getCompanyIdByPhoneNumber(RetrieveCallInfoContext context,
        String phoneNumber) {
        return Mono.justOrEmpty(phoneNumber)
                   .filter(this::hasCountryCode)
                   .switchIfEmpty(addCountryCode(phoneNumber, COUNTRY_CODE))
                   .doOnEach(logOnNext(
                       num -> log.info("About to call retrieveCompanyIdByPhoneNumber: {}", num)))
                   .map(PhoneNumber::new)
                   .flatMap(bitrixAdapter::retrieveCompanyIdByPhoneNumber)
                   .map(this::getCompanyId)
                   .filter(Optional::isPresent)
                   .map(Optional::get)
                   .doOnEach(
                       logOnNext(companyId -> log.info("Retrieved companyId: {}", companyId)))
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
