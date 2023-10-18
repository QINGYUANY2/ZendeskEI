package com.whaleal.zendesk.service.helpCenter.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.helpCenter.IExportGatherService;
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
public class IExportGatherServiceImpl extends BaseExportService implements IExportGatherService {
    @Override
    public void exportTopicInfo() {
        JSONObject request = this.doGet("/api/v2/community/topics",new HashMap<>());
        List<JSONObject> list = request.getJSONArray("topics").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,"topic_info");
    }

    @Override
    public void importTopicInfo() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "topic_info");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("topic", jsonObject);
                JSONObject request = this.doPost("/api/v2/community/topics", requestParam);
                log.info("请求结果{}", request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            mongoTemplate.save(document,"topic_info");
        }
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
