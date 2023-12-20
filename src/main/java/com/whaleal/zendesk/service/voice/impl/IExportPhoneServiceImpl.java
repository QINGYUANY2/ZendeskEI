package com.whaleal.zendesk.service.voice.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.voice.IExportPhoneService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IExportPhoneServiceImpl extends BaseExportService implements IExportPhoneService {
    @Override
    public void exportPhoneNumberInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportPhoneNumberInfo");
        Long useTime = doExport("/api/v2/channels/voice/phone_numbers/search", "phone_numbers", ExportEnum.PHONE.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);

    }

    @Override
    public void importPhoneNumberInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importPhoneNumberInfo");
        long startTime = System.currentTimeMillis();
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.PHONE.getValue() + "_info");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("phone_number", jsonObject);
                request = this.doPost("/api/v2/channels/voice/phone_numbers", requestParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importPhoneNumberInfo 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.PHONE.getValue() + "_info");
            saveImportInfo("importPhoneNumberInfo", request);
        }
        log.info("导入phone_info成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportGreetingCategoriesInfo() {

        ModuleRecord moduleRecord = beginModuleRecord("exportGreetingCategoriesInfo");
        Long useTime = doExport("/api/v2/channels/voice/greeting_categories", "greeting_categories", ExportEnum.GREETING_CATEGORIES.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);

    }

    @Override
    public void exportGreetingInfo() {

        ModuleRecord moduleRecord = beginModuleRecord("exportGreetingInfo");
        Long useTime = doExport("/api/v2/channels/voice/greetings", "greetings", ExportEnum.GREETING.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importGreetingInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importGreetingInfo");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.GREETING.getValue() + "_info");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("greeting", jsonObject);
                request = this.doPost("/api/v2/channels/voice/greetings", requestParam);
                document.put("newId", request.get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importGreetingInfo 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            saveImportInfo("importGreetingInfo", request);
            mongoTemplate.save(document, ExportEnum.GREETING.getValue() + "_info");
        }
        log.info("导入Greeting_info成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);

    }

    @Override
    public void exportIVRsInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportIVRsInfo");
        Long useTime = doExport("/api/v2/channels/voice/ivr", "ivrs", ExportEnum.IVRS.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importIVRsInfo() {

        ModuleRecord moduleRecord = beginModuleRecord("importIVRsInfo");
        long startTime = System.currentTimeMillis();
        JSONObject requestParam = new JSONObject();

        JSONObject request = null;
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.IVRS.getValue() + "_info");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("ivr", jsonObject);
                request = this.doPost("/api/v2/channels/voice/ivr", requestParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importIVRsInfo 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            saveImportInfo("importIVRsInfo", request);
            mongoTemplate.save(document, ExportEnum.IVRS.getValue() + "_info");
        }
        log.info("导入ivrs_info成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }
}
