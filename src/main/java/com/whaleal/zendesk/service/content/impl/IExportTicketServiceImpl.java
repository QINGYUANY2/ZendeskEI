package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.Constants;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportTicketService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class IExportTicketServiceImpl extends BaseExportService implements IExportTicketService {


    @Override
    public void exportTicketRequest() {
        ModuleRecord moduleRecord = beginModuleRecord("exportTicketRequest");
        Long useTime = doExport("/api/v2/requests", "requests", ExportEnum.TICKET.getValue() + "_request");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importTicketRequest() {
        ModuleRecord moduleRecord = beginModuleRecord("importTicketRequest");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.TICKET.getValue() + "_request");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                //更新collaboratorId
                if(jsonObject.getJSONArray("collaborator_ids").size()!=0) {
                    JSONArray collaboratorIds = jsonObject.getJSONArray("collaborator_ids");
                    for (int i=0; i<collaboratorIds.size();i++) {
                        Document collaboratorDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(collaboratorIds.get(i))), Document.class, ExportEnum.USER.getValue() + "_info");
                        collaboratorIds.set(i, collaboratorDoc.get("newId"));
                    }
                    jsonObject.put("collaborator_ids", collaboratorIds);
                }

                //更新custom_field
                if(jsonObject.getJSONArray("custom_fields").size()!=0) {
                    JSONArray customFields = jsonObject.getJSONArray("custom_fields");
                    for (Object customFieldObj : customFields) {
                        JSONObject customField = (JSONObject) customFieldObj;
                        Long customFieldId = customField.getLong("id");
                        Document customFieldDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(customFieldId)), Document.class, ExportEnum.TICKET.getValue() + "_field");
                        customField.put("id", customFieldDoc.get("newId"));
                    }
                    jsonObject.put("custom_fields", customFields);
                }

                //更新ticket_status
                if(jsonObject.getLong("custom_status_id")!=null) {
                    Long ticketStatusId = jsonObject.getLong("custom_status_id");
                    Document ticketStatusDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(ticketStatusId)), Document.class, ExportEnum.TICKET.getValue() + "_status");
                    jsonObject.put("custom_status_id", ticketStatusDoc.get("newId"));
                }

                //更新field
                if(jsonObject.getJSONArray("fields").size()!=0) {
                    JSONArray fields = jsonObject.getJSONArray("fields");
                    for (Object fieldObj : fields) {
                        JSONObject field = (JSONObject) fieldObj;
                        Long fieldId = field.getLong("id");
                        Document fieldDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(fieldId)), Document.class, ExportEnum.TICKET.getValue() + "_field");
                        field.put("id", fieldDoc.get("newId"));
                    }
                    jsonObject.put("fields", fields);
                }

                //更新organization_id
                if(jsonObject.getLong("organization_id")!=null) {
                    Long organizationId = jsonObject.getLong("organization_id");
                    Document orgDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(organizationId)), Document.class, ExportEnum.ORGANIZATIONS.getValue() + "_info");
                    jsonObject.put("organization_id", orgDoc.get("newId"));
                }

                //更新requester_id
                if(jsonObject.getLong("requester_id")!=null) {
                    Long requesterId = jsonObject.getLong("requester_id");
                    Document requesterDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(requesterId)), Document.class, ExportEnum.USER.getValue() + "_info");
                    jsonObject.put("requester_id", requesterDoc.get("newId"));
                }

                //更新ticket_form_id
                if(jsonObject.getLong("ticket_form_id")!=null) {
                    Long ticketFormId = jsonObject.getLong("ticket_form_id");
                    Document ticketFormDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(ticketFormId)), Document.class, ExportEnum.TICKET.getValue() + "_forms");
                    jsonObject.put("ticket_form_id", ticketFormDoc.get("newId"));
                }

                //更新assignee_id
                if(jsonObject.getLong("assignee_id")!=null) {
                    Long assigneeId = jsonObject.getLong("assignee_id");
                    Document assigneeDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(assigneeId)), Document.class, ExportEnum.USER.getValue() + "_info");
                    jsonObject.put("assignee_id", assigneeDoc.get("newId"));
                }



                requestParam.put("request", jsonObject);
                request = this.doPost("/api/v2/requests", requestParam);
                document.put("newId", request.getJSONObject("requests").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importTicketRequest 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.TICKET.getValue() + "_request");
            saveImportInfo("importTicketRequest", request);
        }
        log.info("importTicketRequest 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    @Override
    public void exportTicketAudit() {
        ModuleRecord moduleRecord = beginModuleRecord("exportTicketAudit");
        Long useTime = doExport("/api/v2/ticket_audits", "audits", ExportEnum.TICKET + "_audits");
        endModuleRecord(moduleRecord, useTime);
    }

    @Deprecated
    @Override
    public void exportTicketInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportTicketInfo");
        long startTime = System.currentTimeMillis();
        JSONObject request = this.doGet("/api/v2/tickets", new HashMap<>());
        List<JSONObject> list = request.getJSONArray("tickets").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            JSONObject comment = this.doGet("/api/v2/tickets/" + jsonObject.get("id") + "/comments", new HashMap<>());
            jsonObject.put("comments", comment.get("comments"));
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list, ExportEnum.TICKET.getValue() + "_info");
        log.info("导出TicketInfo成功，一共导出{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void importTicketInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importTicketInfo");
        long startTime = System.currentTimeMillis();
        int count = 1;
        Criteria criteria = new Criteria();
        criteria.and("domain").is(StringSub.getDomain(this.sourceDomain));
//        criteria.and("id").is(72);
        Query query = new Query(criteria);

        List<Document> list = mongoTemplate.find(query, Document.class, ExportEnum.TICKET.getValue() + "_info");
        JSONObject request = null;
        for (Document document : list) {
            JSONObject requestParam = new JSONObject();
            Document param = Document.parse(document.toJson());
            try {
                if (document.get("brand_id") != null) {
                    Document brandDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("brand_id"))), Document.class, ExportEnum.BRAND.getValue() + "_info");
                    if (brandDoc != null) {
                        param.put("brand_id", brandDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 brand_id", document.get("brand_id"));
                    }
                }
                if (document.get("group_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("group_id"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                    if (groupDoc != null) {
                        param.put("group_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 group_id", document.get("group_id"));
                    }
                }
                if (document.get("assignee_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("assignee_id"))), Document.class, ExportEnum.USER.getValue() + "_info");
                    if (groupDoc != null) {
                        param.put("assignee_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 assignee_id", document.get("assignee_id"));
                    }
                }else{
                    //count++;
                    //System.out.println(count);
                }
                if (document.get("requester_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("requester_id"))), Document.class, ExportEnum.USER.getValue() + "_info");
                    if (groupDoc != null) {
                        param.put("requester_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 requester_id", document.get("requester_id"));
                    }
                }
                if (document.get("follower_ids") != null) {
                    List<Long> followerIds = param.getList("follower_ids", Long.class);
                    List<Long> longList = new ArrayList<>();
                    for (Long followerId : followerIds) {
                        Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(followerId)), Document.class, ExportEnum.USER.getValue() + "_info");
                        if (groupDoc != null) {
                            longList.add((Long) groupDoc.get("newId"));
                        } else {
                            log.warn("同步ticket时,未找到 {} 对应的新 follower_ids", document.get("follower_ids"));
                        }
                    }
                    param.put("follower_ids", longList);
                }
                if (document.get("custom_fields") != null) {
                    List<Document> customFields = param.getList("custom_fields", Document.class);
                    List<Long> longList = new ArrayList<>();
                    for (Document customerField : customFields) {
                        Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(customerField.get("id"))), Document.class, ExportEnum.TICKET.getValue() + "_field");
                        if (groupDoc != null) {
                            longList.add((Long) groupDoc.get("newId"));
                        } else {
                            log.warn("同步ticket时,未找到customerField {} 对应的新 follower_ids", document.get("follower_ids"));
                        }
                    }
                    param.put("follower_ids", longList);
                }

                if (document.get("email_cc_ids") != null) {
                    List<Long> emailList = new ArrayList<>();
                    List<Long> emailCcIds = param.getList("email_cc_ids", Long.class);
                    for (Long emailCc : emailCcIds) {
                        Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(emailCc)), Document.class, ExportEnum.USER.getValue() + "_info");
                        if (groupDoc != null) {
                            emailList.add((Long) groupDoc.get("newId"));
                        } else {
                            log.warn("同步ticket时,未找到 {} 对应的新 email_cc_ids", document.get("email_cc_ids"));
                        }
                    }
                    param.put("email_cc_ids", emailList);
                }

                if (document.get("fields") != null) {
                    List<Document> fields = param.getList("fields", Document.class);
                    for (Document field : fields) {
                        Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(field.get("id"))), Document.class, ExportEnum.TICKET.getValue() + "_field");
                        if (groupDoc != null) {
                            field.put("id", groupDoc.get("newId"));
                        } else {
                            log.warn("同步ticket时,未找到 {} 对应的新 fields", field.get("id"));
                        }
                    }
                }

                if (document.get("submitter_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("submitter_id"))), Document.class, ExportEnum.USER.getValue() + "_info");
                    if (groupDoc != null) {
                        param.put("submitter_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 submitter_id", document.get("submitter_id"));
                    }
                }

                if (document.get("organization_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("organization_id"))), Document.class, ExportEnum.ORGANIZATIONS.getValue() + "_info");
                    if (groupDoc != null) {
                        param.put("organization_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 organization_id", document.get("organization_id"));
                    }
                }
                if (document.get("ticket_form_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("ticket_form_id"))), Document.class, ExportEnum.TICKET.getValue() + "_forms");
                    if (groupDoc != null) {
                        param.put("ticket_form_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 ticket_form_id", document.get("ticket_form_id"));
                    }
                }

                if (document.get("comments") != null) {
                    List<Document> comments = param.getList("comments", Document.class);
                    for (Document comment : comments) {
                        List<String> tokenList = new ArrayList<>();
                        //映射新id
                        if (comment.get("author_id") != null) {
                            if(comment.get("author_id") instanceof Integer){
                                Document authorDoc = mongoTemplate.findOne(new Query(new Criteria("add_reason").is("author_id_-1")), Document.class, ExportEnum.USER.getValue() + "_info");
                                comment.put("author_id", authorDoc.get("newId"));
                            }else {
                                Document authorDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(comment.get("author_id"))), Document.class, ExportEnum.USER.getValue() + "_info");
                                if (authorDoc != null) {
                                    comment.put("author_id", authorDoc.get("newId"));
                                } else {
                                    log.warn("同步ticket时,未找到comment中 {} 对应的新 author_id", comment.get("author_id"));
                                }
                            }
                        }
                        // 附件相关
                        if (comment.get("attachments") != null) {
                            List<Document> attachments = comment.getList("attachments", Document.class);
                            for (Document attachment : attachments) {
                                String url = (String) attachment.get("mapped_content_url");
                                String filename = (String) attachment.get("file_name");
                                String type = (String) attachment.get("content_type");
                                File file = downloadFile(url, filename);
                                JSONObject jsonObject = doPost("/api/v2/uploads", type, file);
                                if (!file.delete()) {
                                    log.warn("临时文件：{}删除失败", file.getAbsolutePath());
                                }
                                tokenList.add(jsonObject.getJSONObject("upload").getString("token"));
                            }
                            comment.put("uploads", tokenList);
                        }
                    }
                }

                // todo 必须填good 或 bad  其余的都报错  null也报错
                if (document.get("satisfaction_rating") != null) {
                    Document satisfaction = (Document) param.get("satisfaction_rating");
                    if (satisfaction.get("score") != null && satisfaction.get("score").equals("unoffered")) {
                        satisfaction.put("score", "good");
                    }else if (satisfaction.get("score") != null && satisfaction.get("score").equals("offered")) {
                        satisfaction.put("score", "good");
                    }
                }
                requestParam.put("ticket", param);
                request = this.doPost("/api/v2/imports/tickets", requestParam);
                document.put("newId", request.getJSONObject("ticket").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            log.info("importTicketInfo 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            saveImportInfo("importTicketInfo", request);
            mongoTemplate.save(document, ExportEnum.TICKET.getValue() + "_info");
        }
        log.info("导入ticker_info成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);

    }

    @Override
    public void deleteTicketInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteTicketInfo");
        log.info("开始执行删除 ticket_info 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/tickets", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("tickets");
        List<String> ticketIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            ticketIds.add(temps.getLong("id").toString());
        }
        try{
            for (String ticketId : ticketIds) {
                doDelete("/api/v2/tickets/",ticketId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 ticket_info 成功，一共删除{}条记录\n", ticketIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    public File downloadFile(String url, String filename) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        // todo  临时目录
        File dir = new File(filePath);
        File file = null;
        FileOutputStream fos = null;
        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                file = new File(dir, filename);
                fos = new FileOutputStream(file);
                fos.write(body.bytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }


    @Override
    public void exportTicketFields() {
        ModuleRecord moduleRecord = beginModuleRecord("exportTicketFields");
        Long useTime = doExport("/api/v2/ticket_fields", "ticket_fields", ExportEnum.TICKET.getValue() + "_field");
        endModuleRecord(moduleRecord, useTime);

    }

    @Override
    public void importTicketFields() {
        ModuleRecord moduleRecord = beginModuleRecord("importTicketFields");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.TICKET.getValue() + "_field");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            if (Constants.stringToList().contains(document.get("type"))) {
                try {
                    JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                    requestParam.put("ticket_field", jsonObject);
                    request = this.doPost("/api/v2/ticket_fields", requestParam);
                    document.put("newId", request.getJSONObject("ticket_field").get("id"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("importTicketFields 执行完毕,请求参数：{},执行结果{}", requestParam, request);
                saveImportInfo("importTicketFields", request);
                mongoTemplate.save(document, ExportEnum.TICKET.getValue() + "_field");
            }
        }
        log.info("导入 TicketFields 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteTicketFields() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteTicketFieldsInfo");
        log.info("开始执行删除 ticket_field 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/ticket_fields", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("ticket_fields");
        List<String> ticketFieldsIds = new ArrayList<>();
        JSONObject request = null;
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            ticketFieldsIds.add(temps.getLong("id").toString());
        }
        try{
            for (String ticketFieldsId : ticketFieldsIds) {
                doDelete("/api/v2/ticket_fields/",ticketFieldsId);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 ticket_field 成功，一共删除{}条记录\n", ticketFieldsIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportSatisfactionRatingInfo() {

        ModuleRecord moduleRecord = beginModuleRecord("exportSatisfactionRatingInfo");
        Long useTime = doExport("/api/v2/satisfaction_ratings", "satisfaction_ratings", ExportEnum.SATISFACTION.getValue() + "_rating");
        endModuleRecord(moduleRecord, useTime);

    }

    @Override
    public void importSatisfactionRatingInfo() {

        ModuleRecord moduleRecord = beginModuleRecord("importSatisfactionRatingInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.SATISFACTION.getValue() + "_rating");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                Document ticket = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(document.get("ticket_id"))), Document.class, ExportEnum.TICKET.getValue() + "_info");
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("satisfaction_rating", jsonObject);
                request = this.doPost("/api/v2/tickets/"+ticket.get("newId")+"/satisfaction_rating", requestParam);
                document.put("newId", request.getJSONObject("satisfaction_rating").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importSatisfactionRatingInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importSatisfactionRatingInfo", request);
            mongoTemplate.save(document, ExportEnum.TICKET.getValue() + "_field");
        }
        log.info("导入 importSatisfactionRatingInfo 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    @Override
    public void exportCustomTicketStatus() {
        ModuleRecord moduleRecord = beginModuleRecord("exportCustomTicketStatus");
        Long useTime = doExport("/api/v2/custom_statuses", "custom_statuses", ExportEnum.TICKET.getValue() + "_status");
        endModuleRecord(moduleRecord, useTime);

    }

    @Override
    public void importCustomTicketStatus() {
        ModuleRecord moduleRecord = beginModuleRecord("importCustomTicketStatus");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.TICKET.getValue() + "_status");
        JSONObject allStatus = doGetTarget("/api/v2/custom_statuses", new HashMap<>());
        JSONArray statuses = allStatus.getJSONArray("custom_statuses");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {

                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                if(jsonObject.getBoolean("default")==true){
                    for (Object statusObj : statuses) {
                        JSONObject status = (JSONObject) statusObj;
                        if(status.get("raw_agent_label").equals(jsonObject.get("raw_agent_label"))){
                            document.put("newId", status.get("id"));
                        }
                    }
                }else {
                    requestParam.put("custom_status", jsonObject);
                    request = this.doPost("/api/v2/custom_statuses", requestParam);
                    document.put("newId", request.getJSONObject("custom_status").get("id"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importCustomTicketStatus 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importCustomTicketStatus", request);
            mongoTemplate.save(document, ExportEnum.TICKET.getValue() + "_status");
        }
        log.info("导入 importCustomTicketStatus 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }



    @Override
    public void exportSharingAgreement() {
        ModuleRecord moduleRecord = beginModuleRecord("exportSharingAgreement");
        Long useTime = doExport("/api/v2/sharing_agreements", "sharing_agreements", ExportEnum.SHARING_AGREEMENT.getValue() + "_status");
        endModuleRecord(moduleRecord, useTime);

    }

    @Override
    public void importSharingAgreement() {
        ModuleRecord moduleRecord = beginModuleRecord("exportSharingAgreement");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.SHARING_AGREEMENT.getValue() + "_status");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("sharing_agreement", jsonObject);
                request = this.doPost("/api/v2/sharing_agreements", requestParam);
                document.put("newId", request.getJSONObject("sharing_agreement").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("exportSharingAgreement 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("exportSharingAgreement", request);
            mongoTemplate.save(document, ExportEnum.SHARING_AGREEMENT.getValue() + "_status");
        }
        log.info("导入 exportSharingAgreement 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    @Override
    public void deleteSchedules(){
        ModuleRecord moduleRecord = beginModuleRecord("deleteSchedulesInfo");
        log.info("开始执行删除 schedules 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/business_hours/schedules", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("schedules");
        List<String> scheduleIds = new ArrayList<>();
        JSONObject request = null;
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            scheduleIds.add(temps.getLong("id").toString());
        }
        try{
            for (String scheduleId : scheduleIds) {
                doDelete("/api/v2/business_hours/schedules/",scheduleId);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 schedules 成功，一共删除{}条记录\n", scheduleIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    @Override
    public void exportSchedules() {
        ModuleRecord moduleRecord = beginModuleRecord("exportSchedules");
        Long useTime = doExport("/api/v2/business_hours/schedules", "schedules", ExportEnum.SCHEDULES.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);

    }

    @Override
    public void importSchedules() {
        ModuleRecord moduleRecord = beginModuleRecord("importSchedules");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.SCHEDULES.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("schedule", jsonObject);
                request = this.doPost("/api/v2/business_hours/schedules", requestParam);
                document.put("newId", request.getJSONObject("schedule").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importSchedules 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importSchedules", request);
            mongoTemplate.save(document, ExportEnum.SCHEDULES.getValue() + "_info");
        }
        log.info("导入 importSchedules 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    @Override
    public void exportAttributeValue() {
        ModuleRecord moduleRecord = beginModuleRecord("exportAttributeValue");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ACCOUNT_ATTRIBUTES.getValue() + "_info");
        for (Document document : list) {
            JSONObject jsonObject = JSONObject.parseObject(document.toJson());
            JSONObject request = this.doGet("/api/v2/routing/attributes/"+jsonObject.get("id").toString()+"/values", new HashMap<>());
            List<JSONObject> innerList = request.getJSONArray("attribute_values").toJavaList(JSONObject.class);
            for (JSONObject attributeId : innerList) {
                attributeId.put("attribute_id", jsonObject.get("id"));
            }
            mongoTemplate.insert(innerList, ExportEnum.ACCOUNT_ATTRIBUTES.getValue() + "_value");
            log.info("导出AttributeValue成功，一共导出{}条记录", list.size());
        }
        endModuleRecord(moduleRecord, System.currentTimeMillis()-startTime);

    }

    @Override
    public void importAttributeValue(){
        ModuleRecord moduleRecord = beginModuleRecord("importAccountAttributesValue");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(), Document.class, ExportEnum.ACCOUNT_ATTRIBUTES.getValue() + "_value");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try{
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                //更新condition中 customfield_id
                if(jsonObject.getJSONObject("conditions")!=null) {
                    JSONObject conditions = jsonObject.getJSONObject("conditions");
                    if(conditions.getJSONArray("all").size()!=0) {
                        JSONArray alls = conditions.getJSONArray("all");
                        for (Object allObj : alls) {
                            JSONObject all = (JSONObject) allObj;
                            if (all.get("subject").toString().contains("custom_fields_")) {
                                String[] split = all.get("subject").toString().split("_");
                                String field_id = Arrays.asList(split).get(split.length - 1);
                                Document ticketFieldDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(Long.parseLong(field_id))), Document.class, ExportEnum.TICKET.getValue() + "_field");
                                all.put("subject", "custom_fields_" + ticketFieldDoc.get("newId").toString());
                            }
                        }
                    }
                }
                //更新attribute_id
                String attributeId = jsonObject.get("attribute_id").toString();
                Document attributeDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(attributeId)), Document.class, ExportEnum.ACCOUNT_ATTRIBUTES.getValue() + "_info");
                jsonObject.remove("attribute_id");
                requestParam.put("attribute_value", jsonObject);
                request = this.doPost("/api/v2/routing/attributes/"+attributeDoc.get("newId")+"/values", requestParam);
                document.put("newId", request.getJSONObject("attribute_value").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("AccountAttributeValue 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("AccountAttributeValue", request);
            mongoTemplate.save(document, ExportEnum.ACCOUNT_ATTRIBUTES.getValue() + "_value");
        }
        log.info("导入 importAccountAttributeValue 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    @Override
    public void deleteAccountAttributes(){
        ModuleRecord moduleRecord = beginModuleRecord("deleteAccountAttributesInfo");
        log.info("开始执行删除 account_attributes 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/routing/attributes", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("attributes");
        List<String> attributeIds = new ArrayList<>();
        JSONObject request = null;
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            attributeIds.add(temps.get("id").toString());
        }
        try{
            for (String attributeId : attributeIds) {
                doDelete("/api/v2/routing/attributes/",attributeId);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 account_attributes 成功，一共删除{}条记录\n", attributeIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }




    @Override
    public void exportAccountAttributes() {
        ModuleRecord moduleRecord = beginModuleRecord("exportAccountAttributes");
        Long useTime = doExport("/api/v2/routing/attributes", "attributes", ExportEnum.ACCOUNT_ATTRIBUTES.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);

    }

    @Override
    public void importAccountAttributes() {
        ModuleRecord moduleRecord = beginModuleRecord("importAccountAttributes");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ACCOUNT_ATTRIBUTES.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try{
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("attribute", jsonObject);
                request = this.doPost("/api/v2/routing/attributes", requestParam);
                document.put("newId", request.getJSONObject("attribute").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importAccountAttributes 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importAccountAttributes", request);
            mongoTemplate.save(document, ExportEnum.ACCOUNT_ATTRIBUTES.getValue() + "_info");
        }
        log.info("导入 importAccountAttributes 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    @Override
    public void exportResourceCollections() {
        ModuleRecord moduleRecord = beginModuleRecord("exportResourceCollections");
        Long useTime = doExport("/api/v2/resource_collections", "resource_collections", ExportEnum.RESOURCE_COLLECTIONS.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);

    }

    @Override
    public void importResourceCollections() {
        ModuleRecord moduleRecord = beginModuleRecord("importResourceCollections");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.RESOURCE_COLLECTIONS.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                //更新resource列中resource_id
                if(jsonObject.getJSONArray("resources").size()!=0) {
                    JSONArray resourceArray = jsonObject.getJSONArray("resources");
                    for (Object resourceObj : resourceArray) {
                        JSONObject resource = (JSONObject) resourceObj;
                        if(resource.get("type").equals("ticket_fields")){
                            Document ticketFieldDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(resource.getLong("resource_id"))), Document.class, ExportEnum.TICKET.getValue() + "_field");
                            resource.put("resource_id",ticketFieldDoc.getLong("newId"));
                        }
                        if(resource.get("type").equals("user_fields")){
                            Document userFieldDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(resource.getLong("resource_id"))), Document.class, ExportEnum.USER.getValue() + "_field");
                            resource.put("resource_id",userFieldDoc.getLong("newId"));
                        }
                    }
                    jsonObject.put("resource", resourceArray);
                }

                requestParam.put("payload", jsonObject);
                request = this.doPost("/api/v2/resource_collections", requestParam);
                document.put("newId", request.getJSONObject("job_status").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importResourceCollections 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importResourceCollections", request);
            mongoTemplate.save(document, ExportEnum.RESOURCE_COLLECTIONS.getValue() + "_info");
        }
        log.info("导入 importResourceCollections 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteResourceCollections() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteResourceCollectionsInfo");
        log.info("开始执行删除 recource_collections 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/resource_collections", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("resource_collections");
        List<String> resourceIds = new ArrayList<>();
        JSONObject request = null;
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            resourceIds.add(temps.get("id").toString());
        }
        try{
            for (String resourceId : resourceIds) {
                doDelete("/api/v2/resource_collections/",resourceId);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 resource_collections 成功，一共删除{}条记录\n", resourceIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


}
