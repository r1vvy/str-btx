package com.straujupite.core.service;

import org.springframework.stereotype.Component;
import com.straujupite.common.dto.GetStageID;
import com.straujupite.out.adapter.RetrieveLatestDealAdapter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RetrieveLatestCompanyDealService {

    private final RetrieveLatestDealAdapter retrieveLatestDealAdapter;

    public Mono<GetStageID> retriveLatestDeal(String companyID) {
        return retrieveLatestDealAdapter.retriveLatestDeal(companyID);
    }
}
