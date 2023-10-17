package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportTicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Slf4j
@Service
public class IExportTicketServiceImpl extends BaseExportService implements IExportTicketService {
    @Override
    public void exportTicketInfo() {
        //todo 参数
        // .addQueryParameter("external_id", "")
        JSONObject request = this.doGet("/api/v2/tickets",new HashMap<>());
        // todo 所有的Ticket都在tickets中，获取时可能会内存溢出， 后期可分成每个ticket一条记录与账户绑定
        mongoTemplate.save(request,"ticket_info");
    }

    @Override
    public void importTicketInfo() {
        JSONObject info = mongoTemplate.findOne(new Query(), JSONObject.class, "ticket_info");
        JSONObject requestParam = new JSONObject();
        requestParam.put("tickets", info.getJSONArray("tickets"));
        JSONObject request = this.doPost("/api/v2/tickets/create_many",requestParam);
        log.info("请求结果{}",request);
    }

    @Override
    public void exportSatisfactionRatingInfo() {
        JSONObject request = this.doGet("/api/v2/satisfaction_ratings",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("satisfaction_ratings");
//        mongoTemplate.insert(array,"satisfaction_rating");
    }

    @Override
    public void importSatisfactionRatingInfo() {

    }

    @Override
    public void exportResourceCollectionInfo() {
        JSONObject request = this.doGet("/api/v2/resource_collections",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("resource_collections");
//        mongoTemplate.insert(array,"resource_collection");
    }

    @Override
    public void importResourceCollectionInfo() {

    }
}
