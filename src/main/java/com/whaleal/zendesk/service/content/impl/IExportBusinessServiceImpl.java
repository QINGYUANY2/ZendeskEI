package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportBusinessService;
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
public class IExportBusinessServiceImpl extends BaseExportService implements IExportBusinessService {
    @Override
    public void exportViewInfo() {
        // todo 参数  不加可查全部
        //                .addQueryParameter("access", "")
        //                .addQueryParameter("active", "")
        //                .addQueryParameter("group_id", "")
        //                .addQueryParameter("sort_by", "")
        //                .addQueryParameter("sort_order", "")
        JSONObject request = this.doGet("/api/v2/views",new HashMap<>());
        JSONArray array = request.getJSONArray("views");
        for (JSONObject jsonObject : array.toJavaList(JSONObject.class)) {
            jsonObject.put("status",0);
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(array,"view_info");
    }

    @Override
    public void importViewInfo() {

        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "view_info");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("view",jsonObject);
                JSONObject request = this.doPost("/api/v2/views", requestParam);
                log.info("请求结果{}",request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
           mongoTemplate.save(document,"view_info");
        }
    }

    @Override
    public void exportMacroInfo() {
        //todo  参数
        // .addQueryParameter("access", "personal")
        // .addQueryParameter("active", "true")
        // .addQueryParameter("category", "25")
        // .addQueryParameter("group_id", "25")
        // .addQueryParameter("include", "usage_7d")
        // .addQueryParameter("only_viewable", "false")
        // .addQueryParameter("sort_by", "alphabetical")
        // .addQueryParameter("sort_order", "asc")
        JSONObject request = this.doGet("/api/v2/macros",new HashMap<>());
        List<JSONObject> list = request.getJSONArray("macros").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain",StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,"macro_info");
    }

    @Override
    public void importMacroInfo() {
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "macro_info");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("macro",jsonObject);
                JSONObject request = this.doPost("/api/v2/macros", requestParam);
                log.info("请求结果{}",request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            mongoTemplate.save(document,"macro_info");
        }
    }
}
