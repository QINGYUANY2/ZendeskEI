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
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
        JSONArray array = request.getJSONArray("macros");
        mongoTemplate.insert(array,"macro_info");
    }

    @Override
    public void importMacroInfo() {

    }
}
