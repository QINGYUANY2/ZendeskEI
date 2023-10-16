package com.whaleal.zendesk.service.helpCenter.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.helpCenter.IExportGuideService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IExportGuideServiceImpl extends BaseExportService implements IExportGuideService {
    @Override
    public void exportThemeInfo() {
        JSONObject request = this.doGet("/api/v2/guide/theming/themes",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("themes");
//        mongoTemplate.insert(array,"themes_info");
    }

    @Override
    public void importThemeInfo() {

    }

    @Override
    public void exportArticleInfo() {
        //todo /api/v2/help_center{/locale}/articles local需要根据所需填写
        JSONObject request = this.doGet("/api/v2/help_center/en/articles",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("articles");
//        mongoTemplate.insert(array,"article_info");
    }

    @Override
    public void importArticleInfo() {

    }

    @Override
    public void exportPermissionGroupInfo() {
        JSONObject request = this.doGet("/api/v2/guide/permission_groups.json",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("permission_groups");
//        mongoTemplate.insert(array,"permission_group");
    }

    @Override
    public void importPermissionGroupInfo() {

    }
}
