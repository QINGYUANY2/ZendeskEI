package com.whaleal.zendesk.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.TaskEnum;
import com.whaleal.zendesk.model.ImportInfo;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.util.StringSub;
import com.whaleal.zendesk.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lyz
 * @desc
 * @create: 2023-10-11 15:35
 **/
@Slf4j
@Component
public abstract class BaseExportService {

    @Value("${zendesk.filePath}")
    public String filePath;

    @Resource
    public MongoTemplate mongoTemplate;
    @Value("${zendesk.source.domain}")
    public String sourceDomain;
    @Value("${zendesk.target.domain}")
    public String targetDomain;
    @Value("${zendesk.source.username}")
    private String sourceUsername;
    @Value("${zendesk.source.password}")
    private String sourcePassword;
    @Value("${zendesk.target.username}")
    private String targetUsername;
    @Value("${zendesk.target.password}")
    private String targetPassword;

    private OkHttpClient sourceClient;
    private OkHttpClient targetClient;


    private String sourceBasic;
    private String targetBasic;

    @PostConstruct
    private void init() {
        this.sourceClient = new OkHttpClient();
        this.targetClient = new OkHttpClient();
        sourceBasic = Credentials.basic(sourceUsername, sourcePassword);
        targetBasic = Credentials.basic(targetUsername, targetPassword);
    }

    public JSONObject doGet(String url, Map<String, String> param) {
        JSONObject jsonObject = null;
        try {
            Response response = doGet(sourceDomain, url, param);
            jsonObject = JSONObject.parseObject(response.body().string());
            if (response.code() == 429) {
                //API调用达到上线 就等待一下
                Thread.sleep(1000);
                response = doGet(sourceDomain, url, param);
                jsonObject = JSONObject.parseObject(response.body().string());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    public Response doGet(String domain, String url, Map<String, String> param) {
        //拼接源端域名与接口路径
        String realPath = domain + url;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(realPath)
                .newBuilder();
        //如果携带参数了 就拼接参数
        if (!param.isEmpty()) {
            param.entrySet().forEach(item -> {
                urlBuilder.addQueryParameter(item.getKey(), item.getValue());
            });
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", sourceBasic)
                .build();

        Response response;
        String string;
        try {
            response = sourceClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }


    public JSONObject doPost(String url, JSONObject param) {

        JSONObject jsonObject = null;
        try {
            Response response = doPost(targetDomain, url, param);
            System.out.println(response.body().string());
            System.out.println(response.code());
            jsonObject = JSONObject.parseObject(response.body().string());

            if (response.code() == 429) {
                //API调用达到上线 就等待一下
                Thread.sleep(1000);
                response = doPost(targetDomain, url, param);
                jsonObject = JSONObject.parseObject(response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;

    }


    public Response doPost(String domain, String url, JSONObject param) {

        //拼接源端域名与接口路径
        String realPath = domain + url;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(realPath)
                .newBuilder();
        RequestBody creatBody = RequestBody.create(MediaType.parse("application/json"), param.toString());
        Request creatRequest = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", creatBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(targetUsername, targetPassword))
                .build();
        Response creatResponse = null;
        String string = null;
        try {
            creatResponse = targetClient.newCall(creatRequest).execute();
//            string = creatResponse.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return creatResponse;
    }


    public JSONObject doPost(String url, String type, File file) {

        //拼接源端域名与接口路径
        String realPath = targetDomain + url + "?filename=" + file.getName();
        System.out.println("realPath:" + realPath + "   type:" + type);
        // 构建请求体
        RequestBody requestBody = RequestBody.create(MediaType.parse(type), file);


        // 构建请求
        Request request = new Request.Builder()
                .url(realPath)
                .header("Authorization", Credentials.basic(targetUsername, targetPassword))  // 替换成实际的 base64 编码后的 email 和密码
                .post(requestBody)
                .build();
        String string = null;
        try {
            Response creatResponse = targetClient.newCall(request).execute();
            string = creatResponse.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(string);
    }


    public void doImport(String param, String path, String coll) {
        // todo  后期添加分页 以防过大
        Criteria criteria = new Criteria();
        criteria.and("domain").is(StringSub.getDomain(sourceDomain));
        Query query = new Query(criteria);
        List<Document> list = mongoTemplate.find(query, Document.class, coll);
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put(param, jsonObject);
                JSONObject request = this.doPost(path, requestParam);
                log.info("请求结果{}", request);
                document.put("status", 1);
            } catch (Exception e) {
                e.printStackTrace();
                document.put("status", 2);
            }
            mongoTemplate.save(document, coll);
        }
    }

    public Long doExport(String url, String param, String coll) {
        long startTime = System.currentTimeMillis();
        try {
            JSONObject request = this.doGet(url, new HashMap<>());
            JSONArray users = request.getJSONArray(param);
            List<JSONObject> list = users.toJavaList(JSONObject.class);
            for (JSONObject jsonObject : list) {
                jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
            }
            mongoTemplate.insert(list, coll);
            log.info("导出{}成功，一共导出{}条记录", coll, list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() - startTime;
    }


    public ImportInfo saveImportInfo(String type, JSONObject request) {
        ImportInfo importInfo = new ImportInfo();
        importInfo.setCreatTime(TimeUtil.getTime());
        importInfo.setSubDomain(StringSub.getDomain(this.sourceDomain));
        importInfo.setType(type);
        importInfo.setRequest(request);
        return mongoTemplate.save(importInfo, "importInfo");
    }


    public ModuleRecord beginModuleRecord(String name) {
        ModuleRecord moduleRecord = new ModuleRecord();
        moduleRecord.setModuleName(name);
        moduleRecord.setStartTime(TimeUtil.getTime());
        moduleRecord.setStatus(1);
        moduleRecord.setSourceUrl(sourceDomain);
        moduleRecord.setType(TaskEnum.NOT_STARTED.getValue());
        return mongoTemplate.save(moduleRecord);
    }

    public void endModuleRecord(ModuleRecord moduleRecord, long useTime) {
        moduleRecord.setEndTime(TimeUtil.getTime());
        moduleRecord.setDuration(useTime);
        moduleRecord.setStatus(TaskEnum.COMPLETED.getValue());
        mongoTemplate.save(moduleRecord);
    }


}
