package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportBusinessService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                    //查找restriction中id队应的新id
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(nestedObject.get("id"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                    //System.out.println(nestedObject.get("id").getClass());
                    //创建ids的list保存所有ids中的id对应的newId
                    List<Integer> idsList = ((List<Integer>) nestedObject.get("ids"));
                    List<Document> newIds = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").in(idsList)), Document.class, ExportEnum.GROUP.getValue() + "_info");
                    List<Long> newId = newIds.stream().map(e -> e.getLong("newId")).collect(Collectors.toList());
                    //替换原来的
                    nestedObject.put("ids",newId);
                    nestedObject.put("id",groupDoc.get("newId"));
                    jsonObject.put("restriction", nestedObject);
                }
                if(jsonObject.get("conditions")!= null ){
                    JSONObject ConditionObject = jsonObject.getJSONObject("conditions");
                    JSONArray nestedConditionObject = ConditionObject.getJSONArray("all");
                    for (Object nestedConditionObj : nestedConditionObject) {
                        JSONObject nestedCondition = (JSONObject) nestedConditionObj;
                        if(nestedCondition.get("field").equals("group_id")){
                            //System.out.println(nestedCondition.get("value").getClass()); //nestedCondition.get("value")
                            //System.out.println("Query Criteria: " + new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(nestedCondition.get("value"))));
                            Document ConditionGroupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(nestedCondition.getLong("value"))), Document.class, ExportEnum.GROUP.getValue() + "_info");

                            nestedCondition.put("value", ConditionGroupDoc.get("newId").toString());
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
                requestParam.put("macro", jsonObject);
                request = this.doPost("/api/v2/macros", requestParam);
                document.put("newId", request.getJSONObject("macro").get("id"));
                List<Document> macroAttachments = document.getList("macro_attachments",Document.class);
                for (Document attachment : macroAttachments) {
                    JSONObject attachmentJson = JSONObject.parseObject(attachment.toJson());
                    attachmentRequest = this.doPost("/api/v2/macros/" + document.get("newId") + "/attachments", attachmentJson);
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
                Document categories = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(document.get("category_id"))), Document.class, ExportEnum.TRIGGER_CATEGORIES.getValue() + "_info");
                document.put("category_id", categories.get("newId"));
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("trigger", jsonObject);
                request = this.doPost("/api/v2/triggers", requestParam);
                document.put("newId", request.getJSONObject("trigger").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importTriggerInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.TRIGGER.getValue() + "_info");
            saveImportInfo("importMacroInfo", request);
        }
        log.info("导入TriggerInfo成功，一共导入{}条记录", list.size());
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
                request = this.doPost("/api/v2/slas/policies", requestParam);
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
