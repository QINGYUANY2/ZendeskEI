package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportSysService;
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
public class IExportSysServiceImpl extends BaseExportService implements IExportSysService {
    @Override
    public void exportBrandInfo() {
        JSONObject request = this.doGet("/api/v2/brands",new HashMap<>());
        List<JSONObject> list = request.getJSONArray("brands").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,"brand_info");
    }

    @Override
    public void importBrandInfo() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "brand_info");
        JSONObject request = null;
        for (Document document : list) {
            try {
                // host_mapping 关联问题 请求时移除即可
                document.remove("host_mapping");
                // todo  临时加 111 防止重复
                document.put("subdomain",document.get("subdomain")+"111");
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("brand", jsonObject);
                request = this.doPost("/api/v2/brands", requestParam);
                log.info("请求结果{}", request);
                document.put("status",1);
                document.put("newId",request.getJSONObject("brand").get("id"));
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            document.put("request",request);
            mongoTemplate.save(document,"brand_info");
        }
    }
}
