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
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                if(jsonObject.get("is_public").equals(false)){
                    jsonObject.put("is_public","true");
                }
                requestParam.put("group", jsonObject);
                request = this.doPost("/api/v2/groups", requestParam);
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
