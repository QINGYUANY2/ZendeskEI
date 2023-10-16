package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportTicketService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IExportTicketServiceImpl extends BaseExportService implements IExportTicketService {
    @Override
    public void exportTicketInfo() {
        //todo 参数
        // .addQueryParameter("external_id", "")
        JSONObject request = this.doGet("/api/v2/tickets",new HashMap<>());
        System.out.println("=====================");
        System.out.println(request);
        System.out.println("=====================");
//        JSONArray array = request.getJSONArray("tickets");
//        mongoTemplate.insert(array,"ticket_info");
    }

    @Override
    public void importTicketInfo() {

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
