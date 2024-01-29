package com.whaleal.zendesk;

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

//@SpringBootTest
class ZendeskApplicationTests extends BaseExportService {

    @Resource
    MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {

        String sourceUrl = "https://jinmutraining.zendesk.com";
        String sourceEmail = "user1@nqmo.com";
        String sourcePassword = "1qaz@WSX";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/users")
                .newBuilder();
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(sourceEmail, sourcePassword))
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("==========================");

            System.out.println(response.body().string());
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testFind() {

        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        String sourceEmail = "user1@yzm.de";
        String sourcePassword = "1qaz@WSX";

//        String sourceUrl = "https://jinmu1442.zendesk.com";
//        String sourceEmail = "1102361302@qq.com";
//        String sourcePassword = "123456";
        OkHttpClient client = new OkHttpClient();
//        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/tickets/72/comments")
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/macros/10535172089881/attachments")
                .newBuilder();
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(sourceEmail, sourcePassword))
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("==========================");

            System.out.println(response.body().string());
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {

        String sourceUrl = "https://jinmu1442.zendesk.com";
        String sourceEmail = "1102361302@qq.com";
        String sourcePassword = "123456";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/users/25149742565145/identities")
//        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://support.zendesk.com/api/v2/channels/voice/phone_numbers")
                .newBuilder();
        RequestBody creatBody = RequestBody.create(MediaType.parse("application/json"),
                "\"{\\\"ticket\\\":{\\\"_id\\\":{\\\"date\\\":\\\"2023-10-23 16:44:45\\\",\\\"timestamp\\\":1698050685},\\\"from_messaging_channel\\\":false,\\\"subject\\\":\\\"测试推荐\\\",\\\"email_cc_ids\\\":[11946146000281],\\\"created_at\\\":\\\"2023-07-11T03:14:43Z\\\",\\\"description\\\":\\\"推荐智能机器人\\\",\\\"custom_status_id\\\":8427025599129,\\\"via\\\":{\\\"channel\\\":\\\"web\\\",\\\"source\\\":{\\\"from\\\":{},\\\"to\\\":{}}},\\\"allow_attachments\\\":true,\\\"updated_at\\\":\\\"2023-10-23T08:04:54Z\\\",\\\"follower_ids\\\":[11946146000281],\\\"id\\\":243,\\\"assignee_id\\\":24270405632793,\\\"raw_subject\\\":\\\"测试推荐\\\",\\\"custom_fields\\\":[{\\\"id\\\":9774117095705,\\\"value\\\":\\\"zendesk\\\"},{\\\"id\\\":20662954108185},{\\\"id\\\":10787110724889},{\\\"id\\\":10655083997977},{\\\"id\\\":11603759350809},{\\\"id\\\":10655406704409},{\\\"id\\\":20872321656857},{\\\"id\\\":10655178778905},{\\\"id\\\":20545173682969},{\\\"id\\\":21666059419929},{\\\"id\\\":10787195361305},{\\\"id\\\":11419882209049},{\\\"id\\\":10533760582681},{\\\"id\\\":21117433011481},{\\\"id\\\":11603761819417},{\\\"id\\\":10655235898137},{\\\"id\\\":17352695020569},{\\\"id\\\":11719029235097},{\\\"id\\\":11410119079321},{\\\"id\\\":10652776289945},{\\\"id\\\":10655260761497},{\\\"id\\\":11410078524313},{\\\"id\\\":11719064129433},{\\\"id\\\":10655290792345},{\\\"id\\\":10655239089817},{\\\"id\\\":19756886178457},{\\\"id\\\":10076189080985,\\\"value\\\":\\\"s3\\\"},{\\\"id\\\":21312612516249},{\\\"id\\\":20873520728473},{\\\"id\\\":21907692703897},{\\\"id\\\":11718915445145},{\\\"id\\\":11718951228825},{\\\"id\\\":10655155019161},{\\\"id\\\":21115427350425},{\\\"id\\\":20659931375769},{\\\"id\\\":10680308591769},{\\\"id\\\":10655181829017}],\\\"allow_channelback\\\":false,\\\"satisfaction_rating\\\":{\\\"score\\\":\\\"good\\\"},\\\"submitter_id\\\":11942258898585,\\\"collaborator_ids\\\":[11946146000281],\\\"url\\\":\\\"https://pdi-jinmuinfo.zendesk.com/api/v2/tickets/72.json\\\",\\\"tags\\\":[\\\"has_new_attachment\\\",\\\"s3\\\",\\\"zendesk\\\",\\\"文章推荐\\\"],\\\"brand_id\\\":24359725926041,\\\"ticket_form_id\\\":8427015306521,\\\"sharing_agreement_ids\\\":[],\\\"group_id\\\":24269983601177,\\\"organization_id\\\":11456749118233,\\\"followup_ids\\\":[],\\\"domain\\\":\\\"pdi-jinmuinfo\\\",\\\"is_public\\\":true,\\\"comments\\\":[{\\\"audit_id\\\":20530199901465,\\\"metadata\\\":{\\\"system\\\":{\\\"latitude\\\":\\\"37.5112\\\",\\\"client\\\":\\\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36\\\",\\\"location\\\":\\\"South Korea\\\",\\\"ip_address\\\":\\\"210.87.195.109\\\",\\\"longitude\\\":\\\"126.9741\\\"},\\\"custom\\\":{}},\\\"attachments\\\":[],\\\"public\\\":true,\\\"html_body\\\":\\\"<div class=\\\\\\\"zd-comment\\\\\\\" dir=\\\\\\\"auto\\\\\\\">推荐智能机器人<br>&nbsp;<br>&nbsp;<br></div>\\\",\\\"plain_body\\\":\\\"推荐智能机器人\\\\n&nbsp;\\\\n&nbsp;\\\",\\\"created_at\\\":\\\"2023-07-11T03:14:43Z\\\",\\\"id\\\":20530199901593,\\\"type\\\":\\\"Comment\\\",\\\"author_id\\\":24270405632793,\\\"body\\\":\\\"推荐智能机器人\\\",\\\"via\\\":{\\\"channel\\\":\\\"web\\\",\\\"source\\\":{\\\"from\\\":{},\\\"to\\\":{\\\"address\\\":\\\"user1@yzm.de\\\",\\\"email_ccs\\\":[11946146000281],\\\"name\\\":\\\"Agent No.1\\\"}}}},{\\\"audit_id\\\":20754202162329,\\\"metadata\\\":{\\\"system\\\":{\\\"latitude\\\":\\\"22.2842\\\",\\\"client\\\":\\\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36\\\",\\\"location\\\":\\\"Hong Kong, HCW, Hong Kong\\\",\\\"ip_address\\\":\\\"43.154.94.19\\\",\\\"longitude\\\":\\\"114.1759\\\"},\\\"custom\\\":{}},\\\"attachments\\\":[],\\\"public\\\":true,\\\"html_body\\\":\\\"<div class=\\\\\\\"zd-comment\\\\\\\" dir=\\\\\\\"auto\\\\\\\"><a rel=\\\\\\\"noopener noreferrer\\\\\\\" href=\\\\\\\"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/articles/20360171943193\\\\\\\">测试文章</a><br></div>\\\",\\\"plain_body\\\":\\\"测试文章\\\",\\\"created_at\\\":\\\"2023-07-17T07:34:01Z\\\",\\\"id\\\":20754218851993,\\\"type\\\":\\\"Comment\\\",\\\"author_id\\\":11942258898585,\\\"body\\\":\\\"测试文章\\\",\\\"via\\\":{\\\"channel\\\":\\\"web\\\",\\\"source\\\":{\\\"from\\\":{},\\\"to\\\":{}}}},{\\\"audit_id\\\":20754255032985,\\\"metadata\\\":{\\\"system\\\":{\\\"latitude\\\":\\\"22.2842\\\",\\\"client\\\":\\\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36\\\",\\\"location\\\":\\\"Hong Kong, HCW, Hong Kong\\\",\\\"ip_address\\\":\\\"43.154.94.19\\\",\\\"longitude\\\":\\\"114.1759\\\"},\\\"custom\\\":{}},\\\"attachments\\\":[],\\\"public\\\":true,\\\"html_body\\\":\\\"<div class=\\\\\\\"zd-comment\\\\\\\" dir=\\\\\\\"auto\\\\\\\">这是一个测试文章<br></div>\\\",\\\"plain_body\\\":\\\"这是一个测试文章\\\",\\\"created_at\\\":\\\"2023-07-17T07:34:50Z\\\",\\\"id\\\":20754291237145,\\\"type\\\":\\\"Comment\\\",\\\"author_id\\\":11942258898585,\\\"body\\\":\\\"这是一个测试文章\\\",\\\"via\\\":{\\\"channel\\\":\\\"web\\\",\\\"source\\\":{\\\"from\\\":{},\\\"to\\\":{}}}},{\\\"audit_id\\\":24362751711897,\\\"metadata\\\":{\\\"system\\\":{\\\"latitude\\\":\\\"31.2222\\\",\\\"client\\\":\\\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36\\\",\\\"location\\\":\\\"Shanghai, SH, China\\\",\\\"ip_address\\\":\\\"180.164.96.222\\\",\\\"longitude\\\":\\\"121.4581\\\"},\\\"custom\\\":{}},\\\"attachments\\\":[],\\\"public\\\":true,\\\"html_body\\\":\\\"<div class=\\\\\\\"zd-comment\\\\\\\" dir=\\\\\\\"auto\\\\\\\"><img style=\\\\\\\"width: 300px\\\\\\\" src=\\\\\\\"https://media1.giphy.com/media/v1.Y2lkPTA3YzM0Y2I2NGR5cW8zbDI4ZnVvc24wb2VxeXB0YzgydW93M24yc2xzdmczdWpndCZlcD12MV9naWZzX3RyZW5kaW5nJmN0PWc/EZICHGrSD5QEFCxMiC/giphy.gif\\\\\\\"><br>via <a rel=\\\\\\\"noopener noreferrer\\\\\\\" href=\\\\\\\"https://giphy.com/streamonmax/\\\\\\\">Max</a> on <a rel=\\\\\\\"noopener noreferrer\\\\\\\" href=\\\\\\\"https://giphy.com/gifs/HBOMax-tom-and-jerry-hbomax-n-EZICHGrSD5QEFCxMiC\\\\\\\">GIPHY</a><br>&nbsp;<br></div>\\\",\\\"plain_body\\\":\\\"via Max on GIPHY\\\\n&nbsp;\\\",\\\"created_at\\\":\\\"2023-10-23T08:04:21Z\\\",\\\"id\\\":24362783161241,\\\"type\\\":\\\"Comment\\\",\\\"author_id\\\":11942258898585,\\\"body\\\":\\\" ![](https://media1.giphy.com/media/v1.Y2lkPTA3YzM0Y2I2NGR5cW8zbDI4ZnVvc24wb2VxeXB0YzgydW93M24yc2xzdmczdWpndCZlcD12MV9naWZzX3RyZW5kaW5nJmN0PWc/EZICHGrSD5QEFCxMiC/giphy.gif)\\\\nvia Max on GIPHY\\\",\\\"via\\\":{\\\"channel\\\":\\\"web\\\",\\\"source\\\":{\\\"from\\\":{},\\\"to\\\":{}}}},{\\\"audit_id\\\":24362776528153,\\\"metadata\\\":{\\\"system\\\":{\\\"latitude\\\":\\\"31.2222\\\",\\\"client\\\":\\\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36\\\",\\\"location\\\":\\\"Shanghai, SH, China\\\",\\\"ip_address\\\":\\\"180.164.96.222\\\",\\\"longitude\\\":\\\"121.4581\\\"},\\\"custom\\\":{}},\\\"attachments\\\":[{\\\"mapped_content_url\\\":\\\"https://pdi-jinmuinfo.zendesk.com/attachments/token/iBhm1Ll3Nf601WwEKoa1THmQ7/?name=20210719150601_4401e.jpg\\\",\\\"malware_access_override\\\":false,\\\"file_name\\\":\\\"20210719150601_4401e.jpg\\\",\\\"malware_scan_result\\\":\\\"malware_not_found\\\",\\\"url\\\":\\\"https://pdi-jinmuinfo.zendesk.com/api/v2/attachments/24362754795289.json\\\",\\\"deleted\\\":false,\\\"content_type\\\":\\\"image/jpeg\\\",\\\"size\\\":65229,\\\"inline\\\":false,\\\"width\\\":1000,\\\"content_url\\\":\\\"https://pdi-jinmuinfo.zendesk.com/attachments/token/iBhm1Ll3Nf601WwEKoa1THmQ7/?name=20210719150601_4401e.jpg\\\",\\\"id\\\":24362754795289,\\\"thumbnails\\\":[{\\\"mapped_content_url\\\":\\\"https://pdi-jinmuinfo.zendesk.com/attachments/token/FYlv18f7NCu6l4yAoCdaulC4I/?name=20210719150601_4401e_thumb.jpg\\\",\\\"malware_access_override\\\":false,\\\"file_name\\\":\\\"20210719150601_4401e_thumb.jpg\\\",\\\"malware_scan_result\\\":\\\"malware_not_found\\\",\\\"url\\\":\\\"https://pdi-jinmuinfo.zendesk.com/api/v2/attachments/24362786382489.json\\\",\\\"deleted\\\":false,\\\"content_type\\\":\\\"image/jpeg\\\",\\\"size\\\":3080,\\\"inline\\\":false,\\\"width\\\":80,\\\"content_url\\\":\\\"https://pdi-jinmuinfo.zendesk.com/attachments/token/FYlv18f7NCu6l4yAoCdaulC4I/?name=20210719150601_4401e_thumb.jpg\\\",\\\"id\\\":24362786382489,\\\"height\\\":80}],\\\"height\\\":1000}],\\\"public\\\":true,\\\"html_body\\\":\\\"<div class=\\\\\\\"zd-comment\\\\\\\" dir=\\\\\\\"auto\\\\\\\">jpg<br></div>\\\",\\\"plain_body\\\":\\\"jpg\\\",\\\"created_at\\\":\\\"2023-10-23T08:04:54Z\\\",\\\"id\\\":24362776528281,\\\"type\\\":\\\"Comment\\\",\\\"author_id\\\":11942258898585,\\\"body\\\":\\\"jpg\\\",\\\"via\\\":{\\\"channel\\\":\\\"web\\\",\\\"source\\\":{\\\"from\\\":{},\\\"to\\\":{}}}}],\\\"has_incidents\\\":false,\\\"fields\\\":[{\\\"id\\\":9774117095705,\\\"value\\\":\\\"zendesk\\\"},{\\\"id\\\":20662954108185},{\\\"id\\\":10787110724889},{\\\"id\\\":10655083997977},{\\\"id\\\":11603759350809},{\\\"id\\\":10655406704409},{\\\"id\\\":20872321656857},{\\\"id\\\":10655178778905},{\\\"id\\\":20545173682969},{\\\"id\\\":21666059419929},{\\\"id\\\":10787195361305},{\\\"id\\\":11419882209049},{\\\"id\\\":10533760582681},{\\\"id\\\":21117433011481},{\\\"id\\\":11603761819417},{\\\"id\\\":10655235898137},{\\\"id\\\":17352695020569},{\\\"id\\\":11719029235097},{\\\"id\\\":11410119079321},{\\\"id\\\":10652776289945},{\\\"id\\\":10655260761497},{\\\"id\\\":11410078524313},{\\\"id\\\":11719064129433},{\\\"id\\\":10655290792345},{\\\"id\\\":10655239089817},{\\\"id\\\":19756886178457},{\\\"id\\\":10076189080985,\\\"value\\\":\\\"s3\\\"},{\\\"id\\\":21312612516249},{\\\"id\\\":20873520728473},{\\\"id\\\":21907692703897},{\\\"id\\\":11718915445145},{\\\"id\\\":11718951228825},{\\\"id\\\":10655155019161},{\\\"id\\\":21115427350425},{\\\"id\\\":20659931375769},{\\\"id\\\":10680308591769},{\\\"id\\\":10655181829017}],\\\"status\\\":0,\\\"requester_id\\\":11942258898585}}\");\n"
                );

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", creatBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(sourceEmail, sourcePassword))
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
    }

    @Test
    void test2() {
        List<Document> documentList = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("status").is(2)), Document.class, "user_info");
        JSONObject request = null;
        for (Document users : documentList) {
            System.out.println("====================");
            users.remove("custom_role_id");
            System.out.println(users);
            System.out.println("====================");
            try {
                JSONObject jsonObject = JSONObject.parseObject(users.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("user", jsonObject);
                request = doPost("/api/v2/users", requestParam);
                users.put("status", 1);
                users.put("newId", request.getJSONObject("user").get("id"));

            } catch (Exception e) {
                e.printStackTrace();
                users.put("status", 2);
            }
            System.out.println("_____________________");
            System.out.println(request);
            System.out.println("_____________________");
            mongoTemplate.save(users, "user_info");
        }

    }


    @Test
    void test33() {
        List<Document> documentList = mongoTemplate.find(new Query(new Criteria("brand_id").is(10184545635097L)), Document.class, "ticket_info");
//
//        for (Document document : documentList) {
//
//            Document temp = new Document();
//            temp.putAll(document);
//            List<Document> comments = temp.getList("comments", Document.class);
//            for (Document comment : comments) {
//
//            }
//
//        }

        System.out.println("+++++++++++++++");
        System.out.println(documentList);
        System.out.println("+++++++++++++++");


    }





    @Test
    void test11111() {
//        {"score": null, "satisfaction_rating.score": null}
        Document document = new Document();

        Document temp = new Document();


        temp.put("core", 111);
        temp.put("satisfaction_rating.score", 11111);
        document.put("id", 1);
        document.put("score", temp);


        Document obj = (Document) document.get("score");

        Document demo = new Document(document);
        demo.put("id", 2);
        System.out.println(document);
        System.out.println(demo);

//        obj.put("core",222);


    }


    @Test
    void demo(){

        for (int i = 0; i < 2; i++) {
            try {
                int  i1 = 1/0;
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("内部");
        }
        System.out.println("外部");
    }


    @Test
    void demo1111(){
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://www.googleapis.com/auth/cloud-platform")
                .newBuilder();
        RequestBody creatBody = RequestBody.create(MediaType.parse("application/json"),
                        "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", creatBody)
                .addHeader("Content-Type", "application/json")
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


    }


    @Test
    void demo1112(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/tickets/42/satisfaction_rating")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"satisfaction_rating\": {\"score\": \"good\", \"comment\": \"Awesome support.\"}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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

    }
//----------------------------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------------------
// Author: 杨清元
// 测试所有GET POST

    @Test
    //打印所有items并获取id后面用
    //结果为空    {"items":[],"next_page":null,"previous_page":null,"count":0}
    void demo110(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/dynamic_content/items/show_many")
                .newBuilder()
                .addQueryParameter("identifiers", "item1,item2");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }


    //导入动态内容主数据   用户信息批量导入人员模块
    //https://developer.zendesk.com/api-reference/ticketing/ticket-management/dynamic_content/#create-item
    //测试结果：{"item":{"url":"https://jinmutraining.zendesk.com/api/v2/dynamic_content/items/21858416949780.json","id":21858416949780,"name":"Snowboard Problem3","placeholder":"{{dc.snowboard_problem3}}","default_locale_id":16,"outdated":false,"created_at":"2023-12-14T02:40:55Z","updated_at":"2023-12-14T02:40:55Z","variants":[{"url":"https://jinmutraining.zendesk.com/api/v2/dynamic_content/items/21858416949780/variants/21858406347284.json","id":21858406347284,"content":"Este es mi contenido dinámico en español","locale_id":2,"outdated":false,"active":true,"default":false,"created_at":"2023-12-14T02:40:55Z","updated_at":"2023-12-14T02:40:55Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/dynamic_content/items/21858416949780/variants/21858433417876.json","id":21858433417876,"content":"Voici mon contenu dynamique en français","locale_id":16,"outdated":false,"active":true,"default":true,"created_at":"2023-12-14T02:40:55Z","updated_at":"2023-12-14T02:40:55Z"}]}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/dynamic_content/items}
    //201
    //发现：不能重复post同一个name，会显示name重复error
    //{"error":"RecordInvalid","description":"Record validation errors","details":{"标题：":[{"description":"标题： 已被使用"}],"占位符：":[{"description":"占位符：  {{dc.snowboard_problem3}} 已经被使用"}]}}
    //Response{protocol=h2, code=422, message=, url=https://jinmutraining.zendesk.com/api/v2/dynamic_content/items}
    //422
    @Test
    void demo111(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/dynamic_content/items")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"item\": {\"name\": \"Snowboard Problem3\", \"default_locale_id\": 16, \"variants\": [{\"locale_id\": 16, \"default\": true, \"content\": \"Voici mon contenu dynamique en français\"}, {\"locale_id\": 2, \"default\": false, \"content\": \"Este es mi contenido dinámico en español\"}]}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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

    }


    //导入用户信息    用户信息导入人员模块
    //https://developer.zendesk.com/api-reference/ticketing/users/user_identities/#create-identity
    //{"job_status":{"id":"V3-45c54376286af5f39a91663220a135c2","job_type":"Bulk Create User","url":"https://jinmutraining.zendesk.com/api/v2/job_statuses/V3-45c54376286af5f39a91663220a135c2.json","total":2,"progress":null,"status":"queued","message":null,"results":null}}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/users/create_many}
    //200
    //与上面不一样，不管post几遍都是200success
    @Test
    void demo121(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/users")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"user\": {\"name\": \"Roger Wilco\", \"email\": \"roge@example.org\", \"role\": \"end-user\", \"custom_role_id\": null}}"
        );

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //导入用户信息   用户信息导入人员模块
    //https://developer.zendesk.com/api-reference/ticketing/users/user_identities/#create-identity
    //每次要传入不同email不然会报错400email已被占用
    //成功时返回：
    //{"identity":{"url":"https://jinmutraining.zendesk.com/api/v2/users/10332801830548/identities/21858583652372.json","id":21858583652372,"user_id":10332801830548,"type":"email","value":"123@asd.com","verified":false,"primary":false,"created_at":"2023-12-14T02:50:16Z","updated_at":"2023-12-14T02:50:16Z","undeliverable_count":0,"deliverable_state":"deliverable"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/users/10332801830548/identities}
    //201
    @Test
    void demo122(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/users/10332801830548/identities")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                //"{\"identity\": {\"type\": \"email\", \"value\": \"foo@bar.com\"}}"
                "{\"identity\": {\"type\": \"email\", \"value\": \"123@asd.com\"}}"
        );

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //get user后面使用，保存在本地文件user
    @Test
    void user1list(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = " https://pdi-jinmuinfo.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/users")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }

//{"identities":[{"url":"https://jingmu.zendesk.com/api/v2/users/26906349711897/identities/26906352617497.json","id":26906352617497,"user_id":26906349711897,"type":"email","value":"2694445233@qq.com","verified":true,"primary":true,"created_at":"2023-12-27T08:18:57Z","updated_at":"2024-01-02T07:22:18Z","undeliverable_count":0,"deliverable_state":"deliverable"},{"url":"https://jingmu.zendesk.com/api/v2/users/26906349711897/identities/27112224858777.json","id":27112224858777,"user_id":26906349711897,"type":"email","value":"user1@yzm.de","verified":true,"primary":false,"created_at":"2024-01-02T05:13:56Z","updated_at":"2024-01-02T07:22:18Z","undeliverable_count":0,"deliverable_state":"deliverable"},{"url":"https://jingmu.zendesk.com/api/v2/users/26906349711897/identities/27118670032537.json","id":27118670032537,"user_id":26906349711897,"type":"email","value":"2622799675@qq.com","verified":true,"primary":false,"created_at":"2024-01-02T07:28:16Z","updated_at":"2024-01-02T07:29:02Z","undeliverable_count":0,"deliverable_state":"deliverable"}],"next_page":null,"previous_page":null,"count":3}
    @Test
    void Update_brands(){
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("/api/v2/brands/{brand_id}")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "    \"identity\":{\n" +
                        "        \"primary\" : \"true\"\n" +
                        "    }\n" +
                        "}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("PUT", body)
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
    }


    //get user后面使用，保存在本地文件user
    //{"user_fields":[{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/21858734723860.json","id":21858734723860,"type":"text","key":"support_description1","title":"Support description","description":"This field describes the support plan this user has","raw_title":"Support description","raw_description":"This field describes the support plan this user has","position":0,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-12-14T02:59:04Z","updated_at":"2023-12-14T02:59:04Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/21857933470100.json","id":21857933470100,"type":"text","key":"support_description","title":"Support description","description":"This field describes the support plan this user has","raw_title":"Support description","raw_description":"This field describes the support plan this user has","position":1,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-12-14T02:14:17Z","updated_at":"2023-12-14T02:59:04Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/20062343242900.json","id":20062343242900,"type":"text","key":"edu_demo_employee_title","title":"Edu Demo-Employee Title 职称","description":"","raw_title":"Edu Demo-Employee Title 职称","raw_description":"","position":2,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-10-16T07:51:22Z","updated_at":"2023-12-14T02:59:04Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/20062301563540.json","id":20062301563540,"type":"text","key":"edu_demo_staff_id","title":"Edu Demo-Staff ID 员工号","description":"","raw_title":"Edu Demo-Staff ID 员工号","raw_description":"","position":3,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-10-16T07:51:22Z","updated_at":"2023-12-14T02:59:04Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/20062343276692.json","id":20062343276692,"type":"dropdown","key":"edu_demo_employment_type","title":"Edu Demo-Employment Type 员工类型","description":"","raw_title":"Edu Demo-Employment Type 员工类型","raw_description":"","position":4,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-10-16T07:51:23Z","updated_at":"2023-12-14T02:59:04Z","custom_field_options":[{"id":20062310514708,"name":"Full Time 全职","raw_name":"Full Time 全职","value":"full_time_全职"},{"id":20062310514836,"name":"Part Time 兼职","raw_name":"Part Time 兼职","value":"part_time_兼职"},{"id":20062310514964,"name":"Temporary 临时","raw_name":"Temporary 临时","value":"temporary_临时"},{"id":20062310515092,"name":"Other 其他","raw_name":"Other 其他","value":"other_其他"}]},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/20062301594772.json","id":20062301594772,"type":"dropdown","key":"edu_demo_department","title":"Edu Demo-Department 部门","description":"","raw_title":"Edu Demo-Department 部门","raw_description":"","position":5,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-10-16T07:51:23Z","updated_at":"2023-12-14T02:59:04Z","custom_field_options":[{"id":20062295439636,"name":"Marketing 市场","raw_name":"Marketing 市场","value":"marketing_市场"},{"id":20062295439764,"name":"Sales 销售","raw_name":"Sales 销售","value":"sales_销售"},{"id":20062295439892,"name":"Engineering 工程","raw_name":"Engineering 工程","value":"engineering_工程"},{"id":20062295440020,"name":"Facilities 设备","raw_name":"Facilities 设备","value":"facilities_设备"},{"id":20062295440148,"name":"Human Resource 人力资源","raw_name":"Human Resource 人力资源","value":"human_resource_人力资源"},{"id":20062295440276,"name":"IT 信息技术","raw_name":"IT 信息技术","value":"it_信息技术"},{"id":20062295440404,"name":"Purchasing 采购","raw_name":"Purchasing 采购","value":"purchasing_采购"}]},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/20062301605396.json","id":20062301605396,"type":"dropdown","key":"edu_demo_reports_to","title":"Edu Demo-Reports to 上级领导","description":"","raw_title":"Edu Demo-Reports to 上级领导","raw_description":"","position":6,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-10-16T07:51:24Z","updated_at":"2023-12-14T02:59:04Z","custom_field_options":[{"id":20062310549652,"name":"Joyce","raw_name":"Joyce","value":"joyce"},{"id":20062310549780,"name":"York","raw_name":"York","value":"york"},{"id":20062310549908,"name":"Cindy","raw_name":"Cindy","value":"cindy"},{"id":20062310550036,"name":"Wicky","raw_name":"Wicky","value":"wicky"}]},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/20062301620500.json","id":20062301620500,"type":"dropdown","key":"edu_demo_mobile_device","title":"Edu Demo-Mobile Device 移动设备","description":"","raw_title":"Edu Demo-Mobile Device 移动设备","raw_description":"","position":7,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-10-16T07:51:24Z","updated_at":"2023-12-14T02:59:04Z","custom_field_options":[{"id":20062310566292,"name":"Android","raw_name":"Android","value":"android"},{"id":20062310566420,"name":"iPhone","raw_name":"iPhone","value":"iphone"},{"id":20062310566548,"name":"Other","raw_name":"Other","value":"other"}]},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/20062301634964.json","id":20062301634964,"type":"dropdown","key":"gangwei","title":"岗位","description":"","raw_title":"岗位","raw_description":"","position":8,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-10-16T07:51:25Z","updated_at":"2023-12-14T02:59:04Z","custom_field_options":[{"id":20062295476244,"name":"普通员工","raw_name":"普通员工","value":"普通员工"},{"id":20062295476372,"name":"组长","raw_name":"组长","value":"组长"}]},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/20062343336980.json","id":20062343336980,"type":"textarea","key":"attachment_library","title":"Attachment Library Field","description":"Required field for Attachments Library App. Helps store data for user's attachments.","raw_title":"Attachment Library Field","raw_description":"Required field for Attachments Library App. Helps store data for user's attachments.","position":9,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-10-16T07:51:25Z","updated_at":"2023-12-14T02:59:04Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/20062343343508.json","id":20062343343508,"type":"checkbox","key":"loyal_customer","title":"忠实用户","description":"","raw_title":"忠实用户","raw_description":"","position":10,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-10-16T07:51:26Z","updated_at":"2023-12-14T02:59:04Z","tag":"忠实用户"},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/10360859057812.json","id":10360859057812,"type":"dropdown","key":"language","title":"Language","description":"","raw_title":"Language","raw_description":"","position":11,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2022-10-26T05:57:20Z","updated_at":"2023-12-14T02:59:04Z","custom_field_options":[{"id":10360859057428,"name":"English","raw_name":"English","value":"english"},{"id":10360859057556,"name":"中文","raw_name":"中文","value":"中文"},{"id":10360859057684,"name":"日本語","raw_name":"日本語","value":"日本語"}]},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/20062327443860.json","id":20062327443860,"type":"checkbox","key":"title_test","title":"{{dc.test}} ","description":"","raw_title":"{{dc.test}} ","raw_description":"","position":12,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-10-16T07:51:26Z","updated_at":"2023-12-14T02:59:04Z","tag":null},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/17588134757780.json","id":17588134757780,"type":"text","key":"name","title":"姓名 ","description":"","raw_title":"姓名 ","raw_description":"","position":13,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-07-21T02:40:58Z","updated_at":"2023-12-14T02:59:04Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/17588106067732.json","id":17588106067732,"type":"text","key":"address","title":"客户地址信息 ","description":"","raw_title":"客户地址信息 ","raw_description":"","position":14,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-07-21T02:41:22Z","updated_at":"2023-12-14T02:59:04Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/17588121514132.json","id":17588121514132,"type":"integer","key":"mobile_phone","title":"客户电话 ","description":"","raw_title":"客户电话 ","raw_description":"","position":15,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-07-21T02:41:53Z","updated_at":"2023-12-14T02:59:04Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/17588144050580.json","id":17588144050580,"type":"dropdown","key":"sex","title":"性别 ","description":"","raw_title":"性别 ","raw_description":"","position":16,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-07-21T02:42:12Z","updated_at":"2023-12-14T02:59:04Z","custom_field_options":[{"id":17588122546708,"name":"男","raw_name":"男","value":"男_复制"},{"id":17588122546836,"name":"女","raw_name":"女","value":"女_复制"},{"id":17588122546964,"name":"保密","raw_name":"保密","value":"保密_复制"}]},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/17588124019092.json","id":17588124019092,"type":"date","key":"birthday","title":"用户生日","description":"","raw_title":"用户生日","raw_description":"","position":17,"active":true,"system":false,"regexp_for_validation":"\\A([0-9]{4})-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])\\z","created_at":"2023-07-21T02:42:38Z","updated_at":"2023-12-14T02:59:04Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/17588178672020.json","id":17588178672020,"type":"integer","key":"postal_code","title":"邮政编码","description":"","raw_title":"邮政编码","raw_description":"","position":18,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-07-21T02:43:52Z","updated_at":"2023-12-14T02:59:04Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/17588387606804.json","id":17588387606804,"type":"textarea","key":"important_date","title":"重要日期（对客户）","description":"标记日期性质，如：纪恋日，生日","raw_title":"重要日期（对客户）","raw_description":"标记日期性质，如：纪恋日，生日","position":19,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-07-21T02:53:52Z","updated_at":"2023-12-14T02:59:04Z"}],"next_page":null,"previous_page":null,"count":20}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/user_fields}
    //200
    @Test
    void list_user_field(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/user_fields")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //导入用户字段  用户字段导入人员模块
    //https://developer.zendesk.com/api-reference/ticketing/users/user_fields/#create-user-field
    //201success：{"user_field":{"url":"https://jinmutraining.zendesk.com/api/v2/user_fields/21858734723860.json","id":21858734723860,"type":"text","key":"support_description1","title":"Support description","description":"This field describes the support plan this user has","raw_title":"Support description","raw_description":"This field describes the support plan this user has","position":9999,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-12-14T02:59:04Z","updated_at":"2023-12-14T02:59:04Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/user_fields}
    //同一个key会失败：
    // {"error":"RecordInvalid","description":"Record validation errors","details":{"key":[{"description":"键值： support_description1 已被使用","error":"DuplicateValue"}]}}
    //Response{protocol=h2, code=422, message=, url=https://jinmutraining.zendesk.com/api/v2/user_fields}
    //422

    @Test
    void demo123(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/user_fields")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"user_field\": {\"type\": \"text\", \"title\": \"Support description\",\n" +
                        "      \"description\": \"This field describes the support plan this user has\",\n" +
                        "      \"position\": 0, \"active\": true, \"key\": \"support_description1\"}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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

    }


    //批量导入组织  组织数据导入组织模块
    //https://developer.zendesk.com/api-reference/ticketing/organizations/organizations/#create-many-organizations
    //{"job_status":{"id":"V3-b387a2861909bf05fc645a1a35e16d64","job_type":"Bulk Create Organization","url":"https://jinmutraining.zendesk.com/api/v2/job_statuses/V3-b387a2861909bf05fc645a1a35e16d64.json","total":2,"progress":null,"status":"queued","message":null,"results":null}}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/organizations/create_many}
    //200
    //不管post几次都是200success
    @Test
    void demo130(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/organizations/create_many")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"organizations\": [{\"name\": \"Org1\"}, {\"name\": \"Org2\"}]}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //导入组织结构 组织结构在组织中可用
    //https://developer.zendesk.com/api-reference/ticketing/organizations/organizations/#create-organization
    //success情况：
    //{"organization":{"url":"https://jinmutraining.zendesk.com/api/v2/organizations/21858994192020.json","id":21858994192020,"name":"My Organization1","shared_tickets":false,"shared_comments":false,"external_id":null,"created_at":"2023-12-14T03:12:51Z","updated_at":"2023-12-14T03:12:51Z","domain_names":[],"details":null,"notes":null,"group_id":null,"tags":[],"organization_fields":{}}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/organizations}
    //201
    //name重复会导致
    //{"error":"RecordInvalid","description":"Record validation errors","details":{"name":[{"description":"名称：My Organization 已被使用","error":"DuplicateValue"}]}}
    //Response{protocol=h2, code=422, message=, url=https://jinmutraining.zendesk.com/api/v2/organizations}
    //422

    @Test
    void demo131(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/organizations")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"organization\": {\"name\": \"My Organization1\"}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //导入组织成员关系     组织成员导入组织
    //https://developer.zendesk.com/api-reference/ticketing/organizations/organization_fields/#create-organization-field
    //创建organization_id要用现有id
    //{"organization_membership":{"url":"https://jinmutraining.zendesk.com/api/v2/organization_memberships/21859498328596.json","id":21859498328596,"user_id":10335617720852,"organization_id":10332806750740,"default":true,"created_at":"2023-12-14T03:36:32Z","organization_name":"JinmuTraining123","updated_at":"2023-12-14T03:36:32Z","view_tickets":true}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/organization_memberships}
    //201
    //不能重复post
    //{"error":"RecordInvalid","description":"Record validation errors","details":{"user_id":[{"description":" This user is already a member of this organization.","error":"DuplicateValue"}]}}
    //Response{protocol=h2, code=422, message=, url=https://jinmutraining.zendesk.com/api/v2/organization_memberships}
    //422
    @Test
    void demo132(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/organization_memberships")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"organization_membership\":{\"view_tickets\":true,\"default\":true,\"updated_at\":\"2023-07-13T03:33:20Z\",\"organization_id\":8533649767695,\"domain\":\"jinmutraining\",\"created_at\":\"2023-07-13T03:33:20Z\",\"_id\":{\"$oid\":\"656fee2c7dd6b556588f58b6\"},\"id\":17353293803028,\"organization_name\":\"JinmuTraining123\",\"url\":\"https://jinmutraining.zendesk.com/api/v2/organization_memberships/17353293803028.json\"}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //导入组织字段   组织字段数据在组织字段中可用
    //https://developer.zendesk.com/api-reference/ticketing/organizations/organization_fields/#create-organization-field
    //{"organization_field":{"url":"https://jinmutraining.zendesk.com/api/v2/organization_fields/21859576718868.json","id":21859576718868,"type":"text","key":"support_description","title":"Support description","description":"This field describes the support plan this organization has","raw_title":"Support description","raw_description":"This field describes the support plan this organization has","position":9999,"active":true,"system":false,"regexp_for_validation":null,"created_at":"2023-12-14T03:43:39Z","updated_at":"2023-12-14T03:43:39Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/organization_fields}
    //201
    //重复post会报422键值已存在
    //{"error":"RecordInvalid","description":"Record validation errors","details":{"key":[{"description":"键值： support_description 已被使用","error":"DuplicateValue"}]}}
    //Response{protocol=h2, code=422, message=, url=https://jinmutraining.zendesk.com/api/v2/organization_fields}
    //422
    @Test
    void demo133(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/organization_fields")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "   \"organization_field\":{\n" +
                        "      \"raw_title\":\"组织昵称\",\n" +
                        "      \"description\":\"组织昵称\",\n" +
                        "      \"active\":true,\n" +
                        "      \"created_at\":\"2022-10-13T07:36:01Z\",\n" +
                        "      \"type\":\"text\",\n" +
                        "      \"title\":\"组织昵称\",\n" +
                        "      \"raw_description\":\"组织昵称\",\n" +
                        "      \"url\":\"https://pdi-jinmuinfo.zendesk.com/api/v2/organization_fields/11456369949465.json\",\n" +
                        "      \"system\":false,\n" +
                        "      \"updated_at\":\"2022-10-13T07:36:01Z\",\n" +
                        "      \"domain\":\"pdi-jinmuinfo\",\n" +
                        "      \"_id\":{\n" +
                        "         \"$oid\":\"65a772c9a8eafc7a4ebaeae4\"\n" +
                        "      },\n" +
                        "      \"id\":11456369949465,\n" +
                        "      \"position\":0,\n" +
                        "      \"key\":\"org_nickname\"\n" +
                        "   }\n" +
                        "}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //get organization_list
    // https:// developer.zendesk.com/api-reference/ticketing/organizations/organization_subscriptions/#list-organization-subscriptions
    //{"organization_subscriptions":[],"next_page":null,"previous_page":null,"count":0}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/organization_subscriptions}
    //200
    //无任何
    @Test
    void organization_list(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/organization_subscriptions")
                .newBuilder();

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }

    //https://developer.zendesk.com/api-reference/ticketing/organizations/organization_subscriptions/#create-organization-subscription
    //导入组织订阅    组织订阅导入订阅模块
    //{"organization_subscription":{"url":"https://jinmutraining.zendesk.com/api/v2/organization_subscriptions/22056930222868.json","id":22056930222868,"organization_id":10332806750740,"user_id":10332801830548,"created_at":"2023-12-20T02:01:03Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/organization_subscriptions}
    //201
    @Test
    void demo134(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/organization_subscriptions")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"organization_subscription\": {\n" +
                        "    \"organization_id\": 10332806750740,\n" +
                        "    \"user_id\": 10332801830548\n" +
                        "  }\n" +
                        "}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //List all Memberships
    //{"group_memberships":[{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10332815304468.json","id":10332815304468,"user_id":10332850159252,"group_id":10332850315540,"default":false,"created_at":"2022-10-25T03:50:28Z","updated_at":"2023-09-06T08:44:38Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10335580287380.json","id":10335580287380,"user_id":10335558472340,"group_id":10332850315540,"default":true,"created_at":"2022-10-25T09:23:49Z","updated_at":"2022-10-25T09:23:49Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10335639570708.json","id":10335639570708,"user_id":10335617720852,"group_id":10332850315540,"default":true,"created_at":"2022-10-25T09:31:22Z","updated_at":"2023-09-08T01:52:07Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10335657464084.json","id":10335657464084,"user_id":10335667132692,"group_id":10332850315540,"default":false,"created_at":"2022-10-25T09:35:25Z","updated_at":"2023-09-07T06:28:28Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10358060310292.json","id":10358060310292,"user_id":10332850159252,"group_id":10358060309652,"default":false,"created_at":"2022-10-26T01:33:23Z","updated_at":"2022-10-26T01:33:23Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10359041198356.json","id":10359041198356,"user_id":10335617720852,"group_id":10358062100244,"default":false,"created_at":"2022-10-26T02:44:36Z","updated_at":"2022-10-26T02:44:36Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10359067296020.json","id":10359067296020,"user_id":10335558472340,"group_id":10358061078036,"default":false,"created_at":"2022-10-26T02:43:30Z","updated_at":"2022-10-26T02:43:30Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387023520020.json","id":10387023520020,"user_id":10387023513492,"group_id":10358060309652,"default":true,"created_at":"2022-10-27T02:12:48Z","updated_at":"2022-10-27T02:12:48Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387032066836.json","id":10387032066836,"user_id":10387041346324,"group_id":10358060309652,"default":true,"created_at":"2022-10-27T02:17:00Z","updated_at":"2022-10-27T02:17:00Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387038983572.json","id":10387038983572,"user_id":10387029761428,"group_id":10358060309652,"default":true,"created_at":"2022-10-27T02:15:36Z","updated_at":"2022-10-27T02:15:36Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387146026260.json","id":10387146026260,"user_id":10387029761428,"group_id":10360903397140,"default":false,"created_at":"2022-10-27T02:26:09Z","updated_at":"2022-10-27T02:26:09Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387155055764.json","id":10387155055764,"user_id":10387023513492,"group_id":10360971766676,"default":false,"created_at":"2022-10-27T02:25:53Z","updated_at":"2022-10-27T02:25:53Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387157464212.json","id":10387157464212,"user_id":10335667132692,"group_id":10358017293332,"default":false,"created_at":"2022-10-27T02:27:39Z","updated_at":"2022-10-27T02:27:39Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387157897364.json","id":10387157897364,"user_id":10387041346324,"group_id":10360939320596,"default":false,"created_at":"2022-10-27T02:27:59Z","updated_at":"2022-10-27T02:27:59Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17389958309908.json","id":17389958309908,"user_id":10332850159252,"group_id":17389974779156,"default":false,"created_at":"2023-07-14T05:38:52Z","updated_at":"2023-07-14T05:38:52Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17389959202196.json","id":17389959202196,"user_id":10335558472340,"group_id":17389974779156,"default":false,"created_at":"2023-07-14T05:39:35Z","updated_at":"2023-07-14T05:39:35Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17389968959124.json","id":17389968959124,"user_id":10332850159252,"group_id":17390000935060,"default":false,"created_at":"2023-07-14T05:39:27Z","updated_at":"2023-07-14T05:39:27Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17389983996564.json","id":17389983996564,"user_id":10335617720852,"group_id":17389974779156,"default":false,"created_at":"2023-07-14T05:38:52Z","updated_at":"2023-09-08T01:52:07Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17389987857812.json","id":17389987857812,"user_id":10335558472340,"group_id":17390000935060,"default":false,"created_at":"2023-07-14T05:39:27Z","updated_at":"2023-07-14T05:39:27Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17390012656404.json","id":17390012656404,"user_id":10332850159252,"group_id":17390029169044,"default":false,"created_at":"2023-07-14T05:42:10Z","updated_at":"2023-07-14T05:42:10Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17390037643156.json","id":17390037643156,"user_id":10335558472340,"group_id":17390029169044,"default":false,"created_at":"2023-07-14T05:42:10Z","updated_at":"2023-07-14T05:42:10Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17765969894164.json","id":17765969894164,"user_id":10332850159252,"group_id":17766000965908,"default":true,"created_at":"2023-07-27T07:29:29Z","updated_at":"2023-09-06T08:44:38Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17765985878676.json","id":17765985878676,"user_id":10335558472340,"group_id":17766017245460,"default":false,"created_at":"2023-07-27T07:29:43Z","updated_at":"2023-07-27T07:29:43Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/18187503841172.json","id":18187503841172,"user_id":10387023513492,"group_id":18187468400020,"default":false,"created_at":"2023-08-11T03:20:40Z","updated_at":"2023-08-11T03:20:40Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/18992861372052.json","id":18992861372052,"user_id":10335667132692,"group_id":10358060309652,"default":true,"created_at":"2023-09-07T06:28:26Z","updated_at":"2023-09-07T06:28:27Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/20931217721364.json","id":20931217721364,"user_id":10335667132692,"group_id":20061695377684,"default":false,"created_at":"2023-11-14T01:54:48Z","updated_at":"2023-11-14T01:54:48Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/20931226770836.json","id":20931226770836,"user_id":10335617720852,"group_id":20061695377684,"default":false,"created_at":"2023-11-14T01:53:02Z","updated_at":"2023-11-14T01:53:02Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/20931274305172.json","id":20931274305172,"user_id":10335635046676,"group_id":20061695377684,"default":true,"created_at":"2023-11-14T01:57:20Z","updated_at":"2023-11-14T01:57:20Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/21573512359700.json","id":21573512359700,"user_id":10332850159252,"group_id":21573480606228,"default":false,"created_at":"2023-12-06T05:10:31Z","updated_at":"2023-12-06T05:10:31Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/21857592299540.json","id":21857592299540,"user_id":21857117370132,"group_id":20061695377684,"default":true,"created_at":"2023-12-14T01:59:37Z","updated_at":"2023-12-14T01:59:37Z"}],"next_page":null,"previous_page":null,"count":30}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/group_memberships}
    //200
    @Test
    void group_list(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jingmu.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/groups")
                .newBuilder();

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
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
    }

    //https://developer.zendesk.com/api-reference/ticketing/groups/groups/#create-group
    //导入群组   群组导入群组模块
    //{"group":{"url":"https://jinmutraining.zendesk.com/api/v2/groups/21860955402388.json","id":21860955402388,"is_public":true,"name":"My Group","description":"","default":false,"deleted":false,"created_at":"2023-12-14T05:23:04Z","updated_at":"2023-12-14T05:23:04Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/groups}
    //201success
    @Test
    void demo141(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/groups")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"group\": {\"name\": \"My Group\"}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //List all Memberships
    //{"group_memberships":[{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10332815304468.json","id":10332815304468,"user_id":10332850159252,"group_id":10332850315540,"default":false,"created_at":"2022-10-25T03:50:28Z","updated_at":"2023-09-06T08:44:38Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10335580287380.json","id":10335580287380,"user_id":10335558472340,"group_id":10332850315540,"default":true,"created_at":"2022-10-25T09:23:49Z","updated_at":"2022-10-25T09:23:49Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10335639570708.json","id":10335639570708,"user_id":10335617720852,"group_id":10332850315540,"default":true,"created_at":"2022-10-25T09:31:22Z","updated_at":"2023-09-08T01:52:07Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10335657464084.json","id":10335657464084,"user_id":10335667132692,"group_id":10332850315540,"default":false,"created_at":"2022-10-25T09:35:25Z","updated_at":"2023-09-07T06:28:28Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10358060310292.json","id":10358060310292,"user_id":10332850159252,"group_id":10358060309652,"default":false,"created_at":"2022-10-26T01:33:23Z","updated_at":"2022-10-26T01:33:23Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10359041198356.json","id":10359041198356,"user_id":10335617720852,"group_id":10358062100244,"default":false,"created_at":"2022-10-26T02:44:36Z","updated_at":"2022-10-26T02:44:36Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10359067296020.json","id":10359067296020,"user_id":10335558472340,"group_id":10358061078036,"default":false,"created_at":"2022-10-26T02:43:30Z","updated_at":"2022-10-26T02:43:30Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387023520020.json","id":10387023520020,"user_id":10387023513492,"group_id":10358060309652,"default":true,"created_at":"2022-10-27T02:12:48Z","updated_at":"2022-10-27T02:12:48Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387032066836.json","id":10387032066836,"user_id":10387041346324,"group_id":10358060309652,"default":true,"created_at":"2022-10-27T02:17:00Z","updated_at":"2022-10-27T02:17:00Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387038983572.json","id":10387038983572,"user_id":10387029761428,"group_id":10358060309652,"default":true,"created_at":"2022-10-27T02:15:36Z","updated_at":"2022-10-27T02:15:36Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387146026260.json","id":10387146026260,"user_id":10387029761428,"group_id":10360903397140,"default":false,"created_at":"2022-10-27T02:26:09Z","updated_at":"2022-10-27T02:26:09Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387155055764.json","id":10387155055764,"user_id":10387023513492,"group_id":10360971766676,"default":false,"created_at":"2022-10-27T02:25:53Z","updated_at":"2022-10-27T02:25:53Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387157464212.json","id":10387157464212,"user_id":10335667132692,"group_id":10358017293332,"default":false,"created_at":"2022-10-27T02:27:39Z","updated_at":"2022-10-27T02:27:39Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/10387157897364.json","id":10387157897364,"user_id":10387041346324,"group_id":10360939320596,"default":false,"created_at":"2022-10-27T02:27:59Z","updated_at":"2022-10-27T02:27:59Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17389958309908.json","id":17389958309908,"user_id":10332850159252,"group_id":17389974779156,"default":false,"created_at":"2023-07-14T05:38:52Z","updated_at":"2023-07-14T05:38:52Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17389959202196.json","id":17389959202196,"user_id":10335558472340,"group_id":17389974779156,"default":false,"created_at":"2023-07-14T05:39:35Z","updated_at":"2023-07-14T05:39:35Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17389968959124.json","id":17389968959124,"user_id":10332850159252,"group_id":17390000935060,"default":false,"created_at":"2023-07-14T05:39:27Z","updated_at":"2023-07-14T05:39:27Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17389983996564.json","id":17389983996564,"user_id":10335617720852,"group_id":17389974779156,"default":false,"created_at":"2023-07-14T05:38:52Z","updated_at":"2023-09-08T01:52:07Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17389987857812.json","id":17389987857812,"user_id":10335558472340,"group_id":17390000935060,"default":false,"created_at":"2023-07-14T05:39:27Z","updated_at":"2023-07-14T05:39:27Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17390012656404.json","id":17390012656404,"user_id":10332850159252,"group_id":17390029169044,"default":false,"created_at":"2023-07-14T05:42:10Z","updated_at":"2023-07-14T05:42:10Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17390037643156.json","id":17390037643156,"user_id":10335558472340,"group_id":17390029169044,"default":false,"created_at":"2023-07-14T05:42:10Z","updated_at":"2023-07-14T05:42:10Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17765969894164.json","id":17765969894164,"user_id":10332850159252,"group_id":17766000965908,"default":true,"created_at":"2023-07-27T07:29:29Z","updated_at":"2023-09-06T08:44:38Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/17765985878676.json","id":17765985878676,"user_id":10335558472340,"group_id":17766017245460,"default":false,"created_at":"2023-07-27T07:29:43Z","updated_at":"2023-07-27T07:29:43Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/18187503841172.json","id":18187503841172,"user_id":10387023513492,"group_id":18187468400020,"default":false,"created_at":"2023-08-11T03:20:40Z","updated_at":"2023-08-11T03:20:40Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/18992861372052.json","id":18992861372052,"user_id":10335667132692,"group_id":10358060309652,"default":true,"created_at":"2023-09-07T06:28:26Z","updated_at":"2023-09-07T06:28:27Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/20931217721364.json","id":20931217721364,"user_id":10335667132692,"group_id":20061695377684,"default":false,"created_at":"2023-11-14T01:54:48Z","updated_at":"2023-11-14T01:54:48Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/20931226770836.json","id":20931226770836,"user_id":10335617720852,"group_id":20061695377684,"default":false,"created_at":"2023-11-14T01:53:02Z","updated_at":"2023-11-14T01:53:02Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/20931274305172.json","id":20931274305172,"user_id":10335635046676,"group_id":20061695377684,"default":true,"created_at":"2023-11-14T01:57:20Z","updated_at":"2023-11-14T01:57:20Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/21573512359700.json","id":21573512359700,"user_id":10332850159252,"group_id":21573480606228,"default":false,"created_at":"2023-12-06T05:10:31Z","updated_at":"2023-12-06T05:10:31Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/21857592299540.json","id":21857592299540,"user_id":21857117370132,"group_id":20061695377684,"default":true,"created_at":"2023-12-14T01:59:37Z","updated_at":"2023-12-14T01:59:37Z"}],"next_page":null,"previous_page":null,"count":30}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/group_memberships}
    //200
    @Test
    void membership_list(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jingmu.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/group_memberships")
                .newBuilder();

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
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
    }
    //导入群组成员	群组成员导入群组中
    // https://developer.zendesk.com/api-reference/ticketing/groups/group_memberships/#create-membership
    //{"group_membership":{"url":"https://jinmutraining.zendesk.com/api/v2/group_memberships/21861230616980.json","id":21861230616980,"user_id":10335558472340,"group_id":21573480606228,"default":false,"created_at":"2023-12-14T05:46:15Z","updated_at":"2023-12-14T05:46:15Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/group_memberships}
    //201
    //必须选专员id
    @Test
    void demo142(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/group_memberships")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"group_membership\":{\"group_id\":22950168428180,\"user_id\":10332850159252}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //list all ticket_fields
    //{"ticket_fields":[{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10332806690964.json","id":10332806690964,"type":"subject","title":"标题","raw_title":"標題","description":"","raw_description":"","position":1,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"标题","raw_title_in_portal":"标题","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":true,"tag":null,"created_at":"2022-10-25T03:50:25Z","updated_at":"2023-10-18T03:32:36Z","removable":false,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10332821906708.json","id":10332821906708,"type":"description","title":"描述","raw_title":"描述","description":"请输入您请求的详情。我们的一名支持人员将尽快答复您。","raw_description":"請輸入您的請求詳情。我們的支援人員會儘快回應。","position":2,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"描述","raw_title_in_portal":"描述","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":true,"tag":null,"created_at":"2022-10-25T03:50:25Z","updated_at":"2023-10-18T03:32:23Z","removable":false,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10332801694100.json","id":10332801694100,"type":"status","title":"状态","raw_title":"狀態","description":"请求状态","raw_description":"請求狀態","position":3,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"状态","raw_title_in_portal":"狀態","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2022-10-25T03:50:25Z","updated_at":"2022-11-14T15:43:26Z","removable":false,"key":null,"agent_description":null,"system_field_options":[{"name":"已开启","value":"open"},{"name":"待回应","value":"pending"},{"name":"已解决","value":"solved"}],"sub_type_id":0},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10332850246164.json","id":10332850246164,"type":"tickettype","title":"类型","raw_title":"類型","description":"请求类型","raw_description":"請求類型","position":4,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"类型","raw_title_in_portal":"類型","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2022-10-25T03:50:25Z","updated_at":"2022-11-14T15:43:26Z","removable":true,"key":null,"agent_description":null,"system_field_options":[{"name":"问题","value":"question"},{"name":"事务","value":"incident"},{"name":"故障","value":"problem"},{"name":"任务","value":"task"}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10332815247764.json","id":10332815247764,"type":"priority","title":"优先级","raw_title":"優先等級","description":"请求优先级","raw_description":"請求優先等級","position":5,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"优先级","raw_title_in_portal":"優先等級","visible_in_portal":true,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2022-10-25T03:50:25Z","updated_at":"2022-11-14T15:43:26Z","removable":true,"key":null,"agent_description":null,"system_field_options":[{"name":"低","value":"low"},{"name":"正常","value":"normal"},{"name":"高","value":"high"},{"name":"紧急","value":"urgent"}],"sub_type_id":0},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10332833095828.json","id":10332833095828,"type":"group","title":"组","raw_title":"小組","description":"请求组","raw_description":"請求小組","position":6,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"组","raw_title_in_portal":"小組","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2022-10-25T03:50:25Z","updated_at":"2022-11-14T15:43:26Z","removable":false,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10332850246676.json","id":10332850246676,"type":"assignee","title":"受托人","raw_title":"受託人","description":"已分配专员给您的请求","raw_description":"已為您的請求分配代理","position":7,"active":true,"required":true,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"受托人","raw_title_in_portal":"受託人","visible_in_portal":true,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2022-10-25T03:50:25Z","updated_at":"2022-11-14T15:43:26Z","removable":false,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10332806691860.json","id":10332806691860,"type":"custom_status","title":"Ticket status","raw_title":"Ticket status","description":"Request ticket status","raw_description":"Request ticket status","position":8,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"Ticket status","raw_title_in_portal":"Ticket status","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2022-10-25T03:50:25Z","updated_at":"2022-10-25T03:50:25Z","removable":false,"key":null,"agent_description":null,"custom_statuses":[{"url":"https://jinmutraining.zendesk.com/api/v2/custom_statuses/10332806749844.json","id":10332806749844,"status_category":"new","agent_label":"新建","end_user_label":"已开启","description":"工单正待分配给专员","end_user_description":"我们正在回复您","active":true,"default":true,"created_at":"2022-10-25T03:50:28Z","updated_at":"2022-10-25T03:50:28Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/custom_statuses/10332815300884.json","id":10332815300884,"status_category":"open","agent_label":"已开启","end_user_label":"已开启","description":"员工正在处理工单","end_user_description":"我们正在回复您","active":true,"default":true,"created_at":"2022-10-25T03:50:28Z","updated_at":"2022-10-25T03:50:28Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/custom_statuses/10332821981204.json","id":10332821981204,"status_category":"pending","agent_label":"待回应","end_user_label":"正在等待您的回复","description":"员工正在等待请求者回复","end_user_description":"我们正在等待您的回复","active":true,"default":true,"created_at":"2022-10-25T03:50:28Z","updated_at":"2022-10-25T03:50:28Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/custom_statuses/20159345494164.json","id":20159345494164,"status_category":"pending","agent_label":"在处理","end_user_label":"在处理","description":"","end_user_description":"","active":true,"default":false,"created_at":"2023-10-19T06:31:39Z","updated_at":"2023-10-19T06:31:39Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/custom_statuses/10332833152788.json","id":10332833152788,"status_category":"hold","agent_label":"暂停","end_user_label":"已开启","description":"员工正在等待第三方","end_user_description":"我们正在回复您","active":true,"default":true,"created_at":"2022-10-25T03:50:28Z","updated_at":"2022-10-25T03:50:28Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/custom_statuses/10332801764116.json","id":10332801764116,"status_category":"solved","agent_label":"已解决","end_user_label":"已解决","description":"此工单已解决","end_user_description":"此请求已解决","active":true,"default":true,"created_at":"2022-10-25T03:50:28Z","updated_at":"2022-10-25T03:50:28Z"}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17320162821908.json","id":17320162821908,"type":"tagger","title":"上下分类下A后续 ","raw_title":"上下分类下A后续 ","description":"","raw_description":"","position":9999,"active":false,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"上下分类下A后续 ","raw_title_in_portal":"上下分类下A后续 ","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-12T07:47:46Z","updated_at":"2023-07-13T05:56:34Z","removable":true,"key":null,"agent_description":null,"custom_field_options":[{"id":17320125672852,"name":"下a1","raw_name":"下a1","value":"下a1","default":false},{"id":17320125672980,"name":"下a2","raw_name":"下a2","value":"下a2","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17390663634708.json","id":17390663634708,"type":"tagger","title":"来源渠道2","raw_title":"来源渠道2","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"交流平台在？","raw_title_in_portal":"交流平台在？","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":false,"tag":null,"created_at":"2023-07-14T06:57:43Z","updated_at":"2023-07-14T06:57:43Z","removable":true,"key":null,"agent_description":null,"custom_field_options":[{"id":17390706140436,"name":"邮件","raw_name":"邮件","value":"邮件","default":false},{"id":17390706140564,"name":"电话","raw_name":"电话","value":"电话","default":false},{"id":17390706140692,"name":"WhatsApp::微信","raw_name":"WhatsApp::微信","value":"whatsapp__微信","default":false},{"id":17390706140820,"name":"WhatsApp::Twitter","raw_name":"WhatsApp::Twitter","value":"whatsapp__twitter","default":false},{"id":17390706140948,"name":"WhatsApp::QQ","raw_name":"WhatsApp::QQ","value":"whatsapp__qq","default":false},{"id":17390706141076,"name":"APP::android","raw_name":"APP::android","value":"app__android","default":false},{"id":17390706141204,"name":"APP::IOS","raw_name":"APP::IOS","value":"app__ios","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17555347019540.json","id":17555347019540,"type":"text","title":"标题（动态）","raw_title":"标题（动态）","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"标题","raw_title_in_portal":"{{dc.ticket}}","visible_in_portal":true,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-20T06:53:28Z","updated_at":"2023-07-20T06:53:28Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17320123701012.json","id":17320123701012,"type":"tagger","title":"上下分类上A后续","raw_title":"上下分类上A后续","description":"","raw_description":"","position":9999,"active":false,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"上下分类上A后续","raw_title_in_portal":"上下分类上A后续","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-12T07:46:21Z","updated_at":"2023-07-13T05:56:25Z","removable":true,"key":null,"agent_description":null,"custom_field_options":[{"id":17320148483732,"name":"上a1","raw_name":"上a1","value":"上a1","default":false},{"id":17320148483860,"name":"上a2","raw_name":"上a2","value":"上a2","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/18126054566420.json","id":18126054566420,"type":"textarea","title":"多行字段","raw_title":"多行字段","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"多行字段","raw_title_in_portal":"多行字段","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-08-09T06:23:03Z","updated_at":"2023-08-09T06:23:03Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17318793066260.json","id":17318793066260,"type":"text","title":"来源渠道","raw_title":"来源渠道","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"来源渠道","raw_title_in_portal":"来源渠道","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-12T05:25:40Z","updated_at":"2023-07-12T05:25:40Z","removable":true,"key":null,"agent_description":"描述工单的发起人是从哪个渠道提交的"},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/19518865752596.json","id":19518865752596,"type":"tagger","title":"字段2","raw_title":"字段2","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"字段2","raw_title_in_portal":"字段2","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-09-26T08:12:57Z","updated_at":"2023-09-26T08:12:57Z","removable":true,"key":null,"agent_description":null,"custom_field_options":[{"id":19518873504660,"name":"选项3","raw_name":"选项3","value":"选项3","default":false},{"id":19518873504788,"name":"选项4","raw_name":"选项4","value":"选项4","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17767000654356.json","id":17767000654356,"type":"checkbox","title":"需要协助（日本）","raw_title":"需要协助（日本）","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"需要协助（日本）","raw_title_in_portal":"需要协助（日本）","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-27T09:04:24Z","updated_at":"2023-07-27T09:04:24Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10362886963988.json","id":10362886963988,"type":"text","title":"Serial Number","raw_title":"Serial Number","description":"位于产品背面的产品序列号","raw_description":"位于产品背面的产品序列号","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"Serial Number","raw_title_in_portal":"Serial Number","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":false,"tag":null,"created_at":"2022-10-26T09:28:38Z","updated_at":"2022-10-26T09:40:21Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17387338549780.json","id":17387338549780,"type":"lookup","title":"测试查找关系字段","raw_title":"测试查找关系字段","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"测试查找关系字段","raw_title_in_portal":"测试查找关系字段","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-14T02:01:24Z","updated_at":"2023-07-14T02:01:24Z","removable":true,"key":null,"agent_description":"测试","relationship_target_type":"zen:user","relationship_filter":{"all":[{"field":"locale_id","operator":"is_not","value":"1"}],"any":[]}},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17765125281044.json","id":17765125281044,"type":"checkbox","title":"需要协助","raw_title":"需要协助","description":"","raw_description":"","position":9999,"active":true,"required":true,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"需要协助","raw_title_in_portal":"需要协助","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-27T06:03:37Z","updated_at":"2023-07-27T06:03:37Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10387318659348.json","id":10387318659348,"type":"text","title":" Date of Purchase","raw_title":" Date of Purchase","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":" Date of Purchase","raw_title_in_portal":" Date of Purchase","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":true,"tag":null,"created_at":"2022-10-27T02:46:07Z","updated_at":"2022-10-27T09:55:01Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17351787306260.json","id":17351787306260,"type":"tagger","title":"测试字段多级分类","raw_title":"测试字段多级分类","description":"","raw_description":"","position":9999,"active":true,"required":true,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"测试字段多级分类","raw_title_in_portal":"测试字段多级分类","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":false,"tag":null,"created_at":"2023-07-13T02:03:22Z","updated_at":"2023-08-21T10:24:04Z","removable":true,"key":null,"agent_description":null,"custom_field_options":[{"id":17351802625300,"name":"A::A1::A11","raw_name":"A::A1::A11","value":"a__a1__a11","default":false},{"id":17351802625428,"name":"A::A1::A12","raw_name":"A::A1::A12","value":"a__a1__a12","default":false},{"id":17351900694164,"name":"A::A2::A21","raw_name":"A::A2::A21","value":"a__a2__a21","default":false},{"id":17351900694292,"name":"A::A2::A22","raw_name":"A::A2::A22","value":"a__a2__a22","default":false},{"id":17351900694420,"name":"B::B1::B11","raw_name":"B::B1::B11","value":"b__b1__b11","default":false},{"id":17351900694548,"name":"B::B1::B12","raw_name":"B::B1::B12","value":"b__b1__b12","default":false},{"id":17351900694932,"name":"B::B2::B21","raw_name":"B::B2::B21","value":"b__b2__b21","default":false},{"id":17351900695060,"name":"B::B2::B22","raw_name":"B::B2::B22","value":"b__b2__b22","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/18256167070740.json","id":18256167070740,"type":"text","title":"description","raw_title":"description","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"description","raw_title_in_portal":"description","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-08-14T02:47:25Z","updated_at":"2023-08-14T02:47:25Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10361288879380.json","id":10361288879380,"type":"tagger","title":"Type","raw_title":"Type","description":"","raw_description":"","position":9999,"active":true,"required":true,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"Type of Ticket","raw_title_in_portal":"Type of Ticket","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":true,"tag":null,"created_at":"2022-10-26T06:51:15Z","updated_at":"2022-10-26T06:51:15Z","removable":true,"key":null,"agent_description":"Type of Ticket","custom_field_options":[{"id":10361303442068,"name":"Technical question","raw_name":"Technical question","value":"technical_question","default":false},{"id":10361303442196,"name":"Sales question","raw_name":"Sales question","value":"sales_question","default":false},{"id":10361303442324,"name":"Legal questions","raw_name":"Legal questions","value":"legal_questions","default":false},{"id":10361303442452,"name":"Refund Related","raw_name":"Refund Related","value":"refund_related","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17320161669140.json","id":17320161669140,"type":"tagger","title":"上下分类上B后续 ","raw_title":"上下分类上B后续 ","description":"","raw_description":"","position":9999,"active":false,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"上下分类上B后续 ","raw_title_in_portal":"上下分类上B后续 ","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-12T07:46:57Z","updated_at":"2023-07-13T05:56:28Z","removable":true,"key":null,"agent_description":null,"custom_field_options":[{"id":17320139107604,"name":"上b1","raw_name":"上b1","value":"上b1_复制","default":false},{"id":17320139107732,"name":"上b2","raw_name":"上b2","value":"上b2_复制","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17351170230548.json","id":17351170230548,"type":"tagger","title":"测试字段","raw_title":"测试字段","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"测试字段","raw_title_in_portal":"测试字段","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-13T01:30:40Z","updated_at":"2023-07-13T02:14:20Z","removable":true,"key":null,"agent_description":"改良版测试上下ab12字段","custom_field_options":[{"id":17351399627796,"name":"1上::a::1","raw_name":"1上::a::1","value":"1上__a__1","default":false},{"id":17351399627924,"name":"1上::a::2","raw_name":"1上::a::2","value":"1上__a__2","default":false},{"id":17351399628052,"name":"1上::b::1","raw_name":"1上::b::1","value":"1上__b__1","default":false},{"id":17351399628180,"name":"1上::b::2","raw_name":"1上::b::2","value":"1上__b__2","default":false},{"id":17351399628308,"name":"1下::a::1","raw_name":"1下::a::1","value":"1下__a__1","default":false},{"id":17351399628436,"name":"1下::a::2","raw_name":"1下::a::2","value":"1下__a__2","default":false},{"id":17351399628564,"name":"1下::b::1","raw_name":"1下::b::1","value":"1下__b__1","default":false},{"id":17351399628692,"name":"1下::b::2","raw_name":"1下::b::2","value":"1下__b__2","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10360943316500.json","id":10360943316500,"type":"tagger","title":"Product","raw_title":"Product","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"Product","raw_title_in_portal":"Product","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":false,"tag":null,"created_at":"2022-10-26T06:05:47Z","updated_at":"2023-08-17T03:46:38Z","removable":true,"key":null,"agent_description":null,"custom_field_options":[{"id":10360943315988,"name":"Lightning","raw_name":"Lightning","value":"lightning","default":false},{"id":10360943316116,"name":"Thunder","raw_name":"Thunder","value":"thunder","default":false},{"id":10360943316244,"name":"Stormcloud","raw_name":"Stormcloud","value":"stormcloud","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/19993101000212.json","id":19993101000212,"type":"text","title":"标签","raw_title":"标签","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"标签","raw_title_in_portal":"标签","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-10-13T07:14:55Z","updated_at":"2023-10-13T07:14:55Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17357973418516.json","id":17357973418516,"type":"tagger","title":"测试故障字段","raw_title":"测试故障字段","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"测试故障字段","raw_title_in_portal":"测试故障字段","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-13T09:48:53Z","updated_at":"2023-07-14T05:29:40Z","removable":true,"key":null,"agent_description":"测试用，作为触发器条件","custom_field_options":[{"id":17357960218772,"name":"售前::咨询::无人机","raw_name":"售前::咨询::无人机","value":"售前__咨询__无人机","default":false},{"id":17357960218900,"name":"售前::咨询::遥控车","raw_name":"售前::咨询::遥控车","value":"售前__咨询__遥控车","default":false},{"id":17357960219028,"name":"售前::购买::无人机","raw_name":"售前::购买::无人机","value":"售前__购买__无人机","default":false},{"id":17389892121620,"name":"售前::购买::遥控车","raw_name":"售前::购买::遥控车","value":"售前__购买__遥控车","default":false},{"id":17389892121748,"name":"售后::无人机::零件损坏","raw_name":"售后::无人机::零件损坏","value":"售后__无人机__零件损坏","default":false},{"id":17389892121876,"name":"售后::无人机::电池损坏","raw_name":"售后::无人机::电池损坏","value":"售后__无人机__电池损坏","default":false},{"id":17389892122004,"name":"售后::遥控车::车组损坏","raw_name":"售后::遥控车::车组损坏","value":"售后__遥控车__车组损坏","default":false},{"id":17389892122132,"name":"售后::遥控车::遥控损坏","raw_name":"售后::遥控车::遥控损坏","value":"售后__遥控车__遥控损坏","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/18256221910676.json","id":18256221910676,"type":"text","title":"Descrpition","raw_title":"Descrpition","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"描述","raw_title_in_portal":"描述","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":false,"tag":null,"created_at":"2023-08-14T02:49:41Z","updated_at":"2023-09-04T05:43:24Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10387327110292.json","id":10387327110292,"type":"text","title":"Order Number","raw_title":"Order Number","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"Order Number","raw_title_in_portal":"Order Number","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":false,"tag":null,"created_at":"2022-10-27T02:46:40Z","updated_at":"2022-10-27T02:46:40Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17387538961812.json","id":17387538961812,"type":"lookup","title":"测试组织查找","raw_title":"测试组织查找","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"测试组织查找","raw_title_in_portal":"测试组织查找","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-14T02:07:36Z","updated_at":"2023-07-14T02:22:42Z","removable":true,"key":null,"agent_description":null,"relationship_target_type":"zen:organization","relationship_filter":{"all":[{"field":"tags","operator":"present","value":""}],"any":[]}},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/20126672711316.json","id":20126672711316,"type":"text","title":"Deutschland","raw_title":"{{dc.germany}}","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"Deutschland","raw_title_in_portal":"{{dc.germany}}","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":false,"tag":null,"created_at":"2023-10-18T03:29:22Z","updated_at":"2023-10-18T03:29:22Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/18356838102676.json","id":18356838102676,"type":"tagger","title":"product.No","raw_title":"product.No","description":"","raw_description":"","position":9999,"active":true,"required":true,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"product.No","raw_title_in_portal":"product.No","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":false,"tag":null,"created_at":"2023-08-17T03:47:47Z","updated_at":"2023-08-17T03:50:35Z","removable":true,"key":null,"agent_description":null,"custom_field_options":[{"id":18356813130260,"name":"Lightning-01","raw_name":"Lightning-01","value":"lightning-01","default":false},{"id":18356813130388,"name":"Lightning-02","raw_name":"Lightning-02","value":"lightning-02","default":false},{"id":18356813130516,"name":"Lightning-03","raw_name":"Lightning-03","value":"lightning-03","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17320110488724.json","id":17320110488724,"type":"tagger","title":"上下分类上后续","raw_title":"上下分类上后续","description":"","raw_description":"","position":9999,"active":false,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"上下分类上后续","raw_title_in_portal":"上下分类上后续","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-12T07:41:04Z","updated_at":"2023-07-13T05:56:31Z","removable":true,"key":null,"agent_description":null,"custom_field_options":[{"id":17320102982036,"name":"上a","raw_name":"上a","value":"上a","default":false},{"id":17320102982164,"name":"上b","raw_name":"上b","value":"上b","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17765965011348.json","id":17765965011348,"type":"tagger","title":"升级原因","raw_title":"升级原因","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"升级原因","raw_title_in_portal":"升级原因","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-27T07:26:21Z","updated_at":"2023-07-27T07:26:21Z","removable":true,"key":null,"agent_description":null,"custom_field_options":[{"id":17765964028052,"name":"产品引起了重大事故","raw_name":"产品引起了重大事故","value":"field_the_product_has_been_repaired_and_caused_a_major_accident","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/19518873181588.json","id":19518873181588,"type":"tagger","title":"字段1","raw_title":"字段1","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"字段1","raw_title_in_portal":"字段1","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-09-26T08:12:45Z","updated_at":"2023-09-26T08:12:45Z","removable":true,"key":null,"agent_description":null,"custom_field_options":[{"id":19518859686036,"name":"选项1","raw_name":"选项1","value":"选项1","default":false},{"id":19518859686164,"name":"选项2","raw_name":"选项2","value":"选项2","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/18896018516884.json","id":18896018516884,"type":"text","title":"地址信息","raw_title":"地址信息","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"地址信息","raw_title_in_portal":"地址信息","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":true,"tag":null,"created_at":"2023-09-04T05:35:01Z","updated_at":"2023-09-04T05:35:01Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/19896709786772.json","id":19896709786772,"type":"regexp","title":"正则表达式创建日期","raw_title":"正则表达式创建日期","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":"\\b([0-9]{4})-(1[0-2]|0?[1-9])-(3[0-1]|[1-2][0-9]|0?[1-9])\\b","title_in_portal":"正则表达式创建日期","raw_title_in_portal":"正则表达式创建日期","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-10-10T04:18:20Z","updated_at":"2023-10-10T04:18:20Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/17387258883220.json","id":17387258883220,"type":"lookup","title":"测试工单查找","raw_title":"测试工单查找","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"测试工单查找","raw_title_in_portal":"测试工单查找","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-07-14T01:59:49Z","updated_at":"2023-07-14T02:19:46Z","removable":true,"key":null,"agent_description":null,"relationship_target_type":"zen:ticket","relationship_filter":{"all":[{"field":"organization_id","operator":"is","value":"10332806750740"},{"field":"requester_id","operator":"is","value":"10335558472340"}],"any":[]}},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/18355283872916.json","id":18355283872916,"type":"text","title":"订单号","raw_title":"订单号","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"订单号","raw_title_in_portal":"订单号","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":false,"tag":null,"created_at":"2023-08-17T02:00:31Z","updated_at":"2023-08-17T02:09:54Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/19896506504852.json","id":19896506504852,"type":"date","title":"创建日期","raw_title":"创建日期","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":"\\A([0-9]{4})-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])\\z","title_in_portal":"创建日期","raw_title_in_portal":"创建日期","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-10-10T04:00:36Z","updated_at":"2023-10-10T04:00:36Z","removable":true,"key":null,"agent_description":null},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/18464357145492.json","id":18464357145492,"type":"tagger","title":"客户问题","raw_title":"客户问题","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"你需要怎么样的协助？","raw_title_in_portal":"你需要怎么样的协助？","visible_in_portal":true,"editable_in_portal":true,"required_in_portal":false,"tag":null,"created_at":"2023-08-21T09:51:20Z","updated_at":"2023-08-21T09:51:20Z","removable":true,"key":null,"agent_description":null,"custom_field_options":[{"id":18464369225364,"name":"售前::咨询::玩具","raw_name":"售前::咨询::玩具","value":"售前__咨询__玩具","default":false},{"id":18464369225492,"name":"售前::咨询::遥控器","raw_name":"售前::咨询::遥控器","value":"售前__咨询__遥控器","default":false},{"id":18464369225620,"name":"售后::故障报修::玩具","raw_name":"售后::故障报修::玩具","value":"售后__故障报修__玩具","default":false},{"id":18464369225748,"name":"售后::故障报修::遥控器","raw_name":"售后::故障报修::遥控器","value":"售后__故障报修__遥控器","default":false},{"id":18464369225876,"name":"售前::订购::玩具","raw_name":"售前::订购::玩具","value":"售前__订购__玩具","default":false},{"id":18464369226004,"name":"售前::订购::遥控器","raw_name":"售前::订购::遥控器","value":"售前__订购__遥控器","default":false},{"id":18464369226132,"name":"售后::回评::玩具","raw_name":"售后::回评::玩具","value":"售后__回评__玩具","default":false},{"id":18464369226260,"name":"售后::回评::遥控器","raw_name":"售后::回评::遥控器","value":"售后__回评__遥控器","default":false}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/10361951346068.json","id":10361951346068,"type":"tagger","title":"Support Level","raw_title":"Support Level","description":"","raw_description":"","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"Support Level","raw_title_in_portal":"Support Level","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2022-10-26T08:04:20Z","updated_at":"2022-10-26T08:04:20Z","removable":true,"key":null,"agent_description":null,"custom_field_options":[{"id":10361969363220,"name":"Level 1","raw_name":"Level 1","value":"level_1","default":false},{"id":10361969363348,"name":"Level 2","raw_name":"Level 2","value":"level_2","default":false}]}],"next_page":null,"previous_page":null,"count":43}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/ticket_fields}
    //已保存为文件
    @Test
    void ticket_field_list(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/ticket_fields/21115427350425")
                .newBuilder();
                //.addQueryParameter("creator", "")
                //.addQueryParameter("locale", "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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

    }


    //导入或更新下拉字段选项	下拉字段在字段中可用
    // https://developer.zendesk.com/api-reference/ticketing/tickets/ticket_fields/#create-or-update-ticket-field-option
    //404 只要加option都是404
    @Test
    void demo151(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/ticket_fields/10360943316500/options")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"custom_field_option\": {\"name\": \"Grapes\", \"position\": 9999, \"value\": \"grape\"}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //导入字段	   字段在字段中可用
    //https://developer.zendesk.com/api-reference/ticketing/tickets/ticket_fields/#create-ticket-field
    //{"ticket_field":{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_fields/21862584851860.json","id":21862584851860,"type":"text","title":"Age","raw_title":"Age","description":"Age","raw_description":"Age","position":9999,"active":true,"required":false,"collapsed_for_agents":false,"regexp_for_validation":null,"title_in_portal":"Age","raw_title_in_portal":"Age","visible_in_portal":false,"editable_in_portal":false,"required_in_portal":false,"tag":null,"created_at":"2023-12-14T07:39:17Z","updated_at":"2023-12-14T07:39:17Z","removable":true,"key":null,"agent_description":null}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/ticket_fields}
    //201
    @Test
    void demo152(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/ticket_fields")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"ticket_field\": {\"type\": \"text\", \"title\": \"Age\"}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //list_ticket_form
    //{"ticket_forms":[{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/10332815248788.json","name":"預設工單表單","display_name":"預設工單表單","id":10332815248788,"raw_name":"預設工單表單","raw_display_name":"預設工單表單","end_user_visible":true,"position":0,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,19993101000212,10332806690964,10332821906708,10332850246164,10332815247764,10360943316500,10361288879380,17765965011348,18256167070740],"active":true,"default":false,"created_at":"2022-10-25T03:50:26Z","updated_at":"2023-10-13T07:15:20Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/10361571109268.json","name":"General inquiry","display_name":"General inquiry","id":10361571109268,"raw_name":"General inquiry","raw_display_name":"General inquiry","end_user_visible":true,"position":1,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,10360943316500,10361288879380,17765125281044],"active":true,"default":true,"created_at":"2022-10-26T07:21:30Z","updated_at":"2023-08-17T02:43:28Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/10361603237140.json","name":"Complaints","display_name":"Complaints","id":10361603237140,"raw_name":"Complaints","raw_display_name":"Complaints","end_user_visible":true,"position":2,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806690964,10332821906708,10362886963988,10387318659348],"active":true,"default":false,"created_at":"2022-10-26T07:22:06Z","updated_at":"2023-08-17T02:43:28Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/10361603844244.json","name":"Refund","display_name":"Refund","id":10361603844244,"raw_name":"Refund","raw_display_name":"Refund","end_user_visible":true,"position":3,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,10387327110292,19896506504852],"active":true,"default":false,"created_at":"2022-10-26T07:22:30Z","updated_at":"2023-10-10T04:12:22Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/17320038499860.json","name":"测试分级选项","display_name":"分级选项","id":17320038499860,"raw_name":"测试分级选项","raw_display_name":"分级选项","end_user_visible":true,"position":4,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,17351787306260,10361288879380],"active":true,"default":false,"created_at":"2023-07-12T07:35:20Z","updated_at":"2023-08-17T02:43:28Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/17351207065364.json","name":"改进版测试上下ab12","display_name":"","id":17351207065364,"raw_name":"改进版测试上下ab12","raw_display_name":"","end_user_visible":false,"position":5,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,17351170230548],"active":true,"default":false,"created_at":"2023-07-13T01:31:07Z","updated_at":"2023-08-17T02:43:28Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/17387366239892.json","name":"测试查找关系字段","display_name":"测试查找关系字段","id":17387366239892,"raw_name":"测试查找关系字段","raw_display_name":"测试查找关系字段","end_user_visible":false,"position":6,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,17387258883220],"active":true,"default":false,"created_at":"2023-07-14T02:02:15Z","updated_at":"2023-08-17T02:43:28Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/17387447472404.json","name":"测试查找关系","display_name":"","id":17387447472404,"raw_name":"测试查找关系","raw_display_name":"","end_user_visible":false,"position":7,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,17387258883220,17387538961812],"active":true,"default":false,"created_at":"2023-07-14T02:03:35Z","updated_at":"2023-08-17T02:43:28Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/17389942078228.json","name":"测试故障表格","display_name":"","id":17389942078228,"raw_name":"测试故障表格","raw_display_name":"","end_user_visible":false,"position":8,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,17357973418516,17390663634708],"active":true,"default":false,"created_at":"2023-07-14T05:32:36Z","updated_at":"2023-08-17T02:43:28Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/17555317708564.json","name":"动态测试表格","display_name":"标题","id":17555317708564,"raw_name":"动态测试表格","raw_display_name":"{{dc.ticket}}","end_user_visible":true,"position":9,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,17555347019540,18126054566420,21863396031508,19896506504852,18896018516884,19518865752596,19993101000212,17390663634708],"active":true,"default":false,"created_at":"2023-07-20T06:54:22Z","updated_at":"2023-12-18T07:09:13Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/18326301550100.json","name":"jinmutraining-故障报修","display_name":"jinmutraining-故障报修","id":18326301550100,"raw_name":"jinmutraining-故障报修","raw_display_name":"jinmutraining-故障报修","end_user_visible":true,"position":10,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,17357973418516,18355283872916],"active":true,"default":false,"created_at":"2023-08-16T09:53:17Z","updated_at":"2023-08-17T02:43:28Z","in_all_brands":false,"restricted_brand_ids":[10332833146772],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/18326326813844.json","name":"Lightening-产品咨询","display_name":"Lightening::产品咨询","id":18326326813844,"raw_name":"Lightening-产品咨询","raw_display_name":"Lightening::产品咨询","end_user_visible":true,"position":11,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,10361288879380,10332815247764,18126054566420],"active":true,"default":false,"created_at":"2023-08-16T09:54:27Z","updated_at":"2023-08-17T02:43:28Z","in_all_brands":false,"restricted_brand_ids":[10729402099604],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/18326618677908.json","name":"Lightening-故障报修 ","display_name":"Lightening-故障报修","id":18326618677908,"raw_name":"Lightening-故障报修 ","raw_display_name":"Lightening-故障报修","end_user_visible":true,"position":12,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,17357973418516],"active":true,"default":false,"created_at":"2023-08-16T10:14:20Z","updated_at":"2023-08-17T02:43:28Z","in_all_brands":false,"restricted_brand_ids":[10729402099604],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/18326621136148.json","name":"JinmuTraining-产品咨询","display_name":"JinmuTraining-产品咨询","id":18326621136148,"raw_name":"JinmuTraining-产品咨询","raw_display_name":"JinmuTraining-产品咨询","end_user_visible":true,"position":13,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,10361288879380,10332815247764,18126054566420,18355283872916],"active":true,"default":false,"created_at":"2023-08-16T10:15:35Z","updated_at":"2023-08-17T02:43:28Z","in_all_brands":false,"restricted_brand_ids":[10332833146772],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/18355954931988.json","name":"订单查询","display_name":"订单查询","id":18355954931988,"raw_name":"订单查询","raw_display_name":"订单查询","end_user_visible":true,"position":14,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,18355283872916,19896506504852],"active":true,"default":false,"created_at":"2023-08-17T02:42:42Z","updated_at":"2023-10-10T04:09:02Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/18355925408020.json","name":"订单查询","display_name":"订单查询","id":18355925408020,"raw_name":"订单查询","raw_display_name":"订单查询","end_user_visible":true,"position":15,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,18355283872916],"active":false,"default":false,"created_at":"2023-08-17T02:42:42Z","updated_at":"2023-08-17T02:43:28Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/18356876321684.json","name":"防止客户选择产品类型和型号不一至","display_name":"选择产品类型及型号","id":18356876321684,"raw_name":"防止客户选择产品类型和型号不一至","raw_display_name":"选择产品类型及型号","end_user_visible":true,"position":16,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,10360943316500,18356838102676],"active":true,"default":false,"created_at":"2023-08-17T03:49:26Z","updated_at":"2023-08-17T03:51:04Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[{"parent_field_id":18356838102676,"parent_field_type":"tagger","value":"lightning-03","child_fields":[{"id":10360943316500,"is_required":false}]},{"parent_field_id":18356838102676,"parent_field_type":"tagger","value":"lightning-01","child_fields":[{"id":10360943316500,"is_required":false}]},{"parent_field_id":18356838102676,"parent_field_type":"tagger","value":"lightning-02","child_fields":[{"id":10360943316500,"is_required":false}]}],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/18464358750484.json","name":"客户提交问题","display_name":"你需要什么样的帮助？","id":18464358750484,"raw_name":"客户提交问题","raw_display_name":"你需要什么样的帮助？","end_user_visible":true,"position":17,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,18464357145492],"active":true,"default":false,"created_at":"2023-08-21T09:52:05Z","updated_at":"2023-08-21T09:52:05Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/19518860667540.json","name":"多选表格1","display_name":"","id":19518860667540,"raw_name":"多选表格1","raw_display_name":"","end_user_visible":false,"position":18,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,19518873181588],"active":true,"default":false,"created_at":"2023-09-26T08:13:21Z","updated_at":"2023-09-26T08:13:21Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/19518874336148.json","name":"多选表格2","display_name":"","id":19518874336148,"raw_name":"多选表格2","raw_display_name":"","end_user_visible":false,"position":19,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,19518873181588,19518865752596],"active":true,"default":false,"created_at":"2023-09-26T08:13:34Z","updated_at":"2023-09-26T08:13:34Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[{"parent_field_id":19518873181588,"parent_field_type":"tagger","value":"选项1","child_fields":[{"id":19518865752596,"is_required":false,"required_on_statuses":{"type":"NO_STATUSES"}}]}]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/20126599523860.json","name":"Deutschland","display_name":"Deutschland","id":20126599523860,"raw_name":"{{dc.germany}}","raw_display_name":"{{dc.germany}}","end_user_visible":true,"position":20,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,10332821906708,20126672711316],"active":true,"default":false,"created_at":"2023-10-18T03:25:29Z","updated_at":"2023-10-18T03:29:57Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]},{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/22056723244692.json","name":"Doctor Form","display_name":"","id":22056723244692,"raw_name":"Doctor Form","raw_display_name":"","end_user_visible":false,"position":21,"ticket_field_ids":[10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,21862992474132,21862704101396,10387318659348,21863055098388,10332821906708],"active":true,"default":false,"created_at":"2023-12-20T01:51:26Z","updated_at":"2023-12-20T01:51:26Z","in_all_brands":true,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]}],"next_page":null,"previous_page":null,"count":22}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/ticket_forms}
    //200
    @Test
    void list_ticket_form(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/ticket_forms")
                .newBuilder();
        //.addQueryParameter("creator", "")
        //.addQueryParameter("locale", "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //导入表格	表格在表格模块中可用
    //{"ticket_form":{"url":"https://jinmutraining.zendesk.com/api/v2/ticket_forms/22105732255764.json","name":"Snowboard Problem","display_name":"Snowboard Damage","id":22105732255764,"raw_name":"Snowboard Problem","raw_display_name":"Snowboard Damage","end_user_visible":true,"position":23,"ticket_field_ids":[10332821906708,10332801694100,10332833095828,10332850246676,10332806691860,10332806690964,21862992474132,21862704101396],"active":true,"default":false,"created_at":"2023-12-21T07:40:35Z","updated_at":"2023-12-21T07:40:35Z","in_all_brands":false,"restricted_brand_ids":[],"end_user_conditions":[],"agent_conditions":[]}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/ticket_forms}
    //201
    @Test
    void demo161(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/ticket_forms")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"ticket_form\": {\n" +
                        "    \"name\": \"Snowboard Problem\",\n" +
                        "    \"end_user_visible\": true,\n" +
                        "    \"display_name\": \"Snowboard Damage\",\n" +
                        "    \"position\": 23,\n" +
                        "    \"active\" : true,\n" +
                        "    \"in_all_brands\": false,\n" +
                        "    \"restricted_brand_ids\": [ ],\n" +
                        "    \"ticket_field_ids\": [ 10332833095828,10332850246676,10332806691860,10332806690964,21862992474132,21862704101396 ],\n" +
                        "    \"agent_conditions\":    [],\n" +
                        "    \"end_user_conditions\": [],\n" +
                        "    \"default\" : false\n" +
                        "  }\n" +
                        "}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //list brand
    //{"brands":[{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/brands/9044683548569.json","id":9044683548569,"name":"Consulting","brand_url":"https://con-jinmuinfo.zendesk.com","subdomain":"con-jinmuinfo","host_mapping":null,"has_help_center":false,"help_center_state":"disabled","active":true,"default":false,"is_deleted":false,"logo":null,"ticket_form_ids":[8427015306521,9774273311385,10655314017561,10655363792537,10680393488793,11719068957081,20872386819993,20873529217433,21116381952153,21117632341657,21666076151449,26097656174105],"signature_template":"{{agent.signature}}","created_at":"2022-08-01T02:36:10Z","updated_at":"2023-04-11T08:09:16Z"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/brands/25001004074009.json","id":25001004074009,"name":"EatWel","brand_url":"https://eatwel.zendesk.com","subdomain":"eatwel","host_mapping":null,"has_help_center":false,"help_center_state":"disabled","active":true,"default":false,"is_deleted":false,"logo":null,"ticket_form_ids":[8427015306521,9774273311385,10655314017561,10655363792537,10680393488793,11719068957081,20872386819993,20873529217433,21116381952153,21117632341657,21666076151449,26097656174105],"signature_template":"{{agent.signature}}","created_at":"2023-11-09T03:37:30Z","updated_at":"2023-11-09T03:37:30Z"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/brands/8427041409433.json","id":8427041409433,"name":"Shanghai Jinmu Information Technology Co.,","brand_url":"https://pdi-jinmuinfo.zendesk.com","subdomain":"pdi-jinmuinfo","host_mapping":null,"has_help_center":true,"help_center_state":"enabled","active":true,"default":true,"is_deleted":false,"logo":null,"ticket_form_ids":[8427015306521,9774273311385,10655314017561,10655363792537,10680393488793,11719068957081,20872386819993,20873529217433,21116381952153,21117632341657,21666076151449,26097656174105,20659992519961,20659974171289],"signature_template":"{{agent.signature}}","created_at":"2022-07-13T03:21:13Z","updated_at":"2022-10-19T06:21:38Z"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/brands/10184545635097.json","id":10184545635097,"name":"zendesk","brand_url":"https://jm-zd.zendesk.com","subdomain":"jm-zd","host_mapping":null,"has_help_center":false,"help_center_state":"disabled","active":true,"default":false,"is_deleted":false,"logo":{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/attachments/10184517233305.json","id":10184517233305,"file_name":"LOGO_1973_1973.png","content_url":"https://pdi-jinmuinfo.zendesk.com/system/brands/10184517233305/LOGO_1973_1973.png","mapped_content_url":"https://pdi-jinmuinfo.zendesk.com/system/brands/10184517233305/LOGO_1973_1973.png","content_type":"image/png","size":3474,"width":80,"height":80,"inline":false,"deleted":false,"thumbnails":[{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/attachments/10184517346457.json","id":10184517346457,"file_name":"LOGO_1973_1973_thumb.png","content_url":"https://pdi-jinmuinfo.zendesk.com/system/brands/10184517233305/LOGO_1973_1973_thumb.png","mapped_content_url":"https://pdi-jinmuinfo.zendesk.com/system/brands/10184517233305/LOGO_1973_1973_thumb.png","content_type":"image/png","size":1315,"width":32,"height":32,"inline":false,"deleted":false},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/attachments/10184517416729.json","id":10184517416729,"file_name":"LOGO_1973_1973_small.png","content_url":"https://pdi-jinmuinfo.zendesk.com/system/brands/10184517233305/LOGO_1973_1973_small.png","mapped_content_url":"https://pdi-jinmuinfo.zendesk.com/system/brands/10184517233305/LOGO_1973_1973_small.png","content_type":"image/png","size":1039,"width":24,"height":24,"inline":false,"deleted":false}]},"ticket_form_ids":[8427015306521,9774273311385,10655314017561,10655363792537,10680393488793,11719068957081,20872386819993,20873529217433,21116381952153,21117632341657,21666076151449,26097656174105],"signature_template":"{{agent.signature}}","created_at":"2022-09-05T02:08:44Z","updated_at":"2022-09-05T02:08:44Z"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/brands/10338011880089.json","id":10338011880089,"name":"教育行业","brand_url":"https://zendeskdemo-edu.jinmuinfo.com","subdomain":"edu-demo","host_mapping":"zendeskdemo-edu.jinmuinfo.com","has_help_center":true,"help_center_state":"enabled","active":true,"default":false,"is_deleted":false,"logo":{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/attachments/10337998331033.json","id":10337998331033,"file_name":"LOGO-DEMO-教育.png","content_url":"https://pdi-jinmuinfo.zendesk.com/system/brands/10337998331033/LOGO-DEMO-教育.png","mapped_content_url":"https://pdi-jinmuinfo.zendesk.com/system/brands/10337998331033/LOGO-DEMO-教育.png","content_type":"image/png","size":8624,"width":80,"height":80,"inline":false,"deleted":false,"thumbnails":[{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/attachments/10337998401945.json","id":10337998401945,"file_name":"LOGO-DEMO-教育_thumb.png","content_url":"https://pdi-jinmuinfo.zendesk.com/system/brands/10337998331033/LOGO-DEMO-教育_thumb.png","mapped_content_url":"https://pdi-jinmuinfo.zendesk.com/system/brands/10337998331033/LOGO-DEMO-教育_thumb.png","content_type":"image/png","size":2029,"width":32,"height":32,"inline":false,"deleted":false},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/attachments/10337998448665.json","id":10337998448665,"file_name":"LOGO-DEMO-教育_small.png","content_url":"https://pdi-jinmuinfo.zendesk.com/system/brands/10337998331033/LOGO-DEMO-教育_small.png","mapped_content_url":"https://pdi-jinmuinfo.zendesk.com/system/brands/10337998331033/LOGO-DEMO-教育_small.png","content_type":"image/png","size":1271,"width":24,"height":24,"inline":false,"deleted":false}]},"ticket_form_ids":[8427015306521,9774273311385,10655314017561,10655363792537,10680393488793,11719068957081,20872386819993,20873529217433,21116381952153,21117632341657,21666076151449,26097656174105,10655317014553,10655347405081,10655374991641,10787382034585],"signature_template":"{{agent.signature}}","created_at":"2022-09-09T07:25:34Z","updated_at":"2022-09-09T07:25:35Z"}],"next_page":null,"previous_page":null,"count":5}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/brands}
    //200

    //{"brands":[{"url":"https://jinmutraining.zendesk.com/api/v2/brands/21787015222292.json","id":21787015222292,"name":"Clothing","brand_url":"https://clothing-demo.zendesk.com","subdomain":"clothing-demo","host_mapping":null,"has_help_center":true,"help_center_state":"enabled","active":true,"default":false,"is_deleted":false,"logo":null,"ticket_form_ids":[18355925408020,10332815248788,10361571109268,10361603237140,10361603844244,17320038499860,17351207065364,17387366239892,17387447472404,17389942078228,17555317708564,18355954931988,18356876321684,18464358750484,19518860667540,19518874336148,20126599523860],"signature_template":"{{agent.signature}}","created_at":"2023-12-12T03:04:44Z","updated_at":"2023-12-12T03:04:44Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/brands/10332833146772.json","id":10332833146772,"name":"JinmuTraining01","brand_url":"https://jinmutraining.zendesk.com","subdomain":"jinmutraining","host_mapping":null,"has_help_center":true,"help_center_state":"enabled","active":true,"default":true,"is_deleted":false,"logo":null,"ticket_form_ids":[18355925408020,10332815248788,10361571109268,10361603237140,10361603844244,17320038499860,17351207065364,17387366239892,17387447472404,17389942078228,17555317708564,18355954931988,18356876321684,18464358750484,19518860667540,19518874336148,20126599523860,18326301550100,18326621136148],"signature_template":"{{agent.signature}}\r\nLove your JinMu\r\nhttps://support.zendesk.com/hc/zh-cn/articles/4408886858138#topic_hmx_zzw_4v","created_at":"2022-10-25T03:50:28Z","updated_at":"2023-11-15T02:39:13Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/brands/10729402099604.json","id":10729402099604,"name":"Lightening","brand_url":"https://lightening-jm.zendesk.com","subdomain":"lightening-jm","host_mapping":null,"has_help_center":true,"help_center_state":"enabled","active":true,"default":false,"is_deleted":false,"logo":null,"ticket_form_ids":[18355925408020,10332815248788,10361571109268,10361603237140,10361603844244,17320038499860,17351207065364,17387366239892,17387447472404,17389942078228,17555317708564,18355954931988,18356876321684,18464358750484,19518860667540,19518874336148,20126599523860,18326326813844,18326618677908],"signature_template":"{{agent.signature}}","created_at":"2022-11-10T09:33:24Z","updated_at":"2023-08-16T10:11:54Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/brands/10729403714836.json","id":10729403714836,"name":"Stormcloud","brand_url":"https://stormcloud-jm.zendesk.com","subdomain":"stormcloud-jm","host_mapping":null,"has_help_center":false,"help_center_state":"disabled","active":true,"default":false,"is_deleted":false,"logo":null,"ticket_form_ids":[18355925408020,10332815248788,10361571109268,10361603237140,10361603844244,17320038499860,17351207065364,17387366239892,17387447472404,17389942078228,17555317708564,18355954931988,18356876321684,18464358750484,19518860667540,19518874336148,20126599523860],"signature_template":"{{agent.signature}}","created_at":"2022-11-10T09:34:23Z","updated_at":"2023-08-16T10:11:58Z"},{"url":"https://jinmutraining.zendesk.com/api/v2/brands/10729402858260.json","id":10729402858260,"name":"Thunder","brand_url":"https://thunder-jm.zendesk.com","subdomain":"thunder-jm","host_mapping":null,"has_help_center":false,"help_center_state":"disabled","active":true,"default":false,"is_deleted":false,"logo":null,"ticket_form_ids":[18355925408020,10332815248788,10361571109268,10361603237140,10361603844244,17320038499860,17351207065364,17387366239892,17387447472404,17389942078228,17555317708564,18355954931988,18356876321684,18464358750484,19518860667540,19518874336148,20126599523860],"signature_template":"{{agent.signature}}","created_at":"2022-11-10T09:33:49Z","updated_at":"2023-11-14T04:04:58Z"}],"next_page":null,"previous_page":null,"count":5}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/brands}
    //200
    @Test
    void list_brand(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/brands")
                .newBuilder();


        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //创建品牌达到上线需要delete
    //Response{protocol=h2, code=204, message=, url=https://jinmutraining.zendesk.com/api/v2/brands/21787015222292}
    //204
    @Test
    void delete171(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/brands/21787015222292")
                .newBuilder();

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("DELETE", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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

    }

    //创建品牌	品牌导入品牌模块
    //https://developer.zendesk.com/api-reference/ticketing/account-configuration/brands/#create-brand
    //上限5个，删了就可以创建
    //{"brand":{"url":"https://jinmutraining.zendesk.com/api/v2/brands/21863134340884.json","id":21863134340884,"name":"Consulting","brand_url":"https://apple-co.zendesk.com","subdomain":"apple-co","host_mapping":null,"has_help_center":false,"help_center_state":"disabled","active":true,"default":false,"is_deleted":false,"logo":null,"ticket_form_ids":[18355925408020,10332815248788,10361571109268,10361603237140,10361603844244,17320038499860,17351207065364,17387366239892,17387447472404,17389942078228,17555317708564,18355954931988,18356876321684,18464358750484,19518860667540,19518874336148,20126599523860],"signature_template":null,"created_at":"2023-12-14T08:28:25Z","updated_at":"2023-12-14T08:28:25Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/brands}
    //201
    @Test
    void demo171(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/brands")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"brand\": {\n" +
                        "    \"name\": \"Consulting\",\n" +
                        "    \"subdomain\": \"apple-co\"\n" +
                        "  }\n" +
                        "}");


        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //{"custom_roles":[{"id":9106732438425,"name":"团队主管","description":"可管理所有工单和论坛","role_type":0,"created_at":"2022-08-02T18:07:43Z","updated_at":"2022-08-23T07:22:39Z","configuration":{"chat_access":true,"end_user_list_access":"full","forum_access_restricted_content":false,"light_agent":false,"manage_business_rules":false,"manage_dynamic_content":false,"manage_extensions_and_channels":false,"manage_facebook":false,"moderate_forums":false,"side_conversation_create":true,"ticket_access":"all","ticket_comment_access":"public","ticket_deletion":true,"ticket_tag_editing":true,"twitter_search_access":false,"view_deleted_tickets":true,"voice_access":true,"group_access":true,"organization_editing":true,"organization_notes_editing":true,"assign_tickets_to_any_group":false,"end_user_profile_access":"full","explore_access":"edit","forum_access":"full","macro_access":"full","report_access":"full","ticket_editing":true,"ticket_merge":true,"user_view_access":"full","view_access":"full","voice_dashboard_access":true,"manage_automations":false,"manage_contextual_workspaces":false,"manage_organization_fields":false,"manage_skills":false,"manage_slas":false,"manage_suspended_tickets":true,"manage_ticket_fields":false,"manage_ticket_forms":false,"manage_user_fields":false,"ticket_redaction":true,"manage_roles":"none","manage_groups":true,"manage_group_memberships":true,"manage_organizations":true,"manage_triggers":false,"view_reduced_count":false,"view_filter_tickets":true,"manage_macro_content_suggestions":false,"read_macro_content_suggestions":false,"custom_objects":{}},"team_member_count":0},{"id":9106748855577,"name":"指导专员","description":"可自动处理工单工作流程、管理渠道并对工单添加私密评论","role_type":0,"created_at":"2022-08-02T18:07:43Z","updated_at":"2022-08-23T07:22:39Z","configuration":{"chat_access":true,"end_user_list_access":"full","forum_access_restricted_content":false,"light_agent":false,"manage_business_rules":true,"manage_dynamic_content":false,"manage_extensions_and_channels":true,"manage_facebook":true,"moderate_forums":false,"side_conversation_create":true,"ticket_access":"within-groups","ticket_comment_access":"none","ticket_deletion":false,"ticket_tag_editing":true,"twitter_search_access":false,"view_deleted_tickets":false,"voice_access":true,"group_access":false,"organization_editing":false,"organization_notes_editing":false,"assign_tickets_to_any_group":false,"end_user_profile_access":"readonly","explore_access":"readonly","forum_access":"readonly","macro_access":"full","report_access":"none","ticket_editing":true,"ticket_merge":false,"user_view_access":"full","view_access":"full","voice_dashboard_access":false,"manage_automations":false,"manage_contextual_workspaces":false,"manage_organization_fields":false,"manage_skills":true,"manage_slas":true,"manage_suspended_tickets":false,"manage_ticket_fields":false,"manage_ticket_forms":false,"manage_user_fields":false,"ticket_redaction":false,"manage_roles":"none","manage_groups":false,"manage_group_memberships":false,"manage_organizations":false,"manage_triggers":false,"view_reduced_count":false,"view_filter_tickets":true,"manage_macro_content_suggestions":false,"read_macro_content_suggestions":false,"custom_objects":{}},"team_member_count":0},{"id":8427053484441,"name":"低权限专员","description":"可查看并添加私密评论到工单","role_type":1,"created_at":"2022-07-13T03:21:13Z","updated_at":"2022-08-23T07:22:39Z","configuration":{"chat_access":false,"end_user_list_access":"full","forum_access_restricted_content":false,"light_agent":true,"manage_business_rules":false,"manage_dynamic_content":false,"manage_extensions_and_channels":false,"manage_facebook":false,"moderate_forums":false,"side_conversation_create":true,"ticket_access":"within-groups","ticket_comment_access":"none","ticket_deletion":false,"ticket_tag_editing":false,"twitter_search_access":false,"view_deleted_tickets":false,"voice_access":false,"group_access":false,"organization_editing":false,"organization_notes_editing":false,"assign_tickets_to_any_group":false,"end_user_profile_access":"readonly","explore_access":"readonly","forum_access":"readonly","macro_access":"readonly","report_access":"readonly","ticket_editing":false,"ticket_merge":false,"user_view_access":"none","view_access":"readonly","voice_dashboard_access":false,"manage_automations":false,"manage_contextual_workspaces":false,"manage_organization_fields":false,"manage_skills":false,"manage_slas":false,"manage_suspended_tickets":false,"manage_ticket_fields":false,"manage_ticket_forms":false,"manage_user_fields":false,"ticket_redaction":false,"manage_roles":"none","manage_groups":false,"manage_group_memberships":false,"manage_organizations":false,"manage_triggers":false,"view_reduced_count":false,"view_filter_tickets":true,"manage_macro_content_suggestions":false,"read_macro_content_suggestions":false,"custom_objects":{}},"team_member_count":6},{"id":8427025908889,"name":"参与者","description":"可提供有限的支持","role_type":3,"created_at":"2022-07-13T03:21:17Z","updated_at":"2022-07-13T03:21:18Z","configuration":{"chat_access":false,"end_user_list_access":"none","forum_access_restricted_content":false,"light_agent":false,"manage_business_rules":false,"manage_dynamic_content":false,"manage_extensions_and_channels":false,"manage_facebook":false,"moderate_forums":false,"side_conversation_create":true,"ticket_access":"within-groups","ticket_comment_access":"none","ticket_deletion":false,"ticket_tag_editing":false,"twitter_search_access":false,"view_deleted_tickets":false,"voice_access":false,"group_access":false,"organization_editing":false,"organization_notes_editing":false,"assign_tickets_to_any_group":false,"end_user_profile_access":"readonly","explore_access":"none","forum_access":"readonly","macro_access":"readonly","report_access":"none","ticket_editing":false,"ticket_merge":false,"user_view_access":"none","view_access":"readonly","voice_dashboard_access":false,"manage_automations":false,"manage_contextual_workspaces":false,"manage_organization_fields":false,"manage_skills":false,"manage_slas":false,"manage_suspended_tickets":false,"manage_ticket_fields":false,"manage_ticket_forms":false,"manage_user_fields":false,"ticket_redaction":false,"manage_roles":"none","manage_groups":false,"manage_group_memberships":false,"manage_organizations":false,"manage_triggers":false,"view_reduced_count":false,"view_filter_tickets":true,"manage_macro_content_suggestions":false,"read_macro_content_suggestions":false,"custom_objects":{}},"team_member_count":0},{"id":8427054006681,"name":"帐单结算管理员","description":"可管理包括帐单结算在内的所有设置。","role_type":5,"created_at":"2022-07-13T03:21:18Z","updated_at":"2022-07-13T03:21:18Z","configuration":{"chat_access":true,"end_user_list_access":"full","forum_access_restricted_content":false,"light_agent":false,"manage_business_rules":true,"manage_dynamic_content":true,"manage_extensions_and_channels":true,"manage_facebook":true,"moderate_forums":false,"side_conversation_create":true,"ticket_access":"all","ticket_comment_access":"public","ticket_deletion":true,"ticket_tag_editing":true,"twitter_search_access":false,"view_deleted_tickets":true,"voice_access":true,"group_access":true,"organization_editing":true,"organization_notes_editing":true,"assign_tickets_to_any_group":true,"end_user_profile_access":"full","explore_access":"full","forum_access":"full","macro_access":"full","report_access":"full","ticket_editing":true,"ticket_merge":true,"user_view_access":"full","view_access":"full","voice_dashboard_access":true,"manage_automations":true,"manage_contextual_workspaces":true,"manage_organization_fields":true,"manage_skills":true,"manage_slas":true,"manage_suspended_tickets":true,"manage_ticket_fields":true,"manage_ticket_forms":true,"manage_user_fields":true,"ticket_redaction":true,"manage_roles":"all","manage_groups":true,"manage_group_memberships":true,"manage_organizations":true,"manage_triggers":true,"view_reduced_count":false,"view_filter_tickets":true,"manage_macro_content_suggestions":true,"read_macro_content_suggestions":true,"custom_objects":{}},"team_member_count":0},{"id":8427053672473,"name":"管理员","description":"可管理除帐单结算以外的所有设置","role_type":4,"created_at":"2022-07-13T03:21:14Z","updated_at":"2022-07-13T03:21:15Z","configuration":{"chat_access":true,"end_user_list_access":"full","forum_access_restricted_content":false,"light_agent":false,"manage_business_rules":true,"manage_dynamic_content":true,"manage_extensions_and_channels":true,"manage_facebook":true,"moderate_forums":false,"side_conversation_create":true,"ticket_access":"all","ticket_comment_access":"public","ticket_deletion":true,"ticket_tag_editing":true,"twitter_search_access":false,"view_deleted_tickets":true,"voice_access":true,"group_access":true,"organization_editing":true,"organization_notes_editing":true,"assign_tickets_to_any_group":true,"end_user_profile_access":"full","explore_access":"full","forum_access":"full","macro_access":"full","report_access":"full","ticket_editing":true,"ticket_merge":true,"user_view_access":"full","view_access":"full","voice_dashboard_access":true,"manage_automations":true,"manage_contextual_workspaces":true,"manage_organization_fields":true,"manage_skills":true,"manage_slas":true,"manage_suspended_tickets":true,"manage_ticket_fields":true,"manage_ticket_forms":true,"manage_user_fields":true,"ticket_redaction":true,"manage_roles":"all","manage_groups":true,"manage_group_memberships":true,"manage_organizations":true,"manage_triggers":true,"view_reduced_count":false,"view_filter_tickets":true,"manage_macro_content_suggestions":true,"read_macro_content_suggestions":true,"custom_objects":{}},"team_member_count":5}],"next_page":null,"previous_page":null,"count":6}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/custom_roles}
    //200
    @Test
    void list_custom_roles(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/custom_roles")
                .newBuilder();



        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }
    //创建自定义用户角色	自定义用户角色在系统中可用
    //{"custom_role":{"id":26396442128921,"name":"sample role","description":"sample description","role_type":0,"created_at":"2023-12-14T09:44:13Z","updated_at":"2023-12-14T09:44:13Z","configuration":{"chat_access":true,"end_user_list_access":"full","forum_access_restricted_content":false,"light_agent":false,"manage_business_rules":true,"manage_dynamic_content":false,"manage_extensions_and_channels":true,"manage_facebook":true,"moderate_forums":false,"side_conversation_create":true,"ticket_access":"within-groups","ticket_comment_access":"none","ticket_deletion":false,"ticket_tag_editing":true,"twitter_search_access":false,"view_deleted_tickets":false,"voice_access":true,"group_access":false,"organization_editing":false,"organization_notes_editing":false,"assign_tickets_to_any_group":false,"end_user_profile_access":"readonly","explore_access":"edit","forum_access":"readonly","macro_access":"full","report_access":"none","ticket_editing":true,"ticket_merge":false,"user_view_access":"readonly","view_access":"full","voice_dashboard_access":false,"manage_automations":false,"manage_contextual_workspaces":false,"manage_organization_fields":false,"manage_skills":false,"manage_slas":false,"manage_suspended_tickets":false,"manage_ticket_fields":false,"manage_ticket_forms":false,"manage_user_fields":false,"ticket_redaction":false,"manage_roles":"none","manage_groups":false,"manage_group_memberships":false,"manage_organizations":false,"manage_triggers":false,"view_reduced_count":false,"view_filter_tickets":false,"manage_macro_content_suggestions":false,"read_macro_content_suggestions":false,"custom_objects":{}},"team_member_count":0}}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/custom_roles}
    //200
    @Test
    void demo172() {
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/custom_roles")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{  \"custom_role\": {    \"configuration\": {      \"assign_tickets_to_any_group\": false,      \"chat_access\": true,      \"end_user_list_access\": \"full\",      \"end_user_profile_access\": \"readonly\",      \"explore_access\": \"edit\",      \"forum_access\": \"readonly\",      \"forum_access_restricted_content\": false,      \"group_access\": true,      \"light_agent\": false,      \"macro_access\": \"full\",      \"manage_business_rules\": true,      \"manage_contextual_workspaces\": false,      \"manage_dynamic_content\": false,      \"manage_extensions_and_channels\": true,      \"manage_facebook\": false,      \"manage_organization_fields\": false,      \"manage_ticket_fields\": false,      \"manage_ticket_forms\": false,      \"manage_user_fields\": false,      \"moderate_forums\": false,      \"organization_editing\": false,      \"organization_notes_editing\": false,      \"report_access\": \"none\",      \"side_conversation_create\": true,      \"ticket_access\": \"within-groups\",      \"ticket_comment_access\": \"none\",      \"ticket_deletion\": false,      \"ticket_editing\": true,      \"ticket_merge\": false,      \"ticket_tag_editing\": true,      \"twitter_search_access\": true,      \"user_view_access\": \"readonly\",      \"view_access\": \"full\",      \"view_deleted_tickets\": false,      \"voice_access\": true,      \"voice_dashboard_access\": false    },    \"created_at\": \"2012-03-12T16:32:22Z\",    \"description\": \"sample description\",    \"id\": 10127,    \"name\": \"sample role\",    \"role_type\": 0,    \"team_member_count\": 10,    \"updated_at\": \"2012-03-12T16:32:22Z\"  }}");


        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }

    //{"recipient_addresses":[{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/recipient_addresses/8427030807577.json","id":8427030807577,"brand_id":8427041409433,"default":true,"name":"Shanghai Jinmu Information Technology Co.,","email":"support@pdi-jinmuinfo.zendesk.com","forwarding_status":"verified","spf_status":"verified","cname_status":"verified","domain_verification_status":"verified","domain_verification_code":null,"created_at":"2022-07-13T03:21:13Z","updated_at":"2022-07-13T03:21:13Z"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/recipient_addresses/9044674436633.json","id":9044674436633,"brand_id":9044683548569,"default":true,"name":"Consulting","email":"support@con-jinmuinfo.zendesk.com","forwarding_status":"verified","spf_status":"verified","cname_status":"verified","domain_verification_status":"verified","domain_verification_code":null,"created_at":"2022-08-01T02:36:10Z","updated_at":"2022-08-01T02:36:10Z"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/recipient_addresses/10184517215257.json","id":10184517215257,"brand_id":10184545635097,"default":true,"name":"zendesk","email":"support@jm-zd.zendesk.com","forwarding_status":"verified","spf_status":"verified","cname_status":"verified","domain_verification_status":"verified","domain_verification_code":null,"created_at":"2022-09-05T02:08:44Z","updated_at":"2022-09-05T02:08:44Z"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/recipient_addresses/10337998323097.json","id":10337998323097,"brand_id":10338011880089,"default":true,"name":"教育行业","email":"support@edu-demo.zendesk.com","forwarding_status":"verified","spf_status":"verified","cname_status":"verified","domain_verification_status":"verified","domain_verification_code":null,"created_at":"2022-09-09T07:25:34Z","updated_at":"2022-09-09T07:25:34Z"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/recipient_addresses/17427939025689.json","id":17427939025689,"brand_id":9044683548569,"default":null,"name":"Support","email":"support@abc.com","forwarding_status":"failed","spf_status":"failed","cname_status":"unknown","domain_verification_status":"failed","domain_verification_code":"9c11b5f5ad70e53b","created_at":"2023-04-11T08:08:33Z","updated_at":"2023-04-11T08:09:16Z"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/recipient_addresses/25001020303513.json","id":25001020303513,"brand_id":25001004074009,"default":true,"name":"EatWel","email":"support@eatwel.zendesk.com","forwarding_status":"verified","spf_status":"verified","cname_status":"verified","domain_verification_status":"verified","domain_verification_code":null,"created_at":"2023-11-09T03:37:30Z","updated_at":"2023-11-09T03:37:30Z"}],"next_page":null,"previous_page":null,"count":6}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/recipient_addresses}
    //200
    @Test
    void support_email_list(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/recipient_addresses")
                .newBuilder();


        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }
    //创建支持邮箱	邮箱在系统中可用
    //https://developer.zendesk.com/api-reference/ticketing/account-configuration/support_addresses/#create-support-address
    //{"recipient_address":{"url":"https://jinmutraining.zendesk.com/api/v2/recipient_addresses/21863949509524.json","id":21863949509524,"brand_id":10332833146772,"default":null,"name":"Sales","email":"support999@jinmutraining.zendesk.com","forwarding_status":"verified","spf_status":"verified","cname_status":"verified","domain_verification_status":"verified","domain_verification_code":null,"created_at":"2023-12-14T09:31:08Z","updated_at":"2023-12-14T09:31:08Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/recipient_addresses}
    //201
    @Test
    void demo174(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/recipient_addresses")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "   \"recipient_address\":{\n" +
                        "      \"cname_status\":\"verified\",\n" +
                        "      \"forwarding_status\":\"verified\",\n" +
                        "      \"created_at\":\"2022-07-13T03:21:13Z\",\n" +
                        "      \"spf_status\":\"verified\",\n" +
                        "      \"url\":\"https://pdi-jinmuinfo.zendesk.com/api/v2/recipient_addresses/8427030807577.json\",\n" +
                        "      \"brand_id\":10332833146772,\n" +
                        "      \"default\":false,\n" +
                        "      \"updated_at\":\"2022-07-13T03:21:13Z\",\n" +
                        "      \"domain\":\"pdi-jinmuinfo\",\n" +
                        "      \"name\":\"Shanghai Jinmu Information Technology Co.,\",\n" +
                        "      \"_id\":{\n" +
                        "         \"$oid\":\"659f7da06fa7720e74b79c6a\"\n" +
                        "      },\n" +
                        "      \"id\":8427030807577,\n" +
                        "      \"domain_verification_status\":\"verified\",\n" +
                        "      \"email\":\"support@pdi-jinmuinfo.zendesk.com\"\n" +
                        "   }\n" +
                        "}");


        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //https://developer.zendesk.com/api-reference/ticketing/business-rules/views/#create-view
    //批量导入视图	视图数据在视图中可用
    //{"view":{"url":"https://jinmutraining.zendesk.com/api/v2/views/21864253769620.json","id":21864253769620,"title":"Roger Wilco","active":true,"updated_at":"2023-12-14T09:56:26Z","created_at":"2023-12-14T09:56:26Z","default":false,"position":10005,"description":null,"execution":{"group_by":null,"group_order":"desc","sort_by":null,"sort_order":"desc","group":null,"sort":null,"columns":[{"id":"subject","title":"标题","filterable":false,"sortable":false},{"id":"requester","title":"请求者","filterable":true,"sortable":true},{"id":"created","title":"请求于","filterable":false,"sortable":true},{"id":"updated","title":"更新于","filterable":true,"sortable":true},{"id":"group","title":"组","filterable":true,"sortable":true},{"id":"assignee","title":"受托人","filterable":true,"sortable":true}],"fields":[{"id":"subject","title":"标题","filterable":false,"sortable":false},{"id":"requester","title":"请求者","filterable":true,"sortable":true},{"id":"created","title":"请求于","filterable":false,"sortable":true},{"id":"updated","title":"更新于","filterable":true,"sortable":true},{"id":"group","title":"组","filterable":true,"sortable":true},{"id":"assignee","title":"受托人","filterable":true,"sortable":true}],"custom_fields":[]},"conditions":{"all":[{"field":"status","operator":"is","value":"open"},{"field":"priority","operator":"less_than","value":"high"}],"any":[{"field":"current_tags","operator":"includes","value":"hello"}]},"restriction":null,"raw_title":"Roger Wilco"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/views}
    //201
    @Test
    void demo181(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/views")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"view\": {\"title\": \"Roger Wilco\", \"all\": [{\"field\": \"status\", \"operator\": \"is\", \"value\": \"open\"}, {\"field\": \"priority\", \"operator\": \"less_than\", \"value\": \"high\"}], \"any\": [{ \"field\": \"current_tags\", \"operator\": \"includes\", \"value\": \"hello\" }]}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //导入触发器类别	触发器类别在系统中存在
    //https://developer.zendesk.com/api-reference/ticketing/business-rules/trigger_categories/#create-batch-job-for-trigger-categories
    //{"status":"complete","results":{"trigger_categories":[],"triggers":[]}}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/trigger_categories/jobs}
    //200

    @Test
    void demo1821(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/trigger_categories/jobs")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"job\": {\n" +
                        "    \"action\": \"patch\",\n" +
                        "    \"items\": {\n" +
                        "      \"trigger_categories\": [\n" +
                        "        {\n" +
                        "          \"id\": \"10001\",\n" +
                        "          \"position\": 0\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"id\": \"10002\",\n" +
                        "          \"position\": 1\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"triggers\": [\n" +
                        "        {\n" +
                        "          \"active\": false,\n" +
                        "          \"category_id\": \"10001\",\n" +
                        "          \"id\": \"10011\",\n" +
                        "          \"position\": 10\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"active\": true,\n" +
                        "          \"category_id\": \"10002\",\n" +
                        "          \"id\": \"10012\",\n" +
                        "          \"position\": 1\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    }\n" +
                        "  }\n" +
                        "}"
        );

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //{"triggers":[{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/18822582714388.json","id":18822582714388,"title":"测试内部注释触发触发器","active":true,"updated_at":"2023-09-01T07:55:04Z","created_at":"2023-09-01T06:37:57Z","default":false,"actions":[{"field":"brand_id","value":"10729403714836"}],"conditions":{"all":[{"field":"comment_includes_word","operator":"is","value":"welcome to the Stormcloud"}],"any":[{"field":"update_type","operator":"is","value":"Create"},{"field":"update_type","operator":"is","value":"Change"}]},"description":null,"position":1,"raw_title":"测试内部注释触发触发器","category_id":"18787648410516"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/18787628553620.json","id":18787628553620,"title":"测试分配给多个组","active":false,"updated_at":"2023-08-31T09:59:27Z","created_at":"2023-08-31T06:00:15Z","default":false,"actions":[{"field":"group_id","value":"17766000965908"},{"field":"group_id","value":"17766017245460"}],"conditions":{"all":[{"field":"group_id","operator":"is_not","value":"17316645197716"},{"field":"update_type","operator":"is","value":"Change"}],"any":[]},"description":null,"position":1,"raw_title":"测试分配给多个组","category_id":"18787648410516"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/18187544597524.json","id":18187544597524,"title":"通过邮件转办","active":true,"updated_at":"2023-08-11T05:29:50Z","created_at":"2023-08-11T03:24:46Z","default":false,"actions":[{"field":"notification_group","value":["18187468400020","需要你们部门处理","       这里有一份工单号为#{{ticket.id}}，标题是{{ticket.title}}的来自{{ticket.requester.email}}的客户的工单，请你们部门处理一下。工单链接{{ticket.link}}"]},{"field":"status","value":"solved"}],"conditions":{"all":[{"field":"status","operator":"less_than","value":"solved"},{"field":"group_id","operator":"value","value":"18187468400020"}],"any":[]},"description":null,"position":1,"raw_title":"通过邮件转办","category_id":"18187530565652"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/18678708371732.json","id":18678708371732,"title":"垃圾邮件自动标识触发器","active":true,"updated_at":"2023-08-28T05:33:31Z","created_at":"2023-08-28T02:53:49Z","default":false,"actions":[{"field":"custom_status_id","value":"10332801764116"},{"field":"current_tags","value":"垃圾邮件"}],"conditions":{"all":[{"field":"status","operator":"is","value":"new"},{"field":"update_type","operator":"is","value":"Create"}],"any":[{"field":"subject_includes_word","operator":"includes","value":"Your Facebook Page will be Deleted in"},{"field":"subject_includes_word","operator":"is","value":"Your ad has been deleted for"}]},"description":null,"position":1,"raw_title":"垃圾邮件自动标识触发器","category_id":"18678705260564"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/17765849457300.json","id":17765849457300,"title":"工单升级原因","active":true,"updated_at":"2023-07-27T08:06:12Z","created_at":"2023-07-27T07:10:49Z","default":false,"actions":[{"field":"ticket_form_id","value":"10332815248788"},{"field":"custom_fields_17765965011348","value":"field_the_product_has_been_repaired_and_caused_a_major_accident"}],"conditions":{"all":[{"field":"group_id","operator":"is_not","value":"17316645197716"}],"any":[{"field":"comment_includes_word","operator":"is","value":"catch fire"},{"field":"comment_includes_word","operator":"is","value":"911"},{"field":"comment_includes_word","operator":"is","value":"safety hazard"}]},"description":null,"position":1,"raw_title":"工单升级原因","category_id":"17765929758484"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/17766019941780.json","id":17766019941780,"title":"工单转出到L2组","active":true,"updated_at":"2023-07-27T07:31:25Z","created_at":"2023-07-27T07:31:25Z","default":false,"actions":[{"field":"group_id","value":"17766017245460"},{"field":"follower","value":"current_user"}],"conditions":{"all":[{"field":"group_id","operator":"is_not","value":"17316645197716"}],"any":[{"field":"custom_fields_17765965011348","operator":"is","value":"field_the_product_has_been_repaired_and_caused_a_major_accident"}]},"description":null,"position":1,"raw_title":"工单转出到L2组","category_id":"17765840556052"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/19827652413460.json","id":19827652413460,"title":"唯乐子工单自动分配人员","active":true,"updated_at":"2023-10-07T02:15:23Z","created_at":"2023-10-07T02:15:23Z","default":false,"actions":[{"field":"assignee_id","value":"10335617720852"}],"conditions":{"all":[{"field":"subject_includes_word","operator":"is","value":"A Delivery"},{"field":"current_via_id","operator":"is","value":"69"},{"field":"status","operator":"less_than","value":"pending"}],"any":[]},"description":null,"position":2,"raw_title":"唯乐子工单自动分配人员","category_id":"17765840556052"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/10332815305492.json","id":10332815305492,"title":"通知請求者與副本接收者已收到請求","active":true,"updated_at":"2022-11-14T15:43:20Z","created_at":"2022-10-25T03:50:29Z","default":false,"actions":[{"field":"notification_user","value":["requester_and_ccs","[已收到請求]","已收到您的請求（{{ticket.id}}），正由我們的支援工作人員檢閱。\n\n回覆此電子郵件新增其他評論。"]}],"conditions":{"all":[{"field":"update_type","operator":"is","value":"Create"},{"field":"status","operator":"is_not","value":"solved"},{"field":"ticket_is_public","operator":"is","value":"public"},{"field":"comment_is_public","operator":"is","value":true},{"field":"role","operator":"is","value":"end_user"}],"any":[]},"description":null,"position":1,"raw_title":"通知請求者與副本接收者已收到請求","category_id":"10332806753300"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/10388608282900.json","id":10388608282900,"title":"将法律工单分配通知受托人","active":true,"updated_at":"2022-10-27T05:11:02Z","created_at":"2022-10-27T05:11:01Z","default":false,"actions":[{"field":"notification_user","value":["assignee_id","[提醒]  [{{ticket.account}}] 法律工单分配：{{ticket.title}}","已将此法律工单（#{{ticket.id}}）分配给您，请在2天内处理完成，谢谢。\n\n{{ticket.latest_comment_html}}"]}],"conditions":{"all":[{"field":"assignee_id","operator":"changed","value":""},{"field":"assignee_id","operator":"is_not","value":"current_user"}],"any":[{"field":"current_tags","operator":"includes","value":"sales/legal"},{"field":"custom_fields_10361288879380","operator":"is","value":"legal_questions"}]},"description":null,"position":2,"raw_title":"将法律工单分配通知受托人","category_id":"10332806753300"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/10332815306772.json","id":10332815306772,"title":"通知請求者與副本接收者評論更新","active":true,"updated_at":"2022-11-14T15:43:22Z","created_at":"2022-10-25T03:50:29Z","default":false,"actions":[{"field":"notification_user","value":["requester_and_ccs","[{{ticket.account}}] Re：{{ticket.title}}","{{ticket.latest_comment_html}}"]}],"conditions":{"all":[{"field":"update_type","operator":"is","value":"Change"},{"field":"comment_is_public","operator":"is","value":true}],"any":[]},"description":null,"position":3,"raw_title":"通知請求者與副本接收者評論更新","category_id":"10332806753300"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/10332815308308.json","id":10332815308308,"title":"通知受託人工作分配","active":true,"updated_at":"2022-11-14T15:43:16Z","created_at":"2022-10-25T03:50:29Z","default":false,"actions":[{"field":"notification_user","value":["assignee_id","[{{ticket.account}}] 工作分配：{{ticket.title}}","已将此工单（#{{ticket.id}}）分配给您。\n\n{{ticket.latest_comment_html}}"]}],"conditions":{"all":[{"field":"assignee_id","operator":"changed","value":null},{"field":"assignee_id","operator":"is_not","value":"current_user"}],"any":[]},"description":null,"position":4,"raw_title":"通知受託人工作分配","category_id":"10332806753300"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/10332815306132.json","id":10332815306132,"title":"通知請求者新的主動式工單","active":true,"updated_at":"2023-08-15T07:00:56Z","created_at":"2022-10-25T03:50:29Z","default":false,"actions":[{"field":"notification_user","value":["requester_and_ccs","{{ticket.title}}","这张工单是代表您创建的。\n\n要添加额外的评论，请回复此电邮。\n\n{{ticket.latest_public_comment_html}}"]}],"conditions":{"all":[{"field":"update_type","operator":"is","value":"Create"},{"field":"ticket_is_public","operator":"is","value":"public"},{"field":"role","operator":"is","value":"agent"}],"any":[]},"description":"主動式工單為代理代表請求者建立的工單。","position":5,"raw_title":"通知請求者新的主動式工單","category_id":"10332806753300"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/10332815308180.json","id":10332815308180,"title":"通知受託人評論更新","active":true,"updated_at":"2022-11-14T15:43:14Z","created_at":"2022-10-25T03:50:29Z","default":false,"actions":[{"field":"notification_user","value":["assignee_id","[{{ticket.account}}] RE：{{ticket.title}}","{{ticket.latest_comment_html}}"]}],"conditions":{"all":[{"field":"comment_is_public","operator":"is","value":"not_relevant"},{"field":"assignee_id","operator":"is_not","value":"current_user"},{"field":"assignee_id","operator":"is_not","value":"requester_id"},{"field":"assignee_id","operator":"not_changed","value":null},{"field":"status","operator":"not_value_previous","value":"solved"}],"any":[]},"description":null,"position":6,"raw_title":"通知受託人評論更新","category_id":"10332806753300"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/10332815308948.json","id":10332815308948,"title":"通知受託人重新開啟的工單","active":true,"updated_at":"2022-11-14T15:43:17Z","created_at":"2022-10-25T03:50:29Z","default":false,"actions":[{"field":"notification_user","value":["assignee_id","[{{ticket.account}}] RE：{{ticket.title}}","此工单（#{{ticket.id}}）已重新开启。\n\n{{ticket.latest_comment_html}}"]}],"conditions":{"all":[{"field":"assignee_id","operator":"is_not","value":"current_user"},{"field":"status","operator":"value_previous","value":"solved"},{"field":"status","operator":"is_not","value":"closed"}],"any":[]},"description":null,"position":7,"raw_title":"通知受託人重新開啟的工單","category_id":"10332806753300"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/10332801776660.json","id":10332801776660,"title":"通知所有代理已收到請求","active":true,"updated_at":"2022-11-14T15:43:19Z","created_at":"2022-10-25T03:50:29Z","default":false,"actions":[{"field":"notification_user","value":["all_agents","[{{ticket.account}}] {{ticket.title}}","已收到来自 #{{ticket.id}} 的工单（{{ticket.requester.name}}）。它未分配。\n\n{{ticket.latest_comment_html}}"]}],"conditions":{"all":[{"field":"update_type","operator":"is","value":"Create"}],"any":[]},"description":null,"position":8,"raw_title":"通知所有代理已收到請求","category_id":"10332806753300"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/10388604625556.json","id":10388604625556,"title":"法律触发器","active":true,"updated_at":"2022-10-27T05:11:01Z","created_at":"2022-10-27T05:07:24Z","default":false,"actions":[{"field":"group_id","value":"10358017293332"},{"field":"notification_group","value":["10358017293332","【通知】收到一个新的法律工单{{ticket.title}}","你好，\n有一份新的法律工单等待处理，请尽快安排专人跟进。\n\n{{ticket.title}}\n\n{{ticket.description}}"]}],"conditions":{"all":[{"field":"update_type","operator":"is","value":"Create"},{"field":"group_id","operator":"is_not","value":"10358017293332"}],"any":[{"field":"custom_fields_10361288879380","operator":"is","value":"legal_questions"},{"field":"current_tags","operator":"includes","value":"sales/legal"}]},"description":null,"position":9,"raw_title":"法律触发器","category_id":"10332806753300"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/10474003266324.json","id":10474003266324,"title":"Automatic acknowledgement personalized to be sent back to customer as follows when a ticket is created by email","active":true,"updated_at":"2022-10-31T07:05:51Z","created_at":"2022-10-31T07:05:51Z","default":false,"actions":[{"field":"notification_user","value":["requester_id","[{{ticket.account}}] we have received your request, we will get back to you shortly.","Dear {{requester.first_name}} we have received your request {{ticket.title}} and are\nreviewing it. We will get back to you shortly."]}],"conditions":{"all":[{"field":"update_type","operator":"is","value":"Create"},{"field":"via_id","operator":"is","value":"4"}],"any":[]},"description":null,"position":10,"raw_title":"Automatic acknowledgement personalized to be sent back to customer as follows when a ticket is created by email","category_id":"10332806753300"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/18565522933268.json","id":18565522933268,"title":"测试触发器发送请求客户评价","active":true,"updated_at":"2023-08-24T03:17:57Z","created_at":"2023-08-24T03:00:22Z","default":false,"actions":[{"field":"notification_user","value":["requester_id","Request #{{ticket.id}}: How would you rate the support you received?","Hello {{ticket.requester.name}}\nWe'd love to hear what you think of our customer service. Please take a moment to answer one simple question byclicking either link below:\n{{satisfaction.rating_section}}\nHere's a reminder of what this request was about"]},{"field":"satisfaction_score","value":"offered"}],"conditions":{"all":[{"field":"status","operator":"greater_than","value":"pending"},{"field":"satisfaction_score","operator":"is","value":"unoffered"}],"any":[]},"description":null,"position":11,"raw_title":"测试触发器发送请求客户评价","category_id":"10332806753300"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/10362494497684.json","id":10362494497684,"title":"Answer Bot 触发器","active":false,"updated_at":"2023-07-12T01:26:47Z","created_at":"2022-10-26T08:57:59Z","default":false,"actions":[{"field":"deflection","value":["requester_id","请求已收到：{{ticket.title}}","您的请求（{{ticket.id}}）已收到，正在由我们的支持员工检查。\n\n{% if answer_bot.article_count > 0 %}\n这里有一些很好的文章，可能会有帮助：\n{{answer_bot.article_list}}\n{{answer_bot.first_article_body}}\n{% endif %}\n\n要添加另外的评论，请回复此电邮。\n\n{{ticket.comments_formatted}}\n",""]}],"conditions":{"all":[{"field":"update_type","operator":"is","value":"Create"}],"any":[]},"description":null,"position":1,"raw_title":"Answer Bot 触发器","category_id":"10362506958484"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/17316064796692.json","id":17316064796692,"title":"文章推荐工单测试","active":true,"updated_at":"2023-07-12T02:27:41Z","created_at":"2023-07-12T01:54:08Z","default":false,"actions":[{"field":"current_tags","value":"文章推荐"}],"conditions":{"all":[{"field":"requester_role","operator":"is","value":"0"},{"field":"via_integration_id","operator":"is","value":"63575d126a6e8e00fd2e134a"},{"field":"status","operator":"is_not","value":"closed"},{"field":"status","operator":"is_not","value":"closed"},{"field":"current_tags","operator":"not_includes","value":"文章推荐"}],"any":[]},"description":"给请求文章推荐的工单加文章推荐tag","position":1,"raw_title":"文章推荐工单测试","category_id":"10362506958484"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/17288193340052.json","id":17288193340052,"title":"测试文章推荐","active":false,"updated_at":"2023-10-12T09:21:20Z","created_at":"2023-07-11T06:22:53Z","default":false,"actions":[{"field":"deflection","value":["requester_id","邮件文章自动推送","{{current_user.first_name}}  您好！\n{% if autoreply.article_count > 0 %}\n\n这有{{autoreply.article_count}}篇文章，\n您可以点击查看\n{{autoreply.article_list}}\n{% else %}\n暂时没有找到对应的文章，我们将尽快完善这些内容，稍后我们将安排专人为您服务。\n{% endif %}\n",""]}],"conditions":{"all":[{"field":"status","operator":"is_not","value":"solved"},{"field":"requester_role","operator":"is","value":"0"},{"field":"status","operator":"is_not","value":"closed"}],"any":[{"field":"via_integration_id","operator":"is","value":"63575d126a6e8e00fd2e134a"},{"field":"via_id","operator":"is","value":"4"}]},"description":"文章推荐","position":2,"raw_title":"测试文章推荐","category_id":"10362506958484"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/10389761813396.json","id":10389761813396,"title":"請求客戶滿意度（傳訊）","active":true,"updated_at":"2023-07-12T01:54:45Z","created_at":"2022-10-27T07:49:27Z","default":false,"actions":[{"field":"notification_messaging_csat","value":"requester_id"}],"conditions":{"all":[{"field":"status","operator":"value","value":"solved"},{"field":"via_id","operator":"is","value":75}],"any":[]},"description":"對話結束後請客戶提供評級。此觸發程式由系統所建立。","position":3,"raw_title":"請求客戶滿意度（傳訊）","category_id":"10362506958484"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/10389754292116.json","id":10389754292116,"title":"請求客戶滿意度（社群傳訊）","active":true,"updated_at":"2023-07-12T01:54:45Z","created_at":"2022-10-27T07:49:27Z","default":false,"actions":[{"field":"notification_messaging_csat","value":"requester_id"}],"conditions":{"all":[{"field":"status","operator":"value","value":"solved"}],"any":[{"field":"via_id","operator":"is","value":73},{"field":"via_id","operator":"is","value":72},{"field":"via_id","operator":"is","value":74},{"field":"via_id","operator":"is","value":78},{"field":"via_id","operator":"is","value":88}]},"description":"對話結束後請客戶提供評級。此觸發程式由系統所建立。","position":4,"raw_title":"請求客戶滿意度（社群傳訊）","category_id":"10362506958484"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/17390547520276.json","id":17390547520276,"title":"识别无人机电池损坏","active":true,"updated_at":"2023-07-14T06:39:49Z","created_at":"2023-07-14T06:39:49Z","default":false,"actions":[{"field":"custom_fields_17357973418516","value":"售后__无人机__电池损坏"}],"conditions":{"all":[{"field":"group_id","operator":"is_not","value":"17316645197716"}],"any":[{"field":"subject_includes_word","operator":"includes","value":"自燃"},{"field":"comment_includes_word","operator":"includes","value":"自燃"}]},"description":null,"position":1,"raw_title":"识别无人机电池损坏","category_id":"17390516901268"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/17588105911444.json","id":17588105911444,"title":"威胁退款升级触发器","active":true,"updated_at":"2023-07-21T02:41:19Z","created_at":"2023-07-21T02:41:19Z","default":false,"actions":[{"field":"priority","value":"high"},{"field":"group_id","value":"17390029169044"}],"conditions":{"all":[{"field":"group_id","operator":"is_not","value":"17316645197716"},{"field":"comment_includes_word","operator":"includes","value":"要退款了"}],"any":[]},"description":null,"position":2,"raw_title":"威胁退款升级触发器","category_id":"17390516901268"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/17390368162836.json","id":17390368162836,"title":"邮件渠道触发器","active":true,"updated_at":"2023-07-14T06:29:14Z","created_at":"2023-07-14T06:18:00Z","default":false,"actions":[{"field":"custom_fields_17318793066260","value":"邮件"}],"conditions":{"all":[{"field":"update_type","operator":"is","value":"Create"},{"field":"via_id","operator":"is","value":"4"},{"field":"custom_status_id","operator":"is","value":"10332806749844"}],"any":[]},"description":null,"position":1,"raw_title":"邮件渠道触发器","category_id":"17390287481364"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/17390438789524.json","id":17390438789524,"title":"无人机分组标签字段触发器","active":true,"updated_at":"2023-07-14T06:29:14Z","created_at":"2023-07-14T06:28:05Z","default":false,"actions":[{"field":"current_tags","value":"无人机"}],"conditions":{"all":[{"field":"update_type","operator":"is","value":"Create"},{"field":"custom_status_id","operator":"is","value":"10332806749844"},{"field":"subject_includes_word","operator":"includes","value":"无人机"}],"any":[]},"description":null,"position":2,"raw_title":"无人机分组标签字段触发器","category_id":"17390287481364"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/17390341566740.json","id":17390341566740,"title":"测试分配触发器（无人机）","active":true,"updated_at":"2023-07-14T06:29:14Z","created_at":"2023-07-14T06:12:37Z","default":false,"actions":[{"field":"group_id","value":"17389974779156"}],"conditions":{"all":[{"field":"current_tags","operator":"includes","value":"无人机"},{"field":"replies","operator":"less_than","value":"1"},{"field":"update_type","operator":"is","value":"Create"},{"field":"custom_status_id","operator":"is","value":"10332806749844"}],"any":[]},"description":null,"position":3,"raw_title":"测试分配触发器（无人机）","category_id":"17390287481364"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/17390601879700.json","id":17390601879700,"title":"分配到无人机组","active":true,"updated_at":"2023-07-14T06:42:36Z","created_at":"2023-07-14T06:42:36Z","default":false,"actions":[{"field":"group_id","value":"17389974779156"}],"conditions":{"all":[{"field":"update_type","operator":"is","value":"Create"},{"field":"group_id","operator":"is_not","value":"17316645197716"}],"any":[{"field":"custom_fields_17357973418516","operator":"is","value":"售前__咨询__无人机"}]},"description":null,"position":4,"raw_title":"分配到无人机组","category_id":"17390287481364"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/18256216651412.json","id":18256216651412,"title":"触发器填入多行字段","active":false,"updated_at":"2023-08-14T03:04:27Z","created_at":"2023-08-14T02:52:07Z","default":false,"actions":[{"field":"custom_fields_18256167070740","value":"{{ticket.description}}"}],"conditions":{"all":[{"field":"status","operator":"less_than","value":"solved"},{"field":"ticket_form_id","operator":"is","value":"10332815248788"}],"any":[]},"description":null,"position":1,"raw_title":"触发器填入多行字段","category_id":"18256226593556"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/19896645743892.json","id":19896645743892,"title":"创建时间字段填入","active":true,"updated_at":"2023-10-10T04:13:22Z","created_at":"2023-10-10T04:08:32Z","default":false,"actions":[{"field":"custom_fields_19896506504852","value":["days_from_now","0"]}],"conditions":{"all":[{"field":"custom_status_id","operator":"is","value":"10332806749844"},{"field":"update_type","operator":"is","value":"Create"}],"any":[]},"description":null,"position":1,"raw_title":"创建时间字段填入","category_id":"18717556692500"},{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/20062086570004.json","id":20062086570004,"title":"添加国家工单标记","active":true,"updated_at":"2023-10-16T07:28:06Z","created_at":"2023-10-16T07:28:06Z","default":false,"actions":[{"field":"custom_fields_18896018516884","value":"Germany"}],"conditions":{"all":[{"field":"requester.custom_fields.address","operator":"is","value":"Germany"}],"any":[]},"description":null,"position":1,"raw_title":"添加国家工单标记","category_id":"20062094853140"}],"next_page":null,"previous_page":null,"count":32}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/triggers}
    //200


    @Test
    void list_trigger(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/triggers")
                .newBuilder();
                //.addQueryParameter("active", "true")
                //.addQueryParameter("category_id", "10026")
                //.addQueryParameter("sort", "position")
                //.addQueryParameter("sort_by", "position")
                //.addQueryParameter("sort_order", "desc");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //导入触发器	触发器在系统中可用
    //https://developer.zendesk.com/api-reference/ticketing/business-rules/triggers/#create-trigger
    //{"trigger":{"url":"https://jinmutraining.zendesk.com/api/v2/triggers/21864518660756.json","id":21864518660756,"title":"Roger Wilco","active":true,"updated_at":"2023-12-14T10:16:48Z","created_at":"2023-12-14T10:16:48Z","default":false,"actions":[{"field":"group_id","value":"20455932"}],"conditions":{"all":[{"field":"status","operator":"is","value":"open"},{"field":"priority","operator":"less_than","value":"high"}],"any":[]},"description":null,"position":2,"raw_title":"Roger Wilco","category_id":"18787648410516"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/triggers}
    //201
    @Test
    void demo1822(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/triggers")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"trigger\": {\n" +
                        "    \"actions\": [\n" +
                        "      {\n" +
                        "        \"field\": \"group_id\",\n" +
                        "        \"value\": \"20455932\"\n" +
                        "      }\n" +
                        "    ],\n" +
                        "    \"category_id\": \"18787648410516\",\n" +
                        "    \"conditions\": {\n" +
                        "      \"all\": [\n" +
                        "        {\n" +
                        "          \"field\": \"status\",\n" +
                        "          \"operator\": \"is\",\n" +
                        "          \"value\": \"open\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"field\": \"priority\",\n" +
                        "          \"operator\": \"less_than\",\n" +
                        "          \"value\": \"high\"\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    \"title\": \"Roger Wilco\"\n" +
                        "  }\n" +
                        "}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }
    //导入宏宏数据在宏中可用
    //https://developer.zendesk.com/api-reference/ticketing/business-rules/macros/#create-macro
    //{"macro":{"url":"https://jinmutraining.zendesk.com/api/v2/macros/21864577399956.json","id":21864577399956,"title":"Roger Wilco","active":true,"updated_at":"2023-12-14T10:20:45Z","created_at":"2023-12-14T10:20:45Z","default":false,"position":10007,"description":null,"actions":[{"field":"status","value":"solved"}],"restriction":null,"raw_title":"Roger Wilco"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/macros}
    //201
    //{"macro":{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/26631826097689.json","id":26631826097689,"title":"Roger Wilco","active":true,"updated_at":"2023-12-20T06:52:43Z","created_at":"2023-12-20T06:52:43Z","default":false,"position":10019,"description":null,"actions":[{"field":"status","value":"solved"}],"restriction":null,"raw_title":"Roger Wilco"}}
    //Response{protocol=h2, code=201, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/macros}
    //201
    @Test
    void demo1831(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/macros")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"macro\": {\n" +
                        "    \"actions\": [\n" +
                        "      {\n" +
                        "        \"field\": \"status\",\n" +
                        "        \"value\": \"solved\"\n" +
                        "      }\n" +
                        "    ],\n" +
                        "    \"title\": \"Roger Wilco\"\n" +
                        "  }\n" +
                        "}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }

    //list macro
    //{"macros":[{"url":"https://jinmutraining.zendesk.com/api/v2/macros/21864577399956.json","id":f,"title":"Roger Wilco","active":true,"updated_at":"2023-12-14T10:20:45Z","created_at":"2023-12-14T10:20:45Z","default":false,"position":10007,"description":null,"actions":[{"field":"status","value":"solved"}],"restriction":null,"raw_title":"Roger Wilco"},{"url":"https://jinmutraining.zendesk.com/api/v2/macros/18992229755028.json","id":18992229755028,"title":"whatsapp模版恢复","active":true,"updated_at":"2023-09-07T05:29:41Z","created_at":"2023-09-07T05:29:41Z","default":false,"position":10002,"description":null,"actions":[{"field":"comment_value_html","value":"<pre style=\"white-space: pre-wrap;\"><code>&amp;((<br> namespace=[[namespace]]<br> template=[[template]]<br> fallback=[[fallback]]<br> language=[[language]]<br> body_text=[[param_1]]<br>))&amp;</code></pre><pre style=\"white-space: pre-wrap;\"><code>如果上面出现消息，则宏成功</code></pre>"}],"restriction":null,"raw_title":"whatsapp模版恢复"},{"url":"https://jinmutraining.zendesk.com/api/v2/macros/19286179759252.json","id":19286179759252,"title":"动态内容测试","active":true,"updated_at":"2023-09-18T03:59:51Z","created_at":"2023-09-18T03:59:51Z","default":false,"position":10003,"description":null,"actions":[{"field":"comment_value_html","value":"<p>{{dc.commenttest}}<br></p>"}],"restriction":null,"raw_title":"动态内容测试"},{"url":"https://jinmutraining.zendesk.com/api/v2/macros/10387393779604.json","id":10387393779604,"title":"升级给Tier 2团队","active":true,"updated_at":"2022-10-27T02:52:19Z","created_at":"2022-10-27T02:52:19Z","default":false,"position":10000,"description":null,"actions":[{"field":"follower","value":"current_user"},{"field":"group_id","value":"10358061078036"},{"field":"custom_fields_10361951346068","value":"level_2"}],"restriction":{"type":"Group","id":10358060309652,"ids":[10358060309652]},"raw_title":"升级给Tier 2团队"},{"url":"https://jinmutraining.zendesk.com/api/v2/macros/19827520948756.json","id":19827520948756,"title":"唯乐子工单宏","active":true,"updated_at":"2023-10-07T02:07:16Z","created_at":"2023-10-07T02:07:16Z","default":false,"position":10004,"description":null,"actions":[{"field":"side_conversation_ticket","value":["A Delivery","<p>C Email：cadet（（））<br>Order：</p><p>Number：<br>Tracking：</p>","SupportAssignable:support_assignable/group/10358017293332","text/html"]},{"field":"ticket_form_id","value":"10361603844244"},{"field":"assignee_id","value":"10335617720852"}],"restriction":null,"raw_title":"唯乐子工单宏"},{"url":"https://jinmutraining.zendesk.com/api/v2/macros/19993622216468.json","id":19993622216468,"title":"固定话术事例","active":true,"updated_at":"2023-10-13T08:08:52Z","created_at":"2023-10-13T08:08:52Z","default":false,"position":10005,"description":null,"actions":[{"field":"comment_value_html","value":"<p>Hi！&nbsp;welcome&nbsp;to&nbsp;JM，we&nbsp;will&nbsp;give&nbsp;you&nbsp;help！<br></p>"}],"restriction":null,"raw_title":"固定话术事例"},{"url":"https://jinmutraining.zendesk.com/api/v2/macros/10332815312532.json","id":10332815312532,"title":"客戶未回應","active":true,"updated_at":"2022-11-14T15:43:13Z","created_at":"2022-10-25T03:50:29Z","default":false,"position":9999,"description":null,"actions":[{"field":"status","value":"pending"},{"field":"comment_value","value":"{{ticket.requester.name}} 您好：我們的代理 {{current_user.name}} 就此請求曾試圖聯絡您，但未得到您的回音。若需要更多的幫助，請告訴我們。謝謝。 "}],"restriction":null,"raw_title":"客戶未回應"},{"url":"https://jinmutraining.zendesk.com/api/v2/macros/17929433042196.json","id":17929433042196,"title":"客户的姓占位符测试","active":true,"updated_at":"2023-08-02T04:08:29Z","created_at":"2023-08-02T04:08:29Z","default":false,"position":10001,"description":null,"actions":[{"field":"side_conversation","value":["{{ticket.requester.last_name}}","<p>Hi！{{ticket.requester.last_name}}！<br></p>","","text/html"]}],"restriction":null,"raw_title":"客户的姓占位符测试"},{"url":"https://jinmutraining.zendesk.com/api/v2/macros/21790894632724.json","id":21790894632724,"title":"私密评论宏","active":true,"updated_at":"2023-12-12T07:57:37Z","created_at":"2023-12-12T07:57:37Z","default":false,"position":10006,"description":null,"actions":[{"field":"comment_mode_is_public","value":"false"},{"field":"comment_value_html","value":"<p>这是私密评论</p>"}],"restriction":null,"raw_title":"私密评论宏"},{"url":"https://jinmutraining.zendesk.com/api/v2/macros/10332833164436.json","id":10332833164436,"title":"降級並通知","active":true,"updated_at":"2022-11-14T15:43:12Z","created_at":"2022-10-25T03:50:29Z","default":false,"position":9999,"description":null,"actions":[{"field":"priority","value":"low"},{"field":"comment_value","value":"我們目前的交通負載量非常高。我們將儘快回覆您。"}],"restriction":null,"raw_title":"降級並通知"}],"next_page":null,"previous_page":null,"count":10}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/macros}
    //200

    //{"macros":[{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/21425832610969.json","id":21425832610969,"title":"Demo Marco","active":true,"updated_at":"2023-08-04T02:59:49Z","created_at":"2023-08-04T02:59:49Z","default":false,"position":10015,"description":null,"actions":[{"field":"current_tags","value":"testing"},{"field":"subject","value":"修改后的测试标题"},{"field":"comment_value_html","value":"<p>已经修改了&nbsp;<br></p>"}],"restriction":{"type":"User","id":11942258898585},"raw_title":"Demo Marco"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/10535172089881.json","id":10535172089881,"title":"Edu Demo - Need Approval 需要批准 -  Expense Claim 经费报销","active":true,"updated_at":"2022-09-19T07:20:28Z","created_at":"2022-09-15T15:52:38Z","default":false,"position":10000,"description":"","actions":[{"field":"status","value":"open"},{"field":"group_id","value":"10534938992025"},{"field":"current_tags","value":"need_approval mobile_claim"},{"field":"comment_mode_is_public","value":"false"},{"field":"comment_value_html","value":"<p>员工号：</p><p>报销金额：</p><p>备注：</p><p>账单详见附件</p>"}],"restriction":null,"raw_title":"Edu Demo - Need Approval 需要批准 -  Expense Claim 经费报销"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/10535525169177.json","id":10535525169177,"title":"Edu Demo - Result 批准结果 - Approved / Rejected 获批 / 拒绝","active":true,"updated_at":"2022-09-19T07:20:35Z","created_at":"2022-09-15T16:17:21Z","default":false,"position":10001,"description":null,"actions":[{"field":"status","value":"open"},{"field":"group_id","value":"10524187461913"},{"field":"remove_tags","value":"need_approval"},{"field":"comment_mode_is_public","value":"false"},{"field":"comment_value_html","value":"<p>批准结果 - 获批 / 拒绝<br>原因如下：</p>"}],"restriction":null,"raw_title":"Edu Demo - Result 批准结果 - Approved / Rejected 获批 / 拒绝"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/21085621124505.json","id":21085621124505,"title":"Flow::Side Conversation::Email","active":true,"updated_at":"2023-07-26T08:13:38Z","created_at":"2023-07-26T08:13:38Z","default":false,"position":10008,"description":null,"actions":[{"field":"side_conversation","value":["{{ticket.title}}","<p>Dear xxxx,&nbsp;</p><p>I&nbsp;need some help.</p><p><br></p><p>There is details from customer:</p><p><br></p><p>{{ticket.comments_formatted}}</p><p><br></p>","","text/html"]}],"restriction":null,"raw_title":"Flow::Side Conversation::Email"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/21084916309401.json","id":21084916309401,"title":"Flow::Side Conversation::Ticket","active":true,"updated_at":"2023-07-26T08:41:20Z","created_at":"2023-07-26T07:59:24Z","default":false,"position":10007,"description":null,"actions":[{"field":"side_conversation_ticket","value":["{{ticket.title}}","<p>Dear xxxx,&nbsp;</p><p>I&nbsp;need some help.</p><p><br></p><p>There is details from customer:</p><p><br></p><p>{{ticket.comments_formatted}}<br></p>","SupportAssignable:support_assignable/group/21080803861017","text/html"]},{"field":"type","value":"task"}],"restriction":null,"raw_title":"Flow::Side Conversation::Ticket"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/26631826097689.json","id":26631826097689,"title":"Roger Wilco","active":true,"updated_at":"2023-12-20T06:52:43Z","created_at":"2023-12-20T06:52:43Z","default":false,"position":10019,"description":null,"actions":[{"field":"status","value":"solved"}],"restriction":null,"raw_title":"Roger Wilco"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/17538764101273.json","id":17538764101273,"title":"test111","active":true,"updated_at":"2023-04-14T09:34:11Z","created_at":"2023-04-14T09:34:11Z","default":false,"position":10004,"description":null,"actions":[{"field":"comment_value_html","value":"<p>{{dc.get_user_infomation}}<br></p><p><br></p><p><br></p>"},{"field":"status","value":"solved"},{"field":"priority","value":"normal"}],"restriction":{"type":"User","id":11942258898585},"raw_title":"test111"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/21115143865881.json","id":21115143865881,"title":"下拉字段占位符宏测试","active":true,"updated_at":"2023-07-27T03:13:32Z","created_at":"2023-07-27T02:02:45Z","default":false,"position":10009,"description":null,"actions":[{"field":"ticket_form_id","value":"20873529217433"},{"field":"side_conversation","value":["下拉字段占位符宏测试","<p>这里有一个{{ticket.ticket_field_option_title_20873520728473}}情况<br></p>","","text/html"]},{"field":"status","value":"open"}],"restriction":null,"raw_title":"下拉字段占位符宏测试"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/8427025858329.json","id":8427025858329,"title":"关闭并重定向到主题","active":true,"updated_at":"2022-08-23T07:22:42Z","created_at":"2022-07-13T03:21:17Z","default":false,"position":9999,"description":null,"actions":[{"field":"status","value":"solved"},{"field":"priority","value":"normal"},{"field":"type","value":"incident"},{"field":"assignee_id","value":"current_user"},{"field":"group_id","value":"current_groups"},{"field":"comment_value","value":"感谢您提交请求。您报告的问题是已知问题。如需更多信息，请访问我们的论坛。 "}],"restriction":null,"raw_title":"关闭并重定向到主题"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/8427031216025.json","id":8427031216025,"title":"分配给我！","active":true,"updated_at":"2022-08-23T07:22:43Z","created_at":"2022-07-13T03:21:17Z","default":false,"position":9999,"description":null,"actions":[{"field":"group_id","value":"current_groups"},{"field":"assignee_id","value":"current_user"}],"restriction":null,"raw_title":"分配给我！"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/21115651825305.json","id":21115651825305,"title":"协办::快捷对话::下拉框占位符","active":true,"updated_at":"2023-07-27T02:29:17Z","created_at":"2023-07-27T02:12:25Z","default":false,"position":10010,"description":null,"actions":[{"field":"ticket_form_id","value":"21116381952153"},{"field":"custom_fields_21115427350425","value":"2001-02-02"},{"field":"status","value":"pending"},{"field":"side_conversation","value":["显示2001-02-02","<p>如果出现{{ticket.ticket_field_option_title_21115427350425}}就成功了<br></p>","","text/html"]}],"restriction":null,"raw_title":"协办::快捷对话::下拉框占位符"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/21118318046489.json","id":21118318046489,"title":"协办::快捷对话::文本占位符","active":true,"updated_at":"2023-07-27T03:06:35Z","created_at":"2023-07-27T03:06:35Z","default":false,"position":10011,"description":null,"actions":[{"field":"ticket_form_id","value":"21117632341657"},{"field":"status","value":"pending"},{"field":"side_conversation","value":["显示威胁退款则成功","<div class=\"zd-indent\" data-test-id=\"zd-editor-indent-line\" style=\"margin-left: 20px;\"><p>你好，这个工单转给你们组，升级原因是：{{ticket.ticket_field_21117433011481}}<br></p></div>","","text/html"]}],"restriction":null,"raw_title":"协办::快捷对话::文本占位符"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/20333679647513.json","id":20333679647513,"title":"售前::需求收集表","active":true,"updated_at":"2023-07-05T07:40:13Z","created_at":"2023-07-05T07:37:55Z","default":false,"position":10005,"description":null,"actions":[{"field":"ticket_form_id","value":"9774273311385"}],"restriction":{"type":"Group","id":12032726400921,"ids":[12032726400921,12032724754841]},"raw_title":"售前::需求收集表"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/8427025880857.json","id":8427025880857,"title":"客户未响应","active":true,"updated_at":"2022-08-23T07:22:43Z","created_at":"2022-07-13T03:21:17Z","default":false,"position":9999,"description":null,"actions":[{"field":"status","value":"pending"},{"field":"comment_value","value":"您好 {{ticket.requester.name}}：我们的专员 {{current_user.name}} 已尝试就此请求联系您，但我们尚未收到您的回复。如需进一步帮助，请告诉我们。谢谢。 "}],"restriction":null,"raw_title":"客户未响应"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/21345290149145.json","id":21345290149145,"title":"客户的姓氏占位符宏测试","active":true,"updated_at":"2023-08-02T04:13:27Z","created_at":"2023-08-02T04:13:27Z","default":false,"position":10014,"description":null,"actions":[{"field":"side_conversation","value":["Hi！{{ticket.requester.last_name}}","<p>Hi，dear {{ticket.requester.last_name}}，We&nbsp;get&nbsp;you&nbsp;need，we&nbsp;will&nbsp;give&nbsp;you support!<br></p>","","text/html"]}],"restriction":null,"raw_title":"客户的姓氏占位符宏测试"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/16264486975897.json","id":16264486975897,"title":"工单::关闭工单","active":true,"updated_at":"2023-03-07T02:53:48Z","created_at":"2023-03-07T02:53:48Z","default":false,"position":10003,"description":null,"actions":[{"field":"current_tags","value":"close_now"}],"restriction":null,"raw_title":"工单::关闭工单"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/21963331294745.json","id":21963331294745,"title":"手动发送满意度宏","active":true,"updated_at":"2023-08-18T08:15:31Z","created_at":"2023-08-18T08:15:31Z","default":false,"position":10018,"description":null,"actions":[{"field":"custom_status_id","value":"8427030902553"},{"field":"side_conversation","value":["请求 #{{ticket.id}}：您会如何评价您收到的支持服务？","<p>嗨 {{ticket.requester.name}}：</p><p><br></p><p>我们希望聆听对于我们客户服务的看法。请单击以下任一链接，用简短的时间回答一个简单的问题：<br></p><p><br></p><p>{{satisfaction.rating_section}}<br></p><p><br></p><p>这是此请求所涉及内容的提醒：<br></p><p><br></p><p>{{ticket.comments_formatted}}<br></p><p><br></p>","{{ticket.requester_field}}","text/html"]}],"restriction":null,"raw_title":"手动发送满意度宏"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/21169975349785.json","id":21169975349785,"title":"测试-添加用户字段::忠实用户","active":false,"updated_at":"2023-07-28T07:04:34Z","created_at":"2023-07-28T06:58:08Z","default":false,"position":10013,"description":null,"actions":[{"field":"custom_fields_10652776289945","value":"primary_school"},{"field":"custom_fields_10655239089817","value":"lost_/_stolen_遗失_/_被盗"}],"restriction":null,"raw_title":"测试-添加用户字段::忠实用户"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/21169918477849.json","id":21169918477849,"title":"测试::用户字段添加::忠实用户","active":true,"updated_at":"2023-07-28T07:03:43Z","created_at":"2023-07-28T06:56:29Z","default":false,"position":10012,"description":null,"actions":[{"field":"custom_fields_10655235898137","value":"departure/termination"}],"restriction":null,"raw_title":"测试::用户字段添加::忠实用户"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/11452657828761.json","id":11452657828761,"title":"测试宏","active":true,"updated_at":"2022-10-17T02:10:42Z","created_at":"2022-10-13T06:04:42Z","default":false,"position":10002,"description":null,"actions":[{"field":"brand_id","value":"10338011880089"},{"field":"follower","value":"9514809687577"},{"field":"comment_mode_is_public","value":"false"},{"field":"custom_fields_10076189080985","value":"s1"}],"restriction":null,"raw_title":"测试宏"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/20662800426649.json","id":20662800426649,"title":"话术::售前::欢迎词","active":true,"updated_at":"2023-07-14T09:31:57Z","created_at":"2023-07-14T09:31:57Z","default":false,"position":10006,"description":null,"actions":[{"field":"comment_value_html","value":"<p>Dear <span class=\"atwho-inserted\">{{ticket.requester.name}}</span>​ :<br></p><p><br></p><p>很高兴为您服务，我是 <span class=\"atwho-inserted\">{{ticket.account}}</span>​ 的 <span class=\"atwho-inserted\">{{ticket.assignee.name}}</span>​ 。<br></p>"},{"field":"ticket_form_id","value":"10787382034585"}],"restriction":null,"raw_title":"话术::售前::欢迎词"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/21663244182041.json","id":21663244182041,"title":"转办::邮件转发到商务组","active":true,"updated_at":"2023-08-10T08:17:47Z","created_at":"2023-08-10T08:17:47Z","default":false,"position":10016,"description":null,"actions":[{"field":"side_conversation","value":["需要转发到商务组","<p>这里有一个来自{{ticket.assignee.name}}的工单需要转到你们商务组，请注意查看。</p><p>工单的id是{{ticket.id}}，请求者是{{ticket.requester.name}}</p><p><br></p>","","text/html"]},{"field":"custom_status_id","value":"8427030902553"}],"restriction":null,"raw_title":"转办::邮件转发到商务组"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/21668375238553.json","id":21668375238553,"title":"转办到其他部门","active":true,"updated_at":"2023-08-10T10:31:58Z","created_at":"2023-08-10T10:31:58Z","default":false,"position":10017,"description":null,"actions":[{"field":"custom_status_id","value":"8427030902553"},{"field":"side_conversation","value":["转办到其他部门","","","text/html"]}],"restriction":null,"raw_title":"转办到其他部门"},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/8427041708057.json","id":8427041708057,"title":"降级并通知","active":true,"updated_at":"2022-08-23T07:22:43Z","created_at":"2022-07-13T03:21:17Z","default":false,"position":9999,"description":null,"actions":[{"field":"priority","value":"low"},{"field":"comment_value","value":"我们目前的访问量异常高。我们将尽快回复您。"}],"restriction":null,"raw_title":"降级并通知"}],"next_page":null,"previous_page":null,"count":24}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/macros}
    //200

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

            System.out.println("==========================");
            System.out.println(response.body().string());
            System.out.println(response);
            System.out.println(response.code());
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void macro_attachment_list(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl +"/api/v2/macros/11452657828761/attachments")
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
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }


    //导入宏附件	宏附件在相关宏中可用
    //https://developer.zendesk.com/api-reference/ticketing/business-rules/macros/#create-macro-attachment
    //{"macro_attachments":[],"next_page":null,"previous_page":null,"count":0}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/macros/21864577399956/attachments}
    //200
    //不会上传文件，还需要搜索
//    @Test
//    void demo1832(){
//        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
//        OkHttpClient client = new OkHttpClient();
//        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl +"/api/v2/macros/26907951414169/attachments")
//                .newBuilder();
//
//        // Replace "YourTextValue" with the actual text you want to send
//        String filename = "截屏2023-12-27_13.46.10.png";
//
//
//        MultipartBody body = new MultipartBody.Builder()
//                .addFormDataPart("attachment", filename, RequestBody.create("https://pdi-jinmuinfo.zendesk.com/api/v2/macros/attachments/26907951414169.json", MediaType.parse("application/json")))
//                .addFormDataPart("filename", filename)
//                .build();
//
//        Request request = new Request.Builder()
//                .url(urlBuilder.build())
//                .method("Post", body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//
//            System.out.println("==========================");
//            System.out.println(response.body().string());
//            System.out.println(response);
//            System.out.println(response.code());
//            System.out.println("==========================");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    void test888() throws IOException {
        // /api/v2/channels/voice/phone_numbers


        //不知道是没有私有组还是什么，反正查询出的全是公开组 为什么查出来包含分页参数？
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://pdi-jinmuinfo.zendesk.com/api/v2/macros/11452657828761/attachments")
                .newBuilder();
//                .addQueryParameter("sort_by", "")
//                .addQueryParameter("sort_order", "");

        String str = "{\"macro_attachment\":{\"url\":\"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/attachments/26907951414169.json\",\"id\":26907951414169,\"filename\":\"截屏2023-12-27_13.46.10.png\",\"content_type\":\"image/png\",\"content_url\":\"https://pdi-jinmuinfo.zendesk.com/api/v2/macros/attachments/26907951414169/content\",\"size\":3958,\"created_at\":\"2023-12-27T08:56:00Z\"}}\n";
        JSONObject jsonObject = JSONObject.parseObject(str);
        String url = jsonObject.getJSONObject("macro_attachment").getString("url");
        String filename = jsonObject.getJSONObject("macro_attachment").getString("filename");
        //构建请求体
//        File file = new File(url);
        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("attachment", filename, RequestBody.create(url, MediaType.parse("application/json")))
                .addFormDataPart("filename", filename)
                .build();

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
                .build();
        Response response = client.newCall(request).execute();
            System.out.println("==========================");
            System.out.println(response.body().string());
            System.out.println(response);
            System.out.println(response.code());
            System.out.println("==========================");

    }




    //{"automations":[{"url":"https://jinmutraining.zendesk.com/api/v2/automations/10332815310228.json","id":10332815310228,"title":"當狀態設定為已解決的 4 天後，關閉工單","active":true,"updated_at":"2023-09-27T08:37:24Z","created_at":"2022-10-25T03:50:29Z","default":true,"actions":[{"field":"status","value":"closed"}],"conditions":{"all":[{"field":"status","operator":"is","value":"solved"},{"field":"SOLVED","operator":"greater_than","value":"96"}],"any":[]},"position":0,"raw_title":"當狀態設定為已解決的 4 天後，關閉工單"},{"url":"https://jinmutraining.zendesk.com/api/v2/automations/10332821987220.json","id":10332821987220,"title":"24 小時未解決通知","active":false,"updated_at":"2022-11-14T15:43:24Z","created_at":"2022-10-25T03:50:29Z","default":true,"actions":[{"field":"notification_user","value":["requester_and_ccs","[{{ticket.account}}] 未解決請求：{{ticket.title}}","这封电邮是为了提醒您，您的请求（#{{ticket.id}}）正处于待回应状态，等待您的反馈。\n\n{{ticket.latest_public_comment_html}}"]}],"conditions":{"all":[{"field":"PENDING","operator":"is","value":"24"},{"field":"ticket_is_public","operator":"is","value":"public"}],"any":[]},"position":9998,"raw_title":"24 小時未解決通知"},{"url":"https://jinmutraining.zendesk.com/api/v2/automations/10332833163028.json","id":10332833163028,"title":"5 天未解決通知","active":false,"updated_at":"2022-11-14T15:43:25Z","created_at":"2022-10-25T03:50:29Z","default":true,"actions":[{"field":"notification_user","value":["requester_and_ccs","[{{ticket.account}}] 未解決請求：{{ticket.title}}","这封电邮是为了提醒您，您的请求（#{{ticket.id}}）已处于待回应状态达 5 天，正等待您的反馈。\n\n{{ticket.latest_public_comment_html}}"]}],"conditions":{"all":[{"field":"PENDING","operator":"is","value":"120"},{"field":"ticket_is_public","operator":"is","value":"public"}],"any":[]},"position":9999,"raw_title":"5 天未解決通知"},{"url":"https://jinmutraining.zendesk.com/api/v2/automations/10388174465684.json","id":10388174465684,"title":"提醒处理1天未处理的法律票据","active":true,"updated_at":"2022-10-27T04:09:20Z","created_at":"2022-10-27T04:09:20Z","default":false,"actions":[{"field":"notification_group","value":["10358017293332","【提醒】有一些法律相关的工单即将超时","你好，有一些法律相关的工单即将超时，请尽快处理。"]},{"field":"current_tags","value":"已提醒"}],"conditions":{"all":[{"field":"current_tags","operator":"includes","value":"Sales/Legal"},{"field":"status","operator":"less_than","value":"pending"},{"field":"custom_fields_10361288879380","operator":"is","value":"legal_questions"},{"field":"updated_at","operator":"greater_than","value":"24"},{"field":"current_tags","operator":"not_includes","value":"已提醒"}],"any":[]},"position":10000,"raw_title":"提醒处理1天未处理的法律票据"},{"url":"https://jinmutraining.zendesk.com/api/v2/automations/10388883314068.json","id":10388883314068,"title":"通知客户处理3个工作日以上未处理的工单","active":true,"updated_at":"2022-10-27T05:52:19Z","created_at":"2022-10-27T05:52:19Z","default":false,"actions":[{"field":"current_tags","value":"已提醒"},{"field":"notification_user","value":["requester_id","[{{ticket.account}}] [操作提醒] {{ticket.title}}","{{ticket.requester.name}} 你好：\n\n我们已经在3天前发送了后续的处理要求给您，\n\n{{ticket.latest_comment_html}}\n\n正在等待您的回复，请尽快提供进一步的信息以便我们为您提供更好的服务。\n\n{{ticket.description}}"]}],"conditions":{"all":[{"field":"PENDING","operator":"greater_than_business_hours","value":"72"},{"field":"status","operator":"is","value":"pending"},{"field":"current_tags","operator":"not_includes","value":"已提醒"}],"any":[]},"position":10001,"raw_title":"通知客户处理3个工作日以上未处理的工单"},{"url":"https://jinmutraining.zendesk.com/api/v2/automations/10389761824660.json","id":10389761824660,"title":"請求客戶滿意度（系統自行執行程式）","active":true,"updated_at":"2023-08-18T10:05:06Z","created_at":"2022-10-27T07:49:27Z","default":true,"actions":[{"field":"notification_user","value":["requester_id","請求 #{{ticket.id}}：您會如何評價您的支援服務？","哈囉 {{ticket.requester.name}}：\n\n我們將很樂於得到對我們客戶服務的意見。請按以下任一連結，用簡短的時間回答一個簡單的問題：\n\n{{satisfaction.rating_section}}\n\n這是此請求所涉及內容的提醒：\n\n{{ticket.comments_formatted}}\n"]},{"field":"satisfaction_score","value":"offered"}],"conditions":{"all":[{"field":"status","operator":"less_than","value":"closed"},{"field":"SOLVED","operator":"greater_than","value":"24"},{"field":"satisfaction_score","operator":"is","value":"unoffered"},{"field":"ticket_is_public","operator":"is","value":"public"}],"any":[]},"position":10002,"raw_title":"請求客戶滿意度（系統自行執行程式）"},{"url":"https://jinmutraining.zendesk.com/api/v2/automations/10390800524820.json","id":10390800524820,"title":"白金客户问卷调查","active":true,"updated_at":"2022-10-27T09:47:20Z","created_at":"2022-10-27T09:46:51Z","default":false,"actions":[{"field":"current_tags","value":"已发送满意度调查"},{"field":"notification_user","value":["requester_id","[{{ticket.account}}] 满意度调查","{{ticket.assignee.name}}，你好\n\n很高兴能为您解决问题，希望您能对我们的服务满意，为了不断提升我们的服务品质，希望您能对本次服务进行评价。\n1（不满意） - 10 （非常满意）"]}],"conditions":{"all":[{"field":"status","operator":"is","value":"solved"},{"field":"SOLVED","operator":"greater_than","value":"1"},{"field":"current_tags","operator":"not_includes","value":"已发送满意度调查"},{"field":"organization_id","operator":"is","value":"10361930146836"}],"any":[]},"position":10003,"raw_title":"白金客户问卷调查"}],"next_page":null,"previous_page":null,"count":7}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/automations}
    //200
    @Test
    void list_automations(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl +"/api/v2/automations")
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
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }
    //导入自行程序	自行程序在系统中可用
    //https://developer.zendesk.com/api-reference/ticketing/business-rules/automations/
    //{"automation":{"url":"https://jinmutraining.zendesk.com/api/v2/automations/21892343067284.json","id":21892343067284,"title":"Roger Wilco","active":true,"updated_at":"2023-12-15T02:12:45Z","created_at":"2023-12-15T02:12:45Z","default":false,"actions":[{"field":"priority","value":"high"}],"conditions":{"all":[{"field":"status","operator":"is","value":"open"},{"field":"priority","operator":"less_than","value":"high"}],"any":[]},"position":10004,"raw_title":"Roger Wilco"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/automations}
    //201
    @Test
    void demo1841(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/automations")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"automation\": {\"title\": \"Roger Wilco\", \"all\": [{ \"field\": \"status\", \"operator\": \"is\", \"value\": \"open\" }, { \"field\": \"priority\", \"operator\": \"less_than\", \"value\": \"high\" }], \"actions\": [{ \"field\": \"priority\", \"value\": \"high\" }]}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //导入SLA	SLA数据导入SLA设置
    //https://developer.zendesk.com/api-reference/ticketing/business-rules/sla_policies/#create-sla-policy
    //{"sla_policy":{"url":"https://jinmutraining.zendesk.com/api/v2/slas/policies/21892522948756.json","id":21892522948756,"title":"Incidents","description":"For urgent incidents, we will respond to tickets in 10 minutes","position":3,"filter":{"all":[{"field":"ticket_type_id","operator":"is","value":"2"}],"any":[]},"policy_metrics":[{"priority":"normal","metric":"first_reply_time","target":30,"business_hours":false},{"priority":"urgent","metric":"first_reply_time","target":10,"business_hours":false},{"priority":"low","metric":"requester_wait_time","target":180,"business_hours":false},{"priority":"normal","metric":"requester_wait_time","target":160,"business_hours":false},{"priority":"high","metric":"requester_wait_time","target":140,"business_hours":false},{"priority":"urgent","metric":"requester_wait_time","target":120,"business_hours":false}],"created_at":"2023-12-15T02:20:06Z","updated_at":"2023-12-15T02:20:06Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/slas/policies}
    //201
    @Test
    void demo1851(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/slas/policies")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
            "{\n" +
                    "        \"sla_policy\": {\n" +
                    "          \"title\": \"Incidents\",\n" +
                    "          \"description\": \"For urgent incidents, we will respond to tickets in 10 minutes\",\n" +
                    "          \"position\": 3,\n" +
                    "          \"filter\": {\n" +
                    "            \"all\": [\n" +
                    "              { \"field\": \"type\", \"operator\": \"is\", \"value\": \"incident\" }\n" +
                    "            ],\n" +
                    "            \"any\": []\n" +
                    "          },\n" +
                    "          \"policy_metrics\": [\n" +
                    "            { \"priority\": \"normal\", \"metric\": \"first_reply_time\", \"target\": 30, \"business_hours\": false },\n" +
                    "            { \"priority\": \"urgent\", \"metric\": \"first_reply_time\", \"target\": 10, \"business_hours\": false },\n" +
                    "            { \"priority\": \"low\", \"metric\": \"requester_wait_time\", \"target\": 180, \"business_hours\": false },\n" +
                    "            { \"priority\": \"normal\", \"metric\": \"requester_wait_time\", \"target\": 160, \"business_hours\": false },\n" +
                    "            { \"priority\": \"high\", \"metric\": \"requester_wait_time\", \"target\": 140, \"business_hours\": false },\n" +
                    "            { \"priority\": \"urgent\", \"metric\": \"requester_wait_time\", \"target\": 120, \"business_hours\": false }\n" +
                    "          ]\n" +
                    "        }\n" +
                    "      }");
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //{"group_sla_policies":[{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/group_slas/policies/01HHP43XAK6CC4BDTXJ29FNM6R.json","id":"01HHP43XAK6CC4BDTXJ29FNM6R","title":"测试SLA","description":null,"position":1,"filter":{"all":[{"field":"ticket_form_id","operator":"is","value":[8427015306521]}],"any":[]},"policy_metrics":[{"priority":"low","metric":"group_ownership_time","target":1440,"business_hours":false},{"priority":"normal","metric":"group_ownership_time","target":480,"business_hours":false},{"priority":"high","metric":"group_ownership_time","target":60,"business_hours":false},{"priority":"urgent","metric":"group_ownership_time","target":30,"business_hours":false}],"created_at":"2023-12-15T06:55:11Z","updated_at":"2023-12-15T06:55:11Z"}],"next_page":null,"previous_page":null,"count":1}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/group_slas/policies}
    //200
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/group_slas/policies}
    //200
    @Test
    void list_group_sla_policies(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/group_slas/policies")
                .newBuilder();

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }

    //error，没有数据进行测试
    //{"group_sla_policy":{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/group_slas/policies/01HHP6S5P7X18Z3ZPABD000EGQ.json","id":"01HHP6S5P7X18Z3ZPABD000EGQ","title":"测试SLA","description":null,"position":1,"filter":{"all":[{"field":"ticket_form_id","operator":"is","value":[8427015306521]}],"any":[]},"policy_metrics":[{"priority":"low","metric":"group_ownership_time","target":1440,"business_hours":false},{"priority":"normal","metric":"group_ownership_time","target":480,"business_hours":false},{"priority":"high","metric":"group_ownership_time","target":60,"business_hours":false},{"priority":"urgent","metric":"group_ownership_time","target":30,"business_hours":false}],"created_at":"2023-12-15T07:41:45Z","updated_at":"2023-12-15T07:41:45Z"}}
    //Response{protocol=h2, code=201, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/group_slas/policies}
    //201
    @Test
    void demo1852(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/group_slas/policies")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "        \"group_sla_policy\": {\n" +
                        "  \"title\": \"测试SLA\",\n" +
                        "  \"description\": null,\n" +
                        "  \"position\": 1,\n" +
                        "  \"filter\": {\n" +
                        "   \"all\": [{\n" +
                        "    \"field\": \"ticket_form_id\",\n" +
                        "    \"operator\": \"is\",\n" +
                        "    \"value\": [8427015306521]\n" +
                        "   }],\n" +
                        "   \"any\": []\n" +
                        "  },\n" +
                        "          \"policy_metrics\": [{\n" +
                        "   \"priority\": \"low\",\n" +
                        "   \"metric\": \"group_ownership_time\",\n" +
                        "   \"target\": 1440,\n" +
                        "   \"business_hours\": false\n" +
                        "  }, {\n" +
                        "   \"priority\": \"normal\",\n" +
                        "   \"metric\": \"group_ownership_time\",\n" +
                        "   \"target\": 480,\n" +
                        "   \"business_hours\": false\n" +
                        "  }, {\n" +
                        "   \"priority\": \"high\",\n" +
                        "   \"metric\": \"group_ownership_time\",\n" +
                        "   \"target\": 60,\n" +
                        "   \"business_hours\": false\n" +
                        "  }, {\n" +
                        "   \"priority\": \"urgent\",\n" +
                        "   \"metric\": \"group_ownership_time\",\n" +
                        "   \"target\": 30,\n" +
                        "   \"business_hours\": false\n" +
                        "  }]\n" +
                        "        }\n" +
                        "      }");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }


    //批量导入工单	工单数据导入系统
    //https://developer.zendesk.com/api-reference/ticketing/tickets/tickets/#create-many-tickets
    //{"job_status":{"id":"V3-fd9094bded49266866f61b5eb167114f","job_type":"Bulk Create Ticket","url":"https://jinmutraining.zendesk.com/api/v2/job_statuses/V3-fd9094bded49266866f61b5eb167114f.json","total":2,"progress":null,"status":"queued","message":null,"results":null}}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/tickets/create_many}
    //200
    @Test
    void demo1921(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/tickets/create_many")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"tickets\": [\n" +
                        "    {\n" +
                        "      \"comment\": {\n" +
                        "        \"body\": \"The smoke is very colorful.\"\n" +
                        "      },\n" +
                        "      \"priority\": \"urgent\",\n" +
                        "      \"subject\": \"My printer is on fire!\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"comment\": {\n" +
                        "        \"body\": \"This is a comment\"\n" +
                        "      },\n" +
                        "      \"priority\": \"normal\",\n" +
                        "      \"subject\": \"Help\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    
    //list requests
    //{"requests":[{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/33.json","id":33,"status":"closed","priority":null,"type":null,"subject":"你好能否给一些新的支持呢？","description":"阿斯顿发送到发斯蒂芬","organization_id":11456749118233,"via":{"channel":"side_conversation","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":"mongodb"},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2022-11-02T06:58:16Z","updated_at":"2023-01-05T02:14:51Z","recipient":null,"followup_source_id":null,"assignee_id":11942258898585,"ticket_form_id":8427015306521,"custom_status_id":8427030902553,"fields":[{"id":9774117095705,"value":"mongodb"},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/36.json","id":36,"status":"closed","priority":null,"type":null,"subject":"有一个客户说了一个问题","description":"这个问题具体是啥我没听懂","organization_id":11456749118233,"via":{"channel":"side_conversation","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[11946221287833],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2022-11-11T03:34:08Z","updated_at":"2023-01-05T02:14:52Z","recipient":null,"followup_source_id":null,"assignee_id":11942258898585,"ticket_form_id":8427015306521,"custom_status_id":8427030902553,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/49.json","id":49,"status":"closed","priority":null,"type":null,"subject":"[Flagged] 'MongoDB 安装'","description":"Knowledge Capture\n\n标记来自 工单 #48。 | 编辑文章\n\n# MongoDB 安装\n\nPermanently deleted user，最后编辑于 2022年9月1日\n\n1.分片集群概述\nMongoDB分片集群，英文名称为： Sharded Cluster\n\n旨在通过横向扩展，来提高数据吞吐性能、增大数据存储量。\n\n![](https://www.whaleal.com/file/topic/2022-06-25/image/5719d9862f9e402798e78a190ac0702bb28.png)\n\n这个图片能否美化一下，看起来不太清楚\n\n分片集群由三个组件：“mongos”, “config server”, “shard” 组成。\nmongos:数据库请求路由。负责接收所有客户端应用程序的连接查询请求，并将请求路由到集群内部对应的分片上。\"mongos\"可以有1个或多个。\nconfig server: 配置服务，负责保存集群的元数据信息，比如集群的分片信息、用户信息。\nMongoDB 3.4 版本以后，“config server” 必须是副本集！\nshard: 分片存储。将数据分片存储在多个服务器上。\n有点类似关系数据库\"分区表\"的概念，只不过分区表是将数据分散存储在多个文件中，而sharding将数据分散存储在多个服务器上。一个集群可以有一个或多个分片。\nMongoDB 3.6以后，每个分片都必须是副本集！\n\n2. 分片集群搭建步骤\n分片集群各部分组件搭建顺序（程序启动顺序也是如此）：\n\n“config server” -> 2. “shard” -> 3. “mongos”\n2.1 搭建\"config server\"\n\"config server\"由三台主机组成，每台主机上运行一个mongod进程，三台主机的mongod组成一个副本集。\n\n2.1.1 配置\"config server\" mongod.conf\ndbpath = /home/mongodb/data/db\nlogpath = /home/mongodb/app/mongodb-3.6/mongodb.log\n\nport = 27017\nbind_ip = 0.0.0.0\n\nfork = true                             # run background\n#nohttpinterface = true\n\nreplSet = rs-config\noplogSize = 2048                       #2G\n\nconfigsvr = true\n\n#auth = true\n#keyFile = /home/mongodb/app/mongodb-3.6/keyfile\n\n三台主机的mongod.conf配置相同。\n注意: 必须配置：“configsvr = true”\n\n2.1.2 启动mongod\n三台服务器上，依次启动mongod程序\n\nmongod --config /home/mongodb/app/mongodb-3.6/bin/mongod.conf\n1\n2.1.3 初始化\"config server\"副本集\n任选一个节点，执行初始化命令\n\nrs.initiate(\n  {\n     \"_id\" : \"rs-config\",\n     \"members\" : [\n       {\"_id\" : 0, \"host\" : \"192.168.6.22:27017\"},\n       {\"_id\" : 1, \"host\" : \"192.168.6.23:27017\"},\n       {\"_id\" : 2, \"host\" : \"192.168.6.24:27017\"}\n     ]\n  }\n\n2.2 搭建 \"shard\"分片服务器\n一共有2个分片，每个分片由3台mongod服务组成副本集。配置过程相同。\n\n2.2.1 配置 “shard” mongod.conf\n分片1配置(三个节点配置相同):\n\ndbpath = /home/mongodb/data/db\nlogpath = /home/mongodb/app/mongodb-3.6/mongodb.log\n\nport = 27017\nbind_ip = 0.0.0.0\n\nfork = true                             # run background\n#nohttpinterface = true\n\nreplSet = rs-shard1\noplogSize = 2048                       #2G\n\nshardsvr = true\n\n#auth = true\n#keyFile = /home/mongodb/app/mongodb-3.6/keyfile\n\n分片2配置(三个节点配置相同):\n\ndbpath = /home/mongodb/data/db\nlogpath = /home/mongodb/app/mongodb-3.6/mongodb.log\n\nport = 27017\nbind_ip = 0.0.0.0\n\nfork = true                             # run background\n#nohttpinterface = true\n\nreplSet = rs-shard2\noplogSize = 2048                       #2G\n\nshardsvr = true\n\n#auth = true\n#keyFile = /home/mongodb/app/mongodb-3.6/keyfile\n\n注：\n(1) 分片1和分片2，除了副本集名称不同外，其它配置相同。\n      replSet = rs-shard1\n      replSet = rs-shard2\n(2) 必须配置：“shardsvr = true”\n\n3.2.2 启动mongod\n依次启动两个分片的mongod程序\n\nmongod --config /home/mongodb/app/mongodb-3.6/bin/mongod.conf\n1\n3.2.3 初始化shard副本集\n分片1副本集初始化：任选分片内的一个节点，执行初始化命令\n\nrs.initiate(\n  {\n     \"_id\" : \"rs-shard1\",\n     \"members\" : [\n       {\"_id\" : 0, \"host\" : \"192.168.6.25:27017\"},\n       {\"_id\" : 1, \"host\" : \"192.168.6.26:27017\"},\n       {\"_id\" : 2, \"host\" : \"192.168.6.27:27017\"}\n     ]\n  }\n)\n\n分片2副本集初始化：连接\"primary\"主节点，执行初始化命令\n\nrs.initiate(\n  {\n     \"_id\" : \"rs-shard2\",\n     \"members\" : [\n       {\"_id\" : 0, \"host\" : \"192.168.6.28:27017\"},\n       {\"_id\" : 1, \"host\" : \"192.168.6.29:27017\"},\n       {\"_id\" : 2, \"host\" : \"192.168.6.30:27017\"}\n     ]\n  }\n)\n\n2.3 搭建\"mongos\"\nmongos可以为1个，也可以为多个。\n每个mongos配置相对独立，配置过程及参数相同。\n\n2.3.1 配置 “mongos.conf”\nlogpath = /home/mongodb/app/mongodb-3.6/mongos.log\nlogappend = true\n\nbind_ip = 0.0.0.0\nport = 27017\n\nfork = true\n\n#keyFile = /mongos/autokey\n\nconfigdb = rs-config/192.168.6.22:27017,192.168.6.23:27017,192.168.6.24:27017\n\n# max connections\nmaxConns=200\n\n2.3.2 启动\"mongos\"\nmongos --config /home/mongodb/app/mongodb-3.6/bin/mongos.conf\n1\n3.4 添加分片\n连接任意一个\"mongos\"，添加分片信息\n\nuse admin\n\nsh.addShard(\"rs-shard1/192.168.6.25:27017,192.168.6.26:27017,192.168.6.27:27017\")\n\nsh.addShard(\"rs-shard2/192.168.6.28:27017,192.168.6.29:27017,192.168.6.30:27017\")\n\n查看集群分片状态\n\nsh.status()\n至此，一个基本的\"sharded cluster\"，就已经搭建完成。\n3\n. 使用分片\n使用分片的基本步骤： 1. 开启数据库分片 -> 2. 开启集合分片\n对数据库分片是对集合分片的先决条件。\n\n3.1 开启数据库分片\n以数据库：\"testdb\"为例，开启数据库分片命令为：\n\nuse admin\n\nsh.enableSharding(\"testdb\")\n\n3.2 开启集合分片\n以集合\"testdb.coll1\"为例，开启集合分片命令为：\n\nsh.shardCollection(\"testdb.coll1\", {\"name\" : \"hashed\"})\n1\n说明：\n(1) 第一个参数为集合的完整namespace名称\n(2) 第二个参数为片键，指定根据哪个字段进行分片。\n  具体参考官网\"sh.shardCollection()\"说明\n\n4.3 插入数据验证数据分片\n测试插入数据\n\nuse testdb\n\nfor (var i = 1; i <= 100000; i++){\n  db.coll1.insert({\"id\" : i, \"name\" : \"name\" + i});\n}\n\n验证是否分片\n\nsh.status()\n\n\n结果显示，数据已均匀分布在两个分片上。\n\n4. 添加集群认证\n上文已经成功创建一个分片集群，并验证数据分片可用。\n但在部署生产环境时，还需添加认证，用以保障集群安全性。\n认证分两种：\n\n集群内部认证 (Internal Authentication)\n用于集群内的各个组件(mongos, config server, shard)之间相互访问认证，\n也就是所有的mongos进程和mongod进程之间相互访问认证。\n内部认证通过keyfile密钥文件实现，即所有的monogs/mongod公用同一个keyfile文件来相互认证。\n如果集群外随便来一个\"mongod\"进程，如果没有相同的keyfile，想加入集群，是不可能的。\n\n外部用户访问集群所需的用户认证 (User Access Controls)\n用于外部客户端访问mongos时，所需的用户认证。\n\n4.1 生成并分发密钥文件keyfile\n(1) 生成keyfile文件\n\nopenssl rand -base64 90 -out ./keyfile\n1\n(2) 更改\"keyfile\"密钥文件权限\n\nchmod 600 keyfile\n1\n(3) 将\"keyfile\"密钥文件拷贝到集群每一个mongos/mongod服务器上\n\n5.2 添加超级管理员用户\n连接任意一个\"mongos\"，创建超级管理员用户\n\nuse admin\n\ndb.createUser(\n    {\n        user: \"root\",\n        pwd: \"root\",\n        roles: [{role : \"root\", db : \"admin\"}]\n    }\n)\n\n在\"mongos\"上添加用户，用户信息实际保存在\"config server\"上，\"mongos\"本身不存储任何数据，包括用户信息。\n\n然而，\"mongos\"上创建的用户，是不会自动添加到\"shard\"分片服务器上的。\n为了以后方便维护shard分片服务器，分别登录到每个分片服务器的\"primary\"节点，添加管理员用户\n\nuse admin\n\ndb.createUser(\n  {\n    user: \"useradmin\",\n    pwd: \"useradmin\",\n    roles: [{ role: \"userAdminAnyDatabase\", db: \"admin\" }]\n  }\n)\n4.3 开启认证\n(1) 为所有\"monogd\"程序添加认证参数\n\nauth = true\nkeyFile = /home/mongodb/app/mongodb-3.6/keyfile\n\n(2) 为所有\"mongos\"程序添加认证参数\n\nkeyFile = /home/mongodb/app/mongodb-3.6/keyfile\n(3) 停止集群内所有mongod/mongos程序\n(4) 按照如下顺序启动所有程序：\n      1. “config server” -> 2. “shard” -> 3. “mongos”\n(5) 验证用户访问\n      通过连接任意一个mongos，验证用户访问\n\n\n附：开启用户认证后的最终配置文件\nconfig server: mongod.conf\n\ndbpath = /home/mongodb/data/db\nlogpath = /home/mongodb/app/mongodb-3.6/mongodb.log\n\nport = 27017\nbind_ip = 0.0.0.0\n\nfork = true                             # run background\n#nohttpinterface = true\n\nreplSet = rs-config\noplogSize = 2048                       #2G\n\nconfigsvr = true\n\nauth = true\nkeyFile = /home/mongodb/app/mongodb-3.6/keyfile\n\nshard1: mongod.conf\n\ndbpath = /home/mongodb/data/db\nlogpath = /home/mongodb/app/mongodb-3.6/mongodb.log\n\nport = 27017\nbind_ip = 0.0.0.0\n\nfork = true                             # run background\n#nohttpinterface = true\n\nreplSet = rs-shard1\noplogSize = 2048                       #2G\n\nshardsvr = true\n\nauth = true\nkeyFile = /home/mongodb/app/mongodb-3.6/keyfile\n\nshard2: mongod.conf\n\ndbpath = /home/mongodb/data/db\nlogpath = /home/mongodb/app/mongodb-3.6/mongodb.log\n\nport = 27017\nbind_ip = 0.0.0.0\n\nfork = true                             # run background\n#nohttpinterface = true\n\nreplSet = rs-shard2\noplogSize = 2048                       #2G\n\nshardsvr = true\n\nauth = true\nkeyFile = /home/mongodb/app/mongodb-3.6/keyfile\n\nmongos: mongos.conf\n\nlogpath = /home/mongodb/app/mongodb-3.6/mongos.log\nlogappend = true\n\nbind_ip = 0.0.0.0\nport = 27017\n\nfork = true\n\nkeyFile = /home/mongodb/app/mongodb-3.6/keyfile\n\nconfigdb = rs-config/192.168.6.22:27017,192.168.6.23:27017,192.168.6.24:27017\n\n# max connections\nmaxConns=200","organization_id":11456749118233,"via":{"channel":"api","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":"zendesk"},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-06-07T02:26:48Z","updated_at":"2023-06-30T07:13:01Z","recipient":null,"followup_source_id":null,"assignee_id":11942258898585,"ticket_form_id":8427015306521,"custom_status_id":8427030902553,"fields":[{"id":9774117095705,"value":"zendesk"},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/53.json","id":53,"status":"pending","priority":null,"type":null,"subject":"请求测试","description":"测试","organization_id":11456749118233,"via":{"channel":"web","source":{"from":{},"to":{},"rel":"help_center"}},"custom_fields":[{"id":9774117095705,"value":"zendesk"},{"id":10076189080985,"value":"s1"},{"id":17352695020569,"value":"dc.problem_1"}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-05T02:35:25Z","updated_at":"2023-07-05T07:47:54Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427030889497,"fields":[{"id":9774117095705,"value":"zendesk"},{"id":10076189080985,"value":"s1"},{"id":17352695020569,"value":"dc.problem_1"}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/54.json","id":54,"status":"open","priority":null,"type":null,"subject":"subticket aweer","description":"asdfasd","organization_id":11456749118233,"via":{"channel":"side_conversation","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":"zendesk"},{"id":10076189080985,"value":"s1"},{"id":17352695020569,"value":"dc.problem_1"}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":true,"created_at":"2023-07-05T07:47:55Z","updated_at":"2023-07-05T07:51:55Z","recipient":null,"followup_source_id":null,"assignee_id":11946146000281,"ticket_form_id":8427015306521,"custom_status_id":8427025599129,"fields":[{"id":9774117095705,"value":"zendesk"},{"id":10076189080985,"value":"s1"},{"id":17352695020569,"value":"dc.problem_1"}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/58.json","id":58,"status":"new","priority":null,"type":"question","subject":"請求建立自：什麼是社區？","description":"此請求建立自 Agent No.1 於2023年07月06日 06:07 UTC的貢獻。\n\n連結：https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/community/posts/8696106659225-%E4%BB%80%E9%BA%BC%E6%98%AF%E7%A4%BE%E5%8D%80-#community_comment_20368937230105\n\n--\n\n测试评论","organization_id":11456749118233,"via":{"channel":"help_center","source":{"from":{"post_id":20368937230105,"post_name":"什麼是社區？","post_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/community/posts/8696106659225-%E4%BB%80%E9%BA%BC%E6%98%AF%E7%A4%BE%E5%8D%80-#community_comment_20368937230105"},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-06T06:07:59Z","updated_at":"2023-07-06T06:07:59Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427015451417,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/73.json","id":73,"status":"new","priority":null,"type":null,"subject":"测试创建","description":"智能机器人文章推荐","organization_id":11456749118233,"via":{"channel":"web","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":"s3"},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-11T05:36:59Z","updated_at":"2023-07-11T05:36:59Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427015451417,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":"s3"},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/108.json","id":108,"status":"new","priority":null,"type":null,"subject":"test light angent","description":"123","organization_id":11456749118233,"via":{"channel":"side_conversation","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-26T06:36:36Z","updated_at":"2023-07-26T06:39:11Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427015451417,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/109.json","id":109,"status":"new","priority":null,"type":null,"subject":"1","description":"213","organization_id":11456749118233,"via":{"channel":"side_conversation","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-26T06:42:58Z","updated_at":"2023-07-26T06:42:58Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427015451417,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/110.json","id":110,"status":"new","priority":null,"type":null,"subject":"123","description":"123","organization_id":11456749118233,"via":{"channel":"side_conversation","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-26T06:46:19Z","updated_at":"2023-07-26T06:46:19Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427015451417,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/111.json","id":111,"status":"closed","priority":null,"type":null,"subject":"测试","description":"测试","organization_id":11456749118233,"via":{"channel":"side_conversation","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-26T07:05:43Z","updated_at":"2023-08-14T03:03:41Z","recipient":null,"followup_source_id":null,"assignee_id":11942258898585,"ticket_form_id":8427015306521,"custom_status_id":8427030902553,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/112.json","id":112,"status":"open","priority":null,"type":null,"subject":"1","description":"123","organization_id":11456749118233,"via":{"channel":"side_conversation","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-26T07:09:27Z","updated_at":"2023-10-20T07:26:57Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427025599129,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/113.json","id":113,"status":"pending","priority":null,"type":null,"subject":"测试","description":"测试内容","organization_id":11456749118233,"via":{"channel":"web","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":"s3"},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[11946605192345],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-26T07:12:57Z","updated_at":"2023-10-20T07:26:58Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427030889497,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":"s3"},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/114.json","id":114,"status":"closed","priority":null,"type":null,"subject":"测试","description":"测试1","organization_id":11456749118233,"via":{"channel":"side_conversation","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-26T07:13:22Z","updated_at":"2023-08-14T03:03:41Z","recipient":null,"followup_source_id":null,"assignee_id":11942258898585,"ticket_form_id":8427015306521,"custom_status_id":8427030902553,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/115.json","id":115,"status":"open","priority":null,"type":null,"subject":"111","description":"12","organization_id":11456749118233,"via":{"channel":"web","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":"s3"},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-26T07:28:24Z","updated_at":"2023-10-20T07:26:58Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427025599129,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":"s3"},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/120.json","id":120,"status":"open","priority":null,"type":null,"subject":"测试下拉框占位符07271012","description":"使用宏进行测试123","organization_id":11456749118233,"via":{"channel":"web","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":"s3"},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-27T02:13:11Z","updated_at":"2023-07-27T02:19:07Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427025599129,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":"s3"},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/121.json","id":121,"status":"new","priority":null,"type":null,"subject":"测试下拉字段-用户生日","description":"测试出现2002-02-02则成功","organization_id":11456749118233,"via":{"channel":"web","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":"s3"},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-27T02:31:59Z","updated_at":"2023-07-27T02:31:59Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427015451417,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":"s3"},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/122.json","id":122,"status":"closed","priority":null,"type":null,"subject":"测试用户生日","description":"2001-02-02","organization_id":11456749118233,"via":{"channel":"web","source":{"from":{},"to":{},"rel":null}},"custom_fields":[],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-27T02:58:36Z","updated_at":"2023-08-14T03:03:40Z","recipient":null,"followup_source_id":null,"assignee_id":11942258898585,"custom_status_id":8427030902553,"fields":[]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/127.json","id":127,"status":"open","priority":null,"type":null,"subject":"测试：用户字段忠实用户","description":"如果工单标签出现 忠实用户则成功","organization_id":11456749118233,"via":{"channel":"web","source":{"from":{},"to":{},"rel":null}},"custom_fields":[],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-07-28T07:09:11Z","updated_at":"2023-10-20T07:27:40Z","recipient":null,"followup_source_id":null,"assignee_id":null,"custom_status_id":8427025599129,"fields":[]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/136.json","id":136,"status":"new","priority":"urgent","type":null,"subject":"My printer is on fire!","description":"The smoke is very colorful.","organization_id":11456749118233,"via":{"channel":"api","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-09-20T03:16:31Z","updated_at":"2023-09-20T03:16:31Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427015451417,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}]},{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/137.json","id":137,"status":"new","priority":null,"type":null,"subject":"My printer111 is on fire!","description":"The smoke111 is very colorful.","organization_id":11456749118233,"via":{"channel":"api","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-09-20T03:33:58Z","updated_at":"2023-09-20T03:33:58Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427015451417,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}]}],"next_page":null,"previous_page":null,"count":21}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/requests}
    //200
    @Test
    void list_requests(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/requests")
                .newBuilder();

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }
    
    //工单请求者	工单请求者数据导入系统
    //https://developer.zendesk.com/api-reference/ticketing/tickets/ticket-requests/#create-request
    //{}
    //{"request":{"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/requests/152.json","id":152,"status":"new","priority":null,"type":null,"subject":"Help!","description":"My printer is on fire!","organization_id":11456749118233,"via":{"channel":"api","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}],"requester_id":11942258898585,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-12-20T02:11:10Z","updated_at":"2023-12-20T02:11:10Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":8427015306521,"custom_status_id":8427015451417,"fields":[{"id":9774117095705,"value":null},{"id":10076189080985,"value":null},{"id":17352695020569,"value":null}]}}
    //Response{protocol=h2, code=201, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/requests}
    //201
    //==========================
    //
    //Process finished with exit code 0
    //{"request":{"url":"https://jinmutraining.zendesk.com/api/v2/requests/90.json","id":90,"status":"new","priority":null,"type":null,"subject":"Help!","description":"My printer is on fire!\n\n\r\nLove your JinMu\r\nhttps://support.zendesk.com/hc/zh-cn/articles/4408886858138#topic_hmx_zzw_4v","organization_id":10332806750740,"via":{"channel":"api","source":{"from":{},"to":{},"rel":null}},"custom_fields":[{"id":10360943316500,"value":null},{"id":10361288879380,"value":null}],"requester_id":10332850159252,"collaborator_ids":[],"email_cc_ids":[],"is_public":true,"due_at":null,"can_be_solved_by_me":false,"created_at":"2023-12-25T05:09:16Z","updated_at":"2023-12-25T05:09:16Z","recipient":null,"followup_source_id":null,"assignee_id":null,"ticket_form_id":10361571109268,"custom_status_id":10332806749844,"fields":[{"id":10360943316500,"value":null},{"id":10361288879380,"value":null}]}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/requests}
    //201
    @Test
    void demo1922(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/requests")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "   \"request\":{\n" +
                        "      \"url\":\"https://jinmutraining.zendesk.com/api/v2/requests.json\",\n" +
                        "      \"status\":\"new\",\n" +
                        "      \"priority\":null,\n" +
                        "      \"type\":null,\n" +
                        "      \"subject\":\"Help!\",\n" +
                        "      \"description\":\"My printer is on fire!\",\n" +
                        "      \"organization_id\":22953461957140,\n" +
                        "      \"via\":{\n" +
                        "         \"channel\":\"api\",\n" +
                        "         \"source\":{\n" +
                        "            \"from\":{\n" +
                        "               \n" +
                        "            },\n" +
                        "            \"to\":{\n" +
                        "               \n" +
                        "            },\n" +
                        "            \"rel\":null\n" +
                        "         }\n" +
                        "      },\n" +
                        "      \"custom_fields\":[\n" +
                        "         {\n" +
                        "            \"id\":22953624086036,\n" +
                        "            \"value\":null\n" +
                        "         },\n" +
                        "         {\n" +
                        "            \"id\":22953659083284,\n" +
                        "            \"value\":null\n" +
                        "         },\n" +
                        "         {\n" +
                        "            \"id\":22953614968980,\n" +
                        "            \"value\":null\n" +
                        "         }\n" +
                        "      ],\n" +
                        "      \"requester_id\":10332850159252,\n" +
                        "      \"collaborator_ids\":[\n" +
                        "         \n" +
                        "      ],\n" +
                        "      \"email_cc_ids\":[\n" +
                        "         \n" +
                        "      ],\n" +
                        "      \"is_public\":true,\n" +
                        "      \"followup_source_id\":null,\n" +
                        "      \"assignee_id\":null,\n" +
                        "      \"ticket_form_id\":10361571109268,\n" +
                        "      \"custom_status_id\":10332806749844,\n" +
                        "      \"fields\":[\n" +
                        "         {\n" +
                        "            \"id\":22953624086036,\n" +
                        "            \"value\":null\n" +
                        "         },\n" +
                        "         {\n" +
                        "            \"id\":22953659083284,\n" +
                        "            \"value\":null\n" +
                        "         },\n" +
                        "         {\n" +
                        "            \"id\":22953614968980,\n" +
                        "            \"value\":null\n" +
                        "         }\n" +
                        "      ]\n" +
                        "   }\n" +
                        "}");
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }



    //自定义工单状态	工单状态导入系统并可用
    //https://developer.zendesk.com/api-reference/ticketing/tickets/custom_ticket_statuses/#create-custom-ticket-status
    //{"custom_status":{"url":"https://jinmutraining.zendesk.com/api/v2/custom_statuses/21893869013780.json","id":21893869013780,"status_category":"open","agent_label":"Responding quickly","raw_agent_label":"Responding quickly","end_user_label":"Urgent processing","raw_end_user_label":"Urgent processing","description":"Responding quickly","raw_description":"Responding quickly","end_user_description":"Responding quickly","raw_end_user_description":"Responding quickly","active":true,"default":false,"created_at":"2023-12-15T03:45:54Z","updated_at":"2023-12-15T03:45:54Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/custom_statuses}
    //201
    @Test
    void demo1931(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/custom_statuses")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"custom_status\": {\"status_category\": \"open\", \"agent_label\": \"Responding quickly\", \"end_user_label\": \"Urgent processing\", \"description\": \"Responding quickly\", \"end_user_description\": \"Responding quickly\"}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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

    }





    @Test
    void list_sharing_agreements(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/sharing_agreements")
                .newBuilder();

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }

    //创建工单共享协议	协议数据导入系统
    //https://developer.zendesk.com/api-reference/ticketing/tickets/sharing_agreements/#create-sharing-agreement
    //==========================
    //{"error":"RecordInvalid","description":"Record validation errors","details":{"base":[{"description":"URI： 无效"}]}}
    //Response{protocol=h2, code=422, message=, url=https://jinmutraining.zendesk.com/api/v2/sharing_agreements}
    //422
    //==========================
    @Test
    void demo1941(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/sharing_agreements")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"sharing_agreement\": {\"remote_subdomain\": \"jinmutraining\"}}");
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //导入满意度评级	满意度评级导入系统并可用
    //https://developer.zendesk.com/api-reference/ticketing/ticket-management/satisfaction_ratings/#create-a-satisfaction-rating
    //无权限
    //{
    //  "error": {
    //    "title": "Forbidden",
    //    "message": "You do not have access to this page. Please contact the account owner of this help desk for further help."
    //  }
    //}
    //
    //Response{protocol=h2, code=403, message=, url=https://jinmutraining.zendesk.com/api/v2/tickets/1/satisfaction_rating}
    //403
    @Test
    void demo11011(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/tickets/42/satisfaction_rating")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"satisfaction_rating\": {\"score\": \"good\", \"comment\": \"Awesome support.\"}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }
    @Test
    void list_ticket(){
//        long abc = 24920009118105L;
//        System.out.println(abc);
        String sourceUrl = "https://jinmu6532.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl +"/api/v2/tickets")
                .newBuilder();
                //.addQueryParameter("external_id", "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("yqingyuan@sohu.com", "123456"))
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
    }



    //{"schedules":[{"id":10358680386836,"name":"Tier 1","time_zone":"American Samoa","created_at":"2022-10-26T02:16:08Z","updated_at":"2023-12-15T05:57:02Z","intervals":[{"start_time":1860,"end_time":2580},{"start_time":3300,"end_time":4020},{"start_time":4740,"end_time":5460},{"start_time":6180,"end_time":6900},{"start_time":7620,"end_time":8340},{"start_time":9060,"end_time":9780}]},{"id":10358937417364,"name":"Tier 2","time_zone":"Pacific Time (US & Canada)","created_at":"2022-10-26T02:34:09Z","updated_at":"2022-10-26T02:34:09Z","intervals":[{"start_time":1980,"end_time":2580},{"start_time":3420,"end_time":4020},{"start_time":4860,"end_time":5460},{"start_time":6300,"end_time":6900},{"start_time":7740,"end_time":8340},{"start_time":9180,"end_time":9420}]},{"id":10358991427604,"name":"Tier 3","time_zone":"Pacific Time (US & Canada)","created_at":"2022-10-26T02:36:02Z","updated_at":"2022-10-26T02:36:27Z","intervals":[{"start_time":1920,"end_time":2640},{"start_time":3360,"end_time":4080},{"start_time":4800,"end_time":5520},{"start_time":6240,"end_time":6960},{"start_time":7680,"end_time":8400},{"start_time":9120,"end_time":9420}]},{"id":10360895806356,"name":"Lightning & Thunder","time_zone":"Pacific Time (US & Canada)","created_at":"2022-10-26T06:00:07Z","updated_at":"2022-10-26T06:00:08Z","intervals":[{"start_time":1860,"end_time":2580},{"start_time":3300,"end_time":4020},{"start_time":4740,"end_time":5460},{"start_time":6180,"end_time":6900},{"start_time":7620,"end_time":8340}]},{"id":10360865142036,"name":"Stormcloud","time_zone":"Pacific Time (US & Canada)","created_at":"2022-10-26T06:00:53Z","updated_at":"2022-10-26T06:00:54Z","intervals":[{"start_time":1980,"end_time":2520},{"start_time":3420,"end_time":3960},{"start_time":4860,"end_time":5400},{"start_time":6300,"end_time":6840},{"start_time":7740,"end_time":8280}]},{"id":16966666197396,"name":"国庆假期","time_zone":"Beijing","created_at":"2023-06-30T07:06:54Z","updated_at":"2023-06-30T07:06:56Z","intervals":[{"start_time":1980,"end_time":2460},{"start_time":3420,"end_time":3900},{"start_time":4860,"end_time":5340},{"start_time":6300,"end_time":6780},{"start_time":7740,"end_time":8220}]}],"url":"https://jinmutraining.zendesk.com/api/v2/business_hours/schedules"}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/business_hours/schedules}
    //200
    @Test
    void list_schedule(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl +"/api/v2/business_hours/schedules")
                .newBuilder();
        //.addQueryParameter("external_id", "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }
    //导入时间表	时间表导入系统并可用
    //https://developer.zendesk.com/api-reference/ticketing/ticket-management/schedules/#create-schedule
    //{"schedule":{"id":26430012774297,"name":"East Coast","time_zone":"Eastern Time (US & Canada)","created_at":"2023-12-15T05:52:05Z","updated_at":"2023-12-15T05:52:05Z","intervals":[{"start_time":1980,"end_time":2460},{"start_time":3420,"end_time":3900},{"start_time":4860,"end_time":5340},{"start_time":6300,"end_time":6780},{"start_time":7740,"end_time":8220}]},"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/business_hours/schedules/26430012774297"}
    //Response{protocol=h2, code=201, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/business_hours/schedules}
    //201
    //在jinmutraining里没成功
    //Response{protocol=h2, code=422, message=, url=https://jinmutraining.zendesk.com/api/v2/business_hours/schedules}
    //422
    @Test
    void demo11021(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/business_hours/schedules")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "   \"schedules\":[\n" +
                        "      {\n" +
                        "         \"id\":10358680386836,\n" +
                        "         \"name\":\"Tier 1\",\n" +
                        "         \"time_zone\":\"American Samoa\",\n" +
                        "         \"created_at\":\"2022-10-26T02:16:08Z\",\n" +
                        "         \"updated_at\":\"2023-12-15T05:57:02Z\",\n" +
                        "         \"intervals\":[\n" +
                        "            {\n" +
                        "               \"start_time\":1860,\n" +
                        "               \"end_time\":2580\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":3300,\n" +
                        "               \"end_time\":4020\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":4740,\n" +
                        "               \"end_time\":5460\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":6180,\n" +
                        "               \"end_time\":6900\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":7620,\n" +
                        "               \"end_time\":8340\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":9060,\n" +
                        "               \"end_time\":9780\n" +
                        "            }\n" +
                        "         ]\n" +
                        "      },\n" +
                        "      {\n" +
                        "         \"id\":10358937417364,\n" +
                        "         \"name\":\"Tier 2\",\n" +
                        "         \"time_zone\":\"Pacific Time (US & Canada)\",\n" +
                        "         \"created_at\":\"2022-10-26T02:34:09Z\",\n" +
                        "         \"updated_at\":\"2022-10-26T02:34:09Z\",\n" +
                        "         \"intervals\":[\n" +
                        "            {\n" +
                        "               \"start_time\":1980,\n" +
                        "               \"end_time\":2580\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":3420,\n" +
                        "               \"end_time\":4020\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":4860,\n" +
                        "               \"end_time\":5460\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":6300,\n" +
                        "               \"end_time\":6900\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":7740,\n" +
                        "               \"end_time\":8340\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":9180,\n" +
                        "               \"end_time\":9420\n" +
                        "            }\n" +
                        "         ]\n" +
                        "      },\n" +
                        "      {\n" +
                        "         \"id\":10358991427604,\n" +
                        "         \"name\":\"Tier 3\",\n" +
                        "         \"time_zone\":\"Pacific Time (US & Canada)\",\n" +
                        "         \"created_at\":\"2022-10-26T02:36:02Z\",\n" +
                        "         \"updated_at\":\"2022-10-26T02:36:27Z\",\n" +
                        "         \"intervals\":[\n" +
                        "            {\n" +
                        "               \"start_time\":1920,\n" +
                        "               \"end_time\":2640\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":3360,\n" +
                        "               \"end_time\":4080\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":4800,\n" +
                        "               \"end_time\":5520\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":6240,\n" +
                        "               \"end_time\":6960\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":7680,\n" +
                        "               \"end_time\":8400\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":9120,\n" +
                        "               \"end_time\":9420\n" +
                        "            }\n" +
                        "         ]\n" +
                        "      },\n" +
                        "      {\n" +
                        "         \"id\":10360895806356,\n" +
                        "         \"name\":\"Lightning & Thunder\",\n" +
                        "         \"time_zone\":\"Pacific Time (US & Canada)\",\n" +
                        "         \"created_at\":\"2022-10-26T06:00:07Z\",\n" +
                        "         \"updated_at\":\"2022-10-26T06:00:08Z\",\n" +
                        "         \"intervals\":[\n" +
                        "            {\n" +
                        "               \"start_time\":1860,\n" +
                        "               \"end_time\":2580\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":3300,\n" +
                        "               \"end_time\":4020\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":4740,\n" +
                        "               \"end_time\":5460\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":6180,\n" +
                        "               \"end_time\":6900\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":7620,\n" +
                        "               \"end_time\":8340\n" +
                        "            }\n" +
                        "         ]\n" +
                        "      },\n" +
                        "      {\n" +
                        "         \"id\":10360865142036,\n" +
                        "         \"name\":\"Stormcloud\",\n" +
                        "         \"time_zone\":\"Pacific Time (US & Canada)\",\n" +
                        "         \"created_at\":\"2022-10-26T06:00:53Z\",\n" +
                        "         \"updated_at\":\"2022-10-26T06:00:54Z\",\n" +
                        "         \"intervals\":[\n" +
                        "            {\n" +
                        "               \"start_time\":1980,\n" +
                        "               \"end_time\":2520\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":3420,\n" +
                        "               \"end_time\":3960\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":4860,\n" +
                        "               \"end_time\":5400\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":6300,\n" +
                        "               \"end_time\":6840\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":7740,\n" +
                        "               \"end_time\":8280\n" +
                        "            }\n" +
                        "         ]\n" +
                        "      },\n" +
                        "      {\n" +
                        "         \"id\":16966666197396,\n" +
                        "         \"name\":\"国庆假期\",\n" +
                        "         \"time_zone\":\"Beijing\",\n" +
                        "         \"created_at\":\"2023-06-30T07:06:54Z\",\n" +
                        "         \"updated_at\":\"2023-06-30T07:06:56Z\",\n" +
                        "         \"intervals\":[\n" +
                        "            {\n" +
                        "               \"start_time\":1980,\n" +
                        "               \"end_time\":2460\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":3420,\n" +
                        "               \"end_time\":3900\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":4860,\n" +
                        "               \"end_time\":5340\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":6300,\n" +
                        "               \"end_time\":6780\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"start_time\":7740,\n" +
                        "               \"end_time\":8220\n" +
                        "            }\n" +
                        "         ]\n" +
                        "      }\n" +
                        "   ],\n" +
                        "   \"url\":\"https://jinmutraining.zendesk.com/api/v2/business_hours/schedules\"\n" +
                        "}");
        Request request = new Request.Builder()
                    .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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

    }

    @Test
    void list_schedules(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl +"/api/v2/business_hours/schedules")
                .newBuilder();
        //.addQueryParameter("external_id", "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }
    //导入假期	假期导入系统并可用
    //https://developer.zendesk.com/api-reference/ticketing/ticket-management/schedules/#create-holiday
    //{"holiday":{"id":21895557889556,"name":"New Year","start_date":"2021-12-30","end_date":"2022-01-02"},"url":"https://jinmutraining.zendesk.com/api/v2/business_hours/schedules/10358680386836/holidays/21895557889556"}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/business_hours/schedules/10358680386836/holidays}
    //201
    @Test
    void demo11022(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/business_hours/schedules/10358680386836/holidays")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"holiday\": {\"name\": \"New Year\", \"start_date\": \"2021-12-30\", \"end_date\": \"2022-01-02\"}}");
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //{"attributes":[{"url":"https://jinmutraining.zendesk.com/api/v2/routing/attributes/ff06434e-54ce-11ed-849a-cfbec30b8828.json","id":"ff06434e-54ce-11ed-849a-cfbec30b8828","name":"语言","created_at":"2022-10-26T01:39:13Z","updated_at":"2022-10-26T01:39:13Z"}],"next_page":null,"previous_page":null,"count":1}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/routing/attributes}
    //200
    @Test
    void list_attributes(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl +"/api/v2/routing/attributes\n")
                .newBuilder();
        //.addQueryParameter("external_id", "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }
    //导入属性	属性数据导入系统并可用
    //https://developer.zendesk.com/api-reference/ticketing/ticket-management/skill_based_routing/#create-attribute

    //{"attribute":{"url":"https://jinmutraining.zendesk.com/api/v2/routing/attributes/8270dab1-9b10-11ee-8fda-b181d1a14037.json","id":"8270dab1-9b10-11ee-8fda-b181d1a14037","name":"Language","created_at":"2023-12-15T06:09:30Z","updated_at":"2023-12-15T06:09:30Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/routing/attributes}
    //201
    @Test
    void demo11031(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/routing/attributes")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"attribute\": { \"name\": \"Language\" }}");
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //导入属性值	属性值导入系统并可用
    //https://developer.zendesk.com/api-reference/ticketing/ticket-management/skill_based_routing/#create-attribute-value
    //{"attribute_value":{"url":"https://jinmutraining.zendesk.com/api/v2/routing/attributes/8270dab1-9b10-11ee-8fda-b181d1a14037/values/c99a6eca-7ee7-4eac-be97-dc73ee74e160.json","id":"c99a6eca-7ee7-4eac-be97-dc73ee74e160","name":"Japanese","created_at":"2023-12-15T06:12:55Z","updated_at":"2023-12-15T06:12:55Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/routing/attributes/8270dab1-9b10-11ee-8fda-b181d1a14037/values}
    //201
    @Test
    void demo11032(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/routing/attributes/8270dab1-9b10-11ee-8fda-b181d1a14037/values")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"attribute_value\": { \"name\": \"Japanese\" }}");
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //导入资源	资源导入系统并可用
    //https://developer.zendesk.com/api-reference/ticketing/ticket-management/resource_collections/#create-resource-collection
    //{"job_status":{"id":"V3-ba16ace90aba7592d882de368074934b","job_type":"Create Collection Resources","url":"https://jinmutraining.zendesk.com/api/v2/job_statuses/V3-ba16ace90aba7592d882de368074934b.json","total":null,"progress":null,"status":"queued","message":null,"results":null}}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/resource_collections}
    //200
    @Test
    void demo11041(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/resource_collections")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"payload\": {\n" +
                        "    \"ticket_fields\": {\n" +
                        "      \"support_description\": {\n" +
                        "        \"type\": \"text\",\n" +
                        "        \"title\": \"Support description\"\n" +
                        "      }\n" +
                        "    },\n" +
                        "    \"triggers\": {\n" +
                        "      \"email_on_ticket_solved\": {\n" +
                        "        \"title\": \"Email on ticket solved Trigger\",\n" +
                        "        \"all\": [\n" +
                        "          {\n" +
                        "            \"field\": \"status\",\n" +
                        "            \"operator\": \"is\",\n" +
                        "            \"value\": \"solved\"\n" +
                        "          }\n" +
                        "        ],\n" +
                        "        \"actions\": [\n" +
                        "          {\n" +
                        "            \"field\": \"notification_user\",\n" +
                        "            \"value\": [\n" +
                        "              \"all_agents\",\n" +
                        "              \"[{{ticket.account}}] {{ticket.title}}\",\n" +
                        "              \"A ticket (#{{ticket.id}}) by {{ticket.requester.name}} has been received. It is unassigned.\\n\\n{{ticket.comments_formatted}}\"\n" +
                        "            ]\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }\n" +
                        "}");
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //training 没有权限
    //pdi有权限但是返回值为空

    @Test
    void list_workspaces(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl +"/api/v2/workspaces\n")
                .newBuilder();
        //.addQueryParameter("external_id", "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }
    //导入工作空间	工作空间导入系统并可用
    //https://developer.zendesk.com/api-reference/ticketing/ticket-management/workspaces/#create-workspace
    //==========================
    //Unprocessable Entity
    //Response{protocol=h2, code=422, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/workspaces}
    //422
    //==========================
    //
    //Process finished with exit code 0
    //pdi:
    //{
    //  "error": {
    //    "title": "Forbidden",
    //    "message": "You do not have access to this page. Please contact the account owner of this help desk for further help."
    //  }
    //}
    @Test

    void demo11051(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/workspaces")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"workspace\": {\n" +
                        "    \"conditions\": {\n" +
                        "      \"all\": [\n" +
                        "        {\n" +
                        "          \"field\": \"status\",\n" +
                        "          \"operator\": \"is\",\n" +
                        "          \"value\": \"solved\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"any\": []\n" +
                        "    },\n" +
                        "    \"description\": \"Test rules\",\n" +
                        "    \"macros\": [\n" +
                        "      26631826097689\n" +
                        "    ],\n" +
                        "    \"ticket_form_id\": 8427015306521,\n" +
                        "    \"title\": \"Test Workspace 1\"\n" +
                        "  }\n" +
                        "}");
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    //list 出来都是空值，所以后面测试无法进行
    //{"events":[{"id":"3fa4b0c5-1b08-11ee-8e6d-c4781a10dd9b","side_conversation_id":"3fa4b0c5-1b08-11ee-8e6d-c4781a10dd9b","actor":{"user_id":11942258898585,"name":"Agent No.1","email":"user1@yzm.de"},"type":"create","via":"support","created_at":"2023-07-05T07:47:53.651Z","message":{"subject":"subticket aweer","preview_text":"asdfasd","from":{"user_id":11942258898585,"name":"Agent No.1","email":"user1@yzm.de"},"to":[{"user_id":11942258898585,"group_id":8427015474329,"name":"支持/Agent No.1","email":"user1@yzm.de"}],"body":"asdfasd","html_body":"\u003cdiv class=\"zd-comment\"\u003e\n\u003cdiv data-comment-type=\"body\"\u003e\n\u003cdiv\u003easdfasd\u003c/div\u003e\n\u003c/div\u003e\n\u003c/div\u003e","external_ids":{"ticketAuditId":"20334066107545"},"attachments":[]},"updates":{},"ticket_id":53}],"next_page":null,"previous_page":null,"count":1}
    @Test
    void list_side_conversations(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl +"/api/v2/tickets/120/side_conversations/58915698-2c23-11ee-8c20-d53a6c4e313d/events")
                .newBuilder();
        //.addQueryParameter("external_id", "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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
    }

    //{"side_conversation":{"url":"https://jinmutraining.zendesk.com/api/v2/tickets/2/side_conversations/52e5d9fd-9fe0-11ee-a420-9915bd2d4317","id":"52e5d9fd-9fe0-11ee-a420-9915bd2d4317","ticket_id":2,"subject":"My printer is on fire!","preview_text":"The smoke is very colorful.","state":"open","participants":[{"user_id":10332801830548,"name":"Sample customer","email":"foo@bar.com"},{"user_id":10332850159252,"name":"Agent One","email":"user1@nqmo.com"}],"created_at":"2023-12-21T09:07:10.734Z","updated_at":"2023-12-21T09:07:10.734Z","message_added_at":"2023-12-21T09:07:10.734Z","state_updated_at":"2023-12-21T09:07:10.734Z","external_ids":{}},"event":{"id":"52e5d9fd-9fe0-11ee-a420-9915bd2d4317","side_conversation_id":"52e5d9fd-9fe0-11ee-a420-9915bd2d4317","actor":{"user_id":10332850159252,"name":"Agent One","email":"user1@nqmo.com"},"type":"create","via":"api","created_at":"2023-12-21T09:07:10.734Z","message":{"subject":"My printer is on fire!","preview_text":"The smoke is very colorful.","from":{"user_id":10332850159252,"name":"Agent One","email":"user1@nqmo.com"},"to":[{"user_id":10332801830548,"name":"Sample customer","email":"foo@bar.com"}],"body":"The smoke is very colorful.","html_body":"\u003cdiv class=\"zd-comment\"\u003e\n\u003cp\u003eThe smoke is very colorful.\u003c/p\u003e\n\u003c/div\u003e","external_ids":{},"attachments":[]},"updates":{},"ticket_id":2}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/tickets/2/side_conversations}
    //201
    //
    //导入协助对话	协助对话导入系统并可用
    //https://developer.zendesk.com/api-reference/ticketing/side_conversation/side_conversation/#create-side-conversation
    @Test
    void demo11111(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/tickets/705/side_conversations/import")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "   \"side_conversation\":{\n" +
                        "      \"url\":\"https://pdi-jinmuinfo.zendesk.com/api/v2/tickets/120/side_conversations/58915698-2c23-11ee-8c20-d53a6c4e313d\",\n" +
                        "      \"id\":\"58915698-2c23-11ee-8c20-d53a6c4e313d\",\n" +
                        "      \"ticket_id\":705,\n" +
                        "      \"subject\":\"下拉框占位符测试相关宏是否运行成功\",\n" +
                        "      \"preview_text\":\"我不能看见这个下拉框\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n 周星宇\\nxingyu.zhou@jinmuinfo.com\\n  \\n\\n\\n\\n\\n\\n\\n\\n\\nOriginal:\\nFrom：AAAA (Shanghai Jinmu Information Technology Co.,)\\u003csupport@pdi-jinmuinfo.zendesk.com\\u003eDate：2023-07-27 10:14:41(中国 (GMT+08:00))To：zendesk support\\u003czendesk-support@jinmuinfo.com\\u003eCc：Subject：下拉框占位符测试相关宏是否运行成功如\",\n" +
                        "      \"state\":\"open\",\n" +
                        "      \"participants\":[\n" +
                        "         {\n" +
                        "            \"user_id\":10332850159252,\n" +
                        "            \"name\":\"Agent No.1\",\n" +
                        "            \"email\":\"user1@yzm.de\"\n" +
                        "         },\n" +
                        "         {\n" +
                        "            \"user_id\":22738814337684,\n" +
                        "            \"name\":\"Zendesk-support\",\n" +
                        "            \"email\":\"zendesk-support@jinmuinfo.com\"\n" +
                        "         },\n" +
                        "         {\n" +
                        "            \"user_id\":22738877704084,\n" +
                        "            \"name\":\"Xingyu Zhou\",\n" +
                        "            \"email\":\"xingyu.zhou@jinmuinfo.com\"\n" +
                        "         }\n" +
                        "      ],\n" +
                        "      \"created_at\":\"2023-07-27T02:14:41.645Z\",\n" +
                        "      \"updated_at\":\"2023-07-27T02:19:08.016Z\",\n" +
                        "      \"message_added_at\":\"2023-07-27T02:15:38.028Z\",\n" +
                        "      \"state_updated_at\":\"2023-07-27T02:19:07.338Z\",\n" +
                        "      \"external_ids\":{\n" +
                        "         \n" +
                        "      }\n" +
                        "   }\n" +
                        "   \n" +
                        "\n" +
                        "\n" +
                        "   {\n" +
                        "   \"events\":[\n" +
                        "      {\n" +
                        "         \"id\":\"58915698-2c23-11ee-8c20-d53a6c4e313d\",\n" +
                        "         \"side_conversation_id\":\"58915698-2c23-11ee-8c20-d53a6c4e313d\",\n" +
                        "         \"actor\":{\n" +
                        "            \"user_id\":10332850159252,\n" +
                        "            \"name\":\"Agent No.1\",\n" +
                        "            \"email\":\"user1@yzm.de\"\n" +
                        "         },\n" +
                        "         \"type\":\"create\",\n" +
                        "         \"via\":\"support\",\n" +
                        "         \"created_at\":\"2023-07-27T02:14:41.645Z\",\n" +
                        "         \"message\":{\n" +
                        "            \"subject\":\"下拉框占位符测试相关宏是否运行成功\",\n" +
                        "            \"preview_text\":\"如果出现就成功了\",\n" +
                        "            \"from\":{\n" +
                        "               \"user_id\":10332850159252,\n" +
                        "               \"name\":\"Agent No.1\",\n" +
                        "               \"email\":\"user1@yzm.de\"\n" +
                        "            },\n" +
                        "            \"to\":[\n" +
                        "               {\n" +
                        "                  \"user_id\":22738814337684,\n" +
                        "                  \"name\":\"Zendesk-support\",\n" +
                        "                  \"email\":\"zendesk-support@jinmuinfo.com\"\n" +
                        "               }\n" +
                        "            ],\n" +
                        "            \"body\":\"如果出现就成功了\",\n" +
                        "            \"html_body\":\"\\u003cdiv class=\\\"zd-comment\\\"\\u003e\\n\\u003cdiv data-comment-type=\\\"body\\\"\\u003e\\n\\u003cdiv\\u003e如果出现就成功了\\u003c/div\\u003e\\n\\u003c/div\\u003e\\n\\u003c/div\\u003e\",\n" +
                        "            \"external_ids\":{\n" +
                        "               \"outboundEmail\":\"\\u003cc11n+58915698-2c23-11ee-8c20-d53a6c4e313d+58915698-2c23-11ee-8c20-d53a6c4e313d+245bfb0@zendesk.com\\u003e\",\n" +
                        "               \"ticketAuditId\":\"21115782495897\"\n" +
                        "            },\n" +
                        "            \"attachments\":[\n" +
                        "               \n" +
                        "            ]\n" +
                        "         },\n" +
                        "         \"updates\":{\n" +
                        "            \n" +
                        "         },\n" +
                        "         \"ticket_id\":705\n" +
                        "      },\n" +
                        "      {\n" +
                        "         \"id\":\"7a2c8abc-2c23-11ee-a6b4-a68d6787bbb6\",\n" +
                        "         \"side_conversation_id\":\"58915698-2c23-11ee-8c20-d53a6c4e313d\",\n" +
                        "         \"actor\":{\n" +
                        "            \"user_id\":21115855376537,\n" +
                        "            \"name\":\"Xingyu Zhou\",\n" +
                        "            \"email\":\"xingyu.zhou@jinmuinfo.com\"\n" +
                        "         },\n" +
                        "         \"type\":\"reply\",\n" +
                        "         \"via\":\"email\",\n" +
                        "         \"created_at\":\"2023-07-27T02:15:38.028Z\",\n" +
                        "         \"message\":{\n" +
                        "            \"subject\":\"Re:下拉框占位符测试相关宏是否运行成功\",\n" +
                        "            \"preview_text\":\"我不能看见这个下拉框\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n 周星宇\\nxingyu.zhou@jinmuinfo.com\\n  \\n\\n\\n\\n\\n\\n\\n\\n\\nOriginal:\\nFrom：AAAA (Shanghai Jinmu Information Technology Co.,)\\u003csupport@pdi-jinmuinfo.zendesk.com\\u003eDate：2023-07-27 10:14:41(中国 (GMT+08:00))To：zendesk support\\u003czendesk-support@jinmuinfo.com\\u003eCc：Subject：下拉框占位符测试相关宏是否运行成功如\",\n" +
                        "            \"from\":{\n" +
                        "               \"user_id\":22738877704084,\n" +
                        "               \"name\":\"Xingyu Zhou\",\n" +
                        "               \"email\":\"xingyu.zhou@jinmuinfo.com\"\n" +
                        "            },\n" +
                        "            \"to\":[\n" +
                        "               {\n" +
                        "                  \"user_id\":10332850159252,\n" +
                        "                  \"name\":\"Agent No.1\",\n" +
                        "                  \"email\":\"user1@yzm.de\"\n" +
                        "               },\n" +
                        "               {\n" +
                        "                  \"user_id\":22738814337684,\n" +
                        "                  \"name\":\"Zendesk-support\",\n" +
                        "                  \"email\":\"zendesk-support@jinmuinfo.com\"\n" +
                        "               }\n" +
                        "            ],\n" +
                        "            \"body\":\"我不能看见这个下拉框 [image 358937d44c344b11a20921d666309a7e.png] 周星宇 xingyu.zhou@jinmuinfo.com Original: From：AAAA (Shanghai Jinmu Information Technology Co.,)\\u003csupport@pdi-jinmuinfo.zendesk.com\\u003e Date：2023-07-27 10:14:41(中国 (GMT+08:00)) To：zendesk support\\u003czendesk-support@jinmuinfo.com\\u003e Cc： Subject：下拉框占位符测试相关宏是否运行成功 如果出现就成功了 \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C Conversation ID: 58915698-2c23-11ee-8c20-d53a6c4e313d 58915698-2c23-11ee-8c20-d53a6c4e313d 245bfb0 [image 0a89952125cf00f5kurm189923a2a60?zone=bj\\u0026to=support@pdi-jinmuinfo.zendesk.com\\u0026tm=1690424129263\\u0026sign=229344465b8e35f7bc76c575621519fa\\u0026from=xingyu.zhou@jinmuinfo.com\\u0026mid=\\u0026ack=0\\u0026toname=support]\",\n" +
                        "            \"html_body\":\"\\u003cdiv class=\\\"zd-comment\\\"\\u003e\\n\\u003cp\\u003e我不能看见这个下拉框\\u003c/p\\u003e\\n\\u003cp\\u003e\\u003c/p\\u003e\\n\\u003cdiv\\u003e\\u003c/div\\u003e\\n\\u003cdiv style=\\\"font-size:14px\\\"\\u003e\\n\\u003cbr /\\u003e\\n\\u003c/div\\u003e\\n\\u003cdiv style=\\\"font-size:14px\\\"\\u003e\\n\\u003cbr /\\u003e\\n\\u003c/div\\u003e\\n\\u003cdiv style=\\\"font-size:14px\\\"\\u003e\\n\\u003cbr /\\u003e\\n\\u003c/div\\u003e\\n\\u003cdiv style=\\\"font-size:14px\\\"\\u003e\\n\\u003cbr /\\u003e\\n\\u003c/div\\u003e\\n\\u003cdiv\\u003e     \\u003cdiv\\u003e                    \\u003ctable style=\\\"border-collapse: collapse;width: 100%;text-size-adjust:none !important;table-layout: fixed;\\\"\\u003e               \\u003ctbody style=\\\"text-size-adjust:none !important;word-wrap:break-word; word-break:break-all;\\\"\\u003e                    \\u003ctr style=\\\"width: 100%;text-decoration: none;\\\"\\u003e                         \\u003ctd width=\\\"52\\\" style=\\\"width: 52px;padding: 16px 12px 0 0;white-space: nowrap;box-sizing: border-box;\\\"\\u003e                              \\u003cimg src=\\\"https://cowork-storage-public-cdn.lx.netease.com/sign/2023/06/28/358937d44c344b11a20921d666309a7e.png\\\" style=\\\"border-radius: 50%;object-fit: cover;width: 40px;height: 40px;\\\" width=\\\"40px\\\" height=\\\"40px\\\" /\\u003e                                                       \\u003c/td\\u003e                         \\u003ctd style=\\\"font-size: 14px;line-height: 16px;color: #7A8599;padding: 16px 16px 4px 0px;word-break: break-all;\\\"\\u003e                              \\u003cdiv style=\\\"color: #232D47;border: none;margin-bottom:4px;font-size: 16px;line-height: 20px;height:20px;font-weight: bolder;overflow: hidden;\\\"\\u003e周星宇\\u003c/div\\u003e                                                                                                                                       \\u003cdiv style=\\\"border: none;margin-bottom:8px;\\\"\\u003exingyu.zhou@jinmuinfo.com\\u003c/div\\u003e                                                                                                                                                                \\u003c/td\\u003e                    \\u003c/tr\\u003e               \\u003c/tbody\\u003e          \\u003c/table\\u003e                                             \\u003c/div\\u003e\\u003c/div\\u003e\\n\\u003cdiv\\u003e\\u003c/div\\u003e\\n\\u003cdiv\\u003e\\n\\u003cdiv style=\\\"font-size:14px\\\"\\u003e\\n\\u003cbr /\\u003e\\n\\u003c/div\\u003e\\n\\u003cdiv style=\\\"font-size:14px\\\"\\u003e\\n\\u003cbr /\\u003e\\n\\u003c/div\\u003e\\n\\u003cdiv style=\\\"font-size:14px\\\"\\u003e\\n\\u003cbr /\\u003e\\n\\u003c/div\\u003e\\n\\u003cblockquote style=\\\"PADDING-LEFT: 1ex; MARGIN: 0px 0px 0px 0.8ex; BORDER-LEFT: #ccc 1px solid; margin: 0\\\"\\u003e\\u003cdiv style=\\\"color: #7d8085\\\"\\u003eOriginal:\\u003c/div\\u003e\\u003cul style=\\\"color: #7d8085; font-size:12px; padding-left: 20px\\\"\\u003e\\u003cli\\u003eFrom：AAAA (Shanghai Jinmu Information Technology Co.,)\\u0026lt;\\u003ca href=\\\"mailto:support@pdi-jinmuinfo.zendesk.com\\\" rel=\\\"noreferrer\\\"\\u003esupport@pdi-jinmuinfo.zendesk.com\\u003c/a\\u003e\\u0026gt;\\u003c/li\\u003e\\u003cli\\u003eDate：2023-07-27 10:14:41(中国 (GMT+08:00))\\u003c/li\\u003e\\u003cli\\u003eTo：zendesk support\\u0026lt;\\u003ca href=\\\"mailto:zendesk-support@jinmuinfo.com\\\" rel=\\\"noreferrer\\\"\\u003ezendesk-support@jinmuinfo.com\\u003c/a\\u003e\\u0026gt;\\u003c/li\\u003e\\u003cli\\u003eCc：\\u003c/li\\u003e\\u003cli\\u003eSubject：下拉框占位符测试相关宏是否运行成功\\u003c/li\\u003e\\u003c/ul\\u003e\\n\\n    \\n\\n    \\n\\n  \\n\\n    \\u003cdiv class=\\\"zd-comment\\\"\\u003e\\u003cdiv data-comment-type=\\\"body\\\"\\u003e\\u003cdiv\\u003e如果出现就成功了\\u003c/div\\u003e\\u003c/div\\u003e\\u003c/div\\u003e\\u003cvar style=\\\"color:#FFFFFF;display:none !important;font-size:1px;\\\"\\u003e \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C  Conversation ID: 58915698-2c23-11ee-8c20-d53a6c4e313d 58915698-2c23-11ee-8c20-d53a6c4e313d 245bfb0\\u003c/var\\u003e\\n\\n  \\n\\n\\u003c/blockquote\\u003e\\n\\u003c/div\\u003e\\n\\u003cbr /\\u003e\\n\\u003cbr /\\u003e\\n\\u003cimg width=\\\"0\\\" height=\\\"0\\\" style=\\\"display:flex\\\" src=\\\"https://tr.qiye.163.com/datacapture/mailreport/v2/0a89952125cf00f5kurm189923a2a60?zone=bj\\u0026amp;to=support@pdi-jinmuinfo.zendesk.com\\u0026amp;tm=1690424129263\\u0026amp;sign=229344465b8e35f7bc76c575621519fa\\u0026amp;from=xingyu.zhou@jinmuinfo.com\\u0026amp;mid=\\u0026amp;ack=0\\u0026amp;toname=support\\\" /\\u003e\\n\\u003c/div\\u003e\",\n" +
                        "            \"external_ids\":{\n" +
                        "               \"inboundEmail\":\"\\u003cANcAGABtJF29dakjdaqujaqe.3.1690424125122.Hmail.xingyu.zhou@jinmuinfo.com\\u003e\",\n" +
                        "               \"ticketAuditId\":\"21115817314713\"\n" +
                        "            },\n" +
                        "            \"attachments\":[\n" +
                        "               \n" +
                        "            ]\n" +
                        "         },\n" +
                        "         \"updates\":{\n" +
                        "            \n" +
                        "         },\n" +
                        "         \"ticket_id\":705\n" +
                        "      },\n" +
                        "      {\n" +
                        "         \"id\":\"f6eeb2a0-2c23-11ee-88e5-6bed399a60fd\",\n" +
                        "         \"side_conversation_id\":\"58915698-2c23-11ee-8c20-d53a6c4e313d\",\n" +
                        "         \"actor\":{\n" +
                        "            \"user_id\":10332850159252,\n" +
                        "            \"name\":\"Agent No.1\",\n" +
                        "            \"email\":\"user1@yzm.de\"\n" +
                        "         },\n" +
                        "         \"type\":\"update\",\n" +
                        "         \"via\":\"support\",\n" +
                        "         \"created_at\":\"2023-07-27T02:19:07.338Z\",\n" +
                        "         \"message\":null,\n" +
                        "         \"updates\":{\n" +
                        "            \"state\":\"open\"\n" +
                        "         },\n" +
                        "         \"ticket_id\":705\n" +
                        "      }\n" +
                        "   ],\n" +
                        "   \"next_page\":null,\n" +
                        "   \"previous_page\":null,\n" +
                        "   \"count\":3\n" +
                        "}\n" +
                        "}");
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }
    @Test
    void demo31(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/guide/external_content/records")
                .newBuilder();
                //.addQueryParameter("page", "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


    @Test
    void demo32(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/guide/external_content/sources")
                .newBuilder()
                .addQueryParameter("page", "");
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                " {\"source\": {\"name\": \"My Library\"}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@yzm.de", "1qaz@WSX"))
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

    }

    @Test
    void demo33(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/users/10332850159252")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"user\":{\"default_group_id\":22950168428180}}"
        );

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("PUT", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    @Test
    void demo21(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/community/topics")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),

        "{\"topic\": {\"name\": \"测试主题\"}}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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

    }

    @Test
    void demo211list(){
        //需要拿到所有的community topic信息才能post
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/community/topics")
                .newBuilder();

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }

    //创建主题设置导入工作	主题设置导入系统并可用

    @Test
    void demo212(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/community/posts/21750909441300/comments")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }



    @Test
    void printNew(){
        System.out.println(String.valueOf(new Date().getTime()));
    }


    @Test
    void list_skills(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl +"/api/v2/resource_collections")
                .newBuilder();
        //.addQueryParameter("external_id", "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic("user1@nqmo.com", "1qaz@WSX"))
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
    }


}
