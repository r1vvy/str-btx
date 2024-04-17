package com.straujupite.common.dto.common.bitrix;

public interface BitrixEndpoints {

  String ADD_ACTIVITY_TO_DEAL = "/crm.activity.todo.add?"
      + "ownerTypeId=2"
      + "&ownerId=%s"
      + "&deadline=%s"
      + "&description=%s";
  String GET_NOT_COMPLETED_ACTIVITIES = "/crm.activity.list?"
      + "filter[OWNER_TYPE_ID]=%s"
      + "&filter[OWNER_ID]=%s"
      + "&filter[COMPLETED]=N";
  String UPDATE_ACTIVITY_DEADLINE = "/crm.activity.todo.updateDeadline?"
      + "ownerTypeId=%s"
      + "&ownerId=%s"
      + "&id=%s"
      + "&value=%s";
  String ADD_COMMENT = "/crm.timeline.comment.add";

  String GET_COMPANY_INFO_BY_PHONE_NUMBER = "/crm.duplicate.findbycomm.json?"
      + "entity_type=COMPANY"
      + "&type=PHONE"
      + "&values[]=%s";

  String GET_NOT_COMPLETED_DEALS_BY_COMPANY_ID = "/crm.deal.list.json?"
      + "FILTER[COMPANY_ID]=%s"
      + "&ORDER[DATE_CREATE]=DESC"
      + "&FILTER[STAGE_SEMANTIC_ID]=P";

  String UPDATE_DEAL = "/crm.deal.update.json";
}
