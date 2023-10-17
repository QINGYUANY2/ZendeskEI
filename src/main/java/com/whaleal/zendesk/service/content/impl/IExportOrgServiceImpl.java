package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportOrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class IExportOrgServiceImpl extends BaseExportService implements IExportOrgService {

    @Override
    public void exportOrgInfo() {
        JSONObject request = this.doGet("/api/v2/organizations", new HashMap<>());
        mongoTemplate.insert(request, "org_info");
    }

    @Override
    public void importOrgInfo() {
        // todo  后期添加分页 以防过大
        JSONObject info = mongoTemplate.findOne(new Query(), JSONObject.class, "org_info");
        JSONObject requestParam = new JSONObject();
        requestParam.put("organizations", info.getJSONArray("organizations"));
        JSONObject request = this.doPost("/api/v2/organizations/create_many",requestParam);
        log.info("请求结果{}",request);
    }

    @Override
    public void exportOrgMembershipInfo() {
        JSONObject request = this.doGet("/api/v2/organization_memberships", new HashMap<>());
        mongoTemplate.insert(request, "org_membership");
    }

    @Override
    public void importOrgMembershipInfo() {
        // todo  后期添加分页 以防过大
        JSONObject info = mongoTemplate.findOne(new Query(), JSONObject.class, "org_membership");
        JSONObject requestParam = new JSONObject();
        requestParam.put("organization_memberships", info.getJSONArray("organization_memberships"));
        JSONObject request = this.doPost("/api/v2/organization_memberships/create_many",requestParam);
        log.info("请求结果{}",request);
    }

    @Override
    public void exportOrgSubscriptionsInfo() {
        JSONObject request = this.doGet("/api/v2/organization_subscriptions", new HashMap<>());
        mongoTemplate.insert(request, "organization_subscriptions");
    }

    @Override
    public void importOrgSubscriptionsInfo() {
        // todo  后期添加分页 以防过大
        JSONObject info = mongoTemplate.findOne(new Query(), JSONObject.class, "organization_subscriptions");

        for (Object groups : info.getJSONArray("organization_subscriptions")) {
            JSONObject requestParam = new JSONObject();
            requestParam.put("organization_subscriptions", groups);
            JSONObject request = this.doPost("/api/v2/organization_subscriptions", requestParam);
            log.info("请求结果{}", request);
        }
    }



}
