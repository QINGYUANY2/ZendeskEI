package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportSysService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class IExportSysServiceImpl extends BaseExportService implements IExportSysService {
    @Override
    public void exportBrandInfo() {

        ModuleRecord moduleRecord = beginModuleRecord("exportBrandInfo");
        Long useTime = doExport("/api/v2/brands", "brands", ExportEnum.BRAND.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importBrandInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importBrandInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.BRAND.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        //拿到目标端id
        JSONObject getTargetId = doGetTarget("/api/v2/account/settings", new HashMap<>());
        JSONObject getBrand = getTargetId.getJSONObject("settings").getJSONObject("brands");
        String TargetDefaultBrandId = getBrand.getLong("default_brand_id").toString();
        //拿到目标端domain
        for (Document document : list) {
            try {
                // host_mapping 关联问题 请求时移除即可
                document.remove("host_mapping");
                // todo  加 timestamp 防止重复
                if (document.get("default").toString().equals("false")) {
                    document.put("subdomain", document.get("subdomain") + String.valueOf(new Date().getTime()));
                }else{
                    document.remove("subdomain");
                }
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("brand", jsonObject);
                if (document.get("default").toString().equals("true")){
                    request = this.doUpdate("/api/v2/brands",requestParam, TargetDefaultBrandId);
                }else{
                    request = this.doPost("/api/v2/brands", requestParam);
                }

                document.put("newId", request.getJSONObject("brand").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importBrandInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.BRAND.getValue() + "_info");
            saveImportInfo("importBrandInfo", request);
        }
        log.info("导入 brand_info 成功,一共导入 {} 记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteBrandInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteBrandInfo");
        log.info("开始执行删除 brand_info 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/brands", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("brands");
        List<String> brandIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            brandIds.add(temps.getLong("id").toString());
        }
        try{
            for (String brandId : brandIds) {
                doDelete("/api/v2/brands/",brandId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 brand 成功，一共删除{}条记录\n", brandIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    @Override
    public void exportSupportAddressInfo() {

        ModuleRecord moduleRecord = beginModuleRecord("exportSupportAddressInfo");
        Long useTime = doExport("/api/v2/recipient_addresses", "recipient_addresses", ExportEnum.SUPPORT_ADDRESS.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importSupportAddressInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importSupportAddressInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.SUPPORT_ADDRESS.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("recipient_address", jsonObject);
                request = this.doPost("/api/v2/recipient_addresses", requestParam);
                document.put("newId", request.getJSONObject("recipient_address").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importSupportAddressInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.SUPPORT_ADDRESS.getValue() + "_info");
            saveImportInfo("importSupportAddressInfo", request);
        }
        log.info("导入 importSupportAddressInfo 成功,一共导入 {} 记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }




}
