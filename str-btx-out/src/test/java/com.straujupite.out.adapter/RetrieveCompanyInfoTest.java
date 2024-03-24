package com.straujupite.out.adapter;


//@RunWith(MockitoJUnitRunner.class)
public class RetrieveCompanyInfoTest {
//    @Mock
//    private WebClient webClient;
//    @InjectMocks
//    private RetrieveCompanyInfoAdapter retrieveCompanyInfoAdapter;

    //todo: @Mareks fix these tests to have mocked webClient.
    /*
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

        return GetCompanyInResponse.builder()
                                   .result(Result.builder()
                                                 .company(new ArrayList<>(Arrays.asList(2930)))
                                                 .build()
                                   )
                                   .build();
    }
    */
}
