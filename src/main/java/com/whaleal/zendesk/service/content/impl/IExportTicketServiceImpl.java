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


import java.util.ArrayList;
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
            Document param = Document.parse(document.toJson());
            try {
                if (document.get("brand_id") != null){
                    Document brandDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("brand_id"))), Document.class, "brand_info");
                    if (brandDoc != null) {
                        param.put("brand_id",brandDoc.get("newId"));
                    }else {
                        log.warn("同步ticket时,未找到 {} 对应的新 brand_id",document.get("brand_id"));
                    }
                }
                if (document.get("group_id") != null){
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("group_id"))), Document.class, "group_info");
                    if (groupDoc != null) {
                        param.put("group_id",groupDoc.get("newId"));
                    }else {
                        log.warn("同步ticket时,未找到 {} 对应的新 group_id",document.get("group_id"));
                    }
                }
                if (document.get("assignee_id") != null){
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("assignee_id"))), Document.class, "user_info");
                    if (groupDoc != null) {
                        param.put("assignee_id",groupDoc.get("newId"));
                    }else {
                        log.warn("同步ticket时,未找到 {} 对应的新 assignee_id",document.get("assignee_id"));
                    }
                }
                if (document.get("requester_id") != null){
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("requester_id"))), Document.class, "user_info");
                    if (groupDoc != null) {
                        param.put("requester_id",groupDoc.get("newId"));
                    }else {
                        log.warn("同步ticket时,未找到 {} 对应的新 requester_id",document.get("requester_id"));
                    }
                }
                if (document.get("follower_ids") != null){
                    List<Long> followerIds = param.getList("follower_ids", Long.class);
                    List<Long> longList = new ArrayList<>();
                    for (Long followerId : followerIds) {
                        Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get(followerId))), Document.class, "user_info");
                        if (groupDoc != null) {
                            longList.add((Long) groupDoc.get("newId"));
                        }else {
                            log.warn("同步ticket时,未找到 {} 对应的新 follower_ids",document.get("follower_ids"));
                        }
                    }
                    param.put("follower_ids",longList);
                }

                if (document.get("submitter_id") != null){
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("submitter_id"))), Document.class, "user_info");
                    if (groupDoc != null) {
                        param.put("submitter_id",groupDoc.get("newId"));
                    }else {
                        log.warn("同步ticket时,未找到 {} 对应的新 submitter_id",document.get("submitter_id"));
                    }
                }

                if (document.get("organization_id") != null){
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("organization_id"))), Document.class, "org_info");
                    if (groupDoc != null) {
                        param.put("organization_id",groupDoc.get("newId"));
                    }else {
                        log.warn("同步ticket时,未找到 {} 对应的新 organization_id",document.get("organization_id"));
                    }
                }

                if (document.get("comments") != null){
                    List<Document> comments = param.getList("comments", Document.class);
                    for (Document comment : comments) {
                        if (comment.get("author_id") != null){
                            Document authorDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(comment.get("author_id"))), Document.class, "user_info");
                            if (authorDoc != null) {
                                comment.put("author_id",authorDoc.get("newId"));
                            }else {
                                log.warn("同步ticket时,未找到 {} 对应的新 author_id",comment.get("author_id"));
                            }
                        }
                    }
                }

                // todo 必须天good 或 bad  其余的都报错  null也报错
                if (document.get("satisfaction_rating") != null){
                    Document satisfaction = (Document) param.get("satisfaction_rating");
                    if (satisfaction.get("score")!= null && satisfaction.get("score").equals("unoffered")){
                        satisfaction.put("score","good");
                    }
                }
                JSONObject requestParam = new JSONObject();
                requestParam.put("ticket", param);
                request = this.doPost("/api/v2/imports/tickets",requestParam);
                System.out.println("====================");
                System.out.println(requestParam);
                System.out.println("====================");
                param.clear();
                log.info("请求结果{}",request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            document.put("request",request);
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
