package com.whaleal.zendesk.service;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author lyz
 * @desc
 * @create: 2023-10-11 15:35
 **/
@Component
public abstract class BaseExportService {

    @Resource
    public MongoTemplate mongoTemplate;
    @Value("${zendesk.source.domain}")
    private String sourceDomain;
    @Value("${zendesk.source.username}")
    private String sourceUsername;
    @Value("${zendesk.source.password}")
    private String sourcePassword;

    @Value("${zendesk.target.domain}")
    private String targetDomain;
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
        //拼接源端域名与接口路径
        String realPath = sourceDomain + url;

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

        JSONObject requestParam = new JSONObject();
        requestParam.put("item", param);
        //拼接源端域名与接口路径
        String realPath = targetDomain + url;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(realPath)
                .newBuilder();
        RequestBody creatBody = RequestBody.create(MediaType.parse("application/json"), requestParam.toString());
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
            string=creatResponse.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(string);
    }

}
