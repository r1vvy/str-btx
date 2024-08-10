package com.straujupite.core.service.changedealstage.flow;

import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;

import com.straujupite.common.dto.DealStage;
import com.straujupite.common.dto.context.ChangeDealStageContext;
import com.straujupite.common.dto.out.request.AddActivityOutRequest;
import com.straujupite.common.dto.out.request.GetActivityOutRequest;
import com.straujupite.common.dto.out.request.UpdateActivityDeadlineOutRequest;
import com.straujupite.common.error.changedealstage.ActivityInfoNotFoundException;
import com.straujupite.common.util.formatter.DefaultDateTimeFormatter;
import com.straujupite.out.adapter.BitrixAdapter;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public abstract class ChangeDealStageFlowBase {

  protected static final String DEAL_ACTIVITY_OWNER_TYPE_ID = "2";
  protected static final String AUTHOR_ID = "24";
  protected static final String ACTIVITY_DESCRIPTION = "Zvanīt klientam";
  protected static final DealStage NOT_ANSWERED_MORE_THAN_OR_EQUAL_TO_THREE_TIMES_STAGE = DealStage.DIDNT_PICKUP_AND_EMAIL;
  protected static final List<DealStage> COMPLETED_DEALS = List.of(
      DealStage.SUCCESSFUL_DEAL,
      DealStage.FAILED_DEAL
  );

  @Autowired
  protected BitrixAdapter bitrixAdapter;

  protected String createDeadlineByDayCount(int daysToAdd, String currentDeadline) {
    var deadline = OffsetDateTime.parse(currentDeadline);

    return deadline.plusDays(daysToAdd)
                   .format(DefaultDateTimeFormatter.FORMATTER_WITHOUT_TIMEZONE);
  }

  protected Mono<ChangeDealStageContext> getDealActivity(ChangeDealStageContext context) {
    return Mono.fromSupplier(context.getDealInfo()::getId)
               .map(this::buildGetActivityOutRequest)
               .doOnEach(
                   logOnNext(outRequest -> log.info("About to call getActivity: {}", outRequest)))
               .flatMap(bitrixAdapter::getActivity)
               .map(context::withActivityInfo)
               .switchIfEmpty(
                   Mono.error(new ActivityInfoNotFoundException("Couldn't get activity")));

  }


  protected Mono<ChangeDealStageContext> updateActivityDeadlineIfPresent(
      ChangeDealStageContext context, int daysToAddToDeadline) {
    return Mono.justOrEmpty(context.getActivityInfo())
               .map(
                   activityInfo -> buildUpdateActivityDeadlineRequest(context, daysToAddToDeadline))
               .doOnEach(logOnNext(
                   outRequest -> log.info("About to call updateActivityDeadline: {}", outRequest)))
               .flatMap(bitrixAdapter::updateActivityDeadline)
               .thenReturn(context);
  }

  protected UpdateActivityDeadlineOutRequest buildUpdateActivityDeadlineRequest(
      ChangeDealStageContext context, int daysToAddToDeadline) {
    return UpdateActivityDeadlineOutRequest.builder()
                                           .id(context.getActivityInfo().getActivityId())
                                           .ownerTypeId(DEAL_ACTIVITY_OWNER_TYPE_ID)
                                           .ownerId(context.getDealInfo()
                                                           .getId())
                                           .updatedDeadline(
                                               createDeadlineByDayCount(daysToAddToDeadline,
                                                                        context.getActivityInfo()
                                                                               .getDeadLine())
                                           ).build();
  }

  protected Mono<ChangeDealStageContext> addNewActivityToDeal(ChangeDealStageContext context,
                                                              int deadline) {
    return Mono.justOrEmpty(context)
               .map(ctx -> buildAddActivityOutRequest(deadline, ctx))
               .doOnEach(
                   logOnNext(outRequest -> log.info("About to call addActivity: {}", outRequest)))
               .flatMap(bitrixAdapter::addActivity)
               .thenReturn(context);
  }

  private GetActivityOutRequest buildGetActivityOutRequest(String dealId) {
    return GetActivityOutRequest.builder()
                                .ownerId(dealId)
                                .ownerTypeId(DEAL_ACTIVITY_OWNER_TYPE_ID)
                                .authorId(AUTHOR_ID)
                                .build();
  }

  private AddActivityOutRequest buildAddActivityOutRequest(int deadline,
                                                           ChangeDealStageContext ctx) {
    return AddActivityOutRequest.builder()
                                .dealId(ctx.getDealInfo().getId())
                                .description(ACTIVITY_DESCRIPTION)
                                .deadline(createDeadlineByDayCount(deadline, getCurrentTime()))
                                .build();
  }

  private String getCurrentTime() {
    return ZonedDateTime.now(ZoneOffset.UTC).toString();
  }
}
