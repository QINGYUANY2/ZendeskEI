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

//    @Override
//    public void exportUserSegmentInfo() {
//        //todo 参数
//        // .addQueryParameter("built_in", "")
//        JSONObject request = this.doGet("/api/v2/help_center/user_segments",new HashMap<>());
//        System.out.println("=====================");
//        System.out.println(request);
//        System.out.println("=====================");
////        JSONArray array = request.getJSONArray("user_segments");
////        mongoTemplate.insert(array,"user_segment");
//    }
//
//    @Override
//    public void importUserSegmentInfo() {
//
//    }
}
