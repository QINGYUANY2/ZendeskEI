package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.TaskInfo;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportUserService;
import com.whaleal.zendesk.util.StringSub;
import com.whaleal.zendesk.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

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
        TaskInfo exportUserInfo = saveTaskInfo("exportUserInfo");
        Long useTime = doExport("/api/v2/users", "users", ExportEnum.USER.getValue() + "_info");
        exportUserInfo.setEndTime(TimeUtil.getTime());
        exportUserInfo.setUseTime(useTime);
        exportUserInfo.setStatus(2);
        mongoTemplate.save(exportUserInfo);
    }

    @Override
    public void importUserInfo() {
        TaskInfo saveTask = saveTaskInfo("importUserInfo");
        long startTime = System.currentTimeMillis();
        Criteria criteria = new Criteria();
        criteria.and("domain").is(StringSub.getDomain(this.sourceDomain));
//        criteria.and("_id").is("6539dee3245bba4880f86da4");
        List<Document> documentList = mongoTemplate.find(new Query(criteria), Document.class, ExportEnum.USER.getValue() + "_info");
        JSONObject request = null;
        for (Document users : documentList) {
            JSONObject requestParam = new JSONObject();
            Document param = new Document(users);
            // todo 版本不一致  例专业版与企业版 有custom_role与没有时 带custom_role_id 参数会报错
            if (param.get("custom_role_id") != null) {
                param.remove("custom_role_id");
            }
            try {
                if (users.get("organization_id") != null) {
                    Document orgDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(users.get("organization_id"))), Document.class, ExportEnum.ORGANIZATIONS.getValue() + "_info");
                    param.put("organization_id", orgDoc.get("newId"));
                }
                if (users.get("default_group_id") != null) {
                    Document groupDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(users.get("default_group_id"))), Document.class, ExportEnum.GROUP.getValue() + "_info");
                    param.put("default_group_id", groupDoc.get("newId"));
                }
                requestParam.put("user", param);
                request = this.doPost("/api/v2/users", requestParam);
                users.put("newId", request.getJSONObject("user").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importUserInfo执行完毕,请求参数：{},执行结果{}", requestParam, request);
            mongoTemplate.save(users, "user_info");
            saveImportInfo("importUserInfo", request);
        }
        log.info("导入user_info成功，一共导出{}条记录",documentList.size());
        saveTask.setEndTime(TimeUtil.getTime());
        saveTask.setUseTime(System.currentTimeMillis() - startTime);
        saveTask.setStatus(2);
        mongoTemplate.save(saveTask);
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
        TaskInfo exportUserInfo = saveTaskInfo("exportUserField");
        Long useTime = doExport("/api/v2/user_fields", "user_fields", ExportEnum.USER.getValue() + "_field");
        exportUserInfo.setEndTime(TimeUtil.getTime());
        exportUserInfo.setUseTime(useTime);
        exportUserInfo.setStatus(2);
        mongoTemplate.save(exportUserInfo);
    }

    @Override
    public void importUserField() {
        // todo  后期添加分页 以防过大

        TaskInfo saveTask = saveTaskInfo("importUserField");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "user_field");
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
            mongoTemplate.save(document, "user_field");
        }
        log.info("导入user_Field成功，一共导出{}条记录",list.size());
        saveTask.setEndTime(TimeUtil.getTime());
        saveTask.setUseTime(System.currentTimeMillis() - startTime);
        saveTask.setStatus(2);
        mongoTemplate.save(saveTask);
    }


}
