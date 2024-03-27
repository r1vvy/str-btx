package com.straujupite.out.adapter;

import com.straujupite.common.dto.GetCompanyOutResponse;
import com.straujupite.common.error.BitrixError;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RetrieveCompanyInfoTest {
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private WebClient webClient;
	@InjectMocks
	private RetrieveCompanyInfoAdapter retrieveCompanyInfoAdapter;

	@Test
	public void retrieveCompanyWithExistingNumber() {

		when(retrieveCompanyInfoAdapter.retrieveCompanyByPhoneNumber("existingNumber"))
				.thenReturn(Mono.just(getCompanyInResponse()));
		var result = retrieveCompanyInfoAdapter.retrieveCompanyByPhoneNumber("existingNumber").block();
		Integer expectedID = 2930;
		System.out.println(expectedID);
		assertEquals(result.getCompanies().get(0), expectedID);

	}

	@Test
	public void retrieveCompanyWithWrongNumber() {
		when(retrieveCompanyInfoAdapter.retrieveCompanyByPhoneNumber("nonExistingNumber"))
				.thenThrow(new BitrixError("Company ID not found"));

		BitrixError thrown = assertThrows(
				BitrixError.class,
				() -> retrieveCompanyInfoAdapter.retrieveCompanyByPhoneNumber("nonExistingNumber"));

		assertEquals("Company ID not found", thrown.getMessage());
	}

	public GetCompanyOutResponse getCompanyInResponse() {

		return GetCompanyOutResponse.builder()
				.companies(Arrays.asList(2930))
				.build();
	}

}