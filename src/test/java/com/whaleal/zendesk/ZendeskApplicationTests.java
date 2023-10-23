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
//        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/users")
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/groups")
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
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/tickets")
//        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://support.zendesk.com/api/v2/channels/voice/phone_numbers")
                .newBuilder();
        RequestBody creatBody = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "    \"ticket\":{\n" +
                        "        \"_id\":{\n" +
                        "            \"date\":\"2023-10-23 11:23:26\",\n" +
                        "            \"timestamp\":1698031406\n" +
                        "        },\n" +
                        "        \"from_messaging_channel\":true,\n" +
                        "        \"subject\":\"与 1232 的对话\",\n" +
                        "        \"email_cc_ids\":[\n" +
                        "\n" +
                        "        ],\n" +
                        "        \"created_at\":\"2023-10-16T08:56:55Z\",\n" +
                        "        \"description\":\"与 1232 的对话\\n\\nURL: None\",\n" +
                        "        \"custom_status_id\":8427015451417,\n" +
                        "        \"via\":{\n" +
                        "            \"channel\":\"native_messaging\",\n" +
                        "            \"source\":{\n" +
                        "                \"from\":{\n" +
                        "\n" +
                        "                },\n" +
                        "                \"to\":{\n" +
                        "\n" +
                        "                }\n" +
                        "            }\n" +
                        "        },\n" +
                        "        \"allow_attachments\":true,\n" +
                        "        \"updated_at\":\"2023-10-16T09:07:02Z\",\n" +
                        "        \"follower_ids\":[\n" +
                        "\n" +
                        "        ],\n" +
                        "        \"id\":142,\n" +
                        "        \"raw_subject\":\"与 1232 的对话\",\n" +
                        "        \"custom_fields\":[\n" +
                        "            {\n" +
                        "                \"id\":9774117095705,\n" +
                        "                \"value\":\"mongodb\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":20662954108185\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10787110724889\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655083997977\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11603759350809,\n" +
                        "                \"value\":\"ecf\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655406704409\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":20872321656857\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655178778905\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":20545173682969\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":21666059419929\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10787195361305\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11419882209049\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10533760582681\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":21117433011481\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11603761819417,\n" +
                        "                \"value\":\"eqe\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655235898137\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":17352695020569\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11719029235097\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11410119079321\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10652776289945\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655260761497\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11410078524313\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11719064129433\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655290792345\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655239089817\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":19756886178457\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10076189080985\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":21312612516249\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":20873520728473\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":21907692703897\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11718915445145\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11718951228825\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655155019161\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":21115427350425\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":20659931375769\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10680308591769\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655181829017\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"allow_channelback\":false,\n" +
                        "        \"satisfaction_rating\":{\n" +
                        "            \"score\":\"unoffered\"\n" +
                        "        },\n" +
                        "        \"submitter_id\":24103831201049,\n" +
                        "        \"collaborator_ids\":[\n" +
                        "\n" +
                        "        ],\n" +
                        "        \"url\":\"https://pdi-jinmuinfo.zendesk.com/api/v2/tickets/142.json\",\n" +
                        "        \"tags\":[\n" +
                        "            \"ab_suggest_false\",\n" +
                        "            \"mongodb\"\n" +
                        "        ],\n" +
                        "        \"brand_id\":8427041409433,\n" +
                        "        \"ticket_form_id\":8427015306521,\n" +
                        "        \"sharing_agreement_ids\":[\n" +
                        "\n" +
                        "        ],\n" +
                        "        \"group_id\":24269969276953,\n" +
                        "        \"followup_ids\":[\n" +
                        "\n" +
                        "        ],\n" +
                        "        \"domain\":\"pdi-jinmuinfo\",\n" +
                        "        \"is_public\":true,\n" +
                        "        \"has_incidents\":false,\n" +
                        "        \"fields\":[\n" +
                        "            {\n" +
                        "                \"id\":9774117095705,\n" +
                        "                \"value\":\"mongodb\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":20662954108185\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10787110724889\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655083997977\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11603759350809,\n" +
                        "                \"value\":\"ecf\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655406704409\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":20872321656857\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655178778905\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":20545173682969\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":21666059419929\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10787195361305\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11419882209049\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10533760582681\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":21117433011481\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11603761819417,\n" +
                        "                \"value\":\"eqe\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655235898137\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":17352695020569\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11719029235097\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11410119079321\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10652776289945\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655260761497\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11410078524313\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11719064129433\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655290792345\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655239089817\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":19756886178457\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10076189080985\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":21312612516249\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":20873520728473\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":21907692703897\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11718915445145\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":11718951228825\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655155019161\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":21115427350425\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":20659931375769\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10680308591769\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\":10655181829017\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"status\":0,\n" +
                        "        \"requester_id\":24103831201049\n" +
                        "    }\n" +
                        "}");

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
