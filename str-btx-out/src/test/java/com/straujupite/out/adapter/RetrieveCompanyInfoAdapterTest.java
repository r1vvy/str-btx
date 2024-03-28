package com.straujupite.out.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.straujupite.common.dto.out.response.GetCompanyOutResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class RetrieveCompanyInfoAdapterTest {
	private static final String NUMBER = "number";
	private static final Integer ID = Integer.valueOf("2930");

	@Mock
	private WebClient webClient;
	@InjectMocks
	private RetrieveCompanyInfoAdapter retrieveCompanyInfoAdapter;

	@Mock
	private RequestHeadersUriSpec requestHeadersUriSpec;
	@Mock
	private RequestHeadersSpec requestHeadersSpec;
	@Mock
	private ResponseSpec responseSpec;

	@Test
	public void retrieveCompanyWithExistingNumber() {
		mockWebClient();
		when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(GetCompanyOutResponse.class))
				.thenReturn(Mono.just(getCompanyInSuccessfulResponse()));

		StepVerifier.create(retrieveCompanyInfoAdapter.retrieveCompanyByPhoneNumber(NUMBER))
				.assertNext(result -> {
					assertNotNull(result);
					assertEquals(List.of(ID), result.getCompanies());
				}).verifyComplete();

		verifyNoMoreInteractions(webClient);
	}

//	@Test
//	public void retrieveCompanyWithWrongNumber() {
//		mockWebClient();
//		when(responseSpec.bodyToMono(GetCompanyOutResponse.class))
//				.thenReturn(Mono.just(getCompanyInResponse()));
//
//		BitrixError thrown = assertThrows(
//				BitrixError.class,
//				() -> retrieveCompanyInfoAdapter.retrieveCompanyByPhoneNumber("nonExistingNumber"));
//
//		assertEquals("Company ID not found", thrown.getMessage());
//	}

	public GetCompanyOutResponse getCompanyInSuccessfulResponse() {

		return GetCompanyOutResponse.builder()
				.companies(List.of(ID))
				.build();
	}

	private void mockWebClient() {
		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
	}

}