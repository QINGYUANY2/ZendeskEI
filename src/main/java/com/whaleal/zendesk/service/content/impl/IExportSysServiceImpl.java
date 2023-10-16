package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportSysService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IExportSysServiceImpl extends BaseExportService implements IExportSysService {
    @Override
    public void exportBrandInfo() {
        JSONObject request = this.doGet("/api/v2/brands",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("brands");
//        mongoTemplate.insert(array,"brand_info");
    }

    @Override
    public void importBrandInfo() {

    }
}
