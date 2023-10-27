package com.whaleal.zendesk.service.helpCenter.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.TaskInfo;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.helpCenter.IExportGuideService;
import com.whaleal.zendesk.util.StringSub;
import com.whaleal.zendesk.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class IExportGuideServiceImpl extends BaseExportService implements IExportGuideService {
    @Override
    public void exportThemeInfo() {

        TaskInfo exportUserInfo = saveTaskInfo("exportThemeInfo");
        Long useTime = doExport("/api/v2/guide/theming/themes", "themes", ExportEnum.THEMES.getValue() + "_info");
        exportUserInfo.setEndTime(TimeUtil.getTime());
        exportUserInfo.setUseTime(useTime);
        exportUserInfo.setStatus(2);
        mongoTemplate.save(exportUserInfo);
    }

    @Override
    public void importThemeInfo() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.THEMES.getValue() + "_info");
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("job", jsonObject);
                request = this.doPost("/api/v2/guide/theming/jobs/themes/imports", requestParam);

                document.put("newId",request.getJSONObject("").get("id"));
            }catch (Exception e){
                e.printStackTrace();

            }
            mongoTemplate.save(document,ExportEnum.THEMES.getValue() + "_info");
        }
    }

    @Override
    public void exportArticleInfo() {
        //todo /api/v2/help_center{/locale}/articles local需要根据所需填写
        JSONObject request = this.doGet("/api/v2/help_center/en/articles",new HashMap<>());
        List<JSONObject> list = request.getJSONArray("articles").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain",StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,"article_info");
    }

    @Override
    public void importArticleInfo() {

    }

    @Override
    public void exportPermissionGroupInfo() {
        JSONObject request = this.doGet("/api/v2/guide/permission_groups.json",new HashMap<>());
        List<JSONObject> list = request.getJSONArray("permission_groups").toJavaList(JSONObject.class);
        for (JSONObject jsonObject : list) {
            jsonObject.put("status",0);
            jsonObject.put("domain",StringSub.getDomain(this.sourceDomain));
        }
        mongoTemplate.insert(list,"permission_group");
    }

    @Override
    public void importPermissionGroupInfo() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, "permission_group");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("permission_group", jsonObject);
                JSONObject request = this.doPost("/api/v2/guide/permission_groups.json", requestParam);
                log.info("请求结果{}", request);
                document.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            mongoTemplate.save(document,"permission_group");
        }
    }
}
