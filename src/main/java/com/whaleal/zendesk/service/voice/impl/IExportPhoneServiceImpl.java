package com.whaleal.zendesk.service.voice.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.voice.IExportPhoneService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class IExportPhoneServiceImpl extends BaseExportService implements IExportPhoneService {
    @Override
    public void exportPhoneNumberInfo() {
        //todo  参数设置 true 简略  false 详细，不填默认详细
        // .addQueryParameter("minimal_mode", "false")
        Map<String,String> map = new HashMap<>();
//        map.put("minimal_mode","true");
        JSONObject request = this.doGet("/api/v2/channels/voice/phone_numbers",map);

        List<JSONObject> list = request.getJSONArray("phone_numbers").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,"phoneNumber_info");
    }

    @Override
    public void importPhoneNumberInfo() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "phoneNumber_info");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("phone_number", jsonObject);
                System.out.println("================");
                System.out.println(requestParam);
                System.out.println("================");
                // todo https://support.zendesk.com/api/v2/channels/voice/phone_numbers
                //  You must supply credentials to complete this request

                JSONObject request = this.doPost("/api/v2/channels/voice/phone_numbers", requestParam);
                log.info("请求结果{}", request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            mongoTemplate.save(document,"phoneNumber_info");
        }
    }

    @Override
    public void exportGreetingCategoriesInfo() {
        JSONObject request = this.doGet("/api/v2/channels/voice/greeting_categories",new HashMap<>());

        List<JSONObject> list = request.getJSONArray("greeting_categories").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain",StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,"greeting_categories");
    }

    @Override
    public void importGreetingCategoriesInfo() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "greeting_categories");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("greeting", jsonObject);
                JSONObject request = this.doPost("/api/v2/channels/voice/greetings", requestParam);
                log.info("请求结果{}", request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            mongoTemplate.save(document,"greeting_categories");
        }
    }

    @Override
    public void exportIVRsInfo() {
        JSONObject request = this.doGet("/api/v2/channels/voice/ivr",new HashMap<>());
        List<JSONObject> list = request.getJSONArray("ivrs").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain",StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,"ivr_info");
    }

    @Override
    public void importIVRsInfo() {

        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "ivr_info");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("ivr", jsonObject);
                //todo 请求报错You do not have access to this page. Please contact the account owner of this help desk for further help
                JSONObject request = this.doPost("/api/v2/channels/voice/ivr", requestParam);
                log.info("请求结果{}", request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            mongoTemplate.save(document,"ivr_info");
        }
    }
}
