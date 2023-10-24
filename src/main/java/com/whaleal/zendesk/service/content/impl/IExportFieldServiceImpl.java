package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportFieldService;
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
public class IExportFieldServiceImpl extends BaseExportService implements IExportFieldService {
    @Override
    public void exportFieldInfo() {
        //todo  参数
        // .addQueryParameter("locale", "")

        JSONObject request = this.doGet("/api/v2/ticket_fields",new HashMap<>());
        List<JSONObject> list = request.getJSONArray("ticket_fields").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,"ticket_field");
    }

    @Override
    public void importFieldInfo() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "ticket_field");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("ticket_field", jsonObject);
                JSONObject request = this.doPost("/api/v2/ticket_fields", requestParam);
                log.info("请求结果{}", request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            mongoTemplate.save(document,"ticket_field");
        }
    }
}
