package com.straujupite.core.service;

import com.straujupite.commons.dto.GetCompanyInResponse;
import com.straujupite.out.adapter.RetrieveCompanyInfoAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CompanyInfoService {

    private final RetrieveCompanyInfoAdapter retrieveCompanyInfoAdapter;

    public Mono<GetCompanyInResponse> retrieveCompanyByPhoneNumber(String phoneNumber){
        return retrieveCompanyInfoAdapter.retrieveCompanyByPhoneNumber(phoneNumber);
    }
}
