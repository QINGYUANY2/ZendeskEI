package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportOrgService;
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
public class IExportOrgServiceImpl extends BaseExportService implements IExportOrgService {



    @Override
    public void exportOrgInfo() {
        JSONObject request = this.doGet("/api/v2/organizations", new HashMap<>());
        //todo 所有的存库后加上标识
        request.put("domain",this.targetDomain);

        JSONArray array = request.getJSONArray("organizations");
        List<JSONObject> list = array.toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("domain",StringSub.getDomain(this.sourceDomain));
        }

        mongoTemplate.insert(list, "org_info");

    }

    @Override
    public void importOrgInfo() {
        // todo  后期添加分页 以防过大

        List<Document> documentList = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "org_info");
        for (Document document : documentList) {
            JSONObject jsonObject = JSONObject.parseObject(document.toJson());
            JSONObject requestParam = new JSONObject();
            requestParam.put("organization", jsonObject);
            JSONObject request = this.doPost("/api/v2/organizations",requestParam);
            document.append("newId",request.getJSONObject("organization").get("id"));
            mongoTemplate.save(document,"org_info");
        }
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
