package com.whaleal.zendesk.service.helpCenter.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.helpCenter.IExportGatherService;
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
public class IExportGatherServiceImpl extends BaseExportService implements IExportGatherService {
    @Override
    public void exportTopicInfo() {

        ModuleRecord moduleRecord = beginModuleRecord("exportTopicInfo");
        Long useTime = doExport("/api/v2/community/topics", "topics", ExportEnum.TOPIC.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importTopicInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importTopicInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.TOPIC.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("topic", jsonObject);
                request = this.doPost("/api/v2/community/topics", requestParam);
                document.put("newId", request.getJSONObject("topic").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importTopicInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importTopicInfo", request);
            mongoTemplate.save(document, ExportEnum.TOPIC.getValue() + "_info");
        }
        log.info("导入 importTopicInfo 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportPostsInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportPostsInfo");
        Long useTime = doExport("/api/v2/community/posts", "posts", ExportEnum.POSTS.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importPostsInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importPostsInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.POSTS.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("post", jsonObject);
                request = this.doPost("/api/v2/community/posts", requestParam);
                document.put("newId", request.getJSONObject("post").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importPostsInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importPostsInfo", request);
            mongoTemplate.save(document, ExportEnum.POSTS.getValue() + "_info");
        }
        log.info("导入 importPostsInfo 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }



    @Override
    public void exportUserSegment() {
        ModuleRecord moduleRecord = beginModuleRecord("exportUserSegmentInfo");
        Long useTime = doExport("/api/v2/help_center/user_segments", "user_segments", ExportEnum.USER_SEGMENT.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importUserSegment() {
        ModuleRecord moduleRecord = beginModuleRecord("importUserSegment");
        JSONObject request = null;
        JSONObject requestParam = new JSONObject();
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.USER_SEGMENT.getValue() + "_info");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                //更新userid
                JSONArray userIds = jsonObject.getJSONArray("added_user_ids");
                JSONArray userNewIds = new JSONArray();
                if(userIds.size()!=0){
                    for (Object userIdObject : userIds) {
                        Long userId = (Long) userIdObject;
                        Document userDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(userId)), Document.class, ExportEnum.USER.getValue() + "_info");
                        if(userDoc!=null) {
                            userNewIds.add(userDoc.get("newId"));
                        }
                    }
                    jsonObject.put("added_user_ids", userNewIds);
                }
                //更新organizationIds
                JSONArray organizationIds = jsonObject.getJSONArray("organization_ids");
                JSONArray organizationNewIds = new JSONArray();
                if(organizationIds.size()!=0){
                    for (Object organizationIdObject : organizationIds) {
                        Long organizationId = (Long) organizationIdObject;
                        Document organizationDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(organizationId)), Document.class, ExportEnum.ORGANIZATIONS.getValue() + "_info");
                        organizationNewIds.add(organizationDoc.get("newId"));
                    }
                    jsonObject.put("organization_ids", organizationNewIds);
                }
                //更新groupIds
                JSONArray groupIds = jsonObject.getJSONArray("group_ids");
                JSONArray groupNewIds = new JSONArray();
                if(groupIds.size()!=0){
                    for (Object groupIdObject : groupIds) {
                        Long groupId = (Long) groupIdObject;
                        Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(groupId)), Document.class, ExportEnum.GROUP.getValue() + "_info");
                        groupNewIds.add(groupDoc.get("newId"));
                    }
                    jsonObject.put("group_ids", groupNewIds);
                }

                requestParam.put("user_segment", jsonObject);
                //built_in不能被更改
                if(jsonObject.getBoolean("built_in")==true){
                    JSONObject temp = doGetTarget("/api/v2/help_center/user_segments",new HashMap<>());
                    JSONArray tempArray = temp.getJSONArray("user_segments");
                    for (Object tempObject : tempArray) {
                        JSONObject userSegment = (JSONObject) tempObject;
                        if(userSegment.getBoolean("built_in")==true && userSegment.get("name").equals(jsonObject.get("name"))){
                            //request = this.doUpdate("/api/v2/help_center/user_segments/",requestParam, user_segment.get("id").toString());
                            document.put("newId", userSegment.get("id"));
                        }
                    }
                }else {
                    request = this.doPost("/api/v2/help_center/user_segments", requestParam);
                    document.put("newId", request.getJSONObject("user_segment").get("id"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importUserSegment 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.USER_SEGMENT.getValue() + "_info");
            saveImportInfo("importUserSegment", request);
        }
        log.info("导入 UserSegment 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteUserSegment(){
        ModuleRecord moduleRecord = beginModuleRecord("deleteUserSegment");
        log.info("开始执行删除 user_segment 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/help_center/user_segments", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("user_segments");
        List<String> userSegmentIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            userSegmentIds.add(temps.getLong("id").toString());
        }
        try{
            for (String userSegmentId : userSegmentIds) {
                doDelete("/api/v2/help_center/user_segments/",userSegmentId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 user_segment 成功，一共删除{}条记录\n", userSegmentIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportContentTag() {
        ModuleRecord moduleRecord = beginModuleRecord("exportContentTagInfo");
        Long useTime = doExport("/api/v2/guide/content_tags", "records", ExportEnum.CONTENT_TAG.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importContentTag() {
        ModuleRecord moduleRecord = beginModuleRecord("importContentTagInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.CONTENT_TAG.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("content_tag", jsonObject);
                request = this.doPost("/api/v2/guide/content_tags", requestParam);
                document.put("newId", request.getJSONObject("content_tag").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importContentTag 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importContentTag", request);
            mongoTemplate.save(document, ExportEnum.CONTENT_TAG.getValue() + "_info");
        }
        log.info("导入 importContentTag 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    @Override
    public void deleteContentTag(){
        ModuleRecord moduleRecord = beginModuleRecord("deleteContentTag");
        log.info("开始执行删除 content_tag 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/guide/content_tags", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("records");
        List<String> contentTagIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            contentTagIds.add(temps.get("id").toString());
        }
        try{
            for (String contentTagId : contentTagIds) {
                doDelete("/api/v2/guide/content_tags/",contentTagId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 content_tag 成功，一共删除{}条记录\n", contentTagIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


}
