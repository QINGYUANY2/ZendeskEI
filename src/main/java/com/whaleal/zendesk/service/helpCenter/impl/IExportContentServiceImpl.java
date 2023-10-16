package com.whaleal.zendesk.service.helpCenter.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.helpCenter.IExportContentService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IExportContentServiceImpl extends BaseExportService implements IExportContentService {

    @Override
    public void exportExternalContentRecordInfo() {
        JSONObject request = this.doGet("/api/v2/guide/external_content/records",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("records");
//        mongoTemplate.insert(array,"record_info");
    }

    @Override
    public void importExternalContentRecordInfo() {

    }
}
