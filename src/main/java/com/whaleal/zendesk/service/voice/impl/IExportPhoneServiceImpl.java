package com.whaleal.zendesk.service.voice.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.voice.IExportPhoneService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IExportPhoneServiceImpl extends BaseExportService implements IExportPhoneService {
    @Override
    public void exportPhoneNumberInfo() {
        //todo  参数设置 true 简略  false 详细，不填默认详细
        // .addQueryParameter("minimal_mode", "false")
        Map<String,String> map = new HashMap<>();
        map.put("minimal_mode","true");
        JSONObject request = this.doGet("/api/v2/channels/voice/phone_numbers",map);
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("phone_numbers");
//        mongoTemplate.insert(array,"phoneNumber_info");
    }

    @Override
    public void importPhoneNumberInfo() {

    }

    @Override
    public void exportGreetingCategoriesInfo() {
        JSONObject request = this.doGet("/api/v2/channels/voice/greeting_categories",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("greeting_categories");
//        mongoTemplate.insert(array,"greeting_categories");
    }

    @Override
    public void importGreetingCategoriesInfo() {

    }

    @Override
    public void exportIVRsInfo() {
        JSONObject request = this.doGet("/api/v2/channels/voice/ivr",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("ivrs");
//        mongoTemplate.insert(array,"ivr_info");
    }

    @Override
    public void importIVRsInfo() {

    }
}
