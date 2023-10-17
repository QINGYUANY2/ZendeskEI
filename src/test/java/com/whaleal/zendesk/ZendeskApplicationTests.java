package com.whaleal.zendesk;

import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class ZendeskApplicationTests {

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
//        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/users")
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/users/9401832486937")
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
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/users/create_many")
                .newBuilder();
        RequestBody creatBody = RequestBody.create(MediaType.parse("application/json"), "{\"users\":[{\n" +
                "        \"shared\":false,\n" +
                "        \"last_login_at\":\"2022-08-11T10:49:20Z\",\n" +
                "        \"role\":\"end-user\",\n" +
                "        \"notes\":\"\",\n" +
                "        \"shared_agent\":false,\n" +
                "        \"moderator\":false,\n" +
                "        \"created_at\":\"2022-08-11T10:47:02Z\",\n" +
                "        \"locale\":\"zh-cn\",\n" +
                "        \"locale_id\":10,\n" +
                "        \"user_fields\":{\n" +
                "            \"title_test\":false,\n" +
                "            \"loyal_customer\":false\n" +
                "        },\n" +
                "        \"updated_at\":\"2022-08-23T07:22:39Z\",\n" +
                "        \"report_csv\":false,\n" +
                "        \"alias\":\"\",\n" +
                "        \"details\":\"\",\n" +
                "        \"email\":\"1559299956@qq.com\",\n" +
                "        \"restricted_agent\":true,\n" +
                "        \"two_factor_auth_enabled\":false,\n" +
                "        \"only_private_comments\":false,\n" +
                "        \"iana_time_zone\":\"Asia/Shanghai\",\n" +
                "        \"verified\":true,\n" +
                "        \"active\":true,\n" +
                "        \"time_zone\":\"Asia/Shanghai\",\n" +
                "        \"url\":\"https://pdi-jinmuinfo.zendesk.com/api/v2/users/9401832486937.json\",\n" +
                "        \"suspended\":false,\n" +
                "        \"tags\":[\n" +
                "\n" +
                "        ],\n" +
                "        \"name\":\"heng\",\n" +
                "        \"_id\":{\n" +
                "            \"date\":\"2023-10-16 13:10:44\",\n" +
                "            \"timestamp\":1697433044\n" +
                "        },\n" +
                "        \"ticket_restriction\":\"organization\"\n" +
                "    }]}");

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
            System.out.println(response.toString());
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
