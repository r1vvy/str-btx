package com.straujupite.itest;


import org.junit.jupiter.api.Test;

import static com.straujupite.in.config.PathConfiguration.ADD_DISCOUNT_CARD;

public class AddDiscountCardTest extends BaseIntegrationTest {

    @Test
    public void shouldAddDiscountCard_WhenRequestorTypeIsPrivatePerson() {
        var request = getJson("/in/addDiscountCardPrivatePersonSuccess.json");

        callAddDiscountCard(request);
    }

    @Test
    public void shouldAddDiscountCard_WhenRequesterTypeIsLegalEntity() {
        var request = getJson("/in/addDiscountCardLegalEntitySuccess.json");

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
