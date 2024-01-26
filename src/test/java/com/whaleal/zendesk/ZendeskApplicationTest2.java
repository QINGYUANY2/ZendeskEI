package com.whaleal.zendesk;

import com.whaleal.zendesk.service.BaseExportService;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.io.File;


public class ZendeskApplicationTest2 {


    //{"themes":[{"id":"f418fefe-3f2f-4c04-8213-fe0420dab2dc","brand_id":"8427041409433","name":"Copenhagen","author":"Zendesk","live":false,"created_at":"2022-07-21T03:01:15Z","updated_at":"2023-02-24T02:45:52Z","version":"2.19.4"},{"id":"2166a8f3-80e1-4486-87c5-28a0db5a135a","brand_id":"8427041409433","name":"Obscu","author":"Grow Shine","live":false,"created_at":"2022-11-22T03:26:57Z","updated_at":"2023-03-21T06:51:13Z","version":"4.0.6"},{"id":"2a5d1228-b6b3-4939-9d92-bfe231004dfb","brand_id":"8427041409433","name":"Vertio","author":"Grow Shine","live":true,"created_at":"2022-11-22T03:28:56Z","updated_at":"2023-11-24T03:57:54Z","version":"4.1.3"},{"id":"f6895e3d-cf44-47ad-af76-b3efef9d5f5d","brand_id":"8427041409433","name":"Role Theme","author":"Zendesk Themes Marketplace","live":false,"created_at":"2023-02-15T11:12:37Z","updated_at":"2023-02-16T09:31:38Z","version":"2.2.4"},{"id":"93a47e13-9c0d-4ddb-ad7a-9983687c2b6a","brand_id":"8427041409433","name":"Cavel","author":"Grow Shine","live":false,"created_at":"2023-02-16T02:49:37Z","updated_at":"2023-02-16T02:49:37Z","version":"4.0.4"},{"id":"5fa4f334-c897-4bce-b327-76674a6430b9","brand_id":"8427041409433","name":"Green","author":"Grow Shine","live":false,"created_at":"2023-02-16T02:50:04Z","updated_at":"2023-03-21T06:51:12Z","version":"4.0.2"},{"id":"3cc0d497-df0e-4c18-a28b-63785b26a84a","brand_id":"8427041409433","name":"Copenhagen","author":"Zendesk","live":false,"created_at":"2023-02-24T02:46:38Z","updated_at":"2023-11-24T03:57:54Z","version":"2.19.4"},{"id":"fde20b9b-0f66-4b15-938e-0392ee2c761e","brand_id":"8427041409433","name":"Copenhagen","author":"Zendesk","live":false,"created_at":"2023-07-05T03:36:03Z","updated_at":"2023-07-11T07:38:42Z","version":"3.0.1"},{"id":"68d622d4-8755-4a0e-b7fe-2ca8a333be72","brand_id":"8427041409433","name":"测试主题1","author":"Zendesk","live":false,"created_at":"2023-07-05T05:58:58Z","updated_at":"2023-07-05T06:12:17Z","version":"3.0.1"},{"id":"19dda5f8-9135-452d-b6ad-fe479c86a5a7","brand_id":"10338011880089","name":"Copenhagen","author":"Zendesk","live":false,"created_at":"2022-09-15T08:34:31Z","updated_at":"2023-02-16T02:39:26Z","version":"2.19.4"},{"id":"16e73933-66b7-41ab-9cc6-db25948ec950","brand_id":"10338011880089","name":"Obscu","author":"Customer Support Theme","live":false,"created_at":"2022-11-30T06:38:46Z","updated_at":"2022-11-30T06:48:43Z","version":"4.0.4"},{"id":"ef59bbe1-45dc-412c-85c0-b7fee8dbdc5d","brand_id":"10338011880089","name":"Obscu","author":"Customer Support Theme","live":true,"created_at":"2022-11-30T06:48:25Z","updated_at":"2023-02-16T02:39:26Z","version":"4.0.4"},{"id":"c29a6afe-82ee-43a1-bbe9-eece4a2e2a1a","brand_id":"10338011880089","name":"Vertio","author":"Grow Shine","live":false,"created_at":"2023-02-15T11:10:23Z","updated_at":"2023-02-16T01:51:05Z","version":"4.1.1"}]}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/guide/theming/themes}
    //200
    @Test
    void get_theme(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/guide/theming/themes")
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
        };
    }

    //创建主题设置导入工作	主题设置导入系统并可用
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/theming/#create-theme-import-job
    //{"job":{"id":"fbf6a5d1b02f65a07b4573c04dab6b30","status":"pending","errors":null,"data":{"theme_id":"d9a40885-7733-4463-bcc6-4992c3965ffe","upload":{"url":"https://theme.zdassets.com","parameters":{"key":"theme_imports/pod/20/15330352/fbf6a5d1b02f65a07b4573c04dab6b30.zip","Expires":"Thu, 21 Dec 2023 10:09:03 GMT","x-amz-server-side-encryption":"AES256","success_action_status":"201","acl":"private","policy":"eyJleHBpcmF0aW9uIjoiMjAyMy0xMi0yMVQxMTowODowM1oiLCJjb25kaXRpb25zIjpbeyJidWNrZXQiOiJndWlkZS10aGVtaW5nLWNlbnRlci1wcm9kdWN0aW9uIn0seyJrZXkiOiJ0aGVtZV9pbXBvcnRzL3BvZC8yMC8xNTMzMDM1Mi9mYmY2YTVkMWIwMmY2NWEwN2I0NTczYzA0ZGFiNmIzMC56aXAifSx7IkV4cGlyZXMiOiJUaHUsIDIxIERlYyAyMDIzIDEwOjA5OjAzIEdNVCJ9LHsieC1hbXotc2VydmVyLXNpZGUtZW5jcnlwdGlvbiI6IkFFUzI1NiJ9LFsiY29udGVudC1sZW5ndGgtcmFuZ2UiLDEsMzE0NTcyODBdLHsic3VjY2Vzc19hY3Rpb25fc3RhdHVzIjoiMjAxIn0seyJhY2wiOiJwcml2YXRlIn0seyJ4LWFtei1jcmVkZW50aWFsIjoiQUtJQTQ3RkVZSkRUTVhNVFFNNDQvMjAyMzEyMjEvdXMtd2VzdC0xL3MzL2F3czRfcmVxdWVzdCJ9LHsieC1hbXotYWxnb3JpdGhtIjoiQVdTNC1ITUFDLVNIQTI1NiJ9LHsieC1hbXotZGF0ZSI6IjIwMjMxMjIxVDEwMDgwM1oifV19","x-amz-credential":"AKIA47FEYJDTMXMTQM44/20231221/us-west-1/s3/aws4_request","x-amz-algorithm":"AWS4-HMAC-SHA256","x-amz-date":"20231221T100803Z","x-amz-signature":"0763bfc6c5cf34caab228ec06a67b1651f2114b72f1d7908f3b7b0725c7fd820"}}}}}
    //Response{protocol=h2, code=202, message=, url=https://jinmutraining.zendesk.com/api/v2/guide/theming/jobs/themes/imports}
    //202
    @Test
    void demo2111(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/guide/theming/jobs/themes/imports")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{ \"job\": { \"attributes\": { \"brand_id\": \"10332833146772\", \"format\":\"zip\" } } }");

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


    //{"categories":[],"page":1,"previous_page":null,"next_page":null,"per_page":30,"page_count":0,"count":0,"sort_by":"position","sort_order":"asc"}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/en-us/categories}
    //200
    //list categories
    @Test
    void list_categories(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/en-us/categories")
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
        };
    }

    //文章类别导入	文章类别导入系统并可用
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/categories/#create-category
    //{"category":{"id":21975975922836,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/en-us/categories/21975975922836.json","html_url":"https://jinmutraining.zendesk.com/hc/en-us/categories/21975975922836-Super-Hero-Tricks","position":0,"created_at":"2023-12-18T05:40:25Z","updated_at":"2023-12-18T05:40:25Z","name":"Super Hero Tricks","description":"This category contains a collection of Super Hero tricks","locale":"en-us","source_locale":"en-us","outdated":false}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/help_center/en-us/categories}
    //201
    @Test
    void demo2121(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/en-us/categories")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"category\": {\n" +
                        "    \"description\": \"This category contains a collection of Super Hero tricks\",\n" +
                        "    \"id\": 37486578,\n" +
                        "    \"locale\": \"en-us\",\n" +
                        "    \"name\": \"Super Hero Tricks\"\n" +
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



    //文章组别导入	文章组别导入系统并可用
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/sections/#create-section
    //{"section":{"id":21975969356948,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/en-us/sections/21975969356948.json","html_url":"https://jinmutraining.zendesk.com/hc/en-us/sections/21975969356948-Avionics","category_id":21975975922836,"position":2,"sorting":"manual","created_at":"2023-12-18T05:43:00Z","updated_at":"2023-12-18T05:43:00Z","name":"Avionics","description":"This section contains articles on flight instruments","locale":"en-us","source_locale":"en-us","outdated":false,"parent_section_id":null,"theme_template":"section_page"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/help_center/en-us/categories/21975975922836/sections}
    //201
    @Test
    void demo2131(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/en-us/categories/21975975922836/sections")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"section\": {\n" +
                        "    \"description\": \"This section contains articles on flight instruments\",\n" +
                        "    \"id\": 3457836,\n" +
                        "    \"locale\": \"en-us\",\n" +
                        "    \"name\": \"Avionics\",\n" +
                        "    \"position\": 2\n" +
                        "  }\n" +
                        "}\n");

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



    //{"sections":[{"id":20728554380692,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/en-us/sections/20728554380692.json","html_url":"https://jinmutraining.zendesk.com/hc/en-us/sections/20728554380692-english-ver","category_id":20129493948692,"position":0,"sorting":"manual","created_at":"2023-11-08T05:08:06Z","updated_at":"2023-11-08T05:10:10Z","name":"english ver","description":"","locale":"en-us","source_locale":"en-us","outdated":false,"parent_section_id":null,"theme_template":"section_page"},{"id":21975969356948,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/en-us/sections/21975969356948.json","html_url":"https://jinmutraining.zendesk.com/hc/en-us/sections/21975969356948-Avionics","category_id":21975975922836,"position":2,"sorting":"manual","created_at":"2023-12-18T05:43:00Z","updated_at":"2023-12-18T08:18:58Z","name":"Avionics","description":"This section contains articles on flight instruments","locale":"en-us","source_locale":"en-us","outdated":false,"parent_section_id":null,"theme_template":"section_page"}],"page":1,"previous_page":null,"next_page":null,"per_page":30,"page_count":1,"count":2,"sort_by":"position","sort_order":"asc"}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/help_center/en-us/sections}
    //200
    @Test
    void list_sections(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/en-us/sections\n")
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

    //{"count":2,"next_page":null,"page":1,"page_count":1,"per_page":30,"previous_page":null,"articles":[{"id":20728574273428,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles/20728574273428.json","html_url":"https://jinmutraining.zendesk.com/hc/en-us/articles/20728574273428--english-article","author_id":10332850159252,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":20728554380692,"created_at":"2023-11-08T05:09:45Z","updated_at":"2023-11-08T05:10:10Z","name":" english article","title":" english article","source_locale":"en-us","locale":"en-us","outdated":false,"outdated_locales":[],"edited_at":"2023-11-08T05:09:45Z","user_segment_id":null,"permission_group_id":10361325375636,"content_tag_ids":[],"label_names":[],"body":"\u003cp\u003ethis is an english article\u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e"},{"id":10361313057300,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles/10361313057300.json","html_url":"https://jinmutraining.zendesk.com/hc/en-us/articles/10361313057300-How-can-agent-use-their-knowledge-to-help-customers-","author_id":10332850159252,"comments_disabled":false,"draft":false,"promoted":true,"position":0,"vote_sum":0,"vote_count":0,"section_id":10361378844308,"created_at":"2022-10-26T06:53:36Z","updated_at":"2023-07-12T02:42:09Z","name":"How can agent use their knowledge to help customers?","title":"How can agent use their knowledge to help customers?","source_locale":"zh-cn","locale":"en-us","outdated":false,"outdated_locales":[],"edited_at":"2022-10-27T08:20:16Z","user_segment_id":null,"permission_group_id":10361325375636,"content_tag_ids":["01H51TY2KGBKS8ZDWGKMMFWB4G"],"label_names":["Thunder","文章推荐"],"body":"\u003cp\u003eYou can use our Knowledge Extraction application to leverage your team's collective knowledge.\u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e\n\u003cp\u003eWith this application, specialists can:\u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e\n\u003cp\u003eSearch Help Center without leaving the job\u003c/p\u003e\n\u003cp\u003eInsert a link to the relevant Help Center article in the work order comment\u003c/p\u003e\n\u003cp\u003eAdd embedded feedback to existing articles that need to be updated\u003c/p\u003e\n\u003cp\u003eCreate a new article using the predefined template while replying to the work order\u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e\n\u003cp\u003eThe specialists can share, mark or create knowledge without leaving the work order interface, so as to improve the self-service experience provided to other customers while helping customers.\u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e\n\u003cp\u003eTo get started, consult our knowledge extraction documentation.\u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e\n\u003cp\u003eBefore the specialist starts to create new knowledge directly from the work order, you need to create a template for them to use. We have provided some template ideas below to help you get started. You can copy and paste any of the following template examples into a new article and add KCTemplate tags to the article.\u003c/p\u003e"}],"sort_by":"position","sort_order":"asc"}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles}
    //200
    //无数据
    //{"count":0,"next_page":null,"page":1,"page_count":0,"per_page":30,"previous_page":null,"articles":[],"sort_by":"position","sort_order":"asc"}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/en-us/articles}
    //200
    //

    //{"count":20,"next_page":null,"page":1,"page_count":1,"per_page":30,"previous_page":null,"articles":[{"id":26672627027097,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-hk/articles/26672627027097.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/articles/26672627027097-0000000","author_id":11942258898585,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":1,"vote_count":1,"section_id":20358487660313,"created_at":"2023-12-21T05:15:53Z","updated_at":"2023-12-21T05:27:56Z","name":"0000000","title":"0000000","source_locale":"zh-hk","locale":"zh-hk","outdated":false,"outdated_locales":[],"edited_at":"2023-12-21T05:16:18Z","user_segment_id":8696049524889,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":[],"body":"\u003cp\u003e000000\u003c/p\u003e"},{"id":26672438796057,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-hk/articles/26672438796057.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/articles/26672438796057-test-article-1","author_id":11942258898585,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":20358487660313,"created_at":"2023-12-21T05:10:48Z","updated_at":"2023-12-21T05:23:09Z","name":"test article 1","title":"test article 1","source_locale":"zh-hk","locale":"zh-hk","outdated":false,"outdated_locales":[],"edited_at":"2023-12-21T05:11:42Z","user_segment_id":8696049524889,"permission_group_id":8696049536537,"content_tag_ids":["01H51C0BAQGG5BCHEZQFXY3E01"],"label_names":[],"body":"\u003cp\u003eabcabc\u003cimg src=\"https://pdi-jinmuinfo.zendesk.com/hc/article_attachments/26672438789529\" alt=\"截屏2023-11-28 01.26.18.png\"\u003e\u003c/p\u003e"},{"id":25132625112857,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-hk/articles/25132625112857.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/articles/25132625112857","author_id":11946605192345,"comments_disabled":false,"draft":true,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":21159597028889,"created_at":"2023-11-13T02:01:42Z","updated_at":"2023-11-13T02:01:43Z","name":"低权限专员能否创建文章","title":"低权限专员能否创建文章","source_locale":"zh-hk","locale":"zh-hk","outdated":false,"outdated_locales":[],"edited_at":"2023-11-13T02:01:43Z","user_segment_id":null,"permission_group_id":12739724718873,"content_tag_ids":[],"label_names":[],"body":"\u003cp\u003e是否可以看到文章\u003c/p\u003e"},{"id":24784785683865,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-hk/articles/24784785683865.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/articles/24784785683865","author_id":11942258898585,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":21160059488025,"created_at":"2023-11-03T08:17:55Z","updated_at":"2023-11-03T08:17:55Z","name":"一个不添堵且有温度的AI客服到底长啥样？","title":"一个不添堵且有温度的AI客服到底长啥样？","source_locale":"zh-hk","locale":"zh-hk","outdated":false,"outdated_locales":[],"edited_at":"2023-11-03T08:17:55Z","user_segment_id":8696049524889,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":[],"body":"\u003cdiv data-page-id=\"Dd7zdBNj1oTs45xEyMvcBl3KnBd\" data-docx-has-block-data=\"true\"\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-Dd7zdBNj1oTs45xEyMvcBl3KnBd\"\u003e\u003c/div\u003e\n\u003ch1 id=\"h_01HEA42V3FA75SA039G5S4CWDH\" class=\"heading-1 ace-line old-record-id-GYS4dAEwwo8SE6xaeoec1TUvnvd\"\u003e\n\u003cspan\u003e\u003cstrong\u003e绕来绕去不解决问题的\u003c/strong\u003e\u003c/span\u003e\u003cspan\u003e\u003cstrong\u003eAI\u003c/strong\u003e\u003c/span\u003e\u003cspan\u003e\u003cstrong\u003e客服不是好客服\u003c/strong\u003e\u003c/span\u003e\n\u003c/h1\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-Xyymd6eEYoQme8x7eLlchVZinNi\"\u003e\n\u003cspan\u003e作为一个客服行业的“老兵”，演讲一开始，段毓铮居然跟大家吐槽了两件关于“\u003c/span\u003e\u003cspan\u003eAI\u003c/span\u003e\u003cspan\u003e客服添堵”的事情。\u003c/span\u003e\n\u003c/div\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-J2QEdCawUoOgOgxuyZAcs9ghn9d\"\u003e\n\u003cspan\u003e一件是自己亲身经历的，场景是线上买菜。一个双休日，他在线上买菜平台下单，准备给家人做一顿饭，预约的时间是中午11点之前配送。11点的时候，菜没有收到，他去平台查看进度，却显示“订单已完成配送”。这时候，他联系平台的客服，在清晰表达了问题的来龙去脉后，客服机器人引导他去门口查看，查看反馈没有后，再沟通，机器人再次引导说是不是可能放在别的地方。段毓铮说他很无奈也很懊恼，他的焦虑点是“骑手擅自点了已送达，我无法查看送菜的进度，我不知道我的菜究竟什么时候可以送到”，但\u003c/span\u003e\u003cspan\u003eAI\u003c/span\u003e\u003cspan\u003e客服一直绕来绕去，没有给他一个合适的解答。\u003c/span\u003e\n\u003c/div\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-VKEUdQ4emoskuaxUT1KcFF8DnhV\"\u003e\n\u003cspan\u003e一件是发生在客户身上的，场景是用户退会员费。客户的业务是会员制收费模式，涉及到退会员费是很常见的事情，但因为最开始服务流程设计的不好，引来了很多不必要的麻烦和投诉。比如当用户咨询能不能退费、怎么退费的时候，\u003c/span\u003e\u003cspan\u003eAI\u003c/span\u003e\u003cspan\u003e客服给出满满一箩筐的“退费规则”，里面涉及到会员等级、红包、优惠券，还有海外业务的汇率换算等等。这些规则又臭又长，给每个人发的都是一样的，AI客服看似回答了用户的问题，但实际上根本没有把用户想办的事情给办成。\u003c/span\u003e\n\u003c/div\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-EUOEdW2AAoAGMOxOYs1ctu5nn9O\"\u003e\n\u003cspan\u003e“在我们看来，绕来绕去、不说人话、不办人事、不真正去理解和解决用户问题的，都不是好的\u003c/span\u003e\u003cspan\u003eAI\u003c/span\u003e\u003cspan\u003e客服”，段毓铮坦言，现在有一些媒体和\u003c/span\u003e\u003cspan\u003e\u003ca href=\"http://s.iresearch.cn/search/xiaofeizhe/\"\u003e消费者\u003c/a\u003e\u003c/span\u003e\u003cspan\u003e说智能客服不智能，症结也在于此。\u003c/span\u003e\n\u003c/div\u003e\n\u003ch1 id=\"h_01HEA42V3FG2NBQNSCZ0NHE3MD\" class=\"heading-1 ace-line old-record-id-DQuIdqy2Yo2wO8xzbgDcP2XAnjk\"\u003e\n\u003cspan\u003e\u003cstrong\u003e有温度的\u003c/strong\u003e\u003c/span\u003e\u003cspan\u003e\u003cstrong\u003eAI\u003c/strong\u003e\u003c/span\u003e\u003cspan\u003e\u003cstrong\u003e客服=懂业务、懂用户、更主动\u003c/strong\u003e\u003c/span\u003e\n\u003c/h1\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-VKKwdO4gmoo0OmxEM77ciDbUn3Y\"\u003e\n\u003cspan\u003e“6年来，我们陪伴40万家企业设计和优化了服务策略和服务流程，我们也一直在观察和研究到底什么样的\u003c/span\u003e\u003cspan\u003eAI\u003c/span\u003e\u003cspan\u003e客服才是有温度的AI客服”，段毓铮说，在很多人看来，可能“说话轻声细语、态度热情、语气温柔”的是有温度的服务，但在网易七鱼看来，真正有温度的AI服务需要具备三个特征：懂业务、懂用户、更主动。\u003c/span\u003e\n\u003c/div\u003e\n\u003ch2 id=\"h_01HEA42V3FVN2F354H9XZQPNKB\" class=\"heading-2 ace-line old-record-id-KS8SdMMAwoQMwGxcxdkcIdGanWf\"\u003e\u003cspan\u003e\u003cstrong\u003e懂业务：\u003c/strong\u003e\u003c/span\u003e\u003c/h2\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-DY6kd6OYUoIa6Oxeo1zcyvjinGc\"\u003e\n\u003cspan\u003e简单的说，懂业务就是这个\u003c/span\u003e\u003cspan\u003eAI\u003c/span\u003e\u003cspan\u003e客服不能脱离业务，要了解业务办理的整个流程，然后基于流程顺利把用户的问题解决掉。段毓铮在现场举了一个非常典型的案例：松果出行是一个知名的共享电单车平台，用户在借车、用车、锁车还车等过程中会有一连串的问题，这些问题关联到业务流程的方方面面，比如：所在的区域、会员等级、车辆当前状态、参与的营销活动等等。网易七鱼将AI技术与松果出行的业务深度融合，成功搭建4000+流程节点、300+判断节点和500+接口获取值，将海量咨询问题梳理为任务流程，构建起“一触即达”的流程。从效果来看，AI客服问题解决率达到95%，AI客服咨询量占比90%，也就是说，懂业务的机器人可以妥善解决90%的问题。\u003c/span\u003e\n\u003c/div\u003e\n\u003ch2 id=\"h_01HEA42V3FXNGBMNZMSQP3GGKQ\" class=\"heading-2 ace-line old-record-id-TAGWdagIAoGosKxAFqJclxxhnyc\"\u003e\u003cspan\u003e\u003cstrong\u003e懂用户：\u003c/strong\u003e\u003c/span\u003e\u003c/h2\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-R0CEdqw48ogSwwx0GDocnjpCnie\"\u003e\n\u003cspan\u003e包括懂用户的意图和懂用户的心理两个维度。懂用户的意图，是指用户在进线之前的经历和在进线之后的诉求，我们能不能预判。段毓铮以生鲜配送平台举例：如果用户在下单半小时到一小时左右进线，大概率是配送相关的问题。如果我们的\u003c/span\u003e\u003cspan\u003eAI\u003c/span\u003e\u003cspan\u003e客服能够预判这一点，并且做好了相应的智能化配置，接下来的服务就会顺畅许多。消费者觉得这个客服很懂我，自然而然就能感受到温度。懂用户的心理，通俗一点说就是要了解不同消费者的性格或者说价值观。有的消费者直来直往，追求高效解决问题，这个时候我们的AI客服不要绕来绕去解释一大堆，要单刀直入给出问题的解决方案。而有一部分消费者，做事情讲究原则，喜欢把事情搞得明明白白，这个时候要采取的就是另一种服务策略和方式了。\u003c/span\u003e\n\u003c/div\u003e\n\u003ch2 id=\"h_01HEA42V3GFQAPRXNJ38V3A9R3\" class=\"heading-2 ace-line old-record-id-WAMQdEK8EoKWE6xQTQbcx3Aynyg\"\u003e\u003cspan\u003e更主动：\u003c/span\u003e\u003c/h2\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-EGOsdygekoS2OqxaeCSccBlxn0c\"\u003e\n\u003cspan\u003e当我们的客服更懂业务更懂用户之后，我们需要改变以往被动解答问题的状态，可以更贴近用户，提供一些更主动的服务。段毓铮说网易七鱼在各行各业都积累了很多“主动服务”的实践：医疗行业的\u003c/span\u003e\u003cspan\u003eAI\u003c/span\u003e\u003cspan\u003e客服，主动回访慢病患者，提醒他们定期复查按时服药；\u003c/span\u003e\u003cspan\u003e\u003ca href=\"http://s.iresearch.cn/search/qiche/\"\u003e汽车\u003c/a\u003e\u003c/span\u003e\u003cspan\u003e行业的AI客服，主动提醒用户免费到店洗车和保养；女装行业的AI客服，在会员生日当天，主动进行生日关怀；游戏行业的AI客服，通过模拟游戏角色的声音，主动为玩家创造惊喜......\u003c/span\u003e\n\u003c/div\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-OGAIdiIGIoGQm2xEt2UcCXvnnjg\"\u003e\u003cspan\u003e“上面提到的这些主动服务场景，其实也是另一种营销的方式。我们认为，服务与营销的边界越来越模糊，企业走向服务营销的一体化，会是未来智能客服行业的一大趋势”。\u003c/span\u003e\u003c/div\u003e\n\u003ch1 id=\"h_01HEA42V3GHAP30406T54CEJ16\" class=\"heading-1 ace-line old-record-id-QgEad4ey2oOaEKxGoGWc9oNUnOc\"\u003e\n\u003cspan\u003e\u003cstrong\u003eAI\u003c/strong\u003e\u003c/span\u003e\u003cspan\u003e\u003cstrong\u003e客服和人工客服是朋友，不是对手\u003c/strong\u003e\u003c/span\u003e\n\u003c/h1\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-IUyUdI48woIMOmxIhjWcD65qnRc\"\u003e\n\u003cspan\u003eAI\u003c/span\u003e\u003cspan\u003e将取代人工吗？这一灵魂拷问如今延伸到了客服领域，很多人问：AI客服会取代人工客服吗？\u003c/span\u003e\n\u003c/div\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-CaMiduiiOoqIuAx2fVVcAfg9nse\"\u003e\n\u003cspan\u003e对此，段毓铮给出的答案是：\u003c/span\u003e\u003cspan\u003eAI\u003c/span\u003e\u003cspan\u003e和人工是朋友，AI客服和人工客服也是朋友，不是对手。“从大方向来说，服务过程中的对话信息都是零碎的‘非结构化数据’，这些数据是分析不出东西来的，对业务是起不到帮助的。但有了AI，这些全渠道的服务信息可以实现高效的、高质量的结构化，企业得以从中挖掘出用户真实的意图和需求，然后用于改进服务流程，优化营销活动，甚至升级品牌的价值主张等等”，段毓铮说数据的结构化是企业走向数字化转型的第一步，是一件很关键的事情。\u003c/span\u003e\n\u003c/div\u003e\n\u003cdiv class=\"image-uploaded gallery old-record-id-JyykdW2swowoSQxgSQgc3xOBnTw\" data-type=\"image\" data-ace-gallery-json='{\"items\":[{\"uuid\":\"df2fef30-6c28-4be1-99ed-1bb85af8e5f1\",\"height\":394,\"width\":700,\"currHeight\":394,\"currWidth\":700,\"natrualHeight\":394,\"natrualWidth\":700,\"pluginName\":\"imageUpload\",\"scale\":1,\"src\":\"https%3A%2F%2Finternal-api-drive-stream.feishu.cn%2Fspace%2Fapi%2Fbox%2Fstream%2Fdownload%2Fpreview%2FboxcnenhDA8SDfRrgWvxmJMYKzf%2F%3Fpreview_type%3D16\",\"file_token\":\"boxcnenhDA8SDfRrgWvxmJMYKzf\",\"image_type\":\"image/jpeg\",\"size\":58925,\"comments\":[]}]}'\u003e\u003cimg src=\"https://jinmuinfo.feishu.cn/space/api/box/stream/download/asynccode/?code=NmY5ZjE5YTdhZTkzOWYyZDZmODVjYjU3NGI3NDUyZWZfWEU3a3l1R25Fek5DZmQ4Wlo1M0s3dUsyNG5wajQyalNfVG9rZW46Ym94Y25lbmhEQThTRGZScmdXdnhtSk1ZS3pmXzE2OTg5OTkzODM6MTY5OTAwMjk4M19WNA\" data-single-block=\"true\" data-snapshot=\"eyJsb2NrZWQiOmZhbHNlLCJoaWRkZW4iOmZhbHNlLCJhdXRob3IiOiI3MTMxOTM0MDA2MjA0NzEwOTE2IiwiYWxpZ24iOiJjZW50ZXIiLCJpbWFnZSI6eyJuYW1lIjoiMjIxMjIxMTQ0ODQ0NjcxNTU0OTA5LmpwZyIsImNyb3AiOlswLDAsMCwwXSwidG9rZW4iOiJib3hjbmVuaERBOFNEZlJyZ1d2eG1KTVlLemYiLCJtaW1lVHlwZSI6ImltYWdlL2pwZWciLCJzaXplIjo1ODkyNSwic2NhbGUiOjEsIndpZHRoIjo3MDAsImhlaWdodCI6Mzk0LCJjYXB0aW9uIjp7InRleHQiOnsiYXBvb2wiOnsibmV4dE51bSI6MCwibnVtVG9BdHRyaWIiOm51bGx9LCJpbml0aWFsQXR0cmlidXRlZFRleHRzIjp7ImF0dHJpYnMiOm51bGwsInRleHQiOm51bGx9fX0sInJvdGF0aW9uIjowfSwidHlwZSI6ImltYWdlIiwicGFyZW50X2lkIjoiRGQ3emRCTmoxb1RzNDV4RXlNdmNCbDNLbkJkIiwiY29tbWVudHMiOltdfQ==\" data-suite=\"eyJmaWxlVG9rZW4iOiJib3hjbmVuaERBOFNEZlJyZ1d2eG1KTVlLemYiLCJvYmpUeXBlIjoiZG9jeCIsIm9ialRva2VuIjoiSnl5a2RXMnN3b3dvU1F4Z1NRZ2MzeE9CblR3Iiwib3JpZ2luU3JjIjoiaHR0cHM6Ly9pbnRlcm5hbC1hcGktZHJpdmUtc3RyZWFtLmZlaXNodS5jbi9zcGFjZS9hcGkvYm94L3N0cmVhbS9kb3dubG9hZC9wcmV2aWV3L2JveGNuZW5oREE4U0RmUnJnV3Z4bUpNWUt6Zi8/cHJldmlld190eXBlPTE2In0=\" data-src=\"https://internal-api-drive-stream.feishu.cn/space/api/box/stream/download/all/boxcnenhDA8SDfRrgWvxmJMYKzf/?mount_node_token=JyykdW2swowoSQxgSQgc3xOBnTw\u0026amp;mount_point=docx_image\" data-width=\"700\" data-height=\"394\"\u003e\u003c/div\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-TaaIdmQ2moCwOSxquy6c09vinPb\"\u003e\n\u003cspan\u003e随后，他进一步以网易七鱼的“智能服务小记”功能为例，为我们展现了\u003c/span\u003e\u003cspan\u003eAI\u003c/span\u003e\u003cspan\u003e对人工服务的辅助。从日常客服工作业务流程出发，网易七鱼捕捉到了人工坐席的一大痛点，即：在结束会话后，需要人工手动填写服务过程中的一些纪要。因为子类目众多，既耗时，又容易记错，让人很头疼。由此，AI加持的“网易七鱼智能服务小记”应运而生，在服务结束0.5秒内，机器人就会自动填充咨询分类和服务小记。由算法推荐的咨询分类，深度贴合业务特征，准确率高达95%+。此外，每通会话可为坐席节省20秒，既大大降低了访客的排队时间，也大大提升了坐席的工作体验。\u003c/span\u003e\n\u003c/div\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-UG2sdKAiEoq4euxSSZZcWeFinfd\"\u003e\n\u003cspan\u003e类似这样\u003c/span\u003e\u003cspan\u003eAI\u003c/span\u003e\u003cspan\u003e和人工强强联手、相辅相成的案例，在网易七鱼这里，还有很多很多。\u003c/span\u003e\n\u003c/div\u003e\n\u003cdiv class=\"ace-line ace-line old-record-id-EoUSdg6Aeoe0WexCKpGcHDJqnSf\"\u003e\n\u003cspan\u003e在演讲的最后，段毓铮反复强调两点：第一，\u003c/span\u003e\u003cspan\u003eAI\u003c/span\u003e\u003cspan\u003e一定要融入到整个组织中，整个业务流程中，整个服务体系中，这样的AI才会是有温度的；第二，人永远是企业最宝贵的资源，AI客服诞生后，通过智能化释放了更多人力，“人”得以去做更多更复杂、更有价值的事情。\u003c/span\u003e\n\u003c/div\u003e\n\u003c/div\u003e\n\u003cp\u003e \u003c/p\u003e"},{"id":21160130648217,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/ja-jp/articles/21160130648217.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/ja-jp/articles/21160130648217","author_id":11942258898585,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":21160059488025,"created_at":"2023-07-28T03:03:54Z","updated_at":"2023-07-28T03:03:54Z","name":"商品情報と操作動画","title":"商品情報と操作動画","source_locale":"ja-jp","locale":"ja-jp","outdated":false,"outdated_locales":[],"edited_at":"2023-07-28T03:03:54Z","user_segment_id":null,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":[],"body":"\u003ctable style=\"border-collapse: collapse; width: 349.02pt;\" border=\"0\" width=\"349\" cellspacing=\"0\" cellpadding=\"0\"\u003e\n\u003ctbody\u003e\n\u003ctr style=\"height: 129.00pt;\"\u003e\n\u003ctd class=\"et4\" style=\"height: 129.00pt; width: 349.50pt;\" width=\"349\" height=\"129\"\u003e\u003ca title=\"https://www.youtube.com/@holystonejp9423/videos\" href=\"https://www.youtube.com/@holystonejp9423/videos\"\u003ehttps://www.youtube.com/@holystonejp9423/videos\u003c/a\u003e\u003c/td\u003e\n\u003c/tr\u003e\n\u003c/tbody\u003e\n\u003c/table\u003e"},{"id":21160092739609,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/ja-jp/articles/21160092739609.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/ja-jp/articles/21160092739609","author_id":11942258898585,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":21160059488025,"created_at":"2023-07-28T03:03:31Z","updated_at":"2023-07-28T03:03:31Z","name":"店のリンク","title":"店のリンク","source_locale":"ja-jp","locale":"ja-jp","outdated":false,"outdated_locales":[],"edited_at":"2023-07-28T03:03:31Z","user_segment_id":null,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":[],"body":"\u003ctable style=\"border-collapse: collapse; width: 349.02pt;\" border=\"0\" width=\"349\" cellspacing=\"0\" cellpadding=\"0\"\u003e\n\u003ctbody\u003e\n\u003ctr style=\"height: 129.00pt;\"\u003e\n\u003ctd class=\"et3\" style=\"height: 129.00pt; width: 349.50pt;\" width=\"349\" height=\"129\"\u003e\u003ca title=\"https://www.amazon.co.jp/stores/HolyStone/page/52B6F793-AB39-4782-8A81-DF509C9D2869?ref_=ast_bln\" href=\"https://www.amazon.co.jp/stores/HolyStone/page/52B6F793-AB39-4782-8A81-DF509C9D2869?ref_=ast_bln\"\u003ehttps://www.amazon.co.jp/stores/HolyStone/page/52B6F793-AB39-4782-8A81-DF509C9D2869?ref_=ast_bln\u003c/a\u003e\u003c/td\u003e\n\u003c/tr\u003e\n\u003c/tbody\u003e\n\u003c/table\u003e"},{"id":21159792172441,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/ja-jp/articles/21159792172441.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/ja-jp/articles/21159792172441","author_id":11942258898585,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":21159597028889,"created_at":"2023-07-28T02:57:36Z","updated_at":"2023-11-03T05:52:21Z","name":"使用注意","title":"使用注意","source_locale":"ja-jp","locale":"ja-jp","outdated":false,"outdated_locales":[],"edited_at":"2023-07-28T02:57:36Z","user_segment_id":8696049524889,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":[],"body":"\u003ctable style=\"border-collapse: collapse; width: 349.02pt;\" border=\"0\" width=\"349\" cellspacing=\"0\" cellpadding=\"0\"\u003e\n\u003ctbody\u003e\n\u003ctr style=\"height: 186.75pt;\"\u003e\n\u003ctd class=\"et3\" style=\"height: 186.75pt; width: 349.50pt;\" width=\"349\" height=\"186\"\u003e1.室内でGPS ドローンを利用する時、ロック解除してもプロペラは回らないの原因はなんですか。\u003cbr\u003e室内あるいはGPS信号が弱いところでドローンを利用する時、GPSモードをオフ必要があります。\u003cbr\u003e\u003cbr\u003e2.手元のドローンは15m以上の距離を飛ぶことができません。その原因は何でしょうか。\u003cbr\u003e\u003cbr\u003eドローンには初心者モードがあります。もし手元のドローンが15ｍを超える距離を飛べない場合は、アプリ設定で初心者モードをオフにし、そして飛行距離や高度などを最大値に設定してみてください。\u003c/td\u003e\n\u003c/tr\u003e\n\u003c/tbody\u003e\n\u003c/table\u003e"},{"id":20527985672729,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-hk/articles/20527985672729.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/articles/20527985672729","author_id":11942258898585,"comments_disabled":false,"draft":false,"promoted":true,"position":0,"vote_sum":0,"vote_count":0,"section_id":20358389599385,"created_at":"2023-07-11T02:20:47Z","updated_at":"2023-07-11T03:10:31Z","name":"智能机器人文章推荐","title":"智能机器人文章推荐","source_locale":"zh-hk","locale":"zh-hk","outdated":false,"outdated_locales":[],"edited_at":"2023-07-11T02:20:54Z","user_segment_id":null,"permission_group_id":8696049536537,"content_tag_ids":["01H51CKM0NMAF793EV2EZWDAGX"],"label_names":["文章推荐"],"body":"\u003cp\u003e\u003cstrong\u003e如果这篇文章被推荐了，那么你的智能机器人推荐文章功能就成功了。\u003c/strong\u003e\u003c/p\u003e"},{"id":20360934695577,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-hk/articles/20360934695577.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/articles/20360934695577-123","author_id":11942258898585,"comments_disabled":false,"draft":true,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":20358389599385,"created_at":"2023-07-06T02:59:15Z","updated_at":"2023-11-03T05:52:21Z","name":"123","title":"123","source_locale":"zh-hk","locale":"zh-hk","outdated":false,"outdated_locales":[],"edited_at":"2023-07-06T02:59:15Z","user_segment_id":8696049524889,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":[],"body":"\u003cp\u003e123\u003c/p\u003e"},{"id":20360580421401,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-hk/articles/20360580421401.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/articles/20360580421401","author_id":11942258898585,"comments_disabled":false,"draft":true,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":20358389599385,"created_at":"2023-07-06T02:49:45Z","updated_at":"2023-11-03T05:52:21Z","name":"测试文章2","title":"测试文章2","source_locale":"zh-hk","locale":"zh-hk","outdated":false,"outdated_locales":[],"edited_at":"2023-07-06T02:49:45Z","user_segment_id":8696049524889,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":[],"body":"\u003cp\u003e这是一个为了测试block的文章。\u003c/p\u003e"},{"id":20360171943193,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-hk/articles/20360171943193.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/articles/20360171943193","author_id":11942258898585,"comments_disabled":false,"draft":false,"promoted":true,"position":0,"vote_sum":0,"vote_count":0,"section_id":20358389599385,"created_at":"2023-07-06T02:39:53Z","updated_at":"2023-07-11T02:16:53Z","name":"测试文章","title":"测试文章","source_locale":"zh-hk","locale":"zh-hk","outdated":false,"outdated_locales":[],"edited_at":"2023-07-06T02:42:10Z","user_segment_id":null,"permission_group_id":8696049536537,"content_tag_ids":["01H51BD7BKHYY8GPRA1SE3K1NQ"],"label_names":["文章推荐"],"body":"\u003cp\u003e这是一个测试文章\u003c/p\u003e"},{"id":15400866438809,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-hk/articles/15400866438809.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/articles/15400866438809","author_id":11942258898585,"comments_disabled":false,"draft":false,"promoted":true,"position":0,"vote_sum":0,"vote_count":0,"section_id":15400768064537,"created_at":"2023-02-09T09:48:38Z","updated_at":"2023-02-09T09:48:57Z","name":"演示的文章","title":"演示的文章","source_locale":"zh-hk","locale":"zh-hk","outdated":false,"outdated_locales":[],"edited_at":"2023-02-09T09:48:57Z","user_segment_id":null,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":["mangodb"],"body":"\u003cp\u003e测试内容\u003c/p\u003e\n\u003cp\u003e测试内容\u003c/p\u003e\n\u003ch1\u003e测试内容\u003c/h1\u003e\n\u003cp\u003e测试内容\u003c/p\u003e\n\u003cp\u003e测试内容\u003c/p\u003e\n\u003cp\u003e测试内容\u003c/p\u003e\n\u003cp\u003e测试内容\u003c/p\u003e\n\u003cp\u003e测试内容\u003c/p\u003e\n\u003cp\u003e测试内容 \u003c/p\u003e"},{"id":10074474454425,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-hk/articles/10074474454425.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/articles/10074474454425-Internal-Article","author_id":9043574689689,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":10073811807257,"created_at":"2022-09-01T06:58:25Z","updated_at":"2023-11-03T05:52:21Z","name":"Internal Article","title":"Internal Article","source_locale":"zh-hk","locale":"zh-hk","outdated":false,"outdated_locales":[],"edited_at":"2022-09-01T06:58:25Z","user_segment_id":8696049524889,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":["internal"],"body":"\u003cp\u003eHello  everyone\u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e\n\u003cp\u003ethis article only internal use;\u003c/p\u003e"},{"id":10074241388953,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-hk/articles/10074241388953.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/articles/10074241388953","author_id":9043574689689,"comments_disabled":true,"draft":false,"promoted":true,"position":0,"vote_sum":0,"vote_count":0,"section_id":10073811807257,"created_at":"2022-09-01T06:51:55Z","updated_at":"2022-09-01T06:54:29Z","name":"MongoDB 安装","title":"MongoDB 安装","source_locale":"zh-hk","locale":"zh-hk","outdated":false,"outdated_locales":[],"edited_at":"2022-09-01T06:54:29Z","user_segment_id":null,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":["MongoDB"],"body":"\u003cp\u003e1.分片集群概述\u003cbr\u003eMongoDB分片集群，英文名称为： Sharded Cluster\u003c/p\u003e\n\u003cp\u003e旨在通过横向扩展，来提高数据吞吐性能、增大数据存储量。\u003c/p\u003e\n\u003cp\u003e\u003cimg src=\"https://www.whaleal.com/file/topic/2022-06-25/image/5719d9862f9e402798e78a190ac0702bb28.png\" alt=\"\"\u003e\u003c/p\u003e\n\u003cp\u003e\u003cbr\u003e分片集群由三个组件：“mongos”, “config server”, “shard” 组成。\u003cbr\u003emongos:数据库请求路由。负责接收所有客户端应用程序的连接查询请求，并将请求路由到集群内部对应的分片上。\"mongos\"可以有1个或多个。\u003cbr\u003econfig server: 配置服务，负责保存集群的元数据信息，比如集群的分片信息、用户信息。\u003cbr\u003eMongoDB 3.4 版本以后，“config server” 必须是副本集！\u003cbr\u003eshard: 分片存储。将数据分片存储在多个服务器上。\u003cbr\u003e有点类似关系数据库\"分区表\"的概念，只不过分区表是将数据分散存储在多个文件中，而sharding将数据分散存储在多个服务器上。一个集群可以有一个或多个分片。\u003cbr\u003eMongoDB 3.6以后，每个分片都必须是副本集！\u003cbr\u003e\u003cbr\u003e2. 分片集群搭建步骤\u003cbr\u003e分片集群各部分组件搭建顺序（程序启动顺序也是如此）：\u003cbr\u003e\u003cbr\u003e“config server” -\u0026gt; 2. “shard” -\u0026gt; 3. “mongos”\u003cbr\u003e2.1 搭建\"config server\"\u003cbr\u003e\"config server\"由三台主机组成，每台主机上运行一个mongod进程，三台主机的mongod组成一个副本集。\u003cbr\u003e\u003cbr\u003e2.1.1 配置\"config server\" mongod.conf\u003cbr\u003edbpath = /home/mongodb/data/db\u003cbr\u003elogpath = /home/mongodb/app/mongodb-3.6/mongodb.log\u003cbr\u003e\u003cbr\u003eport = 27017\u003cbr\u003ebind_ip = 0.0.0.0\u003cbr\u003e\u003cbr\u003efork = true                             # run background\u003cbr\u003e#nohttpinterface = true\u003cbr\u003e\u003cbr\u003ereplSet = rs-config\u003cbr\u003eoplogSize = 2048                       #2G\u003cbr\u003e\u003cbr\u003econfigsvr = true\u003cbr\u003e\u003cbr\u003e#auth = true\u003cbr\u003e#keyFile = /home/mongodb/app/mongodb-3.6/keyfile\u003cbr\u003e\u003cbr\u003e三台主机的mongod.conf配置相同。\u003cbr\u003e注意: 必须配置：“configsvr = true”\u003cbr\u003e\u003cbr\u003e2.1.2 启动mongod\u003cbr\u003e三台服务器上，依次启动mongod程序\u003cbr\u003e\u003cbr\u003emongod --config /home/mongodb/app/mongodb-3.6/bin/mongod.conf\u003cbr\u003e1\u003cbr\u003e2.1.3 初始化\"config server\"副本集\u003cbr\u003e任选一个节点，执行初始化命令\u003cbr\u003e\u003cbr\u003ers.initiate(\u003cbr\u003e  {\u003cbr\u003e     \"_id\" : \"rs-config\", \u003cbr\u003e     \"members\" : [\u003cbr\u003e       {\"_id\" : 0, \"host\" : \"192.168.6.22:27017\"},\u003cbr\u003e       {\"_id\" : 1, \"host\" : \"192.168.6.23:27017\"},\u003cbr\u003e       {\"_id\" : 2, \"host\" : \"192.168.6.24:27017\"}\u003cbr\u003e     ]\u003cbr\u003e  }\u003cbr\u003e\u003cbr\u003e2.2 搭建 \"shard\"分片服务器\u003cbr\u003e一共有2个分片，每个分片由3台mongod服务组成副本集。配置过程相同。\u003cbr\u003e\u003cbr\u003e2.2.1 配置 “shard” mongod.conf\u003cbr\u003e分片1配置(三个节点配置相同):\u003cbr\u003e\u003cbr\u003edbpath = /home/mongodb/data/db\u003cbr\u003elogpath = /home/mongodb/app/mongodb-3.6/mongodb.log\u003cbr\u003e\u003cbr\u003eport = 27017\u003cbr\u003ebind_ip = 0.0.0.0\u003cbr\u003e\u003cbr\u003efork = true                             # run background\u003cbr\u003e#nohttpinterface = true\u003cbr\u003e\u003cbr\u003ereplSet = rs-shard1\u003cbr\u003eoplogSize = 2048                       #2G\u003cbr\u003e\u003cbr\u003eshardsvr = true\u003cbr\u003e\u003cbr\u003e#auth = true\u003cbr\u003e#keyFile = /home/mongodb/app/mongodb-3.6/keyfile\u003cbr\u003e\u003cbr\u003e分片2配置(三个节点配置相同):\u003cbr\u003e\u003cbr\u003edbpath = /home/mongodb/data/db\u003cbr\u003elogpath = /home/mongodb/app/mongodb-3.6/mongodb.log\u003cbr\u003e\u003cbr\u003eport = 27017\u003cbr\u003ebind_ip = 0.0.0.0\u003cbr\u003e\u003cbr\u003efork = true                             # run background\u003cbr\u003e#nohttpinterface = true\u003cbr\u003e\u003cbr\u003ereplSet = rs-shard2\u003cbr\u003eoplogSize = 2048                       #2G\u003cbr\u003e\u003cbr\u003eshardsvr = true\u003cbr\u003e\u003cbr\u003e#auth = true\u003cbr\u003e#keyFile = /home/mongodb/app/mongodb-3.6/keyfile\u003cbr\u003e\u003cbr\u003e注：\u003cbr\u003e(1) 分片1和分片2，除了副本集名称不同外，其它配置相同。\u003cbr\u003e      replSet = rs-shard1\u003cbr\u003e      replSet = rs-shard2\u003cbr\u003e(2) 必须配置：“shardsvr = true”\u003cbr\u003e\u003cbr\u003e3.2.2 启动mongod\u003cbr\u003e依次启动两个分片的mongod程序\u003cbr\u003e\u003cbr\u003emongod --config /home/mongodb/app/mongodb-3.6/bin/mongod.conf\u003cbr\u003e1\u003cbr\u003e3.2.3 初始化shard副本集\u003cbr\u003e分片1副本集初始化：任选分片内的一个节点，执行初始化命令\u003cbr\u003e\u003cbr\u003ers.initiate(\u003cbr\u003e  {\u003cbr\u003e     \"_id\" : \"rs-shard1\", \u003cbr\u003e     \"members\" : [\u003cbr\u003e       {\"_id\" : 0, \"host\" : \"192.168.6.25:27017\"},\u003cbr\u003e       {\"_id\" : 1, \"host\" : \"192.168.6.26:27017\"},\u003cbr\u003e       {\"_id\" : 2, \"host\" : \"192.168.6.27:27017\"}\u003cbr\u003e     ]\u003cbr\u003e  }\u003cbr\u003e)\u003cbr\u003e\u003cbr\u003e分片2副本集初始化：连接\"primary\"主节点，执行初始化命令\u003cbr\u003e\u003cbr\u003ers.initiate(\u003cbr\u003e  {\u003cbr\u003e     \"_id\" : \"rs-shard2\", \u003cbr\u003e     \"members\" : [\u003cbr\u003e       {\"_id\" : 0, \"host\" : \"192.168.6.28:27017\"},\u003cbr\u003e       {\"_id\" : 1, \"host\" : \"192.168.6.29:27017\"},\u003cbr\u003e       {\"_id\" : 2, \"host\" : \"192.168.6.30:27017\"}\u003cbr\u003e     ]\u003cbr\u003e  }\u003cbr\u003e)\u003cbr\u003e\u003cbr\u003e2.3 搭建\"mongos\"\u003cbr\u003emongos可以为1个，也可以为多个。\u003cbr\u003e每个mongos配置相对独立，配置过程及参数相同。\u003cbr\u003e\u003cbr\u003e2.3.1 配置 “mongos.conf”\u003cbr\u003elogpath = /home/mongodb/app/mongodb-3.6/mongos.log\u003cbr\u003elogappend = true\u003cbr\u003e\u003cbr\u003ebind_ip = 0.0.0.0\u003cbr\u003eport = 27017\u003cbr\u003e\u003cbr\u003efork = true\u003cbr\u003e\u003cbr\u003e#keyFile = /mongos/autokey\u003cbr\u003e\u003cbr\u003econfigdb = rs-config/192.168.6.22:27017,192.168.6.23:27017,192.168.6.24:27017\u003cbr\u003e\u003cbr\u003e# max connections\u003cbr\u003emaxConns=200\u003cbr\u003e\u003cbr\u003e2.3.2 启动\"mongos\"\u003cbr\u003emongos --config /home/mongodb/app/mongodb-3.6/bin/mongos.conf\u003cbr\u003e1\u003cbr\u003e3.4 添加分片\u003cbr\u003e连接任意一个\"mongos\"，添加分片信息\u003cbr\u003e\u003cbr\u003euse admin\u003cbr\u003e\u003cbr\u003esh.addShard(\"rs-shard1/192.168.6.25:27017,192.168.6.26:27017,192.168.6.27:27017\")\u003cbr\u003e\u003cbr\u003esh.addShard(\"rs-shard2/192.168.6.28:27017,192.168.6.29:27017,192.168.6.30:27017\")\u003cbr\u003e\u003cbr\u003e查看集群分片状态\u003cbr\u003e\u003cbr\u003esh.status()\u003cbr\u003e至此，一个基本的\"sharded cluster\"，就已经搭建完成。\u003cbr\u003e3\u003cbr\u003e. 使用分片\u003cbr\u003e使用分片的基本步骤： 1. 开启数据库分片 -\u0026gt; 2. 开启集合分片\u003cbr\u003e对数据库分片是对集合分片的先决条件。\u003cbr\u003e\u003cbr\u003e3.1 开启数据库分片\u003cbr\u003e以数据库：\"testdb\"为例，开启数据库分片命令为：\u003cbr\u003e\u003cbr\u003euse admin\u003cbr\u003e\u003cbr\u003esh.enableSharding(\"testdb\")\u003cbr\u003e\u003cbr\u003e3.2 开启集合分片\u003cbr\u003e以集合\"testdb.coll1\"为例，开启集合分片命令为：\u003cbr\u003e\u003cbr\u003esh.shardCollection(\"testdb.coll1\", {\"name\" : \"hashed\"})\u003cbr\u003e1\u003cbr\u003e说明：\u003cbr\u003e(1) 第一个参数为集合的完整namespace名称\u003cbr\u003e(2) 第二个参数为片键，指定根据哪个字段进行分片。\u003cbr\u003e  具体参考官网\"sh.shardCollection()\"说明\u003cbr\u003e\u003cbr\u003e4.3 插入数据验证数据分片\u003cbr\u003e测试插入数据\u003cbr\u003e\u003cbr\u003euse testdb\u003cbr\u003e\u003cbr\u003efor (var i = 1; i \u0026lt;= 100000; i++){\u003cbr\u003e  db.coll1.insert({\"id\" : i, \"name\" : \"name\" + i});\u003cbr\u003e}    \u003cbr\u003e\u003cbr\u003e验证是否分片\u003cbr\u003e\u003cbr\u003esh.status()\u003cbr\u003e\u003cbr\u003e\u003cbr\u003e结果显示，数据已均匀分布在两个分片上。\u003cbr\u003e\u003cbr\u003e4. 添加集群认证\u003cbr\u003e上文已经成功创建一个分片集群，并验证数据分片可用。\u003cbr\u003e但在部署生产环境时，还需添加认证，用以保障集群安全性。\u003cbr\u003e认证分两种：\u003cbr\u003e\u003cbr\u003e集群内部认证 (Internal Authentication)\u003cbr\u003e用于集群内的各个组件(mongos, config server, shard)之间相互访问认证，\u003cbr\u003e也就是所有的mongos进程和mongod进程之间相互访问认证。\u003cbr\u003e内部认证通过keyfile密钥文件实现，即所有的monogs/mongod公用同一个keyfile文件来相互认证。\u003cbr\u003e如果集群外随便来一个\"mongod\"进程，如果没有相同的keyfile，想加入集群，是不可能的。\u003cbr\u003e\u003cbr\u003e外部用户访问集群所需的用户认证 (User Access Controls)\u003cbr\u003e用于外部客户端访问mongos时，所需的用户认证。\u003cbr\u003e\u003cbr\u003e4.1 生成并分发密钥文件keyfile\u003cbr\u003e(1) 生成keyfile文件\u003cbr\u003e\u003cbr\u003eopenssl rand -base64 90 -out ./keyfile\u003cbr\u003e1\u003cbr\u003e(2) 更改\"keyfile\"密钥文件权限\u003cbr\u003e\u003cbr\u003echmod 600 keyfile\u003cbr\u003e1\u003cbr\u003e(3) 将\"keyfile\"密钥文件拷贝到集群每一个mongos/mongod服务器上\u003cbr\u003e\u003cbr\u003e5.2 添加超级管理员用户\u003cbr\u003e连接任意一个\"mongos\"，创建超级管理员用户\u003cbr\u003e\u003cbr\u003euse admin\u003cbr\u003e\u003cbr\u003edb.createUser(\u003cbr\u003e    {\u003cbr\u003e        user: \"root\",\u003cbr\u003e        pwd: \"root\",\u003cbr\u003e        roles: [{role : \"root\", db : \"admin\"}]\u003cbr\u003e    }\u003cbr\u003e)\u003cbr\u003e\u003cbr\u003e在\"mongos\"上添加用户，用户信息实际保存在\"config server\"上，\"mongos\"本身不存储任何数据，包括用户信息。\u003cbr\u003e\u003cbr\u003e然而，\"mongos\"上创建的用户，是不会自动添加到\"shard\"分片服务器上的。\u003cbr\u003e为了以后方便维护shard分片服务器，分别登录到每个分片服务器的\"primary\"节点，添加管理员用户\u003cbr\u003e\u003cbr\u003euse admin\u003cbr\u003e\u003cbr\u003edb.createUser(\u003cbr\u003e  {\u003cbr\u003e    user: \"useradmin\",\u003cbr\u003e    pwd: \"useradmin\",\u003cbr\u003e    roles: [ { role: \"userAdminAnyDatabase\", db: \"admin\" } ]\u003cbr\u003e  }\u003cbr\u003e)\u003cbr\u003e4.3 开启认证\u003cbr\u003e(1) 为所有\"monogd\"程序添加认证参数\u003cbr\u003e\u003cbr\u003eauth = true\u003cbr\u003ekeyFile = /home/mongodb/app/mongodb-3.6/keyfile\u003cbr\u003e\u003cbr\u003e(2) 为所有\"mongos\"程序添加认证参数\u003cbr\u003e\u003cbr\u003ekeyFile = /home/mongodb/app/mongodb-3.6/keyfile\u003cbr\u003e(3) 停止集群内所有mongod/mongos程序\u003cbr\u003e(4) 按照如下顺序启动所有程序：\u003cbr\u003e      1. “config server” -\u0026gt; 2. “shard” -\u0026gt; 3. “mongos”\u003cbr\u003e(5) 验证用户访问\u003cbr\u003e      通过连接任意一个mongos，验证用户访问\u003cbr\u003e\u003cbr\u003e\u003cbr\u003e附：开启用户认证后的最终配置文件\u003cbr\u003econfig server: mongod.conf\u003cbr\u003e\u003cbr\u003edbpath = /home/mongodb/data/db\u003cbr\u003elogpath = /home/mongodb/app/mongodb-3.6/mongodb.log\u003cbr\u003e\u003cbr\u003eport = 27017\u003cbr\u003ebind_ip = 0.0.0.0\u003cbr\u003e\u003cbr\u003efork = true                             # run background\u003cbr\u003e#nohttpinterface = true\u003cbr\u003e\u003cbr\u003ereplSet = rs-config\u003cbr\u003eoplogSize = 2048                       #2G\u003cbr\u003e\u003cbr\u003econfigsvr = true\u003cbr\u003e\u003cbr\u003eauth = true\u003cbr\u003ekeyFile = /home/mongodb/app/mongodb-3.6/keyfile\u003cbr\u003e\u003cbr\u003eshard1: mongod.conf\u003cbr\u003e\u003cbr\u003edbpath = /home/mongodb/data/db\u003cbr\u003elogpath = /home/mongodb/app/mongodb-3.6/mongodb.log\u003cbr\u003e\u003cbr\u003eport = 27017\u003cbr\u003ebind_ip = 0.0.0.0\u003cbr\u003e\u003cbr\u003efork = true                             # run background\u003cbr\u003e#nohttpinterface = true\u003cbr\u003e\u003cbr\u003ereplSet = rs-shard1\u003cbr\u003eoplogSize = 2048                       #2G\u003cbr\u003e\u003cbr\u003eshardsvr = true\u003cbr\u003e\u003cbr\u003eauth = true\u003cbr\u003ekeyFile = /home/mongodb/app/mongodb-3.6/keyfile\u003cbr\u003e\u003cbr\u003eshard2: mongod.conf\u003cbr\u003e\u003cbr\u003edbpath = /home/mongodb/data/db\u003cbr\u003elogpath = /home/mongodb/app/mongodb-3.6/mongodb.log\u003cbr\u003e\u003cbr\u003eport = 27017\u003cbr\u003ebind_ip = 0.0.0.0\u003cbr\u003e\u003cbr\u003efork = true                             # run background\u003cbr\u003e#nohttpinterface = true\u003cbr\u003e\u003cbr\u003ereplSet = rs-shard2\u003cbr\u003eoplogSize = 2048                       #2G\u003cbr\u003e\u003cbr\u003eshardsvr = true\u003cbr\u003e\u003cbr\u003eauth = true\u003cbr\u003ekeyFile = /home/mongodb/app/mongodb-3.6/keyfile\u003cbr\u003e\u003cbr\u003emongos: mongos.conf\u003cbr\u003e\u003cbr\u003elogpath = /home/mongodb/app/mongodb-3.6/mongos.log\u003cbr\u003elogappend = true\u003cbr\u003e\u003cbr\u003ebind_ip = 0.0.0.0\u003cbr\u003eport = 27017\u003cbr\u003e\u003cbr\u003efork = true\u003cbr\u003e\u003cbr\u003ekeyFile = /home/mongodb/app/mongodb-3.6/keyfile\u003cbr\u003e\u003cbr\u003econfigdb = rs-config/192.168.6.22:27017,192.168.6.23:27017,192.168.6.24:27017\u003cbr\u003e\u003cbr\u003e# max connections\u003cbr\u003emaxConns=200\u003c/p\u003e"},{"id":8696106636185,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-tw/articles/8696106636185.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-tw/articles/8696106636185","author_id":8426999947417,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":8696106562841,"created_at":"2022-07-21T03:01:12Z","updated_at":"2022-07-21T03:01:12Z","name":"代理可如何用知識幫助客戶？","title":"代理可如何用知識幫助客戶？","source_locale":"zh-tw","locale":"zh-tw","outdated":false,"outdated_locales":[],"edited_at":"2022-07-21T03:01:12Z","user_segment_id":null,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":[],"body":"\u003cp\u003e您可使用\u003ca href=\"https://support.zendesk.com/hc/en-us/articles/115012706488\" target=\"_blank\"\u003e知識提取 app\u003c/a\u003e 使用團隊的集體知識。\u003c/p\u003e\n\u003cp\u003e使用此 app，代理可：\n\u003c/p\u003e\u003cul\u003e\n  \u003cli\u003e搜尋客服中心，無需離開工單\u003c/li\u003e\n  \u003cli\u003e在工單評論中插入連結至相關客服中心文章\u003c/li\u003e\n  \u003cli\u003e新增內嵌式意見至需更新的現有文章\u003c/li\u003e\n  \u003cli\u003e使用預先定義的範本，在回覆工單時建立新文章\u003c/li\u003e\n\u003c/ul\u003e\n\n\n\u003cp\u003e代理無需離開工單介面，即可共用、標幟或建立知識，便於其幫助客戶，同時改進為其他客戶提供的自助服務。\u003c/p\u003e\n\n\u003cp\u003e若要開始，查閱我們的\u003ca href=\"https://support.zendesk.com/hc/en-us/articles/360001975088\" target=\"_blank\"\u003e知識提取文件\u003c/a\u003e。\u003c/p\u003e\n\n\u003cp\u003e在代理可直接從工單開始建立新知識之前，您需\u003ca href=\"https://support.zendesk.com/hc/en-us/articles/115002374987\" target=\"_blank\"\u003e建立範本\u003c/a\u003e供其使用。我們已提供一些範本提議，以助您開始。您可將下列任意範本樣本複製並貼上新建文章，新增 \u003cstrong\u003eKCTemplate\u003c/strong\u003e 標籤至文章，即一切就緒。\u003c/p\u003e\n\n\u003ch4\u003e問答範本：\u003c/h4\u003e\n\n\u003cblockquote\u003e\n\n\u003cp\u003e\n\u003c/p\u003e\n\u003ch3\u003e[標題]\u003c/h3\u003e\n\n\n\u003cp\u003e\n\u003c/p\u003e\n\u003ch3\u003e問題\u003c/h3\u003e\n在此處輸入問題。\n\n\n\u003cp\u003e\n\u003c/p\u003e\n\u003ch3\u003e答案\u003c/h3\u003e\n在此處輸入答案。\n\n\n\u003c/blockquote\u003e\n\n\u003ch4\u003e解決方案範本：\u003c/h4\u003e\n\n\u003cblockquote\u003e\n\n\u003cp\u003e\n\u003c/p\u003e\n\u003ch3\u003e[標題]\u003c/h3\u003e\n\n\n\u003cp\u003e\n\u003c/p\u003e\n\u003ch3\u003e徵兆\u003c/h3\u003e\n在此處輸入徵兆。\n\n\n\u003cp\u003e\n\u003c/p\u003e\n\u003ch3\u003e解決方案\u003c/h3\u003e\n在此處輸入解決方案。\n\n\n\u003cp\u003e\n\u003c/p\u003e\n\u003ch3\u003e原因\u003c/h3\u003e\n在此處輸入原因。\n\n\n\u003c/blockquote\u003e\n\n\u003ch4\u003e使用方法範本：\u003c/h4\u003e\n\n\u003cblockquote\u003e\n\n\u003cp\u003e\n\u003c/p\u003e\n\u003ch3\u003e[標題]\u003c/h3\u003e\n\n\n\u003cp\u003e\n\u003c/p\u003e\n\u003ch3\u003e目標\u003c/h3\u003e\n在此處輸入目標或工作。\n\n\n\u003cp\u003e\n\u003c/p\u003e\n\u003ch3\u003e程序\u003c/h3\u003e\n在此處輸入步驟。\n\n\n\u003c/blockquote\u003e\n"},{"id":8696049637785,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-tw/articles/8696049637785.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-tw/articles/8696049637785","author_id":8426999947417,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":8696106562841,"created_at":"2022-07-21T03:01:12Z","updated_at":"2022-07-21T03:01:12Z","name":"怎樣以其他語言發佈內容？","title":"怎樣以其他語言發佈內容？","source_locale":"zh-tw","locale":"zh-tw","outdated":false,"outdated_locales":[],"edited_at":"2022-07-21T03:01:12Z","user_segment_id":null,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":[],"body":"\u003cp\u003e若您已\u003ca href=\"https://support.zendesk.com/hc/en-us/articles/224857687\" target=\"_blank\"\u003e設定客服中心支援多種語言\u003c/a\u003e，您可以支援的語言發佈內容。\u003c/p\u003e\n\n\u003cp\u003e以下是將您的客服中心當地語系化為其他語言的工作流程：\u003c/p\u003e\n\n\u003col\u003e\n\u003cli\u003e將您的內容翻譯為其他語言。\u003c/li\u003e\n\u003cli\u003e設定客服中心以支援您所有的語言。\u003c/li\u003e\n\u003cli\u003e新增已翻譯的內容至客服中心。\u003c/li\u003e\n\u003c/ol\u003e\n\n\n\u003cp\u003e說明請查閱 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/203664336#topic_inn_3qy_43\" target=\"_blank\"\u003eLocalizing the Help Center（英文）\u003c/a\u003e。\u003c/p\u003e"},{"id":8696049615001,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-tw/articles/8696049615001.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-tw/articles/8696049615001","author_id":8426999947417,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":8696106562841,"created_at":"2022-07-21T03:01:12Z","updated_at":"2022-07-21T03:01:12Z","name":"怎樣自訂客服中心？","title":"怎樣自訂客服中心？","source_locale":"zh-tw","locale":"zh-tw","outdated":false,"outdated_locales":[],"edited_at":"2022-07-21T03:01:12Z","user_segment_id":null,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":[],"body":"\u003cp\u003e您可變更色彩和字型以變更客服中心的外觀和感覺。請查閱 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/206177737\" target=\"_blank\"\u003eBranding your Help Center（英文）\u003c/a\u003e瞭解方法。\u003c/p\u003e\n\n\u003cp\u003e您也可變更客服中心的設計。若您熟悉頁面程式碼，您也可研究網站的 HTML、CSS 和 Javascript，以自訂主題。要開始請查閱 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/203664326\" target=\"_blank\"\u003eCustomizing the Help Center（英文）\u003c/a\u003e。\u003c/p\u003e"},{"id":8696106570009,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-tw/articles/8696106570009.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-tw/articles/8696106570009","author_id":8426999947417,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":8696106562841,"created_at":"2022-07-21T03:01:11Z","updated_at":"2022-07-21T03:01:11Z","name":"這些段落和文章有什麼用？","title":"這些段落和文章有什麼用？","source_locale":"zh-tw","locale":"zh-tw","outdated":false,"outdated_locales":[],"edited_at":"2022-07-21T03:01:11Z","user_segment_id":null,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":[],"body":"\u003cp\u003e此常見問題是客服中心知識庫「一般」類別中的一個段落。我們建立此類別以及一些常用的段落，幫您開始使用客服中心。\u003c/p\u003e\n\n\u003cp\u003e客服中心的知識庫包括三個主要頁面類型：類別頁面、段落頁面以及文章。其結構為：\u003c/p\u003e\n\n\u003cp\u003e\u003cimg src=\"//static.zdassets.com/hc/assets/sample-articles/article0_image.png\" alt=\"評論為文章的一部分。文章頁面為段落頁面的一部分。段落頁面為類別頁面的一部分。\"\u003e\u003c/p\u003e\n\n\u003cp\u003e您可建立您自己的類別、段落和文章，修改或完全刪除我們的。請查閱 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/218222877\" target=\"_blank\"\u003eOrganizing knowledge base content\u003c/a\u003e 和 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/203664366\" target=\"_blank\"\u003eCreating articles in the Help Center（英文）\u003c/a\u003e瞭解方法。\u003c/p\u003e"},{"id":8696049569945,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-tw/articles/8696049569945.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-tw/articles/8696049569945","author_id":8426999947417,"comments_disabled":false,"draft":true,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":8696049541401,"created_at":"2022-07-21T03:01:11Z","updated_at":"2022-07-21T03:01:11Z","name":"樣本文章：Stellar Skyonomy 退款政策","title":"樣本文章：Stellar Skyonomy 退款政策","source_locale":"zh-tw","locale":"zh-tw","outdated":false,"outdated_locales":[],"edited_at":"2022-07-21T03:01:11Z","user_segment_id":null,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":[],"body":"\u003cp\u003e購買 \u003cstrong\u003eStellar Skyonomy\u003c/strong\u003e 的所有商品都享有我們的 30 天滿意度保證，退貨無需理由。我們甚至會幫您支付寄回的運費。此外，您隨時可以取消 \u003cstrong\u003eStellar Skyonomy\u003c/strong\u003e 訂閱。取消之前，請檢閱這篇文章中的退款政策。\u003c/p\u003e\u003cbr\u003e\u003cp\u003e\u003cstrong\u003e退款政策\u003c/strong\u003e\u003c/p\u003e\u003cp\u003e您於出貨 30 天內\u003ca\u003e提出退貨\u003c/a\u003e時，我們會自動發出全額退款。\u003cbr\u003e\u003cbr\u003e若要\u003ca\u003e取消每年的網站訂閱\u003c/a\u003e，您隨時都可以進行此操作，退款將根據取消日期，按比例分配。\u003c/p\u003e\u003cbr\u003e\u003cp\u003e\u003cstrong\u003e申請退款\u003c/strong\u003e\u003c/p\u003e\u003cp\u003e若您覺得自己符合退款資格，但仍未收到退款，請填寫\u003ca\u003e退款請求表單以聯絡我們。\u003c/a\u003e我們會檢閱每一筆退款，且致力於在兩個工作日內回覆。\u003cbr\u003e\u003cbr\u003e請注意，若您未收到預期的退款，則款項最長可能需要 10 個工作日才會出現在信用卡對帳單上。\u003c/p\u003e"},{"id":8696049549977,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/zh-tw/articles/8696049549977.json","html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-tw/articles/8696049549977","author_id":8426999947417,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":8696049541401,"created_at":"2022-07-21T03:01:11Z","updated_at":"2022-07-21T03:01:11Z","name":"歡迎來到您的客服中心！","title":"歡迎來到您的客服中心！","source_locale":"zh-tw","locale":"zh-tw","outdated":false,"outdated_locales":[],"edited_at":"2022-07-21T03:01:11Z","user_segment_id":null,"permission_group_id":8696049536537,"content_tag_ids":[],"label_names":[],"body":"\u003cp\u003e您看到的是新\u003ca href=\"https://www.zendesk.tw/service/help-center/\" target=\"_blank\"\u003e客服中心\u003c/a\u003e。我們填入預留位置內容以幫您開始。您盡可編輯或刪除此內容。\u003c/p\u003e\r\n\r\n\u003cp\u003e客服中心設計用於為您的客戶提供完整的自助服務支援選擇。客服中心包括：知識庫以及適用於 Guide Professional 和 Enterprise 的用於支援請求的客戶入口網站。若您有 Zendesk Gather，您亦可新增一個社區至客服中心。\u003c/p\u003e\r\n\r\n\u003cp\u003e您的客戶可搜尋知識庫文章，瞭解如何工作，或搜尋社區（若可用），詢問其他使用者問題。若您的客戶找不到答案，仍可提交支援請求。\u003c/p\u003e\r\n\r\n\u003cp\u003e如需更多資訊，請查閱 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/203664386\" target=\"_blank\"\u003eHelp Center guide for end-users（英文）\u003c/a\u003e。\u003c/p\u003e\u003cp\u003e各個使用者均有客服中心個人檔案（Guide Professional 與 Enterprise），使客服中心使用者可更好地相互認識對方。個人檔案中包括有關使用者的資訊，及其活動和貢獻。\u003c/p\u003e"}],"sort_by":"position","sort_order":"asc"}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/articles}
    //200
    @Test
    void list_articles(){
        String sourceUrl = " https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/articles")
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
        };
    }
    //导入文章内容	文章导入知识库并可用
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/articles/#create-article
    //{"article":{"id":21976223789332,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles/21976223789332-Taking-photos-in-low-light.json","html_url":"https://jinmutraining.zendesk.com/hc/en-us/articles/21976223789332-Taking-photos-in-low-light","author_id":10332850159252,"comments_disabled":false,"content_tag_ids":[],"label_names":[],"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":21975969356948,"created_at":"2023-12-18T06:01:10Z","updated_at":"2023-12-18T06:01:10Z","edited_at":"2023-12-18T06:01:10Z","name":"Taking photos in low light","title":"Taking photos in low light","body":"Use a tripod","source_locale":"en-us","locale":"en-us","outdated":false,"outdated_locales":[],"permission_group_id":10361325375636,"user_segment_id":null}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/help_center/en-us/sections/21975969356948/articles}
    //201
    @Test
    void demo2141(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/en-us/sections/21975969356948/articles")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"article\": {\n" +
                        "    \"body\": \"Use a tripod\",\n" +
                        "    \"locale\": \"en-us\",\n" +
                        "    \"permission_group_id\": 10361325375636,\n" +
                        "    \"title\": \"Taking photos in low light\",\n" +
                        "    \"user_segment_id\": null\n" +
                        "  },\n" +
                        "  \"notify_subscribers\": false\n" +
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


    //{"article_attachments":[{"id":26672622852377,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/articles/attachments/26672622852377","article_id":26672627027097,"display_file_name":"1206.txt","file_name":"1206.txt","content_url":"https://pdi-jinmuinfo.zendesk.com/hc/article_attachments/26672622852377/1206.txt","relative_path":"/hc/article_attachments/26672622852377/1206.txt","content_type":"text/plain","size":354,"inline":false,"created_at":"2023-12-21T05:15:24Z","updated_at":"2023-12-21T05:15:53Z"}]}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/articles/26672627027097/attachments}
    //200
    @Test
    void list_article_attachments(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/articles/26672627027097/attachments")
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
        };
    }


    //上传文章附件
    //{"upload":{"token":"efv7dqTD9uaCYGEMSdncNtUTf","expires_at":"2023-12-24T06:49:04Z","attachments":[{"url":"https://jinmutraining.zendesk.com/api/v2/attachments/22104774538132.json","id":22104774538132,"file_name":"1206.txt","content_url":"https://jinmutraining.zendesk.com/attachments/token/7pdRqbEX0jL9f9u7QfLAgD8WH/?name=1206.txt","mapped_content_url":"https://jinmutraining.zendesk.com/attachments/token/7pdRqbEX0jL9f9u7QfLAgD8WH/?name=1206.txt","content_type":"application/json","size":190,"width":null,"height":null,"inline":false,"deleted":false,"malware_access_override":false,"malware_scan_result":"not_scanned","thumbnails":[]}],"attachment":{"url":"https://jinmutraining.zendesk.com/api/v2/attachments/22104774538132.json","id":22104774538132,"file_name":"1206.txt","content_url":"https://jinmutraining.zendesk.com/attachments/token/7pdRqbEX0jL9f9u7QfLAgD8WH/?name=1206.txt","mapped_content_url":"https://jinmutraining.zendesk.com/attachments/token/7pdRqbEX0jL9f9u7QfLAgD8WH/?name=1206.txt","content_type":"application/json","size":190,"width":null,"height":null,"inline":false,"deleted":false,"malware_access_override":false,"malware_scan_result":"not_scanned","thumbnails":[]}}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/uploads?filename=1206.txt}
    //201
    @Test
    void update_attachment(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/uploads?filename=1206.txt")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        " \"attachment\": {\n" +
                        "  \"content_type\": \"text/plain\",\n" +
                        "  \"content_url\": \"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/articles/attachments/26672622852377\",\n" +
                        "  \"file_name\": \"1206.txt\"\n" +
                        " }\n" +
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

    //导入文章附件	文章附件导入对应文章
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/article_attachments/#create-unassociated-attachment
    //不会导入附件
    //{"article_attachment":{"id":22105672400788,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/articles/attachments/22105672400788","article_id":20728574273428,"display_file_name":"test1.png","file_name":"test1.png","content_url":"https://jinmutraining.zendesk.com/hc/article_attachments/22105672400788/test1.png","relative_path":"/hc/article_attachments/22105672400788/test1.png","content_type":"image/png","size":36689,"inline":false,"created_at":"2023-12-21T07:35:32Z","updated_at":"2023-12-21T07:35:32Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/help_center/articles/20728574273428/attachments}
    //201
    @Test
    void demo2142(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/articles/20728574273428/attachments")
                .newBuilder();
        File file = new File("/Users/qingyuanyang/Desktop/test1.png");
        MultipartBody multipartBody = new MultipartBody.Builder()
                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("text/plain")))
                .build();
        //RequestBody body = RequestBody.create(MediaType.parse("application/json"),
        //        "{\n" +
        //                "  \"article_attachment\": {\n" +
        //                "    \"article_id\": 20728574273428,\n" +
        //                "    \"content_type\": \"application/json\",\n" +
        //                "    \"content_url\": \"https://jinmutraining.zendesk.com/attachments/token/7pdRqbEX0jL9f9u7QfLAgD8WH/?name=1206.txt\",\n" +
        //                "    \"file_name\": \"1206.txt\",\n" +
        //                "    \"id\": 22104774538132\n" +
        //                "  }\n" +
        //                "}");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("POST", multipartBody)
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



    //{"count":3,"next_page":null,"page":1,"page_count":1,"per_page":30,"previous_page":null,"articles":[{"id":21976223789332,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles/21976223789332.json","html_url":"https://jinmutraining.zendesk.com/hc/en-us/articles/21976223789332-Taking-photos-in-low-light","author_id":10332850159252,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":21975969356948,"created_at":"2023-12-18T06:01:10Z","updated_at":"2023-12-18T06:01:10Z","name":"Taking photos in low light","title":"Taking photos in low light","source_locale":"en-us","locale":"en-us","outdated":false,"outdated_locales":[],"edited_at":"2023-12-18T06:01:10Z","user_segment_id":null,"permission_group_id":10361325375636,"content_tag_ids":[],"label_names":[],"body":"Use a tripod"},{"id":20728574273428,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles/20728574273428.json","html_url":"https://jinmutraining.zendesk.com/hc/en-us/articles/20728574273428--english-article","author_id":10332850159252,"comments_disabled":false,"draft":false,"promoted":false,"position":0,"vote_sum":0,"vote_count":0,"section_id":20728554380692,"created_at":"2023-11-08T05:09:45Z","updated_at":"2023-11-08T05:10:10Z","name":" english article","title":" english article","source_locale":"en-us","locale":"en-us","outdated":false,"outdated_locales":[],"edited_at":"2023-11-08T05:09:45Z","user_segment_id":null,"permission_group_id":10361325375636,"content_tag_ids":[],"label_names":[],"body":"\u003cp\u003ethis is an english article\u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e"},{"id":10361313057300,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles/10361313057300.json","html_url":"https://jinmutraining.zendesk.com/hc/en-us/articles/10361313057300-How-can-agent-use-their-knowledge-to-help-customers-","author_id":10332850159252,"comments_disabled":false,"draft":false,"promoted":true,"position":0,"vote_sum":0,"vote_count":0,"section_id":10361378844308,"created_at":"2022-10-26T06:53:36Z","updated_at":"2023-07-12T02:42:09Z","name":"How can agent use their knowledge to help customers?","title":"How can agent use their knowledge to help customers?","source_locale":"zh-cn","locale":"en-us","outdated":false,"outdated_locales":[],"edited_at":"2022-10-27T08:20:16Z","user_segment_id":null,"permission_group_id":10361325375636,"content_tag_ids":["01H51TY2KGBKS8ZDWGKMMFWB4G"],"label_names":["Thunder","文章推荐"],"body":"\u003cp\u003eYou can use our Knowledge Extraction application to leverage your team's collective knowledge.\u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e\n\u003cp\u003eWith this application, specialists can:\u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e\n\u003cp\u003eSearch Help Center without leaving the job\u003c/p\u003e\n\u003cp\u003eInsert a link to the relevant Help Center article in the work order comment\u003c/p\u003e\n\u003cp\u003eAdd embedded feedback to existing articles that need to be updated\u003c/p\u003e\n\u003cp\u003eCreate a new article using the predefined template while replying to the work order\u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e\n\u003cp\u003eThe specialists can share, mark or create knowledge without leaving the work order interface, so as to improve the self-service experience provided to other customers while helping customers.\u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e\n\u003cp\u003eTo get started, consult our knowledge extraction documentation.\u003c/p\u003e\n\u003cp\u003e \u003c/p\u003e\n\u003cp\u003eBefore the specialist starts to create new knowledge directly from the work order, you need to create a template for them to use. We have provided some template ideas below to help you get started. You can copy and paste any of the following template examples into a new article and add KCTemplate tags to the article.\u003c/p\u003e"}],"sort_by":"position","sort_order":"asc"}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles}
    //200
    @Test
    void list_comments(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/en-us/articles\n")
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



    //导入文章评论	向指定的文章添加评论
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/article_comments/#create-comment
    //{"comment":{"id":21976727122836,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles/21976223789332/comments/21976727122836.json","body":"Good info!","author_id":10332850159252,"source_id":21976223789332,"source_type":"Article","html_url":"https://jinmutraining.zendesk.com/hc/en-us/articles/21976223789332/comments/21976727122836","locale":"en-us","created_at":"2023-12-18T06:39:53Z","updated_at":"2023-12-18T06:39:53Z","vote_sum":0,"vote_count":0,"non_author_editor_id":null,"non_author_updated_at":null}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles/21976223789332/comments}
    //201
    @Test
    void demo2143(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/en-us/articles/21976223789332/comments")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"comment\": {\n" +
                        "    \"author_id\": 10332850159252,\n" +
                        "    \"body\": \"Good info!\",\n" +
                        "    \"id\": 35467,\n" +
                        "    \"locale\": \"en-us\"\n" +
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


    //导入文章标签	文章标签导入系统并可用
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/article_labels/#create-label
    //{"label":{"id":21976861996052,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/articles/labels/21976861996052.json?locale=en-us","name":"instructions","created_at":"2023-12-18T06:52:31Z","updated_at":"2023-12-18T06:52:31Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles/21976223789332/labels}
    //201
    @Test
    void demo2144(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/en-us/articles/21976223789332/labels")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"label\": {\"name\": \"instructions\"}}");

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



    //{"count":1,"page_count":1,"per_page":30,"page":1,"next_page":null,"previous_page":null,"permission_groups":[{"id":10361325375636,"name":"管理员","built_in":true,"publish":[],"created_at":"2022-10-26T06:53:35Z","updated_at":"2022-10-26T06:53:35Z","edit":[]}]}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/guide/permission_groups}
    //200
    @Test
    void list_permission_group(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/guide/permission_groups\n")
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
        };

    }

    //管理权限组导入	在系统自中添加管理权限
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/permission_groups/#create-permission-group
    //{"permission_group":{"id":26543026587033,"name":"管理員","built_in":false,"publish":[],"created_at":"2023-12-18T07:54:23Z","updated_at":"2023-12-18T07:54:23Z","edit":[]}}
    //Response{protocol=h2, code=201, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/guide/permission_groups}
    //201
    @Test
    void demo2151(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/guide/permission_groups")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"permission_group\":\n" +
                        "      {\n" +
                        "         \"name\":\"管理員\",\n" +
                        "         \"built_in\":true,\n" +
                        "         \"publish\":[],\n" +
                        "         \"edit\":[]\n" +
                        "         \n" +
                        "      }\n" +
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
        };
    }

    //{"translations":[{"id":21976223790484,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/articles/21976223789332/translations/en-us","html_url":"https://jinmutraining.zendesk.com/hc/en-us/articles/21976223789332","source_id":21976223789332,"source_type":"Article","locale":"en-us","title":"Taking photos in low light","body":"Use a tripod","outdated":false,"draft":false,"hidden":false,"created_at":"2023-12-18T06:01:10Z","updated_at":"2023-12-18T06:01:10Z","updated_by_id":10332850159252,"created_by_id":10332850159252}],"page":1,"previous_page":null,"next_page":null,"per_page":100,"page_count":1,"count":1}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/help_center/articles/21976223789332/translations}
    //200
    //List translations
    @Test
    void list_translation(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/categories/21975975922836/translations/missing\n")
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


    //文章翻译	文章对应翻译在系统中可用
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/translations/#create-translation
    //{"translation":{"id":21977957703444,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/articles/21976223789332/translations/ja-jp","html_url":"https://jinmutraining.zendesk.com/hc/ja-jp/articles/21976223789332","source_id":21976223789332,"source_type":"Article","locale":"ja-jp","title":"How to take pictures in low light","body":null,"outdated":false,"draft":false,"hidden":false,"created_at":"2023-12-18T08:18:58Z","updated_at":"2023-12-18T08:18:58Z","updated_by_id":10332850159252,"created_by_id":10332850159252}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2//help_center/articles/21976223789332/translations}
    //201
    @Test
    void demo21521(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2//help_center/articles/21976223789332/translations")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"translation\": {\n" +
                        "    \"id\": 634578,\n" +
                        "    \"locale\": \"ja-jp\",\n" +
                        "    \"source_id\": 348756,\n" +
                        "    \"source_type\": \"Article\",\n" +
                        "    \"title\": \"How to take pictures in low light\"\n" +
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

    //translate section
    //{"translation":{"id":21978289657364,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/sections/20728554380692/translations/ja-jp","html_url":"https://jinmutraining.zendesk.com/hc/ja-jp/sections/20728554380692","source_id":20728554380692,"source_type":"Section","locale":"ja-jp","title":"How to take pictures in low light","body":null,"outdated":false,"draft":false,"hidden":false,"created_at":"2023-12-18T08:40:36Z","updated_at":"2023-12-18T08:40:36Z","updated_by_id":10332850159252,"created_by_id":10332850159252}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2//help_center/sections/20728554380692/translations}
    //201
    @Test
    void demo21522(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2//help_center/sections/20728554380692/translations")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"translation\": {\n" +
                        "    \"id\": 634578,\n" +
                        "    \"locale\": \"ja-jp\",\n" +
                        "    \"source_id\": 348756,\n" +
                        "    \"source_type\": \"Article\",\n" +
                        "    \"title\": \"How to take pictures in low light\"\n" +
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

    //translate categories
    //{"translation":{"id":21978294726164,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/categories/21975975922836/translations/zh-cn","html_url":"https://jinmutraining.zendesk.com/hc/zh-cn/categories/21975975922836","source_id":21975975922836,"source_type":"Category","locale":"zh-cn","title":"How to take pictures in low light","body":null,"outdated":false,"draft":false,"hidden":false,"created_at":"2023-12-18T08:42:58Z","updated_at":"2023-12-18T08:42:58Z","updated_by_id":10332850159252,"created_by_id":10332850159252}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2//help_center/categories/21975975922836/translations}
    //201
    @Test
    void demo21523(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2//help_center/categories/21975975922836/translations")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"translation\": {\n" +
                        "    \"id\": 634578,\n" +
                        "    \"locale\": \"zh-cn\",\n" +
                        "    \"source_id\": 348756,\n" +
                        "    \"source_type\": \"Article\",\n" +
                        "    \"title\": \"How to take pictures in low light\"\n" +
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

    //话题导入	话题导入系统中并可用
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/topics/#create-topic
    //{"topic":{"id":21978328723348,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/topics/21978328723348.json","html_url":"https://jinmutraining.zendesk.com/hc/zh-cn/community/topics/21978328723348-%E6%B5%8B%E8%AF%95%E4%B8%BB%E9%A2%98","name":"测试主题","description":null,"position":0,"follower_count":0,"community_id":10361325410452,"created_at":"2023-12-18T08:47:37Z","updated_at":"2023-12-18T08:47:37Z","manageable_by":"staff","user_segment_id":null}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/community/topics}
    //201
    @Test
    void demo2211(){
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

    //{"topics":[{"id":21978328723348,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/topics/21978328723348.json","html_url":"https://jinmutraining.zendesk.com/hc/zh-cn/community/topics/21978328723348-%E6%B5%8B%E8%AF%95%E4%B8%BB%E9%A2%98","name":"测试主题","description":null,"position":0,"follower_count":0,"community_id":10361325410452,"created_at":"2023-12-18T08:47:37Z","updated_at":"2023-12-18T08:47:37Z","manageable_by":"staff","user_segment_id":null},{"id":21828551989140,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/topics/21828551989140.json","html_url":"https://jinmutraining.zendesk.com/hc/zh-cn/community/topics/21828551989140-%E6%B5%8B%E8%AF%95%E4%B8%BB%E9%A2%98","name":"测试主题","description":null,"position":0,"follower_count":0,"community_id":10361325410452,"created_at":"2023-12-13T08:46:23Z","updated_at":"2023-12-13T08:46:23Z","manageable_by":"staff","user_segment_id":null},{"id":21750909441300,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/topics/21750909441300.json","html_url":"https://jinmutraining.zendesk.com/hc/zh-cn/community/topics/21750909441300-%E6%B5%8B%E8%AF%95%E4%B8%BB%E9%A2%98","name":"测试主题","description":"这是一个测试主题","position":0,"follower_count":0,"community_id":10361325410452,"created_at":"2023-07-06T05:52:31Z","updated_at":"2023-12-11T03:12:58Z","manageable_by":"staff","user_segment_id":null},{"id":10361313074452,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/topics/10361313074452.json","html_url":"https://jinmutraining.zendesk.com/hc/zh-cn/community/topics/10361313074452-%E5%8A%9F%E8%83%BD%E8%AF%B7%E6%B1%82","name":"功能请求","description":null,"position":0,"follower_count":1,"community_id":10361325410452,"created_at":"2022-10-26T06:53:37Z","updated_at":"2022-10-26T06:53:37Z","manageable_by":"managers","user_segment_id":null},{"id":10361313068692,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/topics/10361313068692.json","html_url":"https://jinmutraining.zendesk.com/hc/zh-cn/community/topics/10361313068692-%E4%B8%80%E8%88%AC%E8%AE%A8%E8%AE%BA","name":"一般讨论","description":null,"position":0,"follower_count":1,"community_id":10361325410452,"created_at":"2022-10-26T06:53:36Z","updated_at":"2023-12-18T08:56:09Z","manageable_by":"managers","user_segment_id":null}],"page":1,"previous_page":null,"next_page":null,"per_page":100,"page_count":1,"count":5}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/community/topics}
    //200
    @Test
    void list_topic(){
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

    //帖子导入	帖子数据导入
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/posts/#create-post
    //两个都无权限
    //==========================
    //{"error":{"title":"Forbidden","message":"You do not have access to this page. Please contact the account owner of this help desk for further help."}}
    //Response{protocol=h2, code=403, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/community/posts}
    //403
    @Test
    void demo2221(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/community/posts")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"post\": {\"title\": \"Help!\", \"details\": \"My printer is on fire!\", \"topic_id\": 8696049675545}, \"notify_subscribers\": false}");

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


    //{"posts":[{"id":10361313071764,"title":"我应该添加什么主题到社区中？","details":"\u003cp\u003e这要看情况。如果您支持几个产品，您可以为每个产品添加一个主题。如果您有一个大型的产品，您可以为每项重要的功能领域或任务添加一个主题。如果您有不同类型的用户（例如终端用户和 API 开发者），您可以为每种类型的用户添加一个或多个主题。\u003c/p\u003e\u003cp\u003e“一般讨论”主题是给用户讨论不适用其它主题的问题的。您可以对此主题进行观测，看是否有越来越多的问题需要另外专门添加主题。\u003c/p\u003e\n\n\u003cp\u003e要创建您自己的主题，请参阅 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/205586568#topic_dqx_znw_3k\" target=\"_blank\" rel=\"nofollow noreferrer\"\u003eAdding community discussion topics（英语）\u003c/a\u003e。\u003c/p\u003e","author_id":10332850159252,"vote_sum":0,"vote_count":0,"comment_count":0,"follower_count":0,"topic_id":10361313068692,"html_url":"https://jinmutraining.zendesk.com/hc/zh-cn/community/posts/10361313071764-%E6%88%91%E5%BA%94%E8%AF%A5%E6%B7%BB%E5%8A%A0%E4%BB%80%E4%B9%88%E4%B8%BB%E9%A2%98%E5%88%B0%E7%A4%BE%E5%8C%BA%E4%B8%AD-","created_at":"2022-10-26T06:53:37Z","updated_at":"2022-10-26T06:53:37Z","url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/posts/10361313071764-%E6%88%91%E5%BA%94%E8%AF%A5%E6%B7%BB%E5%8A%A0%E4%BB%80%E4%B9%88%E4%B8%BB%E9%A2%98%E5%88%B0%E7%A4%BE%E5%8C%BA%E4%B8%AD-.json","featured":false,"pinned":false,"closed":false,"frozen":false,"status":"none","non_author_editor_id":null,"non_author_updated_at":null,"content_tag_ids":[]},{"id":10361325411860,"title":"什么是社区？","details":"\u003cp\u003e帮助中心社区包括帖子和评论，按主题进行组织。帖子可包括技巧、功能请求或问题。评论可包括观察、澄清、赞扬，或通常社区讨论中的任何其它回复。\u003cstrong\u003e注释\u003c/strong\u003e：不要把主题和文章混淆起来。在社区中，主题是最上层的帖子集合。\u003c/p\u003e\u003cp\u003e您可以使用搜索，或使用视图和筛选浏览主题和帖子。查阅 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/203664386#topic_dqx_znw_3k\" target=\"_blank\" rel=\"nofollow noreferrer\"\u003eGetting around the community（英语）\u003c/a\u003e。\u003c/p\u003e\u003cp\u003e我们创建了一些常用的主题作为占位符，以帮您开始。您可以删除这些主题，添加您自己的。如要了解如何操作，请查阅 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/205586568#topic_dqx_znw_3k\" target=\"_blank\" rel=\"nofollow noreferrer\"\u003eManaging community topics（英语）\u003c/a\u003e。\u003c/p\u003e\u003cp\u003e社区中的每个用户都有帮助中心个人资料（Guide Professional 和 Enterprise），这样社区成员们可以更好地互相了解。个人资料内含关于社区成员的相关信息，以及他们的活动和贡献。\u003c/p\u003e","author_id":10332850159252,"vote_sum":0,"vote_count":0,"comment_count":0,"follower_count":0,"topic_id":10361313068692,"html_url":"https://jinmutraining.zendesk.com/hc/zh-cn/community/posts/10361325411860-%E4%BB%80%E4%B9%88%E6%98%AF%E7%A4%BE%E5%8C%BA-","created_at":"2022-10-26T06:53:37Z","updated_at":"2022-10-26T06:53:37Z","url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/posts/10361325411860-%E4%BB%80%E4%B9%88%E6%98%AF%E7%A4%BE%E5%8C%BA-.json","featured":false,"pinned":false,"closed":false,"frozen":false,"status":"none","non_author_editor_id":null,"non_author_updated_at":null,"content_tag_ids":[]},{"id":10361325413908,"title":"如何使用社区？","details":"\u003cp\u003e您可以使用搜索查找答案。您也可以使用视图和筛选浏览主题和帖子。请查阅 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/203664386#topic_dqx_znw_3k\" target=\"_blank\" rel=\"nofollow noreferrer\"\u003eGetting around the community（英语）\u003c/a\u003e。\u003c/p\u003e","author_id":10332850159252,"vote_sum":0,"vote_count":0,"comment_count":0,"follower_count":0,"topic_id":10361313068692,"html_url":"https://jinmutraining.zendesk.com/hc/zh-cn/community/posts/10361325413908-%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8%E7%A4%BE%E5%8C%BA-","created_at":"2022-10-26T06:53:37Z","updated_at":"2022-10-26T06:53:37Z","url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/posts/10361325413908-%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8%E7%A4%BE%E5%8C%BA-.json","featured":false,"pinned":false,"closed":false,"frozen":false,"status":"none","non_author_editor_id":null,"non_author_updated_at":null,"content_tag_ids":[]},{"id":10361325415700,"title":"推荐帖子","details":"社区管理者和维护者可通过在您的帮助中心社区的主题中推荐某些帖子，来提高其受欢迎程度。\u003ca href=\"https://support.zendesk.com/hc/en-us/articles/203664406#topic_fm4_s1x_ds\" rel=\"nofollow noreferrer\"\u003e了解更多\u003c/a\u003e","author_id":10332850159252,"vote_sum":0,"vote_count":0,"comment_count":0,"follower_count":0,"topic_id":10361313068692,"html_url":"https://jinmutraining.zendesk.com/hc/zh-cn/community/posts/10361325415700-%E6%8E%A8%E8%8D%90%E5%B8%96%E5%AD%90","created_at":"2022-10-26T06:53:37Z","updated_at":"2022-10-26T06:53:37Z","url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/posts/10361325415700-%E6%8E%A8%E8%8D%90%E5%B8%96%E5%AD%90.json","featured":true,"pinned":false,"closed":false,"frozen":false,"status":"none","non_author_editor_id":null,"non_author_updated_at":null,"content_tag_ids":[]},{"id":10361325417492,"title":"我希望用户能够提交功能请求","details":"\u003cp\u003e您可在社区中添加一个像这样的主题。终端用户可以添加功能请求，描述其用例。其他用户可以对请求进行评论及投票。产品经理可以查阅功能请求并提供反馈。\u003c/p\u003e","author_id":10332850159252,"vote_sum":0,"vote_count":0,"comment_count":0,"follower_count":0,"topic_id":10361313074452,"html_url":"https://jinmutraining.zendesk.com/hc/zh-cn/community/posts/10361325417492-%E6%88%91%E5%B8%8C%E6%9C%9B%E7%94%A8%E6%88%B7%E8%83%BD%E5%A4%9F%E6%8F%90%E4%BA%A4%E5%8A%9F%E8%83%BD%E8%AF%B7%E6%B1%82","created_at":"2022-10-26T06:53:37Z","updated_at":"2022-10-26T06:53:37Z","url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/posts/10361325417492-%E6%88%91%E5%B8%8C%E6%9C%9B%E7%94%A8%E6%88%B7%E8%83%BD%E5%A4%9F%E6%8F%90%E4%BA%A4%E5%8A%9F%E8%83%BD%E8%AF%B7%E6%B1%82.json","featured":false,"pinned":false,"closed":false,"frozen":false,"status":"none","non_author_editor_id":null,"non_author_updated_at":null,"content_tag_ids":[]}],"page":1,"previous_page":null,"next_page":null,"per_page":30,"page_count":1,"count":5}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/community/posts}
    //200
    //{"posts":[{"id":20530442472857,"title":"测试推荐","details":"\u003cp\u003e如果这篇文章被推荐，那么测试成功\u003c/p\u003e","author_id":11942258898585,"vote_sum":-1,"vote_count":1,"comment_count":0,"follower_count":1,"topic_id":20368235223577,"html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/community/posts/20530442472857-%E6%B5%8B%E8%AF%95%E6%8E%A8%E8%8D%90","created_at":"2023-07-11T03:20:06Z","updated_at":"2023-07-11T03:20:26Z","url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/community/posts/20530442472857-%E6%B5%8B%E8%AF%95%E6%8E%A8%E8%8D%90.json","featured":false,"pinned":false,"closed":false,"frozen":false,"status":"none","non_author_editor_id":null,"non_author_updated_at":null,"content_tag_ids":["01H51CKM0NMAF793EV2EZWDAGX"]},{"id":8696049695769,"title":"使貼文成為精選","details":"社區管理者與維護者藉由在客服中心社區主題中使某些貼文成為精選，從而使其越來越受到歡迎。\u003ca href=\"https://support.zendesk.com/hc/en-us/articles/203664406#topic_fm4_s1x_ds\" rel=\"nofollow noreferrer\"\u003e瞭解更多\u003c/a\u003e","author_id":8426999947417,"vote_sum":0,"vote_count":0,"comment_count":0,"follower_count":0,"topic_id":8696049675545,"html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/community/posts/8696049695769-%E4%BD%BF%E8%B2%BC%E6%96%87%E6%88%90%E7%82%BA%E7%B2%BE%E9%81%B8","created_at":"2022-07-21T03:01:12Z","updated_at":"2022-07-21T03:01:12Z","url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/community/posts/8696049695769-%E4%BD%BF%E8%B2%BC%E6%96%87%E6%88%90%E7%82%BA%E7%B2%BE%E9%81%B8.json","featured":true,"pinned":false,"closed":false,"frozen":false,"status":"none","non_author_editor_id":null,"non_author_updated_at":null,"content_tag_ids":[]},{"id":8696106659225,"title":"什麼是社區？","details":"\u003cp\u003e客服中心社區包含貼文和評論，用主題來組織。貼文可包括密技、功能請求或問題。評論可包括觀測、釐清、稱讚或任何其他回覆，做為通常社區討論的一部分。\u003cstrong\u003e備註\u003c/strong\u003e：請勿混淆主題和文章。在社區中，主題是貼文的最上層容器。\u003c/p\u003e\u003cp\u003e您可使用搜尋，也可使用視圖和篩選瀏覽主題和貼文。請查閱 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/203664386#topic_dqx_znw_3k\" target=\"_blank\" rel=\"nofollow noreferrer\"\u003e￼Getting around the community￼（英文）\u003c/a\u003e。\u003c/p\u003e\u003cp\u003e我們已建立一些常用主題的預留位置，以幫您開始。您可刪除這些主題，新增您自己的。要瞭解方法，請查閱 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/205586568#topic_dqx_znw_3k\" target=\"_blank\" rel=\"nofollow noreferrer\"\u003e￼Managing community topics（英文）\u003c/a\u003e。\u003c/p\u003e\u003cp\u003e社區中的各個使用者均有客服中心個人檔案（Guide Professional 與 Enterprise），使社區成員可更好地相互認識對方。個人檔案中包括有關社區成員的資訊，及其活動和貢獻。\u003c/p\u003e","author_id":8426999947417,"vote_sum":0,"vote_count":0,"comment_count":1,"follower_count":1,"topic_id":8696049675545,"html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/community/posts/8696106659225-%E4%BB%80%E9%BA%BC%E6%98%AF%E7%A4%BE%E5%8D%80-","created_at":"2022-07-21T03:01:12Z","updated_at":"2023-07-06T06:08:00Z","url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/community/posts/8696106659225-%E4%BB%80%E9%BA%BC%E6%98%AF%E7%A4%BE%E5%8D%80-.json","featured":false,"pinned":false,"closed":false,"frozen":false,"status":"none","non_author_editor_id":null,"non_author_updated_at":null,"content_tag_ids":[]},{"id":8696106692505,"title":"應新增什麼主題到社區？","details":"\u003cp\u003e視情況而定。若您支援幾個產品，您可為每個產品新增一個主題。若您有一個大型產品，您可為每項重要功能領域或工作新增一個主題。若您有不同類型的使用者（例如，終端使用者和 API 開發者），您可為每種類型的使用者新增一個或多個主題。\u003c/p\u003e\u003cp\u003e「一般討論區」主題給使用者討論不適用其他主題的問題。您可監測此主題新出現的問題，以另辟其專門的主題。\u003c/p\u003e\n\n\u003cp\u003e若要建立您自己的主題，請查閱 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/205586568#topic_dqx_znw_3k\" target=\"_blank\" rel=\"nofollow noreferrer\"\u003eAdding community discussion topics（英文）\u003c/a\u003e。\u003c/p\u003e","author_id":8426999947417,"vote_sum":0,"vote_count":0,"comment_count":0,"follower_count":0,"topic_id":8696049675545,"html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/community/posts/8696106692505-%E6%87%89%E6%96%B0%E5%A2%9E%E4%BB%80%E9%BA%BC%E4%B8%BB%E9%A1%8C%E5%88%B0%E7%A4%BE%E5%8D%80-","created_at":"2022-07-21T03:01:12Z","updated_at":"2022-07-21T03:01:12Z","url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/community/posts/8696106692505-%E6%87%89%E6%96%B0%E5%A2%9E%E4%BB%80%E9%BA%BC%E4%B8%BB%E9%A1%8C%E5%88%B0%E7%A4%BE%E5%8D%80-.json","featured":false,"pinned":false,"closed":false,"frozen":false,"status":"none","non_author_editor_id":null,"non_author_updated_at":null,"content_tag_ids":[]},{"id":8696106697113,"title":"怎樣使用社區？","details":"\u003cp\u003e您可使用搜尋來尋找答案。您也可使用視圖和篩選瀏覽主題和貼文。請查閱 \u003ca href=\"https://support.zendesk.com/hc/en-us/articles/203664386#topic_dqx_znw_3k\" target=\"_blank\" rel=\"nofollow noreferrer\"\u003eGetting around the community（英文）\u003c/a\u003e。\u003c/p\u003e","author_id":8426999947417,"vote_sum":0,"vote_count":0,"comment_count":0,"follower_count":0,"topic_id":8696049675545,"html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/community/posts/8696106697113-%E6%80%8E%E6%A8%A3%E4%BD%BF%E7%94%A8%E7%A4%BE%E5%8D%80-","created_at":"2022-07-21T03:01:12Z","updated_at":"2022-07-21T03:01:12Z","url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/community/posts/8696106697113-%E6%80%8E%E6%A8%A3%E4%BD%BF%E7%94%A8%E7%A4%BE%E5%8D%80-.json","featured":false,"pinned":false,"closed":false,"frozen":false,"status":"none","non_author_editor_id":null,"non_author_updated_at":null,"content_tag_ids":[]},{"id":8696106707225,"title":"使用者怎樣提交功能請求？","details":"\u003cp\u003e您可在社區中新增一個像這樣的主題。一般使用者可以新增功能請求，描述其使用案例。其他使用者可評論請求並投票。產品經理可查閱功能請求並提供意見。\u003c/p\u003e","author_id":8426999947417,"vote_sum":0,"vote_count":0,"comment_count":1,"follower_count":1,"topic_id":8696106704409,"html_url":"https://pdi-jinmuinfo.zendesk.com/hc/zh-hk/community/posts/8696106707225-%E4%BD%BF%E7%94%A8%E8%80%85%E6%80%8E%E6%A8%A3%E6%8F%90%E4%BA%A4%E5%8A%9F%E8%83%BD%E8%AB%8B%E6%B1%82-","created_at":"2022-07-21T03:01:12Z","updated_at":"2023-07-06T06:25:10Z","url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/community/posts/8696106707225-%E4%BD%BF%E7%94%A8%E8%80%85%E6%80%8E%E6%A8%A3%E6%8F%90%E4%BA%A4%E5%8A%9F%E8%83%BD%E8%AB%8B%E6%B1%82-.json","featured":false,"pinned":false,"closed":false,"frozen":false,"status":"none","non_author_editor_id":null,"non_author_updated_at":null,"content_tag_ids":[]}],"page":1,"previous_page":null,"next_page":null,"per_page":30,"page_count":1,"count":6}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/community/posts}
    //200
    //========
    @Test
    void list_post(){
        //需要拿到所有的community topic信息才能post
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/community/posts\n")
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
    //帖子评论导入	评论数据导入相关帖子
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/post_comments/#create-post-comment
    //{"comment":{"id":21978420850836,"body":"I love my new non-flammable printer!","author_id":10332850159252,"vote_sum":0,"vote_count":0,"official":false,"html_url":"https://jinmutraining.zendesk.com/hc/zh-cn/community/posts/10361313071764-%E6%88%91%E5%BA%94%E8%AF%A5%E6%B7%BB%E5%8A%A0%E4%BB%80%E4%B9%88%E4%B8%BB%E9%A2%98%E5%88%B0%E7%A4%BE%E5%8C%BA%E4%B8%AD-/comments/21978420850836","created_at":"2023-12-18T08:56:09Z","updated_at":"2023-12-18T08:56:09Z","url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/posts/10361313071764/comments/21978420850836.json","post_id":10361313071764,"non_author_editor_id":null,"non_author_updated_at":null}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/community/posts/10361313071764/comments}
    //201
    @Test
    void demo2222(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/community/posts/10361313071764/comments")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"comment\": {\"body\": \"I love my new non-flammable printer!\"}, \"notify_subscribers\": false}");

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


    //话题订阅导入	话题订阅数据导入系统并可用
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/content_subscriptions/#create-topic-subscription
    //{"subscription":{"id":21978631331732,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/topics/21978328723348/subscriptions/21978631331732.json","user_id":10332850159252,"content_id":21978328723348,"content_type":"Topic","updated_at":"2023-12-18T09:04:51Z","created_at":"2023-12-18T09:04:51Z","include_comments":true}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/community/topics/21978328723348/subscriptions}
    //201
    @Test
    void demo2231(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/community/topics/21978328723348/subscriptions")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"subscription\": {\"include_comments\": true}}");

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

    //帖子订阅导入	帖子订阅数据导入系统并可用
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/content_subscriptions/#create-post-subscription
    //{"subscription":{"id":20530410507417,"url":"https://pdi-jinmuinfo.zendesk.com/api/v2/help_center/community/posts/20530442472857/subscriptions/20530410507417.json","user_id":11942258898585,"content_id":20530442472857,"content_type":"CommunityPost","updated_at":"2023-07-11T03:20:06Z","created_at":"2023-07-11T03:20:06Z"}}
    //Response{protocol=h2, code=201, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/community/posts/20530442472857/subscriptions}
    //201
    //{"subscription":{"id":21978420853780,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/community/posts/10361313071764/subscriptions/21978420853780.json","user_id":10332850159252,"content_id":10361313071764,"content_type":"CommunityPost","updated_at":"2023-12-18T08:56:09Z","created_at":"2023-12-18T08:56:09Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/community/posts/10361313071764/subscriptions}
    //201
    @Test
    void demo2232(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/community/posts/10361313071764/subscriptions")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"subscription\": {\"include_comments\": true}}");

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
    //文章订阅导入	文章订阅数据导入系统并可用
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/content_subscriptions/#create-article-subscription
    //{"subscription":{"content_id":20728574273428,"created_at":"2023-11-08T05:09:45Z","id":20728611394580,"locale":"en-us","updated_at":"2023-11-08T05:09:45Z","url":"https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles/20728574273428/subscriptions/20728611394580.json","user_id":10332850159252}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles/20728574273428/subscriptions}
    //201
    @Test
    void demo2233(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/en-us/articles/20728574273428/subscriptions")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"subscription\": {\"source_locale\": \"en-us\"}}");

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

    //社区用户划分导入	社区用户划分数据导入系统并生效
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/user_segments/#create-user-segment
    //{"user_segment":{"id":21978833938068,"user_type":"staff","group_ids":[10332850315540],"organization_ids":[],"tags":["vip"],"or_tags":[],"created_at":"2023-12-18T09:19:52Z","updated_at":"2023-12-18T09:19:52Z","built_in":false,"added_user_ids":[],"name":"VIP agents"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/help_center/user_segments}
    //201
    @Test
    void demo2241(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/user_segments")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{ \n" +
                        "    \"user_segment\": { \n" +
                        "      \"name\": \"VIP agents\", \n" +
                        "      \"user_type\": \"staff\", \n" +
                        "      \"group_ids\": [10332850315540], \n" +
                        "      \"tags\": [\"vip\"] \n" +
                        "    }\n" +
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

    //社区用户点赞/踩导入	社区用户点赞/踩数据导入并对应相应内容
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/votes/#create-vote
    //{"vote":{"id":21978884190996,"url":"https://jinmutraining.zendesk.com/api/v2/help_center/votes/21978884190996.json?locale=en-us","user_id":10332850159252,"value":-1,"item_id":20728574273428,"item_type":"Article","created_at":"2023-12-18T09:23:47Z","updated_at":"2023-12-18T09:25:21Z"}}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/help_center/en-us/articles/20728574273428/down}
    //200
    //仅仅记录了状态，没有统计数量
    @Test
    void demo2242(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/en-us/articles/20728574273428/down")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"vote\": {\n" +
                        "    \"id\": 37486578,\n" +
                        "    \"value\": 1\n" +
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


    //{"badges":[{"id":"01GG9NP1A7Y6F3X8VNG8MMVCMT","badge_category_id":"01GG9NNPA1NSZTSMA8186SG183","name":"对话发起者","description":"擅于在任何情况下保持对话持续开展。","icon_url":"https://jinmutraining.zendesk.com/hc/badge_icons/01HF6MFKJT42AF0JD6RP4E5GS3","created_at":"2022-10-26T08:14:03.000Z","updated_at":"2022-10-26T08:14:03.000Z"},{"id":"01GG9NP1C13W11KJYEX89W1TS5","badge_category_id":"01GG9NNPA1NSZTSMA8186SG183","name":"新手（铜）","description":"社区的最新成员。","icon_url":"https://jinmutraining.zendesk.com/hc/badge_icons/01HF6ND0GNPENYCBC8APMY6G8X","created_at":"2022-10-26T08:14:03.000Z","updated_at":"2022-10-26T08:14:03.000Z"},{"id":"01GG9NP1D1W69DGJV2PRSEJB65","badge_category_id":"01GG9NNPA1NSZTSMA8186SG183","name":"好人缘","description":"因其协作和包容的精神而在社区中广受尊敬。","icon_url":"https://jinmutraining.zendesk.com/hc/badge_icons/01HF6ND0VVPV8VDR1E3GGRF181","created_at":"2022-10-26T08:14:03.000Z","updated_at":"2022-10-26T08:14:03.000Z"},{"id":"01GG9NP1DFQ5PAN37RN9YSXPRS","badge_category_id":"01GG9NNPA1NSZTSMA8186SG183","name":"创意产生者","description":"持续提供良好的反馈，营造卓越的体验。","icon_url":"https://jinmutraining.zendesk.com/hc/badge_icons/01HF6MTTJGV27C1K5XTRQ113C1","created_at":"2022-10-26T08:14:03.000Z","updated_at":"2022-10-26T08:14:03.000Z"},{"id":"01GG9NP1FAKNWJ9YX9VP97ZBS9","badge_category_id":"01GG9NNPA1NSZTSMA8186SG183","name":"回答出色","description":"为社区带来正能量，从每次一个良好的回答开始。","icon_url":"https://jinmutraining.zendesk.com/hc/badge_icons/01HF6NCZCFH3YCSN45RWS4WHEN","created_at":"2022-10-26T08:14:03.000Z","updated_at":"2022-10-26T08:14:03.000Z"},{"id":"01GG9NP1G173XE2D4GDY4XAZZ7","badge_category_id":"01GG9NNPA1NSZTSMA8186SG183","name":"大师（白金）","description":"智慧、知识和理解力，融于一体。","icon_url":"https://jinmutraining.zendesk.com/hc/badge_icons/01HF6MFM2GE509MNKWBNEBG1AR","created_at":"2022-10-26T08:14:03.000Z","updated_at":"2022-10-26T08:14:03.000Z"},{"id":"01GG9NP1J1R2W9CPGCX6D8WBRW","badge_category_id":"01GG9NNPA1NSZTSMA8186SG183","name":"明日之星（银）","description":"随时准备加入对话中。","icon_url":"https://jinmutraining.zendesk.com/hc/badge_icons/01HF6MTV32QXDE2YXXY765ZRSG","created_at":"2022-10-26T08:14:03.000Z","updated_at":"2022-10-26T08:14:03.000Z"},{"id":"01GG9NP1KHT5AJY0WXFHXNCR0Q","badge_category_id":"01GG9NNPA1NSZTSMA8186SG183","name":"专家（金）","description":"为团队带来宝贵的见解和经验。","icon_url":"https://jinmutraining.zendesk.com/hc/badge_icons/01HF6MGHVB0ZD2ETDC75GKH2C6","created_at":"2022-10-26T08:14:03.000Z","updated_at":"2022-10-26T08:14:03.000Z"},{"id":"01GG9NP1P2NW72QK59KSNAF642","badge_category_id":"01GG9NNP98ME5PQASCA2GXJ92T","name":"社区团队","description":"维护社区正常运作的协助者。","icon_url":"https://jinmutraining.zendesk.com/hc/badge_icons/01HF6ND10A43GBRWQDP7VQDJH5","created_at":"2022-10-26T08:14:03.000Z","updated_at":"2022-10-26T08:14:03.000Z"},{"id":"01GG9NP1SC6GTSF79338HVB9ZH","badge_category_id":"01GG9NNP98ME5PQASCA2GXJ92T","name":"社区管理者","description":"社区对话领袖。","icon_url":"https://jinmutraining.zendesk.com/hc/badge_icons/01HF6ND6BRVVRK4KF96ZE9V07A","created_at":"2022-10-26T08:14:04.000Z","updated_at":"2022-10-26T08:14:04.000Z"},{"id":"01GG9NP1SMVX66YJHMVEX4QPHF","badge_category_id":"01GG9NNP98ME5PQASCA2GXJ92T","name":"社区维护者","description":"正能量、活跃、有益的对话掌控者。","icon_url":"https://jinmutraining.zendesk.com/hc/badge_icons/01HF6NCSPQE2DH46PGWQA2HJW9","created_at":"2022-10-26T08:14:04.000Z","updated_at":"2022-10-26T08:14:04.000Z"}]}
    //Response{protocol=h2, code=200, message=, url=https://jinmutraining.zendesk.com/api/v2/gather/badges}
    //200
    @Test
    //
    void list_badges(){
        String sourceUrl = "https://jinmutraining.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/gather/badges")
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
    //徽章导入	徽章数据导入系统并可用
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/badges/#create-badge

    //{"badge":{"id":"01HHY4T6X733TCNTRMN4NSGNR8","badge_category_id":"01GG9NNPA1NSZTSMA8186SG183","name":"Community Superhero","description":"Saving the day in the community!","icon_url":null,"created_at":"2023-12-18T09:41:17.351Z","updated_at":"2023-12-18T09:41:17.351Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/gather/badges}
    //201
    @Test
    void demo2243(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/gather/badges")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"badge\": { \"badge_category_id\": \"01GG9NNPA1NSZTSMA8186SG183\", \"name\": \"Community Superhero\", \"description\": \"Saving the day in the community!\", \"icon_upload_id\": \"01EYV0KR9EA8VGFMFDMEKPT15C\"}}");

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

    //徽章分配导入	徽章分配规则导入系统并生效
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/badge_assignments/#create-badge-assignment
    //{"badge_assignment":{"id":"01HHY52FGYAVPP288GYFFF84QQ","badge_id":"01GG9NP1A7Y6F3X8VNG8MMVCMT","user_id":"10332801830548","created_at":"2023-12-18T09:45:48.318Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/gather/badge_assignments}
    //201
    @Test
    void demo2244(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/gather/badge_assignments")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"badge_assignment\": {\"badge_id\": \"01GG9NP1A7Y6F3X8VNG8MMVCMT\", \"user_id\": \"10332801830548\"}}");

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



    //徽章类别导入	徽章类别规则导入系统并生效
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/badge_categories/#create-badge-category
    //{"badge_category":{"id":"01HHY57EDTPZR30VA62DC7DZQX","brand_id":"21787015222292","name":"Certifications","slug":"certs","created_at":"2023-12-18T09:48:31.034Z","updated_at":"2023-12-18T09:48:31.034Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/gather/badge_categories}
    //201
    @Test
    void demo2245(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/gather/badge_categories")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\"badge_category\": {\"brand_id\": \"021787015222292\", \"name\": \"Certifications\", \"slug\": \"certs\" }}");

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


    //内容标签导入	内容标签导入系统并可用
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/content_tags/#create-content-tag
    //{"content_tag":{"id":"01HHY59V6H0G2XGSHWJNR00FZ6","name":"feature request","created_at":"2023-12-18T09:49:49.649Z","updated_at":"2023-12-18T09:49:49.649Z"}}
    //Response{protocol=h2, code=201, message=, url=https://jinmutraining.zendesk.com/api/v2/guide/content_tags}
    //201
    @Test
    void demo2246(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/guide/content_tags")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"content_tag\": {\n" +
                        "    \"name\": \"feature request\"\n" +
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

    //外部内容记录导入	导入内容计入，为请求指定type_id和source_id
    //https://developer.zendesk.com/api-reference/help_center/federated-search/external_content_records/#create-external-content-record
    @Test
    void demo2311(){
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


    //外部内容来源导入	导入内容来源并对应相应外部导入内容
    //https://developer.zendesk.com/api-reference/help_center/federated-search/external_content_sources/#create-external-content-source
    //{"source":{"id":"01HHY5DQTY4AMZ23K7NTBPEKM6","name":"My Library1","created_at":"2023-12-18T09:51:57Z","updated_at":"2023-12-18T09:51:57Z"}}
    //Response{protocol=h2, code=201, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/guide/external_content/sources?page=}
    //201
    @Test
    void demo2321(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/guide/external_content/sources")
                .newBuilder()
                .addQueryParameter("page", "");
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                " {\"source\": {\"name\": \"My Library1\"}}");

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


    //{"types":[],"meta":{"has_more":false,"after_cursor":null,"before_cursor":null}}
    //Response{protocol=h2, code=200, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/guide/external_content/types}
    //200
    @Test
    void list_external_content(){
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/guide/external_content/types")
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

    //外部内容类型导入	导入内容来源类型并在系统中可用
    //https://developer.zendesk.com/api-reference/help_center/federated-search/external_content_types/#create-external-content-type
    //{"type":{"id":"01HHY6HW2MR5ZH8GA53BPSNSVB","name":"Book1","created_at":"2023-12-18T10:11:41Z","updated_at":"2023-12-18T10:11:41Z"}}
    //Response{protocol=h2, code=201, message=, url=https://pdi-jinmuinfo.zendesk.com/api/v2/guide/external_content/types}
    //201
    @Test
    void demo2331(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://pdi-jinmuinfo.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/guide/external_content/types")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "   \"type\": {\n" +
                        "     \"name\": \"Book1\"\n" +
                        "   }\n" +
                        " }"
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



}
