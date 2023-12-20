package com.whaleal.zendesk;

import com.whaleal.zendesk.service.BaseExportService;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;


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
    //不会
    @Test
    void demo2111(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/guide/theming/jobs/themes/imports")
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
    @Test
    void list_articles(){
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


    //导入文章附件	文章附件导入对应文章
    //https://developer.zendesk.com/api-reference/help_center/help-center-api/article_attachments/#create-unassociated-attachment
    //不会导入附件
    @Test
    void demo2142(){
        OkHttpClient client = new OkHttpClient();
        String sourceUrl = "https://jinmutraining.zendesk.com";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(sourceUrl+"/api/v2/help_center/articles/attachments")
                .newBuilder();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "  \"article_id\": 23,\n" +
                        "  \"content_type\": \"application/pdf\",\n" +
                        "  \"content_url\": \"https://jinmutraining.zendesk.com/hc/article_attachments/200109629/party_invitation.pdf\",\n" +
                        "  \"created_at\": \"2012-04-04T09:14:57Z\",\n" +
                        "  \"file_name\": \"party_invitation.pdf\",\n" +
                        "  \"id\": 1428,\n" +
                        "  \"inline\": false,\n" +
                        "  \"size\": 58298\n" +
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
                "{\"post\": {\"title\": \"Help!\", \"details\": \"My printer is on fire!\", \"topic_id\": 10046}, \"notify_subscribers\": false}");

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
