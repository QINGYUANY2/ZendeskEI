package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportOrgService;
import com.whaleal.zendesk.util.StringSub;
import com.whaleal.zendesk.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class IExportOrgServiceImpl extends BaseExportService implements IExportOrgService {


    @Override
    public void exportOrgInfo() {
        ModuleRecord exportUserInfo =new ModuleRecord();
        Long useTime = doExport("/api/v2/organizations", "organizations", ExportEnum.ORGANIZATIONS.getValue() + "_info");
        exportUserInfo.setStartTime(TimeUtil.getTime());
        exportUserInfo.setDuration(useTime);
        exportUserInfo.setStatus(2);
        mongoTemplate.save(exportUserInfo);
    }

    @Override
    public void importOrgInfo() {
        TaskInfo saveTask = saveTaskInfo("importOrgInfo");
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
        log.info("导入 org_info 成功，一共导出{}条记录", documentList.size());
        saveTask.setEndTime(TimeUtil.getTime());
        saveTask.setUseTime(System.currentTimeMillis() - startTime);
        saveTask.setStatus(2);
        mongoTemplate.save(saveTask);
    }

    @Override
    public void exportOrgMembershipInfo() {

        TaskInfo exportUserInfo = saveTaskInfo("exportOrgMembershipInfo");
        Long useTime = doExport("/api/v2/organization_memberships", "organization_memberships", ExportEnum.ORGANIZATIONS.getValue() + "_memberships");
        exportUserInfo.setEndTime(TimeUtil.getTime());
        exportUserInfo.setUseTime(useTime);
        exportUserInfo.setStatus(2);
        mongoTemplate.save(exportUserInfo);

    }

    @Override
    public void importOrgMembershipInfo() {
        TaskInfo saveTask = saveTaskInfo("importUserInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ORGANIZATIONS.getValue() + "_memberships");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());

                requestParam.put("organization_membership", jsonObject);
                request = this.doPost("/api/v2/organization_memberships", requestParam);
                document.put("newId",request.getJSONObject("organization_membership").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importOrgMembershipInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importOrgMembershipInfo", request);
            mongoTemplate.save(document, ExportEnum.ORGANIZATIONS.getValue() + "_memberships");
        }
        log.info("导入 OrgMembershipInfo 成功，一共导出{}条记录",list.size());
        saveTask.setEndTime(TimeUtil.getTime());
        saveTask.setUseTime(System.currentTimeMillis() - startTime);
        saveTask.setStatus(2);
        mongoTemplate.save(saveTask);
    }

    @Override
    public void exportOrgSubscriptionsInfo() {

        TaskInfo exportUserInfo = saveTaskInfo("exportOrgSubscriptionsInfo");
        Long useTime = doExport("/api/v2/organization_subscriptions", "organization_subscriptions", ExportEnum.ORGANIZATIONS.getValue() + "_subscriptions");
        exportUserInfo.setEndTime(TimeUtil.getTime());
        exportUserInfo.setUseTime(useTime);
        exportUserInfo.setStatus(2);
        mongoTemplate.save(exportUserInfo);

    }

    @Override
    public void importOrgSubscriptionsInfo() {
        TaskInfo saveTask = saveTaskInfo("importUserInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ORGANIZATIONS.getValue() + "_subscriptions");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());

                requestParam.put("organization_subscription", jsonObject);
                request = this.doPost("/api/v2/organization_subscriptions", requestParam);
                document.put("newId",request.getJSONObject("organization_subscription").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importOrgSubscriptionsInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.ORGANIZATIONS.getValue() + "_subscriptions");
            saveImportInfo("importOrgSubscriptionsInfo", request);
        }
        log.info("导入 OrgSubscriptionsInfo 成功，一共导出{}条记录",list.size());
        saveTask.setEndTime(TimeUtil.getTime());
        saveTask.setUseTime(System.currentTimeMillis() - startTime);
        saveTask.setStatus(2);
        mongoTemplate.save(saveTask);
    }
}
