package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportGroupService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IExportGroupServiceImpl extends BaseExportService implements IExportGroupService {
    @Override
    public void exportGroupInfo() {
        JSONObject request = this.doGet("/api/v2/groups",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("groups");
//        mongoTemplate.insert(array,"group_info");
    }

    @Override
    public void importGroupInfo() {

    }

    @Override
    public void exportMembershipInfo() {
        JSONObject request = this.doGet("/api/v2/group_memberships",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("group_memberships");
//        mongoTemplate.insert(array,"group_membership");
    }

    @Override
    public void importMembershipInfo() {

    }
}
