package com.straujupite.out.controller;


import com.straujupite.commons.dto.GetCompanyInResponse;
import com.straujupite.core.service.CompanyInfoService;
import com.straujupite.out.config.PathConfigurations;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(PathConfigurations.RETRIEVE_COMPANY_INFO)
@RequiredArgsConstructor
public class RetrieveCompanyInfoController {

    private final CompanyInfoService companyInfoService;

    @GetMapping
    public Mono<GetCompanyInResponse> retrieveCompanyInfo(String phoneNumber){
        return companyInfoService.retrieveCompanyByPhoneNumber(phoneNumber);
    }
}
