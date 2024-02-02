package com.whaleal.zendesk.service.helpCenter.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.source.tree.WhileLoopTree;
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

import java.util.ArrayList;
import java.util.HashMap;
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
        JSONObject nestedParam = new JSONObject();
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                //更新brand_id
                Long brandId = jsonObject.getLong("brand_id");
                Document brandDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(brandId)), Document.class, ExportEnum.BRAND.getValue() + "_info");
                jsonObject.put("brand_id", brandDoc.get("newId"));
                nestedParam.put("attributes", jsonObject);
                requestParam.put("job", nestedParam);
                request = this.doPost(" /api/v2/guide/theming/jobs/themes/imports", requestParam);
                document.put("newId", request.getJSONObject("job").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importThemeInfo 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            saveImportInfo("importThemeInfo", request);
            mongoTemplate.save(document, ExportEnum.THEMES.getValue() + "_info");
        }
        log.info("导入Theme_Info成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteArticleCategory(){
        ModuleRecord moduleRecord = beginModuleRecord("deleteArticleCategory");
        log.info("开始执行删除 article_category 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/help_center/categories", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("categories");
        List<String> categoryIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            categoryIds.add(temps.getLong("id").toString());
        }
        try{
            for (String categoryId : categoryIds) {
                doDelete("/api/v2/help_center/categories/",categoryId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 article_category 成功，一共删除{}条记录\n", categoryIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportArticleCategory() {
        ModuleRecord moduleRecord = beginModuleRecord("exportArticleCategory");
        Long useTime = doExport("/api/v2/help_center/categories", "categories", ExportEnum.ARTICLE.getValue() + "_category");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importArticleCategory() {
        ModuleRecord moduleRecord = beginModuleRecord("importArticleCategory");
        long startTime = System.currentTimeMillis();
        JSONObject requestParam = new JSONObject();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ARTICLE.getValue() + "_category");
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("category", jsonObject);
                request = this.doPost("/api/v2/help_center/categories", requestParam);
                document.put("newId", request.getJSONObject("category").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importArticleCategory 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            saveImportInfo("importArticleCategory", request);
            mongoTemplate.save(document, ExportEnum.ARTICLE.getValue() + "_category");
        }
        log.info("导入ArticleCategory成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportArticleInfo() {
        //todo /api/v2/help_center{/locale}/articles local需要根据所需填写
        ModuleRecord moduleRecord = beginModuleRecord("exportArticleInfo");
        Long useTime = doExport("/api/v2/help_center/articles", "articles", ExportEnum.ARTICLE.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }


    //导出文章失败
    @Override
    public void importArticleInfo() {
        ModuleRecord moduleRecord = beginModuleRecord("importArticleInfo");
        long startTime = System.currentTimeMillis();
        JSONObject requestParam = new JSONObject();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ARTICLE.getValue() + "_info");
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("job", jsonObject);
                request = this.doPost("/api/v2/help_center/articles", requestParam);
                document.put("newId", request.getJSONObject("job").get("id"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importArticle 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            saveImportInfo("importArticle", request);
            mongoTemplate.save(document, ExportEnum.ARTICLE.getValue() + "_info");
        }
        log.info("导入Article成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void exportArticleSection(){
        ModuleRecord moduleRecord = beginModuleRecord("exportArticleSection");
        Long useTime = doExport("/api/v2/help_center/sections", "sections", ExportEnum.ARTICLE.getValue() + "_section");
        endModuleRecord(moduleRecord, useTime);

    }

    @Override
    public void importArticleSection(){
        ModuleRecord moduleRecord = beginModuleRecord("importArticleSection");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ARTICLE.getValue() + "_section");
        JSONArray parentSectionArray = new JSONArray();
        recursionList(list);
        recursion(parentSectionArray);
        log.info("导入Article_section成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    private void recursionList(List<Document> list){
        JSONObject request = null;
        JSONObject requestParam = new JSONObject();
        List<Document> parentList = new ArrayList<>();
        JSONObject parentSection = new JSONObject();
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                //更新category_id并取出
                Long categoryId = jsonObject.getLong("category_id");
                Document categoryDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(categoryId)), Document.class, ExportEnum.ARTICLE.getValue() + "_category");
                jsonObject.put("categoryId", categoryDoc.get("newId"));
                String locale = jsonObject.get("locale").toString();
                //如果parent_section_id为空说明是正常组没有父母组，直接进行导入
                if (jsonObject.get("parent_section_id") == null) {
                    requestParam.put("section", jsonObject);
                    request = this.doPost("/api/v2/help_center/" + locale + "/categories/" + categoryDoc.get("newId").toString() + "/sections", requestParam);
                    document.put("newId", request.getJSONObject("section").get("id"));//如果parent值不为空(if已经排除为空的情况),那么如果找到的到新的id那么更新parent_section_id并进行post
                } else if(mongoTemplate.findOne(new Query(new Criteria("id").is(jsonObject.getLong("parent_section_id"))), Document.class, ExportEnum.ARTICLE.getValue() + "_category").get("newId")!=null){
                    Document parentSectionDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(jsonObject.getLong("parent_section_id"))), Document.class, ExportEnum.ARTICLE.getValue() + "_section");
                    parentSection.put("parent_section_id", parentSectionDoc.get("newId"));
                    requestParam.put("section", parentSection);
                    request = this.doPost("/api/v2/help_center/" + locale + "/categories/" + categoryDoc.get("newId").toString() + "/sections", requestParam);
                    document.put("newId", request.getJSONObject("section").get("id"));//剩下一种可能就是parent值不为空且找不到parent对应的新id(parent没被导入)
                }else{
                    parentList.add(document);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importArticleSection 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            saveImportInfo("importArticleSection", request);
            mongoTemplate.save(document, ExportEnum.ARTICLE.getValue() + "_section");
        }
        while(parentList.size()!=0) {
            recursionList(parentList);
        }
    }

    public void recursion(JSONArray parentSectionArray){
        JSONObject parentSection = new JSONObject();
        JSONObject request = null;
        JSONObject requestParam = new JSONObject();
        //写一个recursion
//        while(parentSectionArray.size()!=0) {
//            for (Object parentSectionObject : parentSectionArray) {
//                try {
//                    parentSection = (JSONObject) parentSectionObject;
//                    //更新
//                    Long parentSectionId = parentSection.getLong("parent_section_id");
//                    Document sectionDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(parentSectionId)), Document.class, ExportEnum.ARTICLE.getValue() + "_section");
//                    if (sectionDoc.get("newId") != null) {
//                        parentSection.put("parent_section_id", sectionDoc.get("newId"));
//                        String locale = parentSection.get("locale").toString();
//                        Long categoryId = parentSection.getLong("category_id");
//                        Document categoryDoc = mongoTemplate.findOne(new Query(new Criteria("id").is(categoryId)), Document.class, ExportEnum.ARTICLE.getValue() + "_category");
//                        requestParam.put("section", parentSection);
//                        request = this.doPost("/api/v2/help_center/" + locale + "/categories/" + categoryDoc.get("newId").toString() + "/sections", requestParam);
//                        document.put("newId", request.getJSONObject("section").get("id"));
//                    } else {
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
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
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.PERMISSION_GROUPS.getValue() + "_info");
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
