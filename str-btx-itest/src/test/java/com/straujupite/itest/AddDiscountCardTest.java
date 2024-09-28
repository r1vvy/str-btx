package com.straujupite.itest;


import com.straujupite.itest.mock.SheetsMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.straujupite.in.config.PathConfiguration.ADD_DISCOUNT_CARD;


public class AddDiscountCardTest extends BaseIntegrationTest {

    @Autowired
    private SheetsMock sheetsMock;

    @AfterEach
    void stop() {
        sheetsMock.reset();
    }

    @Test
    public void shouldAddDiscountCard_WhenRequestorTypeIsPrivatePerson() throws IOException {
        var request = getJson("/in/addDiscountCardPrivatePersonSuccess.json");
        sheetsMock.stubAddDataToSheet("appendSheetPrivatePersonRequest.json", "appendSheetSuccessResponse.json");
        callAddDiscountCard(request);
    }

    @Test
    public void shouldAddDiscountCard_WhenRequesterTypeIsLegalEntity() {
        var request = getJson("/in/addDiscountCardLegalEntitySuccess.json");
        sheetsMock.stubAddDataToSheet("appendSheetLegalEntityRequest.json", "appendSheetSuccessResponse.json");
        callAddDiscountCard(request);
    }

    protected void callAddDiscountCard(String request) {
        webTestClient().post()
                       .uri(ADD_DISCOUNT_CARD)
                       .bodyValue(request)
                       .exchange()
                       .expectStatus()
                       .is2xxSuccessful()
                       .expectBody()
                       .isEmpty();
    }
}

