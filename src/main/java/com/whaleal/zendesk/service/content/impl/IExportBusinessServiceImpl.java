package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportBusinessService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");

        JSONArray array = request.getJSONArray("views");
        mongoTemplate.insert(array,"view_info");
    }

    @Override
    public void importViewInfo() {

    }

    @Override
    public void exportMacroInfo() {

    }

    @Override
    public void importMacroInfo() {

    }
}
