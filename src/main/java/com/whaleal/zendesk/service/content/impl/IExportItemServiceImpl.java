package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportItemService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IExportItemServiceImpl extends BaseExportService implements IExportItemService {
    @Override
    public void exportItemInfo() {
        JSONObject request = this.doGet("/api/v2/dynamic_content/items",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("items");
//        mongoTemplate.insert(array,"item_info");
    }

    @Override
    public void importItemInfo() {

    }

    @Override
    public void exportDynamicContent() {
        //todo 参数
        // .addQueryParameter("identifiers", "")
        JSONObject request = this.doGet("/api/v2/dynamic_content/items/show_many",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("items");
//        mongoTemplate.insert(array,"dynamic_content");
    }

    @Override
    public void importDynamicContent() {

    }
}
