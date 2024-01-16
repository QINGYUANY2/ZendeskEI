package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportOrgService;
import com.whaleal.zendesk.service.content.IExportSideConversationService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Slf4j
@Service
public class IExportSideConversationImpl extends BaseExportService implements IExportSideConversationService {

    @Override
    public void exportSideConversationInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportSideConversationInfo");
        JSONObject request = this.doGet("/api/v2/tickets", new HashMap<>());
        List<JSONObject> list = request.getJSONArray("tickets").toJavaList(JSONObject.class);
        Long useTime = null;
        for (JSONObject jsonObject : list) {
            if((doGet("/api/v2/tickets/"+ jsonObject.get("id")+"/side_conversations", new HashMap<>()).getJSONArray("side_conversations").size())!=0){
                useTime = doExport("/api/v2/tickets/"+ jsonObject.get("id")+"/side_conversations", "side_conversations", ExportEnum.SIDE_CONVERSATION.getValue() + "_info");

            }

        }
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void exportSideConversationEventsInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportSideConversationEventInfo");
        JSONObject request = this.doGet("/api/v2/tickets", new HashMap<>());
        List<JSONObject> list = request.getJSONArray("tickets").toJavaList(JSONObject.class);
        Long useTime = null;
        for (JSONObject jsonObject : list) {
            if((doGet("/api/v2/tickets/"+ jsonObject.get("id")+"/side_conversations", new HashMap<>()).getJSONArray("side_conversations").size())!=0){
                JSONObject sideConversationRequest = doGet("/api/v2/tickets/"+ jsonObject.get("id")+"/side_conversations", new HashMap<>());
                JSONArray sideConversationArray = sideConversationRequest.getJSONArray("side_conversations");
                for (Object sideConversationObj : sideConversationArray) {
                    JSONObject sideConversation = (JSONObject) sideConversationObj;
                    useTime = doExport("/api/v2/tickets/"+ jsonObject.get("id")+"/side_conversations/" + sideConversation.get("id") +"/events", "events", ExportEnum.EVENT.getValue() + "_info");
                }
            }

        }
        endModuleRecord(moduleRecord, useTime);
    }



    @Override
    public void importSideConversationInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importSideConversationInfo");
        long startTime = System.currentTimeMillis();
        List<Document> documentList = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.SIDE_CONVERSATION.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : documentList) {
            try {
                //side_conversation
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONArray participants = jsonObject.getJSONArray("participants");
                for (Object participantObjs : participants) {
                    JSONObject participant = (JSONObject) participantObjs;
                    if(participant.get("user_id")!=null) {
                        Document usrDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(participant.getLong("user_id"))), Document.class, ExportEnum.USER.getValue() + "_info");
                        participant.put("user_id", usrDoc.get("newId"));
                    }
                    if (participant.get("group_id")!=null){
                        Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(participant.getLong("group_id"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                        participant.put("group_id", groupDoc.get("newId"));
                    }
                }
                Integer ticket_id = jsonObject.getInteger("ticket_id");
                Document ticketDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(ticket_id)),Document.class,ExportEnum.TICKET.getValue()+"_info");
                jsonObject.put("ticket_id", ticketDoc.get("newId"));
                requestParam.put("side_conversation", jsonObject);
                //events
                JSONArray eventArray = new JSONArray();
                List<Document> eventList = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("side_conversation_id").is(jsonObject.get("id"))), Document.class, ExportEnum.EVENT.getValue() + "_info");
                for (Document document1 : eventList) {
                    //todo:把每个actor中的group_id和user_id写好,把ticket_id改好，把eventlist变成作为array导出（转换前面写过）一个side_conversation可以配几个eventlist
                    JSONObject eventJsonObject = JSONObject.parseObject(document1.toJson());
                    //更改actor中的user_id
                    JSONObject actor = eventJsonObject.getJSONObject("actor");
                    if (actor.get("user_id") != null) {
                        Document eventUsrDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(actor.getLong("user_id"))), Document.class, ExportEnum.USER.getValue() + "_info");
                        actor.put("user_id", eventUsrDoc.get("newId"));
                    }
                    eventJsonObject.put("actor", actor);
                    if (eventJsonObject.getJSONObject("message") != null) {
                        JSONObject message = eventJsonObject.getJSONObject("message");
                        JSONObject from = message.getJSONObject("from");
                        if (from.get("user_id") != null) {
                            Document fromUsrDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(from.getLong("user_id"))), Document.class, ExportEnum.USER.getValue() + "_info");
                            from.put("user_id", fromUsrDoc.get("newId"));
                        }
                        message.put("from", from);

                        JSONArray toArray = message.getJSONArray("to");
                        for (Object toObj : toArray) {
                            JSONObject to = (JSONObject) toObj;
                            if (to.get("user_id") != null) {
                                Document toUsrDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(to.getLong("user_id"))), Document.class, ExportEnum.USER.getValue() + "_info");
                                to.put("user_id", toUsrDoc.get("newId"));
                            }
                            if (to.get("group_id") != null) {
                                Document toGroupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(to.getLong("group_id"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                                to.remove("group_id");
                                to.put("support_group_id", toGroupDoc.get("newId"));
                            }
                        }
                        message.put("to", toArray);
//                    JSONObject externalId = message.getJSONObject("external_id");
//                    if(externalId.get("targetTicketAuditId")!=null){
//                        Document targetAuditUsrDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(to.getLong("user_id"))), Document.class, ExportEnum.USER.getValue() + "_info");
//                        externalId.put("targetTicketAuditId", targetAuditUsrDoc.get("A"))；
//                    }

                        eventJsonObject.put("message", message);
                    }
                        Integer eventTicketId = eventJsonObject.getInteger("ticket_id");
                        Document ticDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(eventTicketId)), Document.class, ExportEnum.TICKET.getValue() + "_info");
                        eventJsonObject.put("ticket_id", ticDoc.get("newId"));
                        eventArray.add(eventJsonObject);

                }
                requestParam.put("events", eventArray);
                request = this.doPost("/api/v2/tickets/"+jsonObject.get("ticket_id").toString()+"/side_conversations/import", requestParam);
                document.put("newId", request.getJSONObject("side_conversation").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importSideConversationInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.SIDE_CONVERSATION.getValue() + "_info");
            saveImportInfo("importSideConversationInfo", request);
        }
        log.info("导入 side_conversation_info 成功，一共导入{}条记录", documentList.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }
    }



