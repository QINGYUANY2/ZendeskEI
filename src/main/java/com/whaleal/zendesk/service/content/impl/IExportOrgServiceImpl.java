package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportOrgService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class IExportOrgServiceImpl extends BaseExportService implements IExportOrgService {


    @Override
    public void exportOrgInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportOrgInfo");
        Long useTime = doExport("/api/v2/organizations", "organizations", ExportEnum.ORGANIZATIONS.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importOrgInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importOrgInfo");
        long startTime = System.currentTimeMillis();
        List<Document> documentList = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ORGANIZATIONS.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : documentList) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());

                requestParam.put("organization", jsonObject);
                request = this.doPost("/api/v2/organizations", requestParam);
                document.put("newId", request.getJSONObject("organization").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importOrgInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.ORGANIZATIONS.getValue() + "_info");
            saveImportInfo("importOrgInfo", request);
        }
        log.info("导入 org_info 成功，一共导入{}条记录", documentList.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteOrgInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteOrgInfo");
        log.info("开始执行删除 organization_info 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/organizations", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("organizations");
        List<String> orgIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            orgIds.add(temps.getLong("id").toString());
        }
        try{
            for (String orgId : orgIds) {
                doDelete("/api/v2/organizations/",orgId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 Organizations 成功，一共删除{}条记录\n", orgIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportOrgMembershipInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportOrgMembershipInfo");
        Long useTime = doExport("/api/v2/organization_memberships", "organization_memberships", ExportEnum.ORGANIZATIONS.getValue() + "_memberships");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importOrgMembershipInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importOrgMembershipInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ORGANIZATIONS.getValue() + "_memberships");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                Document orgDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(document.get("organization_id"))),Document.class,ExportEnum.ORGANIZATIONS.getValue()+"_info");
                Document userDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(document.get("user_id"))),Document.class,ExportEnum.USER.getValue()+"_info");
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                jsonObject.put("organization_id",orgDoc.get("newId"));
                jsonObject.put("user_id",userDoc.get("newId"));
                requestParam.put("organization_membership", jsonObject);
                System.out.println("=========");
                System.out.println("=========");
                System.out.println(requestParam);
                request = this.doPost("/api/v2/organization_memberships", requestParam);
                document.put("newId", request.getJSONObject("organization_membership").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importOrgMembershipInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importOrgMembershipInfo", request);
            mongoTemplate.save(document, ExportEnum.ORGANIZATIONS.getValue() + "_memberships");
        }
        log.info("导入 OrgMembershipInfo 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportOrgSubscriptionsInfo() {

        ModuleRecord moduleRecord = beginModuleRecord("exportOrgSubscriptionsInfo");
        Long useTime = doExport("/api/v2/organization_subscriptions", "organization_subscriptions", ExportEnum.ORGANIZATIONS.getValue() + "_subscriptions");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importOrgSubscriptionsInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importOrgSubscriptionsInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ORGANIZATIONS.getValue() + "_subscriptions");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());

                requestParam.put("organization_subscription", jsonObject);
                request = this.doPost("/api/v2/organization_subscriptions", requestParam);
                document.put("newId", request.getJSONObject("organization_subscription").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importOrgSubscriptionsInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.ORGANIZATIONS.getValue() + "_subscriptions");
            saveImportInfo("importOrgSubscriptionsInfo", request);
        }
        log.info("导入 OrgSubscriptionsInfo 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteOrgSubscriptionsInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteOrgSubscriptionsInfo");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/organizations", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("organizations");
        List<String> orgIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            orgIds.add(temps.getLong("id").toString());
        }
        try{
            for (String orgId : orgIds) {
                doDelete("/api/v2/organizations/",orgId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportOrgField(){
        ModuleRecord moduleRecord = beginModuleRecord("exportOrgField");
        Long useTime = doExport("/api/v2/organization_fields", "organization_fields", ExportEnum.ORGANIZATIONS.getValue() + "_fields");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importOrgField(){
        ModuleRecord moduleRecord = beginModuleRecord("importOrgField");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ORGANIZATIONS.getValue() + "_fields");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());

                requestParam.put("organization_field", jsonObject);
                request = this.doPost("/api/v2/organization_fields", requestParam);
                document.put("newId", request.getJSONObject("organization_field").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importOrgField 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.ORGANIZATIONS.getValue() + "_fields");
            saveImportInfo("importOrgField", request);
        }
        log.info("导入 OrgField 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteOrgField() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteOrgField");
        log.info("开始执行删除 organization_field 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/organization_fields", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("organization_fields");
        List<String> orgFieldIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            orgFieldIds.add(temps.getLong("id").toString());
        }
        try{
            for (String orgFieldId : orgFieldIds) {
                doDelete("/api/v2/organization_fields/",orgFieldId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 organization_field 成功，一共删除{}条记录\n", orgFieldIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }



}
