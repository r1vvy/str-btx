package com.straujupite.core.service.changedealstage.flow;

import com.straujupite.common.dto.DealInfo;
import com.straujupite.common.dto.DealStage;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.out.request.AddActivityOutRequest;
import com.straujupite.common.dto.out.request.GetActivityOutRequest;
import com.straujupite.common.dto.out.request.UpdateActivityDeadlineOutRequest;
import com.straujupite.common.error.changedealstage.ActivityInfoNotFoundException;
import com.straujupite.common.util.DefaultDateTimeFormatter;
import com.straujupite.out.adapter.AddActivityAdapter;
import com.straujupite.out.adapter.GetActivityAdapter;
import com.straujupite.out.adapter.UpdateActivityAdapter;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public abstract class ChangeDealStageFlowBase {

  protected static final String DEAL_ACTIVITY_OWNER_TYPE_ID = "2";
  protected static final String ACTIVITY_DESCRIPTION = "Zvanīt klientam";
  protected static final DealStage NOT_ANSWERED_MORE_THAN_OR_EQUAL_TO_THREE_TIMES_STAGE = DealStage.DIDNT_PICKUP_AND_EMAIL;
  protected static final List<DealStage> COMPLETED_DEALS = List.of(
      DealStage.SUCCESSFUL_DEAL,
      DealStage.FAILED_DEAL
  );

  @Autowired
  protected UpdateActivityAdapter updateActivityAdapter;
  @Autowired
  protected GetActivityAdapter getActivityAdapter;
  @Autowired
  private AddActivityAdapter addActivityAdapter;


  protected String createDeadlineByDayCount(int daysToAdd, String currentDeadline) {
    var deadline = OffsetDateTime.parse(currentDeadline);

    return deadline.plusDays(daysToAdd)
                   .format(DefaultDateTimeFormatter.FORMATTER);
  }

  protected Mono<RetrieveCallInfoContext> getDealActivity(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context.getCompanyId())
               .flatMap(companyId -> getActivityAdapter.getActivity(
                       GetActivityOutRequest.builder()
                                            .ownerId(companyId)
                                            .ownerTypeId(DEAL_ACTIVITY_OWNER_TYPE_ID)
                                            .build()
                   )
               ).map(context::withActivityInfo)
               .defaultIfEmpty(context);

  }

  protected Mono<RetrieveCallInfoContext> updateActivityDeadlineIfPresent(
      RetrieveCallInfoContext context, int daysToAddToDeadline) {
    return Mono.justOrEmpty(context.getActivityInfo())
               .flatMap(activityInfo -> updateActivityAdapter.updateActivityDeadline(
                   buildUpdateActivityDeadlineRequest(context, daysToAddToDeadline))
               ).thenReturn(context)
               .switchIfEmpty(Mono.error(new ActivityInfoNotFoundException(
                   "Context doesn't contain ActivityInfo object")));
  }

  protected UpdateActivityDeadlineOutRequest buildUpdateActivityDeadlineRequest(
      RetrieveCallInfoContext context, int daysToAddToDeadline) {
    return UpdateActivityDeadlineOutRequest.builder()
                                           .id(context.getActivityInfo().getActivityId())
                                           .ownerTypeId(DEAL_ACTIVITY_OWNER_TYPE_ID)
                                           .ownerId(getDealId(context.getDealInfo()))
                                           .updatedDeadline(
                                               createDeadlineByDayCount(daysToAddToDeadline,
                                                   context.getActivityInfo().getDeadLine())
                                           ).build();
  }

  protected Mono<RetrieveCallInfoContext> addNewActivityToDeal(RetrieveCallInfoContext context,
      int deadline) {
    return Mono.justOrEmpty(context)
               .flatMap(ctx -> addActivityAdapter.addActivity(
                   buildAddActivityOutRequest(deadline,
                       ctx)))
               .thenReturn(context);
  }

  private AddActivityOutRequest buildAddActivityOutRequest(int deadline,
      RetrieveCallInfoContext ctx) {
    return AddActivityOutRequest.builder()
                                .companyID(ctx.getCompanyId())
                                .description(ACTIVITY_DESCRIPTION)
                                .deadline(createDeadlineByDayCount(deadline, getCurrentTime()))
                                .build();
  }

  private String getDealId(DealInfo dealInfo) {
    return Optional.ofNullable(dealInfo)
                   .map(DealInfo::getId)
                   .orElseThrow(
                       () -> new RuntimeException("Couldn't retrieve DealID from DealInfo"));
  }

  private String getCurrentTime() {
    return ZonedDateTime.now(ZoneOffset.UTC).toString();
  }
}
