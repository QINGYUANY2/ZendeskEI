package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportBusinessService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IExportBusinessServiceImpl extends BaseExportService implements IExportBusinessService {

    @Override
    public void exportViewInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportViewInfo");
        Long useTime = doExport("/api/v2/views", "views", ExportEnum.VIEW.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importViewInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importViewInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.VIEW.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("view", jsonObject);
                request = this.doPost("/api/v2/views", requestParam);
                document.put("newId", request.getJSONObject("view").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importViewInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.VIEW.getValue() + "_info");
            saveImportInfo("importViewInfo", request);
        }
        log.info("导入view_info成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportMacroInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportMacroInfo");
        Long useTime = doExport("/api/v2/macros", "macros", ExportEnum.MACRO.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importMacroInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importMacroInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.MACRO.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());

                requestParam.put("macro", jsonObject);
                request = this.doPost("/api/v2/macros", requestParam);
                document.put("newId", request.getJSONObject("macro").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importMacroInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.MACRO.getValue() + "_info");
            saveImportInfo("importMacroInfo", request);
        }
        log.info("导入macro_info成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }
}
