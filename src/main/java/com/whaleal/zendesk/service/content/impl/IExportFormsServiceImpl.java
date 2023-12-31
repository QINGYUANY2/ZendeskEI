package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportFormsService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class IExportFormsServiceImpl extends BaseExportService implements IExportFormsService {

    @Override
    public void exportTicketForms() {

        ModuleRecord moduleRecord = beginModuleRecord("exportTicketForms");
        Long useTime = doExport("/api/v2/ticket_forms", "ticket_forms", ExportEnum.TICKET.getValue() + "_forms");
        endModuleRecord(moduleRecord, useTime);
    }

    //还没测试，明天来了 测试
    @Override
    public void importTicketForms() {
        ModuleRecord moduleRecord = beginModuleRecord("importTicketForms");
        long startTime = System.currentTimeMillis();
        String TargetDefaultId = null;
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.TICKET.getValue() + "_forms");
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                if(jsonObject.get("default").equals("true")){
                    JSONObject getDefaultTarget = doGetTarget("/api/v2/ticket_forms", new HashMap<>());
                    JSONArray nestedDefaultTarget = getDefaultTarget.getJSONArray("ticket_forms");
                    for (Object nestedDefaultObj : nestedDefaultTarget) {
                        JSONObject nestedDefault = (JSONObject) nestedDefaultObj;
                        if(nestedDefault.get("default").equals("true")){
                            TargetDefaultId = nestedDefault.get("id").toString();
                        }
                    }
                    requestParam.put("ticket_form", jsonObject);
                    request = this.doUpdate("/api/v2/ticket_forms", requestParam ,TargetDefaultId);
                }else {
                    requestParam.put("ticket_form", jsonObject);
                    request = this.doPost("/api/v2/ticket_forms", requestParam);
                }
                document.put("newId", request.getJSONObject("ticket_form").get("id"));
                log.info("importTicketForms 执行完毕,请求参数：{},执行结果{}", requestParam, request);
                mongoTemplate.save(document, ExportEnum.TICKET.getValue() + "_forms");
                saveImportInfo("importTicketForms", request);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("导入 TicketForms 成功，一共导入{}条记录", list.size());
            endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
        }
    }


}
