package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.TaskInfo;
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
        TaskInfo exportUserInfo = saveTaskInfo("exportOrgInfo");
        Long useTime = doExport("/api/v2/organizations", "organizations", ExportEnum.ORGANIZATIONS.getValue() + "_info");
        exportUserInfo.setEndTime(TimeUtil.getTime());
        exportUserInfo.setUseTime(useTime);
        exportUserInfo.setStatus(2);
        mongoTemplate.save(exportUserInfo);
    }

    @Override
    public void importOrgInfo() {
        // todo  后期添加分页 以防过大

        List<Document> documentList = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ORGANIZATIONS.getValue()+"_info");
        for (Document document : documentList) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("organization", jsonObject);
                JSONObject request = this.doPost("/api/v2/organizations",requestParam);
                document.put("newId",request.getJSONObject("organization").get("id"));
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            mongoTemplate.save(document,ExportEnum.ORGANIZATIONS.getValue()+"_info");
        }

    }

    @Override
    public void exportOrgMembershipInfo() {
        JSONObject request = this.doGet("/api/v2/organization_memberships", new HashMap<>());
        List<JSONObject> list = request.getJSONArray("organization_memberships").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("domain",StringSub.getDomain(this.sourceDomain));
            jsonObject.put("status",0);
        }
        mongoTemplate.insert(list, "org_membership");
    }

    @Override
    public void importOrgMembershipInfo() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "org_membership");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("organization_membership", jsonObject);
                JSONObject request = this.doPost("/api/v2/organization_memberships",requestParam);
                document.put("status",1);
                log.info("请求结果{}",request);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
           mongoTemplate.save(document,"org_membership");
        }

    }

    @Override
    public void exportOrgSubscriptionsInfo() {
        JSONObject request = this.doGet("/api/v2/organization_subscriptions", new HashMap<>());
        List<JSONObject> list = request.getJSONArray("organization_subscriptions").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain",StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list, "organization_subscriptions");
    }

    @Override
    public void importOrgSubscriptionsInfo() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "organization_subscriptions");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("organization_subscription", jsonObject);
                JSONObject request = this.doPost("/api/v2/organization_subscriptions", requestParam);
                log.info("请求结果{}", request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            mongoTemplate.save(document,"organization_subscriptions");
        }
    }
}
