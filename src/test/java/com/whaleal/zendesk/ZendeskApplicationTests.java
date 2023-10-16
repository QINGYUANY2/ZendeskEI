package com.whaleal.zendesk;

import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ZendeskApplicationTests {

    @Test
    void contextLoads() {

        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        String sourceEmail = "user1@yzm.de";
        String sourcePassword = "1qaz@WSX";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl + "/api/v2/channels/voice/ivr")
                .newBuilder();
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(sourceEmail, sourcePassword))
                .build();
        long num = 0;
        try {
            while (true) {
                num++;
                Response response = client.newCall(request).execute();
                System.out.println("==========================");
                System.out.println("这是第 " + num + " 次调用。。。");
                System.out.println(response.body().string());
                System.out.println("==========================");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("+++++++++++++++++++++++++++");
            System.out.println("这是第 " + num + "次调用，结果挂了。。。");
            System.out.println("+++++++++++++++++++++++++++");
        }
    }

}
