package com.whaleal.zendesk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.IExportUserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;


/**
 * @author lyz
 * @desc
 * @create: 2023-10-11 14:25
 **/
@Service
public class ExportUserServiceImpl extends BaseExportService implements IExportUserService {

    @Override
    public void exportUserInfo() {

        JSONObject request = this.doGet("/api/v2/users",new HashMap<>());
        System.out.println(request);

        mongoTemplate.save(request,"user_info");
    }
}
