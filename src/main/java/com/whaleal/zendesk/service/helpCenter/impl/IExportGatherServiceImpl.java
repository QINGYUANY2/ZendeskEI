package com.whaleal.zendesk.service.helpCenter.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.helpCenter.IExportGatherService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IExportGatherServiceImpl extends BaseExportService implements IExportGatherService {
    @Override
    public void exportTopicInfo() {
        JSONObject request = this.doGet("/api/v2/community/topics",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("topics");
//        mongoTemplate.insert(array,"topic_info");
    }

    @Override
    public void importTopicInfo() {

    }

    @Override
    public void exportUserSegmentInfo() {
        //todo 参数
        // .addQueryParameter("built_in", "")
        JSONObject request = this.doGet("/api/v2/help_center/user_segments",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("user_segments");
//        mongoTemplate.insert(array,"user_segment");
    }

    @Override
    public void importUserSegmentInfo() {

    }
}
