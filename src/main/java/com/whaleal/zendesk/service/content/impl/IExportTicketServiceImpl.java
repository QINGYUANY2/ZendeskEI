package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportTicketService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class IExportTicketServiceImpl extends BaseExportService implements IExportTicketService {


    @Override
    public void exportTicketRequest() {
        JSONObject request = this.doGet("/api/v2/requests",new HashMap<>());
        List<JSONObject> list = request.getJSONArray("requests").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain",StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,"ticket_request");
    }

    // todo ticket 是creat or import ？？？
    @Override
    public void exportTicketInfo() {
        //todo 参数
        // .addQueryParameter("external_id", "")
        JSONObject request = this.doGet("/api/v2/tickets",new HashMap<>());
        List<JSONObject> list = request.getJSONArray("tickets").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            JSONObject comment = this.doGet("/api/v2/tickets/"+jsonObject.get("id")+"/comments",new HashMap<>());
            jsonObject.put("comments",comment.get("comments"));
            jsonObject.put("status",0);
            jsonObject.put("domain",StringSub.getDomain(this.sourceDomain));
        }
//        System.out.println(list);
        mongoTemplate.insert(list,"ticket_info");
    }

    @Override
    public void importTicketInfo() {

        Criteria criteria = new Criteria();
        criteria.and("domain").is(StringSub.getDomain(this.sourceDomain));
        criteria.and("brand_id").is(10184545635097L);
        Query query = new Query(criteria);

        List<Document> list = mongoTemplate.find(query, Document.class, "ticket_info");
        JSONObject request = null;
        for (Document document : list) {
            try {
                if (document.get("brand_id") != null){
                    Document orgDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("brand_id"))), Document.class, "brand_info");
                    document.put("brand_id",orgDoc.get("newId"));
                }
                if (document.get("group_id") != null){
                    Document orgDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("group_id"))), Document.class, "group_info");
                    document.put("group_id",orgDoc.get("newId"));
                }

                JSONObject requestParam = new JSONObject();
                requestParam.put("ticket", document);
                // todo  批量建 or 单个建  ？？？？
//                request = this.doPost("/api/v2/tickets",requestParam);

                System.out.println("====================");
                System.out.println(requestParam);
                System.out.println("====================");

                log.info("请求结果{}",request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            document.put("request",request);
            document.remove("brand_id");
            document.remove("group_id");
            mongoTemplate.save(document,"ticket_info");
        }
    }

    @Override
    public void exportSatisfactionRatingInfo() {
        JSONObject request = this.doGet("/api/v2/satisfaction_ratings",new HashMap<>());
        List<JSONObject> list = request.getJSONArray("satisfaction_ratings").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,"satisfaction_rating");
    }

    @Override
    public void importSatisfactionRatingInfo() {

        // todo  需要绑定ticket id

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
