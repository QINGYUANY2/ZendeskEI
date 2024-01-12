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

    String TargetUrl = "https://jinmutraining.zendesk.com";
    String TargetUsername = "user1@nqmo.com";
    String TargetPassword = "1qaz@WSX";

    @Test
//[26951221594393, 26955077864729, 26955409739545, 26954789115673, 26955628191769, 26955537428761, 26955180523545, 26955825727769, 26955326868249, 26955547724825, 26955331128345, 26955769629977, 26954978618649, 26955136959129, 26955176231577, 26955725424537, 26955370746265, 26955648297113, 26951535984793, 26955255612313, 26955466574489, 26955305096089, 26955706771865, 26955558409369, 26951668988569, 26955569370649, 26955582683929, 26955341064473, 26955388718361, 26955284254233, 26955425227033, 26955538346777, 26955633376281, 26955725455897, 26955810259993, 26955466605209, 26955350608793, 26955713819545, 26955624625305, 26955199708569, 26955286485401, 26955769694105, 26955205956505, 26955233037593, 26955279246105, 26955558498073, 26955475077657, 26955569398809, 26955320699161, 26955143360537, 26955154436889, 26955331384345, 26955769757977, 26955538632089, 26955716372889, 26955316473241, 26955740165017, 26955845226137, 26955371010201, 26955394809753, 26955617005721, 26955663341209, 26955281607193, 26955137106201, 26955167855897, 26955206275609, 26955394825753, 26955524069913, 26955345288217, 26955706859929, 26955279306393, 26955671084441, 26955336591001, 26955716440729, 26955852626841, 26955762908825, 26955364852633, 26955617051033, 26955603561625, 26955451067545, 26955547799193, 26955320792089, 26955731705369, 26955543948057, 26955239930393, 26955358229273, 26955877545241, 26955575167001, 26955137194521, 26955286747929, 26955624726553, 26955389067033, 26955255717785, 26955633475737, 26955475148697, 26955706884249, 26955561858969, 26955154489753, 26955394879385, 26955716465305]
    void macro_list_and_update() {
        String sourceUrl = TargetUrl;
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
                .addHeader("Authorization", Credentials.basic(TargetUsername, TargetPassword))
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
        HttpUrl.Builder urlBuilder2 = HttpUrl.parse(TargetUrl+"/api/v2/macros/update_many")
                .newBuilder();

        RequestBody body2 = RequestBody.create(MediaType.parse("application/json"), test.toString());

        Request request2 = new Request.Builder()
                .url(urlBuilder2.build())
                .method("PUT", body2)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(TargetUsername, TargetPassword))
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
        String sourceUrl = TargetUrl;
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
                .addHeader("Authorization", Credentials.basic(TargetUsername, TargetPassword))
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
        String value = "22355958833428, 22356055172116, 22355846244372, 22355891404308, 22356061211156, 22355949803284, 22356210310420, 22355859697940, 22355910229012, 22355777389332, 22355810496276, 22355196181268, 22300472377748, 22355826383764, 22355207861908, 22355908968596, 22356434962836, 22355883301012, 22355670508948, 22356392655764, 22355759185556, 22330910437268, 22356079476116, 22356434991124, 22356435024660, 22356472354964, 22356472388628, 22356462671764, 22355883393556, 22355971231508, 22355865852180, 22330921807124, 22355175895828, 22356032336660, 22355941247252, 22355831868180, 22355677278740, 22355810582676, 22356042327444, 22300448537748, 22355799066772, 22355749263508, 22356428427156, 22355196291988, 22355910307988, 22355865094804, 22356217028756, 22356088709524, 22355859797140, 22355787249812, 22356435670548, 22356447355028, 22356458283540, 22356473491476, 22356436118676, 22356479743636, 22356447828116, 22356473671700, 22356473708436, 22356436356884, 22356464146708, 22356436429076, 22356473924372";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(TargetUrl+"/api/v2/macros/destroy_many")
                .newBuilder()
                .addQueryParameter("ids", value);
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("DELETE", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(TargetUsername, TargetPassword))
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


    @Test
    void deleteForms(){
        //第一步拿到所有ticket_formid
        //第二步吧他们分成list
        //第三步遍历list删除
        JSONObject temp = doGetTarget("/api/v2/ticket_forms", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("ticket_forms");
        List<String> formIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            formIds.add(temps.getLong("id").toString());
        }
        for (String formId : formIds) {
            deleteForm(formId);
        }

    }

    public JSONObject doGetTarget(String url, Map<String, String> param) {
        JSONObject jsonObject = null;
        try {
            Response response = doTargetGet(TargetUrl, url, param);
            jsonObject = JSONObject.parseObject(response.body().string());
            if (response.code() == 429) {
                //API调用达到上线 就等待一下
                Thread.sleep(1000);
                response = doTargetGet(TargetUrl, url, param);
                jsonObject = JSONObject.parseObject(response.body().string());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }


    public Response doTargetGet(String domain, String url, Map<String, String> param) {
        //拼接源端域名与接口路径
        OkHttpClient targetClient = new OkHttpClient();
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
                .addHeader("Authorization", Credentials.basic(TargetUsername, TargetPassword))
                .build();

        Response response;
        String string;
        try {
            response = targetClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }


    public void deleteForm(String a){
        OkHttpClient client = new OkHttpClient();
        String value = a;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(TargetUrl+"/api/v2/ticket_forms/"+a)
                .newBuilder();
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("DELETE", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(TargetUsername, TargetPassword))
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


    @Test
    void deleteTicketField(){
        //第一步拿到所有ticket_formid
        //第二步吧他们分成list
        //第三步遍历list删除
        JSONObject temp = doGetTarget("/api/v2/ticket_fields", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("ticket_fields");
        List<String> formIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            formIds.add(temps.getLong("id").toString());
        }
        for (String formId : formIds) {
            deleteTicketField(formId);
        }

    }

    public void deleteTicketField(String a){
        OkHttpClient client = new OkHttpClient();
        String value = a;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(TargetUrl+"/api/v2/ticket_fields/"+a)
                .newBuilder();
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("DELETE", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(TargetUsername, TargetPassword))
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


    @Test
    void deleteUserField(){
        //第一步拿到所有ticket_formid
        //第二步吧他们分成list
        //第三步遍历list删除
        JSONObject temp = doGetTarget("/api/v2/user_fields/", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("user_fields");
        List<String> formIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            formIds.add(temps.getLong("id").toString());
        }
        for (String formId : formIds) {
            deleteUserField(formId);
        }

    }

    public void deleteUserField(String a){
        OkHttpClient client = new OkHttpClient();
        String value = a;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(TargetUrl+"/api/v2/user_fields/"+a)
                .newBuilder();
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("DELETE", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(TargetUsername, TargetPassword))
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


