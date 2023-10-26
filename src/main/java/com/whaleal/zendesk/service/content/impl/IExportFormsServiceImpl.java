package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
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
        JSONObject request = this.doGet("/api/v2/ticket_forms",new HashMap<>());
        List<JSONObject> list = request.getJSONArray("ticket_forms").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,ExportEnum.TICKET.getValue()+"_forms");
    }

    @Override
    public void importTicketForms() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.TICKET.getValue()+"_forms");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("ticket_form", jsonObject);
                JSONObject request = this.doPost("/api/v2/ticket_forms", requestParam);
                document.put("newId",request.getJSONObject("ticket_form").get("id"));
                log.info("请求结果{}", request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            mongoTemplate.save(document,ExportEnum.TICKET.getValue()+"_forms");
        }
    }


}
