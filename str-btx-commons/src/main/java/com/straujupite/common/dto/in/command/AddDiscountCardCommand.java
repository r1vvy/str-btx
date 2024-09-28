package com.straujupite.common.dto.in.command;

import com.straujupite.common.dto.common.EmailAddress;
import com.straujupite.common.dto.common.PhoneNumber;
import com.straujupite.common.dto.common.StrRequest;
import com.straujupite.common.dto.common.address.LocationAddress;
import com.straujupite.common.dto.common.address.PostalCode;
import com.straujupite.common.dto.common.discountcard.CardCount;
import com.straujupite.common.dto.common.discountcard.OrganizationName;
import com.straujupite.common.dto.common.discountcard.OrganizationRegistrationNumber;
import com.straujupite.common.dto.common.requester.RequesterName;
import com.straujupite.common.dto.common.requester.RequesterSurname;
import com.straujupite.common.dto.common.requester.RequestorType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDiscountCardCommand extends StrRequest {

    @NotNull
    private RequestorType requesterType;

    private RequesterName requesterName;

    private RequesterSurname requesterSurname;

    @Valid
    @NotNull
    private EmailAddress requesterEmail;

    @Valid
    @NotNull
    private PhoneNumber requesterPhone;

    @Valid
    private OrganizationName organizationName;

    @Valid
    private OrganizationRegistrationNumber organizationRegistrationNumber;

    @Valid
    private LocationAddress legalAddress;

    @Valid
    @NotNull
    private LocationAddress deliveryAddress;

    @Valid
    @NotNull
    private PostalCode postalCode;

    @Valid
    private CardCount cardCount;
}
