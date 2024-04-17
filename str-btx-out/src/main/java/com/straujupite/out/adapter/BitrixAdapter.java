package com.straujupite.out.adapter;

import com.straujupite.common.dto.common.PhoneNumber;
import com.straujupite.common.dto.common.bitrix.CompanyId;
import com.straujupite.common.dto.out.command.AddCommentOutCommand;
import com.straujupite.common.dto.out.request.AddActivityOutRequest;
import com.straujupite.common.dto.out.request.ChangeDealStageOutRequest;
import com.straujupite.common.dto.out.request.GetActivityOutRequest;
import com.straujupite.common.dto.out.request.UpdateActivityDeadlineOutRequest;
import com.straujupite.common.dto.out.response.GetActivityOutResponse;
import com.straujupite.common.dto.out.response.GetCompanyDealsOutResponse;
import com.straujupite.common.dto.out.response.GetCompanyOutResponse;
import com.straujupite.common.dto.out.response.UpdateActivityOutResponse;
import reactor.core.publisher.Mono;

public interface BitrixAdapter {

  Mono<Void> addActivity(AddActivityOutRequest addActivityOutRequest);

  Mono<Void> addComment(AddCommentOutCommand command);

  Mono<Void> changeStage(ChangeDealStageOutRequest outRequest);

  Mono<GetActivityOutResponse> getActivity(GetActivityOutRequest request);

  Mono<GetCompanyOutResponse> retrieveCompanyIdByPhoneNumber(PhoneNumber phoneNumber);

  Mono<GetCompanyDealsOutResponse> retrieveDealsByCompanyId(CompanyId companyID);

  Mono<UpdateActivityOutResponse> updateActivityDeadline(UpdateActivityDeadlineOutRequest request);
}
