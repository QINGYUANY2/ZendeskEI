package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportUserService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author lyz
 * @desc
 * @create: 2023-10-11 14:25
 **/


@Slf4j
@Service
public class IExportUserServiceImpl extends BaseExportService implements IExportUserService {

    @Value("${zendesk.source.domain}")
    private String sourceDomain;

    @Override
    public void exportUserInfo() {
        JSONObject request = this.doGet("/api/v2/users", new HashMap<>());
//        JSONArray users = request.getJSONArray("users");
//        String domain = StringSub.getDomain(sourceDomain);
//        users.forEach(obj -> {
//            JSONObject jsonObject = (JSONObject) obj;
//            jsonObject.put("domain", domain);
//        });
        mongoTemplate.insert(request, "user_info");

    }

    @Override
    public void importUserInfo() {
        // todo  后期添加分页 以防过大
        JSONObject info = mongoTemplate.findOne(new Query(), JSONObject.class, "user_info");
        JSONObject requestParam = new JSONObject();
        requestParam.put("users", info.getJSONArray("users"));
        JSONObject request = this.doPost("/api/v2/users/create_many",requestParam);
        log.info("请求结果{}",request);
    }

    @Override
    public void exportRoleInfo() {
        JSONObject request = this.doGet("/api/v2/roles", new HashMap<>());

        System.out.println("==============");
        System.out.println(request);
        System.out.println("==============");
//        mongoTemplate.insert(request, "role_info");

    }

    @Override
    public void importRoleInfo() {

    }


    @Override
    public void exportUserField() {
        JSONObject request = this.doGet("/api/v2/user_fields", new HashMap<>());
        mongoTemplate.insert(request, "user_field");

    }

    @Override
    public void importUserField() {
        // todo  后期添加分页 以防过大
        JSONObject info = mongoTemplate.findOne(new Query(), JSONObject.class, "user_field");

        for (Object groups : info.getJSONArray("user_fields")) {
            JSONObject requestParam = new JSONObject();
            requestParam.put("user_field", groups);
            JSONObject request = this.doPost("/api/v2/user_fields", requestParam);
            log.info("请求结果{}", request);
        }
    }


}
