package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class IExportGroupServiceImpl extends BaseExportService implements IExportGroupService {
    @Override
    public void exportGroupInfo() {
        JSONObject request = this.doGet("/api/v2/groups", new HashMap<>());
        mongoTemplate.insert(request, "group_info");
    }

    @Override
    public void importGroupInfo() {

        // todo  后期添加分页 以防过大
        JSONObject info = mongoTemplate.findOne(new Query(), JSONObject.class, "group_info");
        for (Object groups : info.getJSONArray("groups")) {
            JSONObject requestParam = new JSONObject();
            requestParam.put("group", groups);
            JSONObject request = this.doPost("/api/v2/groups", requestParam);
            log.info("请求结果{}", request);
        }
    }

    @Override
    public void exportMembershipInfo() {
        JSONObject request = this.doGet("/api/v2/group_memberships", new HashMap<>());
        mongoTemplate.insert(request,"group_membership");
    }

    @Override
    public void importMembershipInfo() {

        // todo  后期添加分页 以防过大
        JSONObject info = mongoTemplate.findOne(new Query(), JSONObject.class, "group_membership");
        JSONObject requestParam = new JSONObject();
        requestParam.put("group_memberships", info.getJSONArray("group_memberships"));
        JSONObject request = this.doPost("/api/v2/group_memberships/create_many",requestParam);
        log.info("请求结果{}",request);
    }
}
