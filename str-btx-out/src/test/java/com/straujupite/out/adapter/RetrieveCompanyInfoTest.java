package com.straujupite.out.adapter;

import com.straujupite.common.dto.GetCompanyInResponse;
import com.straujupite.common.dto.GetCompanyResult;
import com.straujupite.common.error.BitrixError;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
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
		assertEquals(result.getResult().getCompany().get(0), expectedID);

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

	public GetCompanyInResponse getCompanyInResponse() {

		return GetCompanyInResponse.builder()
				.result(GetCompanyResult.builder()
						.company(new ArrayList<>(Arrays.asList(2930)))
						.build())
				.build();
	}

}
