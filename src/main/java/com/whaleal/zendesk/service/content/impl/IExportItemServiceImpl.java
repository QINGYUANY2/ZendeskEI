package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportItemService;
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
public class IExportItemServiceImpl extends BaseExportService implements IExportItemService {
    @Override
    public void exportItemInfo() {

        ModuleRecord moduleRecord = beginModuleRecord("exportItemInfo");
        Long useTime = doExport("/api/v2/dynamic_content/items", "items", ExportEnum.ITEM.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importItemInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importItemInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ITEM.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("item", jsonObject);
                request = this.doPost("/api/v2/dynamic_content/items", requestParam);
                document.put("newId", request.getJSONObject("item").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importItemInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.ITEM.getValue() + "_info");
            saveImportInfo("importItemInfo", request);
        }
        log.info("导入 item_info 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteItemInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteItemInfo");
        log.info("开始执行删除 item_info 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/dynamic_content/items", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("items");
        List<String> itemIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            itemIds.add(temps.getLong("id").toString());
        }
        try{
            for (String itemId : itemIds) {
                doDelete("/api/v2/dynamic_content/items/",itemId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 item 成功，一共删除{}条记录\n", itemIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

}
