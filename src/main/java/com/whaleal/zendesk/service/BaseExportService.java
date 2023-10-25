package com.whaleal.zendesk.service;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.util.StringSub;
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
        JSONObject jsonObject = doGet(sourceDomain, url, param);
        return jsonObject;
    }

    public JSONObject doGet(String domain, String url, Map<String, String> param) {
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
            string = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return JSONObject.parseObject(string);
    }


    public JSONObject doPost(String url, JSONObject param) {

        //拼接源端域名与接口路径
        String realPath = targetDomain + url;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(realPath)
                .newBuilder();
        RequestBody creatBody = RequestBody.create(MediaType.parse("application/json"), param.toString());
        Request creatRequest = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", creatBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(targetUsername, targetPassword))
                .build();
        Response creatResponse;
        String string = null;
        try {
            creatResponse = targetClient.newCall(creatRequest).execute();
            string = creatResponse.body().string();
            System.out.println("===========================");
            System.out.println(creatResponse);
            System.out.println("===========================");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(string);
    }

    public JSONObject doPost(String url, String type, File file) {

        //拼接源端域名与接口路径
        String realPath = targetDomain + url;
        // 构建请求体
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("filename", file.getName())
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse(type), file))
                .build();

        // 构建请求
        Request request = new Request.Builder()
                .url(realPath)
                .header("Authorization", Credentials.basic(targetUsername, targetPassword))  // 替换成实际的 base64 编码后的 email 和密码
                .post(requestBody)
                .build();

        Response creatResponse;
        String string = null;
        try {
            creatResponse = targetClient.newCall(request).execute();
            string = creatResponse.body().string();
            System.out.println("===========================");
            System.out.println(creatResponse);
            System.out.println("===========================");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(string);
    }


    public void createRelation(String url, String arrayField, String compareField, String collection) {
        JSONObject source = doGet(sourceDomain, url, new HashMap<>());
        JSONObject target = doGet(targetDomain, url, new HashMap<>());
        JSONObject result = new JSONObject();
        for (Object targetObj : target.getJSONArray(arrayField)) {
            for (Object sourceObj : source.getJSONArray(arrayField)) {
                JSONObject targetJson = JSONObject.parseObject(targetObj.toString());
                JSONObject sourceJson = JSONObject.parseObject(sourceObj.toString());
                if (targetJson.get(compareField).toString().equals(sourceJson.get(compareField).toString())) {
                    result.put("newID", targetJson.getLong("id"));
                    result.put("oldId", sourceJson.getLong("id"));
                    mongoTemplate.insert(result, collection);
                    break;
                }
            }
        }
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
                System.out.println("+++++++++++++++++++++++++++");
                System.out.println(requestParam);
                System.out.println("+++++++++++++++++++++++++++");
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


}
