package com.whaleal.zendesk;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.util.StringSub;
import okhttp3.*;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.util.List;

//@SpringBootTest
class ZendeskApplicationTests extends BaseExportService {

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

//        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
//        String sourceEmail = "user1@yzm.de";
//        String sourcePassword = "1qaz@WSX";

        String sourceUrl = "https://jinmu1442.zendesk.com";
        String sourceEmail = "1102361302@qq.com";
        String sourcePassword = "123456";
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
    void test1() {

        String sourceUrl = "https://jinmu1442.zendesk.com";
        String sourceEmail = "1102361302@qq.com";
        String sourcePassword = "123456";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/imports/tickets")
//        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://support.zendesk.com/api/v2/channels/voice/phone_numbers")
                .newBuilder();
        RequestBody creatBody = RequestBody.create(MediaType.parse("application/json"),
                "{\"ticket\":{\"_id\":{\"date\":\"2023-10-23 16:44:45\",\"timestamp\":1698050685},\"from_messaging_channel\":false,\"subject\":\"测试推荐\",\"email_cc_ids\":[11946146000281],\"created_at\":\"2023-07-11T03:14:43Z\",\"description\":\"推荐智能机器人\",\"custom_status_id\":8427025599129,\"via\":{\"channel\":\"web\",\"source\":{\"from\":{},\"to\":{}}},\"allow_attachments\":true,\"updated_at\":\"2023-10-23T08:04:54Z\",\"follower_ids\":[11946146000281],\"id\":243,\"assignee_id\":24270405632793,\"raw_subject\":\"测试推荐\",\"custom_fields\":[{\"id\":9774117095705,\"value\":\"zendesk\"},{\"id\":20662954108185},{\"id\":10787110724889},{\"id\":10655083997977},{\"id\":11603759350809},{\"id\":10655406704409},{\"id\":20872321656857},{\"id\":10655178778905},{\"id\":20545173682969},{\"id\":21666059419929},{\"id\":10787195361305},{\"id\":11419882209049},{\"id\":10533760582681},{\"id\":21117433011481},{\"id\":11603761819417},{\"id\":10655235898137},{\"id\":17352695020569},{\"id\":11719029235097},{\"id\":11410119079321},{\"id\":10652776289945},{\"id\":10655260761497},{\"id\":11410078524313},{\"id\":11719064129433},{\"id\":10655290792345},{\"id\":10655239089817},{\"id\":19756886178457},{\"id\":10076189080985,\"value\":\"s3\"},{\"id\":21312612516249},{\"id\":20873520728473},{\"id\":21907692703897},{\"id\":11718915445145},{\"id\":11718951228825},{\"id\":10655155019161},{\"id\":21115427350425},{\"id\":20659931375769},{\"id\":10680308591769},{\"id\":10655181829017}],\"allow_channelback\":false,\"satisfaction_rating\":{\"score\":\"good\"},\"submitter_id\":11942258898585,\"collaborator_ids\":[11946146000281],\"url\":\"https://pdi-jinmuinfo.zendesk.com/api/v2/tickets/72.json\",\"tags\":[\"has_new_attachment\",\"s3\",\"zendesk\",\"文章推荐\"],\"brand_id\":24359725926041,\"ticket_form_id\":8427015306521,\"sharing_agreement_ids\":[],\"group_id\":24269983601177,\"organization_id\":11456749118233,\"followup_ids\":[],\"domain\":\"pdi-jinmuinfo\",\"is_public\":true,\"comments\":[{\"audit_id\":20530199901465,\"metadata\":{\"system\":{\"latitude\":\"37.5112\",\"client\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36\",\"location\":\"South Korea\",\"ip_address\":\"210.87.195.109\",\"longitude\":\"126.9741\"},\"custom\":{}},\"attachments\":[],\"public\":true,\"html_body\":\"<div class=\\\"zd-comment\\\" dir=\\\"auto\\\">推荐智能机器人<br>&nbsp;<br>&nbsp;<br></div>\",\"plain_body\":\"推荐智能机器人\\n&nbsp;\\n&nbsp;\",\"created_at\":\"2023-07-11T03:14:43Z\",\"id\":20530199901593,\"type\":\"Comment\",\"author_id\":24270405632793,\"body\":\"推荐智能机器人\",\"via\":{\"channel\":\"web\",\"source\":{\"from\":{},\"to\":{\"address\":\"user1@yzm.de\",\"email_ccs\":[11946146000281],\"name\":\"Agent No.1\"}}}},{\"audit_id\":20754202162329,\"metadata\":{\"system\":{\"latitude\":\"22.2842\",\"client\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36\",\"location\":\"Hong Kong, HCW, Hong Kong\",\"ip_address\":\"43.154.94.19\",\"longitude\":\"114.1759\"},\"custom\":{}},\"attachments\":[],\"public\":true,\"html_body\":\"<div class=\\\"zd-comment\\\" dir=\\\"auto\\\"><a rel=\\\"noopener noreferrer\\\" href=\\\"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/articles/20360171943193\\\">测试文章</a><br></div>\",\"plain_body\":\"测试文章\",\"created_at\":\"2023-07-17T07:34:01Z\",\"id\":20754218851993,\"type\":\"Comment\",\"author_id\":11942258898585,\"body\":\"测试文章\",\"via\":{\"channel\":\"web\",\"source\":{\"from\":{},\"to\":{}}}},{\"audit_id\":20754255032985,\"metadata\":{\"system\":{\"latitude\":\"22.2842\",\"client\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36\",\"location\":\"Hong Kong, HCW, Hong Kong\",\"ip_address\":\"43.154.94.19\",\"longitude\":\"114.1759\"},\"custom\":{}},\"attachments\":[],\"public\":true,\"html_body\":\"<div class=\\\"zd-comment\\\" dir=\\\"auto\\\">这是一个测试文章<br></div>\",\"plain_body\":\"这是一个测试文章\",\"created_at\":\"2023-07-17T07:34:50Z\",\"id\":20754291237145,\"type\":\"Comment\",\"author_id\":11942258898585,\"body\":\"这是一个测试文章\",\"via\":{\"channel\":\"web\",\"source\":{\"from\":{},\"to\":{}}}},{\"audit_id\":24362751711897,\"metadata\":{\"system\":{\"latitude\":\"31.2222\",\"client\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36\",\"location\":\"Shanghai, SH, China\",\"ip_address\":\"180.164.96.222\",\"longitude\":\"121.4581\"},\"custom\":{}},\"attachments\":[],\"public\":true,\"html_body\":\"<div class=\\\"zd-comment\\\" dir=\\\"auto\\\"><img style=\\\"width: 300px\\\" src=\\\"https://media1.giphy.com/media/v1.Y2lkPTA3YzM0Y2I2NGR5cW8zbDI4ZnVvc24wb2VxeXB0YzgydW93M24yc2xzdmczdWpndCZlcD12MV9naWZzX3RyZW5kaW5nJmN0PWc/EZICHGrSD5QEFCxMiC/giphy.gif\\\"><br>via <a rel=\\\"noopener noreferrer\\\" href=\\\"https://giphy.com/streamonmax/\\\">Max</a> on <a rel=\\\"noopener noreferrer\\\" href=\\\"https://giphy.com/gifs/HBOMax-tom-and-jerry-hbomax-n-EZICHGrSD5QEFCxMiC\\\">GIPHY</a><br>&nbsp;<br></div>\",\"plain_body\":\"via Max on GIPHY\\n&nbsp;\",\"created_at\":\"2023-10-23T08:04:21Z\",\"id\":24362783161241,\"type\":\"Comment\",\"author_id\":11942258898585,\"body\":\" ![](https://media1.giphy.com/media/v1.Y2lkPTA3YzM0Y2I2NGR5cW8zbDI4ZnVvc24wb2VxeXB0YzgydW93M24yc2xzdmczdWpndCZlcD12MV9naWZzX3RyZW5kaW5nJmN0PWc/EZICHGrSD5QEFCxMiC/giphy.gif)\\nvia Max on GIPHY\",\"via\":{\"channel\":\"web\",\"source\":{\"from\":{},\"to\":{}}}},{\"audit_id\":24362776528153,\"metadata\":{\"system\":{\"latitude\":\"31.2222\",\"client\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36\",\"location\":\"Shanghai, SH, China\",\"ip_address\":\"180.164.96.222\",\"longitude\":\"121.4581\"},\"custom\":{}},\"attachments\":[{\"mapped_content_url\":\"https://pdi-jinmuinfo.zendesk.com/attachments/token/iBhm1Ll3Nf601WwEKoa1THmQ7/?name=20210719150601_4401e.jpg\",\"malware_access_override\":false,\"file_name\":\"20210719150601_4401e.jpg\",\"malware_scan_result\":\"malware_not_found\",\"url\":\"https://pdi-jinmuinfo.zendesk.com/api/v2/attachments/24362754795289.json\",\"deleted\":false,\"content_type\":\"image/jpeg\",\"size\":65229,\"inline\":false,\"width\":1000,\"content_url\":\"https://pdi-jinmuinfo.zendesk.com/attachments/token/iBhm1Ll3Nf601WwEKoa1THmQ7/?name=20210719150601_4401e.jpg\",\"id\":24362754795289,\"thumbnails\":[{\"mapped_content_url\":\"https://pdi-jinmuinfo.zendesk.com/attachments/token/FYlv18f7NCu6l4yAoCdaulC4I/?name=20210719150601_4401e_thumb.jpg\",\"malware_access_override\":false,\"file_name\":\"20210719150601_4401e_thumb.jpg\",\"malware_scan_result\":\"malware_not_found\",\"url\":\"https://pdi-jinmuinfo.zendesk.com/api/v2/attachments/24362786382489.json\",\"deleted\":false,\"content_type\":\"image/jpeg\",\"size\":3080,\"inline\":false,\"width\":80,\"content_url\":\"https://pdi-jinmuinfo.zendesk.com/attachments/token/FYlv18f7NCu6l4yAoCdaulC4I/?name=20210719150601_4401e_thumb.jpg\",\"id\":24362786382489,\"height\":80}],\"height\":1000}],\"public\":true,\"html_body\":\"<div class=\\\"zd-comment\\\" dir=\\\"auto\\\">jpg<br></div>\",\"plain_body\":\"jpg\",\"created_at\":\"2023-10-23T08:04:54Z\",\"id\":24362776528281,\"type\":\"Comment\",\"author_id\":11942258898585,\"body\":\"jpg\",\"via\":{\"channel\":\"web\",\"source\":{\"from\":{},\"to\":{}}}}],\"has_incidents\":false,\"fields\":[{\"id\":9774117095705,\"value\":\"zendesk\"},{\"id\":20662954108185},{\"id\":10787110724889},{\"id\":10655083997977},{\"id\":11603759350809},{\"id\":10655406704409},{\"id\":20872321656857},{\"id\":10655178778905},{\"id\":20545173682969},{\"id\":21666059419929},{\"id\":10787195361305},{\"id\":11419882209049},{\"id\":10533760582681},{\"id\":21117433011481},{\"id\":11603761819417},{\"id\":10655235898137},{\"id\":17352695020569},{\"id\":11719029235097},{\"id\":11410119079321},{\"id\":10652776289945},{\"id\":10655260761497},{\"id\":11410078524313},{\"id\":11719064129433},{\"id\":10655290792345},{\"id\":10655239089817},{\"id\":19756886178457},{\"id\":10076189080985,\"value\":\"s3\"},{\"id\":21312612516249},{\"id\":20873520728473},{\"id\":21907692703897},{\"id\":11718915445145},{\"id\":11718951228825},{\"id\":10655155019161},{\"id\":21115427350425},{\"id\":20659931375769},{\"id\":10680308591769},{\"id\":10655181829017}],\"status\":0,\"requester_id\":11942258898585}}");

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

    @Resource
    MongoTemplate mongoTemplate;
    @Test
    void test2(){
        List<Document> documentList = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("status").is(2)), Document.class, "user_info");
        JSONObject request = null;
        for (Document users : documentList ) {
            System.out.println("====================");
            users.remove("custom_role_id");
            System.out.println(users);
            System.out.println("====================");
            try {
                JSONObject jsonObject = JSONObject.parseObject(users.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("user", jsonObject);
                request = doPost("/api/v2/users",requestParam);
                users.put("status",1);
                users.put("newId",request.getJSONObject("user").get("id"));

            }catch (Exception e){
                e.printStackTrace();
                users.put("status",2);
            }
            System.out.println("_____________________");
            System.out.println(request);
            System.out.println("_____________________");
            mongoTemplate.save(users,"user_info");
        }

    }



    @Test
    void test33(){
        List<Document> documentList = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("status").is(2)), Document.class, "user_info");
        for (Document document : documentList) {
            System.out.println("+++++++++++++++");
            System.out.println(document);
            System.out.println("+++++++++++++++");
        }

    }


}
