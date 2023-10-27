package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.TaskInfo;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportGroupService;
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
public class IExportGroupServiceImpl extends BaseExportService implements IExportGroupService {
    @Override
    public void exportGroupInfo() {
        TaskInfo exportUserInfo = saveTaskInfo("exportGroupInfo");
        Long useTime = doExport("/api/v2/groups", "groups", ExportEnum.GROUP.getValue() + "_info");
        exportUserInfo.setEndTime(TimeUtil.getTime());
        exportUserInfo.setUseTime(useTime);
        exportUserInfo.setStatus(2);
        mongoTemplate.save(exportUserInfo);
    }

    @Override
    public void importGroupInfo() {
        TaskInfo saveTask = saveTaskInfo("importGroupInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.GROUP.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request=null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("group", jsonObject);
                request= this.doPost("/api/v2/groups", requestParam);
                document.put("newId",request.getJSONObject("group").get("id"));
            }catch (Exception e){
                e.printStackTrace();
            }
            log.info("importGroupInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importGroupInfo", request);
            mongoTemplate.save(document,ExportEnum.GROUP.getValue() + "_info");
        }
        log.info("导入 Group_info 成功，一共导出{}条记录",list.size());
        saveTask.setEndTime(TimeUtil.getTime());
        saveTask.setUseTime(System.currentTimeMillis() - startTime);
        saveTask.setStatus(2);
        mongoTemplate.save(saveTask);
    }

    @Override
    public void exportGroupMembershipInfo() {

        TaskInfo exportUserInfo = saveTaskInfo("exportGroupMembershipInfo");
        Long useTime = doExport("/api/v2/group_memberships", "group_memberships", ExportEnum.GROUP.getValue() + "_membership");
        exportUserInfo.setEndTime(TimeUtil.getTime());
        exportUserInfo.setUseTime(useTime);
        exportUserInfo.setStatus(2);
        mongoTemplate.save(exportUserInfo);
    }

    @Override
    public void importGroupMembershipInfo() {

        TaskInfo saveTask = saveTaskInfo("importUserInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "group_membership");

        JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
        JSONObject requestParam = new JSONObject();
        requestParam.put("group_memberships", array);

        JSONObject request = this.doPost("/api/v2/group_memberships/create_many",requestParam);
        log.info("importGroupMembershipInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
        log.info("导入 user_info 成功，一共导出{}条记录",array.size());
        saveTask.setEndTime(TimeUtil.getTime());
        saveTask.setUseTime(System.currentTimeMillis() - startTime);
        saveTask.setStatus(2);
        mongoTemplate.save(saveTask);
        // todo  单个创建有问题，状态无法更改

//        for (Document document : list) {
//            try {
//                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
//                JSONObject requestParam = new JSONObject();
//                requestParam.put("group_membership", jsonObject);
//                JSONObject request = this.doPost("/api/v2/group_memberships",requestParam);
//                log.info("请求结果{}",request);
//                document.put("status",1);
//            }catch (Exception e){
//                e.printStackTrace();
//                document.put("status",2);
//            }
//            mongoTemplate.save(document,"group_membership");
//        }
    }
}
