package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportUserService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author lyz
 * @desc
 * @create: 2023-10-11 14:25
 **/


@Slf4j
@Service
public class IExportUserServiceImpl extends BaseExportService implements IExportUserService {


    @Override
    public void exportUserInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportUserInfo");
        List<JSONObject> botUser = new ArrayList<>();
        Long useTime = doExport("/api/v2/users", "users", ExportEnum.USER.getValue() + "_info");
        //String botName = "机器人应答";
        //String botEmail = "jiqirenyingda@jiqirenyingda.com";
        JSONObject botResponse = new JSONObject();
        botResponse.put("name", "机器人应答");
        botResponse.put("email","jiqirenyingda@jiqirenyingda.com");
        botResponse.put("role", "end-user");
        botResponse.put("add_reason","author_id_-1");
        botResponse.put("domain", "pdi-jinmuinfo");
        botResponse.put("active", "true");
        botUser.add(botResponse);
        mongoTemplate.insert(botUser, ExportEnum.USER.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importUserInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importUserInfo");
        long startTime = System.currentTimeMillis();
        String filePath = "/Users/qingyuanyang/Desktop/Record/Agent_name.txt";
        try {
            FileWriter fileWriter = new FileWriter(filePath, false);

            // 使用BufferedWriter包装FileWriter，提高性能
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // 清空文件内容
            bufferedWriter.write("");

            // 关闭写入器
            bufferedWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        Criteria criteria = new Criteria();
        criteria.and("domain").is(StringSub.getDomain(this.sourceDomain));
//        criteria.and("_id").is("6539dee3245bba4880f86da4");

        List<Document> documentList = mongoTemplate.find(new Query(criteria), Document.class, ExportEnum.USER.getValue() + "_info");
        JSONObject request = null;
        //源端default user
        JSONObject source_default_user = doGet("/api/v2/users/me", new HashMap<>());
        JSONObject nested_source_user = source_default_user.getJSONObject("user");
        Document defaultSource = new Document(nested_source_user);
        //目标端default user
        JSONObject target_default_user = doGetTarget("/api/v2/users/me", new HashMap<>());
        JSONObject nested_target_user = target_default_user.getJSONObject("user");
        Document defaultTarget = new Document(nested_target_user);

        for (Document users : documentList) {
            JSONObject requestParam = new JSONObject();
            JSONObject requestDefaultParam = new JSONObject();
            JSONObject requestIdentityParam = new JSONObject();
            Document param = new Document(users);
            List<String> duplicateEmail = new ArrayList<>();
            if(param.get("email")!=null) {
                //duplicateEmail.add(param.get("email").toString());
                //if (duplicateEmail.contains(param.get("email").toString())) {
                //    continue;
                //}
            }
            // todo 版本不一致  例专业版与企业版 有custom_role与没有时 带custom_role_id 参数会报错
            if (param.get("custom_role_id") != null) {
                param.remove("custom_role_id");
            }

            BufferedWriter writer = null;
            try {

                System.out.println(param.get("id"));


                if (users.get("organization_id") != null) {
                    Document orgDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(users.get("organization_id"))), Document.class, ExportEnum.ORGANIZATIONS.getValue() + "_info");
                    param.put("organization_id", orgDoc.get("newId"));
                }
                if (users.get("default_group_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(users.get("default_group_id"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                    param.put("default_group_id", groupDoc.get("newId"));
                }

                //如果是agent转换为end-user并记录，手动改
                if (users.get("role").equals("agent")){
                    //IO output user.get("name")
                    param.put("role", "end-user");
                    users.put("user_to_end-user", "changed");
                    String userName = users.get("name").toString();


                    // 将用户名写入文件
                    //---------
                    writer = new BufferedWriter(new FileWriter(filePath,true));

                    writer.append("User Name: ").append(userName);
                    writer.newLine();
                    log.info("User name recorded to file successfully.");


                    }
//                nestedObject.put("role", "end-user");
//                //different
//                //nestedObject.put("role_type", "1");
//                param.put("user", nestedObject);
//                System.out.println("++++++++++++"+jsonObject);
//                System.out.println(param);
//                System.out.println(param.get("role"));
//                System.out.println(param.get(""));
//
//                response = doPost(targetDomain, url, param);
//                jsonObject = JSONObject.parseObject(response.body().string());
 //               }

                if(param.get("id") != null && param.get("id").equals(defaultSource.get("id"))){//确认是源端的id
                    requestDefaultParam.put("user", param);
                    request = this.doUpdate("/api/v2/users",requestDefaultParam, defaultTarget.get("id").toString());
                    //用目标端id返回identities
                    JSONObject target_user_identity = doGetTarget("/api/v2/users/"+request.getJSONObject("user").get("id")+"/identities", new HashMap<>());
                    // 获取 identities 数组
                    JSONArray identities = (JSONArray) target_user_identity.get("identities");
                    //更改identity的primary选项

                    for (Object identityObj : identities) {
                        JSONObject identity = (JSONObject) identityObj;
                        if(identity.get("value").equals(requestDefaultParam.getJSONObject("user").get("email")) && !identity.get("primary").equals("true")){
                            requestIdentityParam.put("primary", "true");
                            this.doUpdate("/api/v2/users/"+ request.getJSONObject("user").get("id") +"/identities",requestIdentityParam,identity.get("id").toString()+"/make_primary");
                        }
                        //requestIdentityParam =
                        //this.doUpdate("/api/v2/users/"+ request.getJSONObject("user").get("id") +"/identities/",requestIdentityParam,identity.get("id")+"/make_primary");
                    }
                    //
//                    Document defaultTarget = new Document(nested_target_user);
                    users.put("newId", request.getJSONObject("user").get("id"));


                }else{
                    requestParam.put("user", param);
                    request = this.doPost("/api/v2/users", requestParam);

                    users.put("newId", request.getJSONObject("user").get("id"));
                }


            } catch (IOException e) {
                log.info("Error writing to file: " + e.getMessage());
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if (null != writer){
                        writer.flush();
                        writer.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            mongoTemplate.save(users, "user_info");
            log.info("importUserInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importUserInfo", request);
        }

        log.info("导入 user_info 成功，一共导入{}条记录", documentList.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteUserInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteUserInfo");
        log.info("开始执行删除 user_info 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/users", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("users");
        List<String> usersIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            usersIds.add(temps.getLong("id").toString());
        }
        try{
            for (String usersId : usersIds) {
                doDelete("/api/v2/users/",usersId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 user_info 成功，一共删除{}条记录\n", usersIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    // 没有角色

    @Override
    public void exportRoleInfo() {
//        JSONObject request = this.doGet("/api/v2/roles", new HashMap<>());
//        request.put("domain",this.sourceDomain);
//        mongoTemplate.insert(request, "role_info");

    }

    @Override
    public void importRoleInfo() {

    }


    @Override
    public void exportUserField() {
        ModuleRecord moduleRecord = beginModuleRecord("exportUserField");
        Long useTime = doExport("/api/v2/user_fields", "user_fields", ExportEnum.USER.getValue() + "_field");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importUserField() {
        ModuleRecord moduleRecord = beginModuleRecord("importUserField");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.USER.getValue() + "_field");
        JSONObject request = null;
        for (Document document : list) {
            JSONObject requestParam = new JSONObject();
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("user_field", jsonObject);
                request = this.doPost("/api/v2/user_fields", requestParam);
                document.put("newId", request.getJSONObject("user_field").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importUserField 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            saveImportInfo("importUserField", request);
            mongoTemplate.save(document, ExportEnum.USER.getValue() + "_field");
        }
        log.info("导入user_Field成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteUserField() {
        ModuleRecord moduleRecord = beginModuleRecord("deleteUserField");
        log.info("开始执行删除 user_field 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/user_fields", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("user_fields");
        List<String> userFieldsIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            userFieldsIds.add(temps.getLong("id").toString());
        }
        try{
            for (String userFieldsId : userFieldsIds) {
                doDelete("/api/v2/user_fields/",userFieldsId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 user_field 成功，一共删除{}条记录\n", userFieldsIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


}
