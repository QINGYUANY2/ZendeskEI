package com.whaleal.zendesk;

import okhttp3.*;
import org.junit.jupiter.api.Test;

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
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/guide/permission_groups.json")
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
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/channels/voice/ivr")
//        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://support.zendesk.com/api/v2/channels/voice/phone_numbers")
                .newBuilder();
        RequestBody creatBody = RequestBody.create(MediaType.parse("application/json"),
                "{\"ivr\": {\"name\": \"IVR Menu\"}}");

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

}
