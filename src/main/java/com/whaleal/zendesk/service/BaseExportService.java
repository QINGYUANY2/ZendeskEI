package com.whaleal.zendesk.service.impl;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author lyz
 * @desc
 * @create: 2023-10-11 15:35
 **/
@Component
public abstract class BaseExportService {

    @Value("${zendesk.source.domain}")
    private String domain;

    @Value("${zendesk.source.username}")
    private String username;

    @Value("${zendesk.source.password}")
    private String password;

    @Resource
    public MongoTemplate mongoTemplate;

    private OkHttpClient client ;


    private String basic;

    @PostConstruct
    private void init() {
        this.client = new OkHttpClient();
        basic =  Credentials.basic(username,password);
    }

    public  JSONObject doGet(String url, Map<String,String> param){
        //拼接源端域名与接口路径
        String realPath = domain + url;

        HttpUrl.Builder urlBuilder = HttpUrl.parse(realPath)
                .newBuilder();
        //如果携带参数了 就拼接参数
        if(!param.isEmpty()){
            param.entrySet().forEach(item -> {
                urlBuilder.addQueryParameter(item.getKey(),item.getValue());
            });
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", basic)
                .build();

        Response response ;
        String string;
        try {
            response = client.newCall(request).execute();
            string = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return JSONObject.parseObject(string);
    }
}
