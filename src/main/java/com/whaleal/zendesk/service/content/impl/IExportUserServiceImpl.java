package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportUserService;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


/**
 * @author lyz
 * @desc
 * @create: 2023-10-11 14:25
 **/
@Service
public class IExportUserServiceImpl extends BaseExportService implements IExportUserService {

    @Override
    public void exportUserInfo() {
        JSONObject request = this.doGet("/api/v2/users",new HashMap<>());
        JSONArray array = request.getJSONArray("users");
        mongoTemplate.insert(array,"user_info");

    }

    @Override
    public void importUserInfo() {
        // todo  后期添加分页 以防过大
        List<JSONObject> list = mongoTemplate.find(new Query(), JSONObject.class, "user_info");
        for (JSONObject jsonObject : list) {
            System.out.println("=========================");
            // todo 需拼接  user：json
            System.out.println(jsonObject);
            System.out.println("=========================");
        }
    }






}
