package com.whaleal.zendesk;

import com.whaleal.zendesk.service.BaseExportService;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

public class ZendeskApplicationTest3 extends BaseExportService{
    @Resource
    MongoTemplate mongoTemplate;


    //{"phone_numbers":[{"id":21646232175257,"country_code":"US","external":false,"nickname":null,"display_number":"+1 (912) 214-5744","capabilities":{"sms":true,"mms":true,"voice":true},"sms_enabled":false,"priority":0,"outbound_enabled":true,"line_type":"phone"},{"id":20300048648473,"country_code":"US","external":false,"nickname":null,"display_number":"+1 (484) 759-8404","capabilities":{"sms":true,"mms":true,"voice":true},"sms_enabled":true,"priority":0,"outbound_enabled":true,"line_type":"phone"},{"id":20283477158553,"country_code":"US","external":false,"nickname":null,"display_number":"+1 (217) 699-1535","capabilities":{"sms":true,"mms":true,"voice":true},"sms_enabled":false,"priority":0,"outbound_enabled":true,"line_type":"phone"},{"id":1900002007768,"country_code":"US","external":false,"nickname":null,"display_number":"+1 (256) 649-6811","capabilities":{"sms":true,"mms":true,"voice":true},"sms_enabled":true,"priority":0,"outbound_enabled":true,"line_type":"phone"}],"next_page":null,"previous_page":null,"count":4}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/channels/voice/phone_numbers?minimal_mode=true}
    //200
    @Test
    void list_phone_numbers(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/channels/voice/phone_numbers?minimal_mode=true")
                .newBuilder();
                //.addQueryParameter("minimal_mode", "");

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


    //无数据
    //{"phone_numbers":[],"next_page":null,"previous_page":null,"count":0}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/channels/voice/phone_numbers/search}
    //200
    @Test
    void list_valid(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/channels/voice/phone_numbers/search")
                .newBuilder();
                //.addQueryParameter("area_code", "510")
                //.addQueryParameter("contains", "")
                //.addQueryParameter("country", "US")
                //.addQueryParameter("toll_free", "true");

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

    //电话号码导入	电话号码导入系统中并可用
    //https://developer.zendesk.com/api-reference/voice/talk-api/phone_numbers/#create-phone-number
    //没有数据
    //Response{protocol=h2, code=400, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/channels/voice/phone_numbers}
    //400
    @Test

    void demo3111() {
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/channels/voice/phone_numbers")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \\\"phone_number\\\": {\n" +
                        "    \\\"token\\\": \\\"24a407cef67213be6d26b4c02d7d86c0\\\"\n" +
                        "  }\n" +
                        "}"
        );

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
    //show digital_lines
    //bug 且没用
    void show_digital_line(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/channels/voice/digital_lines")
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
        };;

    }
    @Test
    //数字线路导入	数字线路导入系统中并可用
    //https://developer.zendesk.com/api-reference/voice/talk-api/digital_lines/#create-digital-line
    //{"digital_line":{"id":26525525651609,"nickname":"Awesome Digital Line","priority":0,"default_group_id":12032724754841,"line_type":"digital","brand_id":8427041409433,"line_id":"b2d7bcc623d03264375dc7805b9a667a","outbound_number":null,"created_at":"2023-12-18T02:08:51Z","transcription":false,"recorded":true,"call_recording_consent":"always","group_ids":[12032724754841],"greeting_ids":[],"default_greeting_ids":["voicemail_en","available_en_digital_lines","wait_en","hold_en","callback_en","callback-confirmation_en","call-recording-opt-out_en","call-recording-opt-in_en"],"categorised_greetings_with_sub_settings":{"1":{"voicemail_on_outside_business_hours":"voicemail_en","voicemail_on_inside_business_hours":"voicemail_en","voicemail_off_inside_business_hours":"voicemail_en_voicemail_config","voicemail_off_outside_business_hours":"voicemail_en_voicemail_config"},"2":{"voicemail_on":"available_en_digital_lines","voicemail_off":"available_en_digital_lines"},"3":"wait_en","4":"hold_en","6":"callback_en","7":"callback-confirmation_en","8":"call-recording-opt-out_en","9":"call-recording-opt-in_en"},"schedule_id":null}}
    // Response{protocol=h2, code=201, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/channels/voice/digital_lines}
    //201
    //jingmutraining 403 没权限
    void demo3121(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/channels/voice/digital_lines")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"digital_line\": {\n" +
                        "    \"group_ids\": [\n" +
                        "      1\n" +
                        "    ],\n" +
                        "    \"nickname\": \"Awesome Digital Line\"\n" +
                        "  }\n" +
                        "}"
        );

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
        };
    }


    //问候语导入  问候语导入系统中并可用
    //https://developer.zendesk.com/api-reference/voice/talk-api/greetings/#create-greeting

    //{"greeting":{"id":21973014482068,"name":"Hello","category_id":1,"default":false,"default_lang":false,"active":false,"pending":true,"audio_url":null,"audio_name":null,"upload_id":null,"phone_number_ids":[],"ivr_ids":[],"has_sub_settings":true}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/channels/voice/greetings}
    //201
    @Test
    void demo3211(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/channels/voice/greetings")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"greeting\": {\n" +
                        "    \"category_id\": 1,\n" +
                        "    \"name\": \"Hello\"\n" +
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
        };
    }


    //地址导入	在系统中添加企业地址并生效
    //https://developer.zendesk.com/api-reference/voice/talk-api/addresses/#create-address
    //{"address":{"id":21973100089108,"name":"Zendesk","street":"1019 Market Street","zip":"94103","city":"San Francisco","state":null,"province":"California","country_code":"US","provider_reference":"AD1b514b1177e0c14c270f32bfbcc558a6"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/channels/voice/addresses}
    //201
    @Test
    void demo3221(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/channels/voice/addresses")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"address\": {\n" +
                        "    \"city\": \"San Francisco\",\n" +
                        "    \"country_code\": \"US\",\n" +
                        "    \"name\": \"Zendesk\",\n" +
                        "    \"province\": \"California\",\n" +
                        "    \"street\": \"1019 Market Street\",\n" +
                        "    \"zip\": \"94103\"\n" +
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
        };
    }


    //IVRs导入	导入IVR到系统中
    //https://developer.zendesk.com/api-reference/voice/talk-api/ivrs/#create-ivr
    //{"ivr":{"id":21973195678484,"name":"IVR Menu","menus":[{"id":21973195678868,"name":"主菜单","default":true,"greeting_id":null,"routes":[]}],"phone_number_ids":[],"phone_number_names":[]}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/channels/voice/ivr}
    //201
    @Test
    void demo3231(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/channels/voice/ivr")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"ivr\": {\n" +
                        "    \"name\": \"IVR Menu\"\n" +
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
        };
    }


    //{"ivrs":[{"id":21973195678484,"name":"IVR Menu","menus":[{"id":21973195678868,"name":"主菜单","default":true,"greeting_id":null,"routes":[]}],"phone_number_ids":[],"phone_number_names":[]}],"next_page":null,"previous_page":null,"count":1}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/channels/voice/ivr}
    //200
    //list IVR Menus
    @Test
    void list_IVR_menus(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/channels/voice/ivr")
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
        };

    }

    //IVR菜单导入	导入IVR菜单到系统中并在IVR中可用
    //https://developer.zendesk.com/api-reference/voice/talk-api/ivr_menus/#create-ivr-menu
    //{"ivr_menu":{"id":21973259290772,"name":"Main Menu","default":false,"greeting_id":null,"routes":[]}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/channels/voice/ivr/21973195678484/menus}
    //201
    @Test
    void demo3232(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/channels/voice/ivr/21973195678484/menus")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"ivr_menu\": {\n" +
                        "    \"name\": \"Main Menu\"\n" +
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
        };
    }


    //IVR路由	导入IVR路由到系统中并在IVR中生效
    //https://developer.zendesk.com/api-reference/voice/talk-api/ivr_routes/#create-ivr-route
    //{"ivr_route":{"id":21973423542804,"keypress":"1","greeting":null,"action":"group","options":{"group_ids":[10001]},"option_text":null,"overflow_options":[]}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/channels/voice/ivr/21973195678484/menus/21973195678868/routes}
    //201
    @Test
    void demo3233(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/channels/voice/ivr/21973195678484/menus/21973195678868/routes")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"ivr_route\": {\n" +
                        "    \"action\": \"group\",\n" +
                        "    \"keypress\": \"1\",\n" +
                        "    \"options\": {\n" +
                        "      \"group_ids\": [\n" +
                        "        10001\n" +
                        "      ]\n" +
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
        };
    }




}
