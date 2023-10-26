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

        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.GROUP.getValue() + "_info");
        JSONObject request=null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("group", jsonObject);
                request= this.doPost("/api/v2/groups", requestParam);
                log.info("请求结果{}", request);
                document.put("status",1);
                document.put("newId",request.getJSONObject("group").get("id"));
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
                document.put("errorDetails",request.get("details"));
            }
            mongoTemplate.save(document,ExportEnum.GROUP.getValue() + "_info");
        }
    }

    @Override
    public void exportGroupMembershipInfo() {
        JSONObject request = this.doGet("/api/v2/group_memberships", new HashMap<>());
        List<JSONObject> list = request.getJSONArray("group_memberships").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain",StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,"group_membership");
    }

    @Override
    public void importGroupMembershipInfo() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "group_membership");

        JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
        JSONObject requestParam = new JSONObject();
        requestParam.put("group_memberships", array);
        System.out.println("===================");
        System.out.println(requestParam);
        System.out.println("===================");

        JSONObject request = this.doPost("/api/v2/group_memberships/create_many",requestParam);
        log.info("请求结果{}",request);
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
