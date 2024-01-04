package com.whaleal.zendesk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.util.StringSub;
import okhttp3.*;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.io.File;
import java.util.stream.Collectors;


public class ZendeskApplicationDelete {

    @Resource
    MongoTemplate mongoTemplate;

    @Test
//[26951221594393, 26955077864729, 26955409739545, 26954789115673, 26955628191769, 26955537428761, 26955180523545, 26955825727769, 26955326868249, 26955547724825, 26955331128345, 26955769629977, 26954978618649, 26955136959129, 26955176231577, 26955725424537, 26955370746265, 26955648297113, 26951535984793, 26955255612313, 26955466574489, 26955305096089, 26955706771865, 26955558409369, 26951668988569, 26955569370649, 26955582683929, 26955341064473, 26955388718361, 26955284254233, 26955425227033, 26955538346777, 26955633376281, 26955725455897, 26955810259993, 26955466605209, 26955350608793, 26955713819545, 26955624625305, 26955199708569, 26955286485401, 26955769694105, 26955205956505, 26955233037593, 26955279246105, 26955558498073, 26955475077657, 26955569398809, 26955320699161, 26955143360537, 26955154436889, 26955331384345, 26955769757977, 26955538632089, 26955716372889, 26955316473241, 26955740165017, 26955845226137, 26955371010201, 26955394809753, 26955617005721, 26955663341209, 26955281607193, 26955137106201, 26955167855897, 26955206275609, 26955394825753, 26955524069913, 26955345288217, 26955706859929, 26955279306393, 26955671084441, 26955336591001, 26955716440729, 26955852626841, 26955762908825, 26955364852633, 26955617051033, 26955603561625, 26955451067545, 26955547799193, 26955320792089, 26955731705369, 26955543948057, 26955239930393, 26955358229273, 26955877545241, 26955575167001, 26955137194521, 26955286747929, 26955624726553, 26955389067033, 26955255717785, 26955633475737, 26955475148697, 26955706884249, 26955561858969, 26955154489753, 26955394879385, 26955716465305]
    void macro_list_and_update() {
        String sourceUrl = "https://jingmu.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/macros/active")
                .newBuilder();
        //      .addQueryParameter("access", "personal")
        //		.addQueryParameter("active", "true")
        //		.addQueryParameter("category", "25")
        //		.addQueryParameter("group_id", "25")
        //		.addQueryParameter("include", "usage_7d")
        //		.addQueryParameter("only_viewable", "false")
        //		.addQueryParameter("sort_by", "alphabetical")
        //		.addQueryParameter("sort_order", "asc");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("2694445233@qq.com", "123456"))
                .build();
        JSONObject test = null;
        try {
            Response response = client.newCall(request).execute();
            String output = response.body().string();
            test = JSON.parseObject(output);
            JSONArray macros = test.getJSONArray("macros");
            List<Long> idList = new ArrayList<>();
            for (Object macroObj : macros) {
                JSONObject macro = (JSONObject) macroObj;
                idList.add(macro.getLong("id"));
                macro.put("active", "false");
            }
            test.put("macros", macros);
            System.out.println(idList);
            System.out.println("==========================");
            //System.out.println(response.body().string());
            //System.out.println(response);
            //System.out.println(response.code());
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //update
        OkHttpClient client2 = new OkHttpClient();
        HttpUrl.Builder urlBuilder2 = HttpUrl.parse("https://jingmu.zendesk.com/api/v2/macros/update_many")
                .newBuilder();

        RequestBody body2 = RequestBody.create(MediaType.parse("application/json"), test.toString());

        Request request2 = new Request.Builder()
                .url(urlBuilder2.build())
                .method("PUT", body2)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("2694445233@qq.com", "123456"))
                .build();
        try {
            Response response = client2.newCall(request2).execute();
            System.out.println("==========================");
            System.out.println(response.body().string());
            System.out.println(response);
            System.out.println(response.code());
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        };
    }


    @Test
    void macro_list(){
        String sourceUrl = "https://jingmu.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl +"/api/v2/macros")
                .newBuilder();
        //      .addQueryParameter("access", "personal")
        //		.addQueryParameter("active", "true")
        //		.addQueryParameter("category", "25")
        //		.addQueryParameter("group_id", "25")
        //		.addQueryParameter("include", "usage_7d")
        //		.addQueryParameter("only_viewable", "false")
        //		.addQueryParameter("sort_by", "alphabetical")
        //		.addQueryParameter("sort_order", "asc");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("2694445233@qq.com", "123456"))
                .build();
        try {
            Response response = client.newCall(request).execute();
            String output = response.body().string();
            JSONObject test = JSON.parseObject(output);
            JSONArray macros = test.getJSONArray("macros");
            List<Long> idList = new ArrayList<>();
            for (Object macroObj : macros) {
                JSONObject macro = (JSONObject) macroObj;
                idList.add(macro.getLong("id"));
            }
            String outputFormatter = idList.toString();
            outputFormatter = outputFormatter.replace("[","").replace("]","");
            System.out.println(outputFormatter);
            System.out.println("==========================");
            //System.out.println(response.body().string());
            //System.out.println(response);
            //System.out.println(response.code());
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void delete_macros() {
        OkHttpClient client = new OkHttpClient();
        String value = "26955475846937, 26955395439385, 26955085523993, 26951638929945, 26955726608921, 26955297086233, 26951567173401, 26955156861465, 26951254393625, 26954777489177, 26955030203289, 26955583721881, 26955584122521, 26955729230233, 26955732441497, 26955684536985, 26955599547545, 26955878342297, 26955256151705, 26955168343449, 26955643825177, 26955743656473, 26955022178585, 26955078624025, 26955771541017, 26955460195481, 26955826869657, 26955592841881, 26955297151129, 26955138030233, 26954777587609, 26951520587161, 26955548697241, 26951649371545, 26955681474201, 26955168395417, 26955270107545, 26955700485273, 26955419541401, 26951654455065, 26955771623449, 26955583802137, 26955264260889, 26954814686489, 26955643851289, 26955846358297, 26955341942553, 26955460236313, 26951551807001, 26955085640729, 26955155175449, 26955681498777, 26955729288857, 26955707793305, 26955436671129, 26955002235545, 26955548741017, 26955128397209, 26955138139673, 26955700570393, 26955771680793, 26955447410713, 26951255991321, 26955089073945, 26955583825945, 26954799820313, 26955425820953, 26955030445849, 26955264284697, 26955672050201, 26955578015257, 26955826922905, 26951567285401, 26951638988697, 26955200612505, 26955629364377, 26955297253273, 26955776848025";
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://jingmu.zendesk.com/api/v2/macros/destroy_many")
                .newBuilder()
                .addQueryParameter("ids", value);
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("DELETE", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("2694445233@qq.com", "123456"))
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("==========================");
            System.out.println(response.body().string());
            System.out.println(response);
            System.out.println(response.code());
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }
}


