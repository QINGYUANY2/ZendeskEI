package com.whaleal.zendesk.service.helpCenter.impl;

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



}
