package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportGroupService;
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
public class IExportGroupServiceImpl extends BaseExportService implements IExportGroupService {
    @Override
    public void exportGroupInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportGroupInfo");
        Long useTime = doExport("/api/v2/groups", "groups", ExportEnum.GROUP.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importGroupInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importGroupInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.GROUP.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        JSONObject getTargetid = doGetTarget("/api/v2/groups", new HashMap<>());
        JSONArray getGroups = (JSONArray) getTargetid.getJSONArray("groups");
        String DefaultGroupId = null;
        for (Object GroupObj : getGroups) {
            JSONObject Group = (JSONObject) GroupObj;
            if(Group.getBoolean("default").equals(true)){
                DefaultGroupId = Group.getLong("id").toString();
            }
        }
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                if(jsonObject.get("is_public").equals(false)){
                    jsonObject.put("is_public","true");
                }
                requestParam.put("group", jsonObject);
                if(jsonObject.get("default").equals(true)){
                    request = this.doUpdate("/api/v2/groups", requestParam, DefaultGroupId);
                }else {
                    request = this.doPost("/api/v2/groups", requestParam);
                }
                document.put("newId", request.getJSONObject("group").get("id"));

            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importGroupInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importGroupInfo", request);
            mongoTemplate.save(document, ExportEnum.GROUP.getValue() + "_info");
        }
        log.info("导入 Group_info 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteGroupInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteGroupInfo");
        log.info("开始执行删除 group 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/groups", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("groups");
        JSONObject agents = doGetTarget("/api/v2/group_memberships", new HashMap<>());
        JSONArray agentsArray = agents.getJSONArray("group_memberships");
        List<String> groupIds = new ArrayList<>();
        JSONObject requestParam = new JSONObject();
        JSONObject nestedParam = new JSONObject();
        Long default_group_id = null;
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            groupIds.add(temps.getLong("id").toString());
            if(temps.getBoolean("default")==true){
                default_group_id = temps.getLong("id");
            }
        }
        Document defaultDoc = mongoTemplate.findOne(new Query(new Criteria("newId").is(default_group_id)), Document.class, ExportEnum.GROUP.getValue() + "_info");
        Long default_group_old_id = defaultDoc.getLong("id");
        try{
            for (Object agentsObj : agentsArray) {
                JSONObject agent = (JSONObject) agentsObj;
                if (agent.get("group_id")!=default_group_old_id && agent.getBoolean("default")==true){
                    nestedParam.put("default_group_id", default_group_id);
                    nestedParam.put("user_id", agent.get("user_id"));
                    requestParam.put("user", nestedParam);
                    doUpdate("/api/v2/users", requestParam, agent.get("user_id").toString());
                }

            }
            for (String groupId : groupIds) {
                doDelete("/api/v2/groups/",groupId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 group 成功，一共删除{}条记录\n", groupIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportGroupMembershipInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportGroupMembershipInfo");
        Long useTime = doExport("/api/v2/group_memberships", "group_memberships", ExportEnum.GROUP.getValue() + "_membership");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importGroupMembershipInfo() {

        ModuleRecord moduleRecord = beginModuleRecord("importGroupMembershipInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.GROUP.getValue() + "_membership");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;

//        JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
//        JSONObject requestParam = new JSONObject();
//        requestParam.put("group_memberships", array);

//        JSONObject request = this.doPost("/api/v2/group_memberships/create_many", requestParam);
//        log.info("importGroupMembershipInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
//        log.info("导入 groupMembership_info 成功，一共入出{}条记录", array.size());
//        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
        // todo  单个创建有问题，状态无法更改

        for (Document document : list) {
            try {
                Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(document.get("group_id"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                Document userDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(document.get("user_id"))), Document.class, ExportEnum.USER.getValue() + "_info");

                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                jsonObject.put("group_id",groupDoc.get("newId"));
                jsonObject.put("user_id",userDoc.get("newId"));
                requestParam.put("group_membership", jsonObject);
                request = this.doPost("/api/v2/group_memberships",requestParam);
                document.put("newId",request.getJSONObject("group_membership").get("id"));
            }catch (Exception e){
                e.printStackTrace();
            }
            log.info("importGroupMembershipInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importGroupMembershipInfo",request);
            mongoTemplate.save(document,ExportEnum.GROUP.getValue() + "_membership");
        }
        log.info("导入 importGroupMembershipInfo 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }
}
