package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportFieldService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IExportFieldServiceImpl extends BaseExportService implements IExportFieldService {
    @Override
    public void exportFieldInfo() {
        //todo  参数
        // .addQueryParameter("locale", "")

        JSONObject request = this.doGet("/api/v2/ticket_fields",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("ticket_fields");
//        mongoTemplate.insert(array,"ticket_field");
    }

    @Override
    public void importFieldInfo() {

    }
}
