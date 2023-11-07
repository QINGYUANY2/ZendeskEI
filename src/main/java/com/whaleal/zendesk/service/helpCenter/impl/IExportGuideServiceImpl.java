package com.whaleal.zendesk.service.helpCenter.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.ModuleRecord;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.helpCenter.IExportGuideService;
import com.whaleal.zendesk.util.StringSub;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IExportGuideServiceImpl extends BaseExportService implements IExportGuideService {
    @Override
    public void exportThemeInfo() {

        ModuleRecord moduleRecord = beginModuleRecord("exportThemeInfo");
        Long useTime = doExport("/api/v2/guide/theming/themes", "themes", ExportEnum.THEMES.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importThemeInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importThemeInfo");
        long startTime = System.currentTimeMillis();
        JSONObject requestParam = new JSONObject();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.THEMES.getValue() + "_info");
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("job", jsonObject);
                request = this.doPost("/api/v2/guide/theming/jobs/themes/imports", requestParam);
                document.put("newId", request.getJSONObject("").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importUserField 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            saveImportInfo("importUserField", request);
            mongoTemplate.save(document, ExportEnum.THEMES.getValue() + "_info");
        }
        log.info("导入Theme_Field成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportArticleInfo() {
        //todo /api/v2/help_center{/locale}/articles local需要根据所需填写
        ModuleRecord moduleRecord = beginModuleRecord("exportArticleInfo");
        Long useTime = doExport("/api/v2/help_center/en/articles", "articles", ExportEnum.ARTICLE.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importArticleInfo() {

    }

    @Override
    public void exportPermissionGroupInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("exportPermissionGroupInfo");
        Long useTime = doExport("/api/v2/guide/permission_groups.json", "permission_groups", ExportEnum.PERMISSION_GROUPS.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importPermissionGroupInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importPermissionGroupInfo");
        JSONObject request = null;
        JSONObject requestParam = new JSONObject();
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.PERMISSION_GROUPS.getValue() + "_group");
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("permission_group", jsonObject);
                request = this.doPost("/api/v2/guide/permission_groups.json", requestParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importPermissionGroupInfo 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            mongoTemplate.save(document, "permission_group");
            saveImportInfo("importPermissionGroupInfo", request);
        }
        log.info("导入 PermissionGroup_info 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }
}
