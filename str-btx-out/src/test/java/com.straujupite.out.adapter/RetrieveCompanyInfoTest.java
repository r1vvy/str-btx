package com.straujupite.out.adapter;


import com.straujupite.common.config.WebClientConfiguration;

import com.straujupite.common.dto.GetCompanyInResponse;
import com.straujupite.common.dto.GetCompanyResult;
import com.straujupite.common.error.BitrixError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;


import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class RetrieveCompanyInfoTest {

    private WebClientConfiguration webClientConfiguration = new WebClientConfiguration();

    private RetrieveCompanyInfoAdapter retrieveCompanyInfoAdapter = new RetrieveCompanyInfoAdapter(webClientConfiguration);
    @Test
    public void retrieveCompanyWithExistingNumber(){

        GetCompanyInResponse company = getCompanyInResponse();
        Integer expectedID = 2930;
        assertEquals(company.getResult().getCompany().get(0), expectedID);

    }

    @Test
    public void retrieveCompanyWithWrongNumber() {
        StepVerifier.create(retrieveCompanyInfoAdapter.retrieveCompanyByPhoneNumber("1234567890"))
                .expectErrorMatches(throwable -> throwable instanceof BitrixError &&
                        throwable.getMessage().equals("Company ID not found"))
                .verify();

    }

    public GetCompanyInResponse getCompanyInResponse(){
        return GetCompanyInResponse.builder().result(GetCompanyResult.builder().company(new ArrayList<>(Arrays.asList(2930))).build()).build();

    }
}
