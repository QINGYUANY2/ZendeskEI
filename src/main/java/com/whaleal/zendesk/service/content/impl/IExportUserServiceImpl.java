package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportUserService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
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
        JSONObject request = this.doGet("/api/v2/users", new HashMap<>());
        JSONArray users = request.getJSONArray("users");
        List<JSONObject> list = users.toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("domain", StringSub.getDomain(this.sourceDomain));
            jsonObject.put("status",0);
        }
        mongoTemplate.insert(list, "user_info");
        log.info("导出用户信息成功，一共导出{}条记录",list.size());
    }

    @Override
    public void importUserInfo() {
        List<Document> documentList = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "user_info");
        JSONObject request= null;
        for (Document users : documentList ) {
            // todo 版本不一致  例专业版与企业版 有custom_role与没有时 带custom_role_id 参数会报错
              users.remove("custom_role_id");
            try {
                if (users.get("organization_id") != null){
                    Document orgDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(users.get("organization_id"))), Document.class, "org_info");
                    users.put("organization_id",orgDoc.get("newId"));
                }
                if (users.get("default_group_id") != null){
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(users.get("default_group_id"))), Document.class, "group_info");
                    users.put("default_group_id",groupDoc.get("newId"));
                }
                JSONObject jsonObject = JSONObject.parseObject(users.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("user", jsonObject);
                request = this.doPost("/api/v2/users",requestParam);
                users.put("status",1);
                users.put("newId",request.getJSONObject("user").get("id"));
            }catch (Exception e){
                e.printStackTrace();
                users.put("status",2);
            }
            users.put("request",request);
            log.info("请求结果{}",request);
            users.remove("organization_id");
            users.remove("default_group_id");
            mongoTemplate.save(users,"user_info");
        }
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
        JSONObject request = this.doGet("/api/v2/user_fields", new HashMap<>());
        JSONArray array = request.getJSONArray("user_fields");
        List<JSONObject> list = array.toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("domain",StringSub.getDomain(this.sourceDomain));
            jsonObject.put("status",0);
        }
        mongoTemplate.insert(list, "user_field");
    }

    @Override
    public void importUserField() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "user_field");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("user_field", jsonObject);
                JSONObject request = this.doPost("/api/v2/user_fields", requestParam);
                log.info("请求结果{}", request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            mongoTemplate.save(document,"user_field");
        }
    }


}
