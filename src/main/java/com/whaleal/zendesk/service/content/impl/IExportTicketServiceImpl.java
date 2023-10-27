package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.TaskInfo;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportTicketService;
import com.whaleal.zendesk.util.StringSub;
import com.whaleal.zendesk.util.TimeUtil;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class IExportTicketServiceImpl extends BaseExportService implements IExportTicketService {


    @Override
    public void exportTicketRequest() {
        TaskInfo taskInfo = saveTaskInfo("exportTicketRequest");
        Long useTime = doExport("/api/v2/requests", "requests", ExportEnum.TICKET+"_request");
        taskInfo.setStatus(2);
        taskInfo.setEndTime(TimeUtil.getTime());
        taskInfo.setUseTime(useTime);
        mongoTemplate.save(taskInfo);
    }

    @Override
    public void exportTicketAudit() {
        TaskInfo taskInfo = saveTaskInfo("exportTicketAudit");
        Long useTime = doExport("/api/v2/ticket_audits", "audits", ExportEnum.TICKET+"_audits");
        taskInfo.setStatus(2);
        taskInfo.setEndTime(TimeUtil.getTime());
        taskInfo.setUseTime(useTime);
        mongoTemplate.save(taskInfo);
    }

    @Deprecated
    @Override
    public void exportTicketInfo() {
        TaskInfo taskInfo = saveTaskInfo("exportTicketInfo");
        long startTime = System.currentTimeMillis();
        JSONObject request = this.doGet("/api/v2/tickets", new HashMap<>());
        List<JSONObject> list = request.getJSONArray("tickets").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            JSONObject comment = this.doGet("/api/v2/tickets/" + jsonObject.get("id") + "/comments", new HashMap<>());
            jsonObject.put("comments", comment.get("comments"));
            jsonObject.put("status", 0);
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list, ExportEnum.TICKET.getValue()+"_info");
        log.info("导出TicketInfo成功，一共导出{}条记录",list.size());
        taskInfo.setStatus(2);
        taskInfo.setEndTime(TimeUtil.getTime());
        taskInfo.setUseTime(System.currentTimeMillis()-startTime);
        mongoTemplate.save(taskInfo);
    }

    @Override
    public void importTicketInfo() {
        TaskInfo saveTask = saveTaskInfo("importTicketInfo");
        long startTime = System.currentTimeMillis();

        Criteria criteria = new Criteria();
        criteria.and("domain").is(StringSub.getDomain(this.sourceDomain));
//        criteria.and("id").is(72);
        Query query = new Query(criteria);

        List<Document> list = mongoTemplate.find(query, Document.class, ExportEnum.TICKET.getValue()+"_info");
        JSONObject request = null;
        for (Document document : list) {
            JSONObject requestParam = new JSONObject();
            Document param = Document.parse(document.toJson());
            try {
                if (document.get("brand_id") != null) {
                    Document brandDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("brand_id"))), Document.class, ExportEnum.BRAND.getValue()+"_info");
                    if (brandDoc != null) {
                        param.put("brand_id", brandDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 brand_id", document.get("brand_id"));
                    }
                }
                if (document.get("group_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("group_id"))), Document.class, ExportEnum.GROUP.getValue()+"_info");
                    if (groupDoc != null) {
                        param.put("group_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 group_id", document.get("group_id"));
                    }
                }
                if (document.get("assignee_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("assignee_id"))), Document.class, ExportEnum.USER.getValue()+"_info");
                    if (groupDoc != null) {
                        param.put("assignee_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 assignee_id", document.get("assignee_id"));
                    }
                }
                if (document.get("requester_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("requester_id"))), Document.class, ExportEnum.USER.getValue()+"_info");
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
                        Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(followerId)), Document.class, ExportEnum.USER.getValue()+"_info");
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
                        Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(emailCc)), Document.class, ExportEnum.USER.getValue()+"_info");
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
                        Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(field.get("id"))), Document.class, ExportEnum.TICKET.getValue()+"_field");
                        if (groupDoc != null) {
                            field.put("id",groupDoc.get("newId"));
                        } else {
                            log.warn("同步ticket时,未找到 {} 对应的新 fields", field.get("id"));
                        }
                    }
                }

                if (document.get("submitter_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("submitter_id"))), Document.class, ExportEnum.USER.getValue()+"_info");
                    if (groupDoc != null) {
                        param.put("submitter_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 submitter_id", document.get("submitter_id"));
                    }
                }

                if (document.get("organization_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("organization_id"))), Document.class, ExportEnum.ORGANIZATIONS.getValue()+"_info");
                    if (groupDoc != null) {
                        param.put("organization_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 organization_id", document.get("organization_id"));
                    }
                }
                if (document.get("ticket_form_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(document.get("ticket_form_id"))), Document.class, ExportEnum.TICKET.getValue()+"_forms");
                    if (groupDoc != null) {
                        param.put("ticket_form_id", groupDoc.get("newId"));
                    } else {
                        log.warn("同步ticket时,未找到 {} 对应的新 ticket_form_id", document.get("ticket_form_id"));
                    }
                }

                if (document.get("comments") != null) {
                    List<Document> comments = param.getList("comments", Document.class);
                    for (Document comment : comments) {
                        List<String> tokenList = new ArrayList<>();
                        //映射新id
                        if (comment.get("author_id") != null) {
                            Document authorDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(comment.get("author_id"))), Document.class, ExportEnum.USER.getValue()+"_info");
                            if (authorDoc != null) {
                                comment.put("author_id", authorDoc.get("newId"));
                            } else {
                                log.warn("同步ticket时,未找到 {} 对应的新 author_id", comment.get("author_id"));
                            }
                        }

                        // 附件相关
                        if (comment.get("attachments") != null){
                            List<Document> attachments = comment.getList("attachments", Document.class);
                            for (Document attachment : attachments) {
                                String url = (String) attachment.get("mapped_content_url");
                                String filename = (String) attachment.get("file_name");
                                String type = (String) attachment.get("content_type");
                                File file = downloadFile(url, filename);
                                JSONObject jsonObject = doPost("/api/v2/uploads", type, file);
                                if (!file.delete()){
                                    log.warn("临时文件：{}删除失败",file.getAbsolutePath());
                                }
                                tokenList.add(jsonObject.getJSONObject("upload").getString("token"));
                            }
                            comment.put("uploads",tokenList);
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
                requestParam.put("ticket", param);
                request = this.doPost("/api/v2/imports/tickets", requestParam);
            } catch (Exception e) {
                e.printStackTrace();
            }

            log.info("importTicketInfo 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            saveImportInfo("importTicketInfo", request);
            mongoTemplate.save(document, ExportEnum.TICKET.getValue()+"_info");
        }
        log.info("导入ticker_info成功，一共导出{}条记录",list.size());
        saveTask.setEndTime(TimeUtil.getTime());
        saveTask.setUseTime(System.currentTimeMillis() - startTime);
        saveTask.setStatus(2);
        mongoTemplate.save(saveTask);

    }

    public File downloadFile(String url,String filename){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        // todo  临时目录
        File dir = new File(filePath);
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



    @Override
    public void exportTicketFields() {
        TaskInfo exportUserInfo = saveTaskInfo("exportTicketFields");
        Long useTime = doExport("/api/v2/ticket_fields", "ticket_fields", ExportEnum.TICKET.getValue() + "_field");
        exportUserInfo.setEndTime(TimeUtil.getTime());
        exportUserInfo.setUseTime(useTime);
        exportUserInfo.setStatus(2);
        mongoTemplate.save(exportUserInfo);

    }

    @Override
    public void importTicketFields() {
        TaskInfo saveTask = saveTaskInfo("importTicketFields");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.TICKET.getValue()+"_field");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());

                requestParam.put("ticket_field", jsonObject);
                request = this.doPost("/api/v2/ticket_fields", requestParam);
                document.put("newId",request.getJSONObject("ticket_field").get("id"));
            }catch (Exception e){
                e.printStackTrace();
            }
            log.info("importTicketFields 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importTicketFields", request);
            mongoTemplate.save(document,ExportEnum.TICKET.getValue()+"_field");
        }
        log.info("导入 TicketFields 成功，一共导出{}条记录",list.size());
        saveTask.setEndTime(TimeUtil.getTime());
        saveTask.setUseTime(System.currentTimeMillis() - startTime);
        saveTask.setStatus(2);
        mongoTemplate.save(saveTask);
    }

    @Override
    public void exportSatisfactionRatingInfo() {

        TaskInfo exportUserInfo = saveTaskInfo("exportSatisfactionRatingInfo");
        Long useTime = doExport("/api/v2/satisfaction_ratings", "satisfaction_ratings", ExportEnum.SATISFACTION.getValue() + "_rating");
        exportUserInfo.setEndTime(TimeUtil.getTime());
        exportUserInfo.setUseTime(useTime);
        exportUserInfo.setStatus(2);
        mongoTemplate.save(exportUserInfo);

    }

    @Override
    public void importSatisfactionRatingInfo() {

        // todo  需要绑定ticket id

    }




}
