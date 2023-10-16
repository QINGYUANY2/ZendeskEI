package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportUserService;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;


/**
 * @author lyz
 * @desc
 * @create: 2023-10-11 14:25
 **/
@Service
public class IExportUserServiceImpl extends BaseExportService implements IExportUserService {

    @Override
    public void exportUserInfo() {
        JSONObject request = this.doGet("/api/v2/users", new HashMap<>());
        mongoTemplate.save(request, "user_info");

    }

    @Override
    public void importUserInfo() {
        // todo  后期添加分页 以防过大
        JSONObject info = mongoTemplate.findOne(new Query(), JSONObject.class, "user_info");

        JSONObject request = this.doPost("/api/v2/users/create_many",info.getJSONObject("users") );

        System.out.println("=========================");
        System.out.println(request);
        System.out.println("=========================");

    }


}
