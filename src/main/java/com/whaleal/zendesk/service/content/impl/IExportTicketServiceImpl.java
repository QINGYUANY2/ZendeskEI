package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportTicketService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class IExportTicketServiceImpl extends BaseExportService implements IExportTicketService {


    @Override
    public void exportTicketRequest() {
        JSONObject request = this.doGet("/api/v2/requests", new HashMap<>());
        List<JSONObject> list = request.getJSONArray("requests").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status", 0);
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list, "ticket_request");
    }

    @Override
    public void exportTicketAudit() {
        JSONObject request = this.doGet("/api/v2/ticket_audits", new HashMap<>());
        List<JSONObject> list = request.getJSONArray("audits").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status", 0);
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list, "ticket_audits");
    }

    @Override
    public void exportTicketInfo() {
        //todo 参数
        // .addQueryParameter("external_id", "")
        JSONObject request = this.doGet("/api/v2/tickets", new HashMap<>());
        List<JSONObject> list = request.getJSONArray("tickets").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            JSONObject comment = this.doGet("/api/v2/tickets/" + jsonObject.get("id") + "/comments", new HashMap<>());
            jsonObject.put("comments", comment.get("comments"));
            jsonObject.put("status", 0);
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
//        System.out.println(list);
        mongoTemplate.insert(list, "ticket_info");
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
                if (document.get("brand_id") != null) {
                    Document brandDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("brand_id"))), Document.class, "brand_info");
                    if (brandDoc != null) {
                        param.put("brand_id", brandDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 brand_id", document.get("brand_id"));
                    }
                }
                if (document.get("group_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("group_id"))), Document.class, "group_info");
                    if (groupDoc != null) {
                        param.put("group_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 group_id", document.get("group_id"));
                    }
                }
                if (document.get("assignee_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("assignee_id"))), Document.class, "user_info");
                    if (groupDoc != null) {
                        param.put("assignee_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 assignee_id", document.get("assignee_id"));
                    }
                }
                if (document.get("requester_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("requester_id"))), Document.class, "user_info");
                    if (groupDoc != null) {
                        param.put("requester_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 requester_id", document.get("requester_id"));
                    }
                }
                if (document.get("follower_ids") != null) {
                    List<Long> followerIds = param.getList("follower_ids", Long.class);
                    List<Long> longList = new ArrayList<>();
                    for (Long followerId : followerIds) {
                        Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(followerId)), Document.class, "user_info");
                        if (groupDoc != null) {
                            longList.add((Long) groupDoc.get("newId"));
                        } else {
                            log.warn("同步ticket时,未找到 {} 对应的新 follower_ids", document.get("follower_ids"));
                        }
                    }
                    param.put("follower_ids", longList);
                }

                if (document.get("email_cc_ids") != null) {
                    List<Long> emailList = new ArrayList<>();
                    List<Long> emailCcIds = param.getList("email_cc_ids", Long.class);
                    for (Long emailCc : emailCcIds) {
                        Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(emailCc)), Document.class, "user_info");
                        if (groupDoc != null) {
                            emailList.add((Long) groupDoc.get("newId"));
                        } else {
                            log.warn("同步ticket时,未找到 {} 对应的新 email_cc_ids", document.get("email_cc_ids"));
                        }
                    }
                    param.put("email_cc_ids", emailList);
                }

                if (document.get("fields") != null) {
                    List<Document> fields = param.getList("fields", Document.class);
                    for (Document field : fields) {
                        Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(field.get("id"))), Document.class, "ticket_field");
                        if (groupDoc != null) {
                            field.put("id",groupDoc.get("newId"));
                        } else {
                            log.warn("同步ticket时,未找到 {} 对应的新 email_cc_ids", field.get("id"));
                        }
                    }
                }

                if (document.get("submitter_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("submitter_id"))), Document.class, "user_info");
                    if (groupDoc != null) {
                        param.put("submitter_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 submitter_id", document.get("submitter_id"));
                    }
                }

                if (document.get("organization_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("organization_id"))), Document.class, "org_info");
                    if (groupDoc != null) {
                        param.put("organization_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 organization_id", document.get("organization_id"));
                    }
                }
                if (document.get("ticket_form_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("ticket_form_id"))), Document.class, "ticket_forms");
                    if (groupDoc != null) {
                        param.put("ticket_form_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 ticket_form_id", document.get("ticket_form_id"));
                    }
                }

                if (document.get("comments") != null) {
                    List<Document> comments = param.getList("comments", Document.class);
                    for (Document comment : comments) {

                        //映射新id
                        if (comment.get("author_id") != null) {
                            Document authorDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(comment.get("author_id"))), Document.class, "user_info");
                            if (authorDoc != null) {
                                comment.put("author_id", authorDoc.get("newId"));
                            } else {
                                log.warn("同步ticket时,未找到 {} 对应的新 author_id", comment.get("author_id"));
                            }
                        }
                        // 附件相关
                        if (comment.get("attachments") != null){
                            Document attachments = (Document) comment.get("attachments");
                            String url = (String) attachments.get("mapped_content_url");
                            String filename = (String) attachments.get("file_name");
                            String type = (String) attachments.get("content_type");
                            File file = downloadFile(url, filename);
                            JSONObject jsonObject = doPost("/api/v2/uploads", type, file);

                        }


                    }
                }

                // todo 必须填good 或 bad  其余的都报错  null也报错
                if (document.get("satisfaction_rating") != null) {
                    Document satisfaction = (Document) param.get("satisfaction_rating");
                    if (satisfaction.get("score") != null && satisfaction.get("score").equals("unoffered")) {
                        satisfaction.put("score", "good");
                    }
                }
                JSONObject requestParam = new JSONObject();
                requestParam.put("ticket", param);
//                request = this.doPost("/api/v2/imports/tickets", requestParam);
                param.clear();
                log.info("请求结果{}", request);
                document.put("status", 1);
            } catch (Exception e) {
                e.printStackTrace();
                document.put("status", 2);
            }
            document.put("request", request);
            mongoTemplate.save(document, "ticket_info");
        }
    }



    public File downloadFile(String url,String filename){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        File dir = new File("C:\\Users\\cc\\Desktop\\temp");
        // 确保目录存在
//        dir.mkdirs();

        File file = null;
        FileOutputStream fos = null;
        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                file = new File(dir, filename);
                fos = new FileOutputStream(file);
                fos.write(body.bytes());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public static void main(String[] args) {



    }


    @Override
    public void exportTicketFields() {
        //todo  参数
        // .addQueryParameter("locale", "")

        JSONObject request = this.doGet("/api/v2/ticket_fields",new HashMap<>());
        List<JSONObject> list = request.getJSONArray("ticket_fields").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,"ticket_field");
    }

    @Override
    public void importTicketFields() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "ticket_field");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("ticket_field", jsonObject);
                JSONObject request = this.doPost("/api/v2/ticket_fields", requestParam);
                document.put("newId",request.get("id"));
                log.info("请求结果{}", request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            mongoTemplate.save(document,"ticket_field");
        }
    }

    @Override
    public void exportSatisfactionRatingInfo() {
        JSONObject request = this.doGet("/api/v2/satisfaction_ratings", new HashMap<>());
        List<JSONObject> list = request.getJSONArray("satisfaction_ratings").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status", 0);
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list, "satisfaction_rating");
    }

    @Override
    public void importSatisfactionRatingInfo() {

        // todo  需要绑定ticket id

    }

    @Override
    public void exportResourceCollectionInfo() {
        JSONObject request = this.doGet("/api/v2/resource_collections", new HashMap<>());
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
