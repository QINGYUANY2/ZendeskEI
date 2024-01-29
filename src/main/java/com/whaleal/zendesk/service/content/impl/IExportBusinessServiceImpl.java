package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportBusinessService;
import com.whaleal.zendesk.util.DetermineNumber;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class IExportBusinessServiceImpl extends BaseExportService implements IExportBusinessService {

    @Override
    public void exportViewInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportViewInfo");
        Long useTime = doExport("/api/v2/views", "views", ExportEnum.VIEW.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importViewInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importViewInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.VIEW.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                if(jsonObject.get("restriction")!= null) {
                    JSONObject nestedObject = jsonObject.getJSONObject("restriction");
                    List<Document> newIds = new ArrayList<>();
                    List<Long> newId = new ArrayList<>();
                    //查找restriction中id队应的新id
                    Document groupDoc = null;
                    Document userDoc = null;
                    if (nestedObject.get("type").equals("Group")) {
                        groupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(nestedObject.get("id"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                        nestedObject.put("id", groupDoc.get("newId"));
                    }else if (nestedObject.get("type").equals("User")){
                        userDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(nestedObject.get("id"))), Document.class, ExportEnum.USER.getValue() + "_info");
                        nestedObject.put("id", userDoc.get("newId"));
                    }
                    //System.out.println(nestedObject.get("id").getClass());
                    //创建ids的list保存所有ids中的id对应的newId
                    List<Integer> idsList = ((List<Integer>) nestedObject.get("ids"));
                    if (idsList != null && idsList.size() != 0) {
                        if (idsList.size() > 1) {
                            newIds = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").in(idsList)), Document.class, ExportEnum.GROUP.getValue() + "_info");
                        } else {
                            newIds = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(idsList.get(0))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                        }
                        newId = newIds.stream().map(e -> e.getLong("newId")).collect(Collectors.toList());
                    }//替换原来的
                    nestedObject.put("ids", newId);

                    jsonObject.put("restriction", nestedObject);

                }
                if(jsonObject.get("conditions")!= null ){
                    JSONObject ConditionObject = jsonObject.getJSONObject("conditions");
                    JSONArray nestedConditionObject = ConditionObject.getJSONArray("all");
                    for (Object nestedConditionObj : nestedConditionObject) {
                        JSONObject nestedCondition = (JSONObject) nestedConditionObj;
                        if(nestedCondition.get("field").equals("group_id") && DetermineNumber.isNumeric(nestedCondition.get("value").toString())){
                            //System.out.println(nestedCondition.get("value").getClass()); //nestedCondition.get("value")
                            //System.out.println("Query Criteria: " + new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(nestedCondition.get("value"))));
                            Document ConditionGroupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(nestedCondition.getLong("value"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                            //System.out.println(nestedCondition);
                            //System.out.println(nestedConditionObj);
                            //nestedCondition和nestedConditionObj地址相同，所以可以通过直接改nestedCondition来更改nestedConditionObject
                            nestedCondition.put("value", ConditionGroupDoc.get("newId").toString());
                            //System.out.println(nestedCondition == nestedConditionObj);
                        }
                        if(nestedCondition.get("field").equals("brand_id") && DetermineNumber.isNumeric(nestedCondition.get("value").toString())){
                            Document ConditionGroupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(nestedCondition.getLong("value"))), Document.class, ExportEnum.BRAND.getValue() + "_info");
                            nestedCondition.put("value", ConditionGroupDoc.get("newId").toString());
                        }
                        if(nestedCondition.get("field").toString().contains("custom_fields")) {
                            String[] split = nestedCondition.get("field").toString().split("_");
                            String field_id = Arrays.asList(split).get(split.length - 1);
                            Document ConditionGroupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(Long.parseLong(field_id))), Document.class, ExportEnum.TICKET.getValue() + "_field");
                            nestedCondition.put("field", "custom_fields_"+ConditionGroupDoc.get("newId").toString());
                        }
                    }
                    ConditionObject.put("all", nestedConditionObject);
                    jsonObject.put("conditions", ConditionObject);
                }

                requestParam.put("view", jsonObject);
                request = this.doPost("/api/v2/views", requestParam);
                document.put("newId", request.getJSONObject("view").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importViewInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.VIEW.getValue() + "_info");
            saveImportInfo("importViewInfo", request);
        }
        log.info("导入view_info成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteViewInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteViewInfo");
        log.info("开始执行删除 view_info 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/views", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("views");
        List<String> viewIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            viewIds.add(temps.getLong("id").toString());
        }
        try{
            for (String viewId : viewIds) {
                doDelete("/api/v2/views/",viewId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 view 成功，一共删除{}条记录\n", viewIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportMacroInfo() {

        ModuleRecord moduleRecord = beginModuleRecord("exportMacroInfo");
        long startTime = System.currentTimeMillis();
        JSONObject request = this.doGet("/api/v2/macros", new HashMap<>());
        List<JSONObject> list = request.getJSONArray("macros").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            JSONObject comment = this.doGet("/api/v2/macros/" + jsonObject.get("id") + "/attachments", new HashMap<>());
            jsonObject.put("macro_attachments", comment.get("macro_attachments"));
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list, ExportEnum.MACRO.getValue() + "_info");
        log.info("exportMacroInfo成功，一共导出{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

//    @Override
//    public void importMacroInfo() {
//        ModuleRecord moduleRecord = beginModuleRecord("importMacroInfo");
//        long startTime = System.currentTimeMillis();
//        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.MACRO.getValue() + "_info");
//        JSONObject requestParam = new JSONObject();
//        JSONObject request = null;
//        JSONObject attachmentRequest = null;
//        for (Document document : list) {
//            try {
//                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
//                requestParam.put("macro", jsonObject);
//                request = this.doPost("/api/v2/macros", requestParam);
//                document.put("newId", request.getJSONObject("macro").get("id"));
//                List<Document> macroAttachments = document.getList("macro_attachments",Document.class);
//                for (Document attachment : macroAttachments) {
//                    JSONObject attachmentJson = JSONObject.parseObject(attachment.toJson());
//                    attachmentRequest = this.doPost("/api/v2/macros/" + document.get("newId") + "/attachments", attachmentJson);
//                    log.info("导入macro的attachments 执行完毕,请求参数：{},执行结果{}", attachmentJson, attachmentRequest);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            log.info("importMacroInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
//            mongoTemplate.save(document, ExportEnum.MACRO.getValue() + "_info");
//            saveImportInfo("importMacroInfo", request);
//        }
//        log.info("导入macro_info成功，一共导入{}条记录", list.size());
//        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
//    }
    @Override
    public void importMacroInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importMacroInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.MACRO.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        JSONObject attachmentRequest = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                if(jsonObject.get("restriction")!= null) {
                    JSONObject nestedObject = jsonObject.getJSONObject("restriction");
                    List<Document> newIds = new ArrayList<>();
                    List<Long> newId = new ArrayList<>();
                    //查找restriction中id队应的新id
                    Document groupDoc = null;
                    Document userDoc = null;
                    if (nestedObject.get("type").equals("Group")) {
                        groupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(nestedObject.get("id"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                        nestedObject.put("id", groupDoc.get("newId"));
                    }else if (nestedObject.get("type").equals("User")){
                        userDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(nestedObject.get("id"))), Document.class, ExportEnum.USER.getValue() + "_info");
                        nestedObject.put("id", userDoc.get("newId"));
                    }
                    //System.out.println(nestedObject.get("id").getClass());
                    //创建ids的list保存所有ids中的id对应的newId
                    List<Integer> idsList = ((List<Integer>) nestedObject.get("ids"));
                    if (idsList != null && idsList.size() != 0) {
                        if (idsList.size() > 1) {
                            newIds = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").in(idsList)), Document.class, ExportEnum.GROUP.getValue() + "_info");
                        } else {
                            newIds = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(idsList.get(0))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                        }
                        newId = newIds.stream().map(e -> e.getLong("newId")).collect(Collectors.toList());
                    }//替换原来的
                    nestedObject.put("ids", newId);

                    jsonObject.put("restriction", nestedObject);

                }
                JSONArray actions = jsonObject.getJSONArray("actions");
                //todo: 完成action中id替换
                if(actions.size()!=0){
                    for (Object actionObj : actions) {
                        JSONObject action = (JSONObject) actionObj;
                        if(action.get("field").equals("group_id") && DetermineNumber.isNumeric(action.get("value").toString())){
                            Document actionGroupDoc =  mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(action.getLongValue("value"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                            action.put("value", actionGroupDoc.get("newId"));
                        }
                        if (action.get("field").equals("ticket_form_id") && DetermineNumber.isNumeric(action.get("value").toString())){
                            Document actionTicketFormDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(action.getLongValue("value"))), Document.class, ExportEnum.TICKET.getValue() + "_forms");
                            action.put("value", actionTicketFormDoc.get("newId"));
                        }
                        if(action.get("field").equals("brand_id")&& DetermineNumber.isNumeric(action.get("value").toString())){
                            Document actionBrandDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(action.getLongValue("value"))), Document.class, ExportEnum.BRAND.getValue() + "_info");
                            action.put("value", actionBrandDoc.get("newId"));
                        }
                        if(action.get("field").toString().contains("custom_fields")) {
                            String[] split_action = action.get("field").toString().split("_");
                            String field_id = Arrays.asList(split_action).get(split_action.length - 1);
                            Document actionTicketDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(Long.parseLong(field_id))), Document.class, ExportEnum.TICKET.getValue() + "_field");
                            action.put("field", "ticket_fields_"+actionTicketDoc.get("newId").toString());
                        }

                    }

                }
                jsonObject.remove("attachments");
                requestParam.put("macro", jsonObject);
                request = this.doPost("/api/v2/macros", requestParam);
                document.put("newId", request.getJSONObject("macro").get("id"));

                List<Document> macroAttachments = document.getList("macro_attachments",Document.class);
                for (Document attachment : macroAttachments) {

                    JSONObject attachmentJson = JSONObject.parseObject(attachment.toJson());
                    String url = attachmentJson.getString("content_url");
                    File file = downloadFile(url, attachmentJson.getString("filename"));
                    attachmentRequest = this.doPostMacroAttachments("/api/v2/macros/" + document.get("newId") + "/attachments",attachmentJson,file);
                    log.info("导入macro的attachments 执行完毕,请求参数：{},执行结果{}", attachmentJson, attachmentRequest);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importMacroInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.MACRO.getValue() + "_info");
            saveImportInfo("importMacroInfo", request);
        }
        log.info("导入macro_info成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteMacroInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteMacroInfo");
        log.info("开始执行删除 macro_info 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/macros", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("macros");
        List<String> macroIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            macroIds.add(temps.getLong("id").toString());
        }
        try{
            for (String macroId : macroIds) {
                doDelete("/api/v2/macros/",macroId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 macro 成功，一共删除{}条记录\n", macroIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    public File downloadFile(String url, String filename) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
                .url(url).build();
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
    public void exportTriggerInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportTriggerInfo");
        Long useTime = doExport("/api/v2/triggers", "triggers", ExportEnum.TRIGGER.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importTriggerInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importTriggerInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.TRIGGER.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {

                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONArray actions = jsonObject.getJSONArray("actions");
                Document categories = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(document.get("category_id"))), Document.class, ExportEnum.TRIGGER_CATEGORIES.getValue() + "_info");
                jsonObject.put("category_id", categories.get("newId"));
                //完成action中id替换
                if(actions.size()!=0) {
                    for (Object actionObj : actions) {
                        JSONObject action = (JSONObject) actionObj;
                        if (action.get("field").equals("group_id") && DetermineNumber.isNumeric(action.get("value").toString())) {
                            Document actionGroupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(action.getLongValue("value"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                            action.put("value", actionGroupDoc.get("newId"));
                        }
                        if (action.get("field").equals("ticket_form_id") && DetermineNumber.isNumeric(action.get("value").toString())) {
                            Document actionTicketFormDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(action.getLongValue("value"))), Document.class, ExportEnum.TICKET.getValue() + "_forms");
                            action.put("value", actionTicketFormDoc.get("newId"));
                        }
                        if (action.get("field").equals("brand_id") && DetermineNumber.isNumeric(action.get("value").toString())) {
                            Document actionBrandDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(action.getLongValue("value"))), Document.class, ExportEnum.BRAND.getValue() + "_info");
                            action.put("value", actionBrandDoc.get("newId"));
                        }
                        if (action.get("field").toString().contains("custom_fields_")) {
                            String[] split_action = action.get("field").toString().split("_");
                            String field_id = Arrays.asList(split_action).get(split_action.length - 1);
                            Document actionTicketDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(Long.parseLong(field_id))), Document.class, ExportEnum.TICKET.getValue() + "_field");
                            action.put("field", "custom_fields_" + actionTicketDoc.get("newId").toString());
                        }
                    }

                    JSONObject conditions = jsonObject.getJSONObject("conditions");
                    JSONArray allConditions = conditions.getJSONArray("all");
                    JSONArray anyConditions = conditions.getJSONArray("any");

                    //完成action中id替换
                    if (allConditions.size() != 0) {
                        for (Object allConditionObj : allConditions) {
                            JSONObject allCondition = (JSONObject) allConditionObj;
                            if (allCondition.get("field").equals("group_id") && DetermineNumber.isNumeric(allCondition.get("value").toString())) {
                                Document conditionGroupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(allCondition.getLongValue("value"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                                allCondition.put("value", conditionGroupDoc.get("newId"));
                            }
                            if (allCondition.get("field").equals("ticket_form_id") && DetermineNumber.isNumeric(allCondition.get("value").toString())) {
                                Document conditionTicketFormDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(allCondition.getLongValue("value"))), Document.class, ExportEnum.TICKET.getValue() + "_forms");
                                allCondition.put("value", conditionTicketFormDoc.get("newId"));
                            }
                            if (allCondition.get("field").equals("brand_id") && DetermineNumber.isNumeric(allCondition.get("value").toString())) {
                                Document conditionBrandDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(allCondition.getLongValue("value"))), Document.class, ExportEnum.BRAND.getValue() + "_info");
                                allCondition.put("value", conditionBrandDoc.get("newId"));
                            }
                            if (allCondition.get("field").equals("custom_status_id") && DetermineNumber.isNumeric(allCondition.get("value").toString())) {
                                Document conditionStatusDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(allCondition.getLongValue("value"))), Document.class, ExportEnum.TICKET.getValue() + "_status");
                                allCondition.put("value", conditionStatusDoc.get("newId"));
                            }
                            if (allCondition.get("field").toString().contains("custom_fields_")) {
                                String[] split_action = allCondition.get("field").toString().split("_");
                                String field_id = Arrays.asList(split_action).get(split_action.length - 1);
                                Document conditionTicketDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(Long.parseLong(field_id))), Document.class, ExportEnum.TICKET.getValue() + "_field");
                                allCondition.put("field", "custom_fields_" + conditionTicketDoc.get("newId").toString());
                            }

                        }
                    }
                    if (anyConditions.size() != 0) {
                        for (Object anyConditionObj : anyConditions) {
                            JSONObject anyCondition = (JSONObject) anyConditionObj;
                            if (anyCondition.get("field").equals("group_id") && DetermineNumber.isNumeric(anyCondition.get("value").toString())) {
                                Document anyConditionGroupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(anyCondition.getLongValue("value"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                                anyCondition.put("value", anyConditionGroupDoc.get("newId"));
                            }
                            if (anyCondition.get("field").equals("ticket_form_id") && DetermineNumber.isNumeric(anyCondition.get("value").toString())) {
                                Document anyConditionTicketFormDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(anyCondition.getLongValue("value"))), Document.class, ExportEnum.TICKET.getValue() + "_forms");
                                anyCondition.put("value", anyConditionTicketFormDoc.get("newId"));
                            }
                            if (anyCondition.get("field").equals("brand_id") && DetermineNumber.isNumeric(anyCondition.get("value").toString())) {
                                Document anyConditionBrandDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(anyCondition.getLongValue("value"))), Document.class, ExportEnum.BRAND.getValue() + "_info");
                                anyCondition.put("value", anyConditionBrandDoc.get("newId"));
                            }
                            if (anyCondition.get("field").toString().contains("custom_fields_")) {
                                String[] split_action = anyCondition.get("field").toString().split("_");
                                String field_id = Arrays.asList(split_action).get(split_action.length - 1);
                                Document anyConditionTicketDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(Long.parseLong(field_id))), Document.class, ExportEnum.TICKET.getValue() + "_field");
                                anyCondition.put("field", "custom_fields_" + anyConditionTicketDoc.get("newId").toString());
                            }
                        }


                    }
                }

                requestParam.put("trigger", jsonObject);
                request = this.doPost("/api/v2/triggers", requestParam);
                document.put("newId", request.getJSONObject("trigger").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importTriggerInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.TRIGGER.getValue() + "_info");
            saveImportInfo("importTriggerInfo", request);
        }
        log.info("导入TriggerInfo成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteTriggerInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteTriggerInfo");
        log.info("开始执行删除 trigger_info 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/triggers", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("triggers");
        List<String> triggerIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            triggerIds.add(temps.getLong("id").toString());
        }
        try{
            for (String triggerId : triggerIds) {
                doDelete("/api/v2/triggers/",triggerId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 trigger 成功，一共删除{}条记录\n", triggerIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportTriggerCategoriesInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportTriggerCategoriesInfo");
        Long useTime = doExport("/api/v2/trigger_categories", "trigger_categories", ExportEnum.TRIGGER_CATEGORIES.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importTriggerCategoriesInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importTriggerCategoriesInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.TRIGGER_CATEGORIES.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("trigger_category", jsonObject);
                request = this.doPost("/api/v2/trigger_categories", requestParam);
                document.put("newId", request.getJSONObject("trigger_category").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importTriggerCategoriesInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.TRIGGER_CATEGORIES.getValue() + "_info");
            saveImportInfo("importTriggerCategoriesInfo", request);
        }
        log.info("importTriggerCategoriesInfo 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteTriggerCategoriesInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteTriggerCategories");
        log.info("开始执行删除 trigger_categories 任务");

        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/trigger_categories", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("trigger_categories");
        List<String> triggerCatIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            triggerCatIds.add(temps.getLong("id").toString());
        }
        try{
            for (String triggerCatId : triggerCatIds) {
                doDelete("/api/v2/trigger_categories/",triggerCatId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 trigger_categories 成功，一共删除{}条记录\n", triggerCatIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteAutomationsInfo(){
        ModuleRecord moduleRecord = beginModuleRecord("deleteAutomationsInfo");
        log.info("开始执行删除 automations 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/automations", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("automations");
        List<String> automationIds = new ArrayList<>();
        JSONObject request = null;
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            automationIds.add(temps.getLong("id").toString());
        }
        try{
            for (String automationId : automationIds) {
                doDelete("/api/v2/automations/",automationId);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 automations 成功，一共删除{}条记录\n", automationIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    @Override
    public void exportAutomationsInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportAutomationsInfo");
        Long useTime = doExport("/api/v2/automations", "automations", ExportEnum.AUTOMATIONS.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importAutomationsInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importAutomationsInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.AUTOMATIONS.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("automation", jsonObject);
                request = this.doPost("/api/v2/automations", requestParam);
                document.put("newId", request.getJSONObject("automation").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importAutomationsInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.AUTOMATIONS.getValue() + "_info");
            saveImportInfo("importAutomationsInfo", request);
        }
        log.info("importAutomationsInfo 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }



    @Override
    public void exportSLAPoliciesInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportSLAPoliciesInfo");
        Long useTime = doExport("/api/v2/slas/policies", "sla_policies", ExportEnum.SLA_POLICIES.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importSLAPoliciesInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importSLAPoliciesInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.SLA_POLICIES.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject filter = jsonObject.getJSONObject("filter");
                if(filter.get("all")!=null){
                    JSONArray all = filter.getJSONArray("all");
                    for (Object allObject : all) {
                        JSONObject field = (JSONObject) allObject;
                        if(field.get("field").equals("brand_id")){
                            Document brandDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(field.getLong("value"))), Document.class, ExportEnum.BRAND.getValue() + "_info");
                            field.put("value", brandDoc.get("newId"));
                        }
                    }

                }
                requestParam.put("sla_policy", jsonObject);
                request = this.doPost("/api/v2/slas/policies", requestParam);
                document.put("newId", request.getJSONObject("sla_policy").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importSLAPoliciesInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.SLA_POLICIES.getValue() + "_info");
            saveImportInfo("importSLAPoliciesInfo", request);
        }
        log.info("importSLAPoliciesInfo 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteSLAPoliciesInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteSLAPoliciesInfo");
        log.info("开始执行删除 SLA_Policies_info 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/slas/policies", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("sla_policies");
        List<String> slaIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            slaIds.add(temps.getLong("id").toString());
        }
        try{
            for (String slaId : slaIds) {
                doDelete("/api/v2/slas/policies/",slaId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 SLA_Policies 成功，一共删除{}条记录\n", slaIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }



    @Override
    public void exportGroupSLAPoliciesInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportGroupSLAPoliciesInfo");
        Long useTime = doExport("/api/v2/group_slas/policies", "group_sla_policies", ExportEnum.GROUP_SLA_POLICIES.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importGroupSLAPoliciesInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importGroupSLAPoliciesInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.GROUP_SLA_POLICIES.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("group_sla_policy", jsonObject);
                request = this.doPost("/api/v2/group_slas/policies", requestParam);
                document.put("newId", request.getJSONObject("group_sla_policy").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importGroupSLAPoliciesInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.GROUP_SLA_POLICIES.getValue() + "_info");
            saveImportInfo("importGroupSLAPoliciesInfo", request);
        }
        log.info("importGroupSLAPoliciesInfo 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }



}
