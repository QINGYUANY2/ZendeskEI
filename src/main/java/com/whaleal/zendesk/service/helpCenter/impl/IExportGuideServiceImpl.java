package com.whaleal.zendesk.service.helpCenter.impl;

import com.alibaba.fastjson.JSONArray;
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
//                Long useTime = doExport("/api/v2/help_center/articles/"+jsonObject.get("id").toString()+"/translations", "translations", ExportEnum.ARTICLE.getValue() + "_translation");


                //更新userSegment的Id
                Long userSegmentId = jsonObject.getLong("user_segment_id");
                if(userSegmentId!=null){
                    Document userSegmentDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(userSegmentId)), Document.class, ExportEnum.USER_SEGMENT.getValue() + "_info");
                    jsonObject.put("user_segment_id", userSegmentDoc.get("newId"));
                }else {
                    jsonObject.put("user_segment_id","null");
                }

                //更新contentTag的Id
                JSONArray contentTagIds = jsonObject.getJSONArray("content_tag_ids");
                JSONArray contentTagNewIds = new JSONArray();
                if(contentTagIds.size()!=0){
                    for (Object contentTagIdObject : contentTagIds) {
                        String contentTagId = (String) contentTagIdObject;
                        Document contentTagDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(contentTagId)), Document.class, ExportEnum.CONTENT_TAG.getValue() + "_info");
                        contentTagNewIds.add(contentTagDoc.get("newId"));
                    }
                    jsonObject.put("content_tag_ids", contentTagNewIds);
                }

                //更新section id
                Long sectionId = jsonObject.getLong("section_id");
                if(sectionId!=null){
                    Document sectionDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(sectionId)), Document.class, ExportEnum.ARTICLE.getValue() + "_section");
                    jsonObject.put("section_id", sectionDoc.get("newId"));
                }

                //更新permission group id
                Long permissionGroupId = jsonObject.getLong("permission_group_id");
                if(permissionGroupId!=null){
                    Document permissionGroupDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(permissionGroupId)), Document.class, ExportEnum.PERMISSION_GROUPS.getValue() + "_info");
                    jsonObject.put("permission_group_id", permissionGroupDoc.get("newId"));
                }

                //更新author id
                Long authorId = jsonObject.getLong("author_id");
                if(authorId!=null){
                    Document userDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(authorId)), Document.class, ExportEnum.USER.getValue() + "_info");
                    if(userDoc!=null) {
                        jsonObject.put("author_id", userDoc.get("newId"));
                    }
                }
                //因为版本问题，嵌套了2个的组都导入不了，会导致section id为空
                if(jsonObject.get("section_id")!=null) {
                    requestParam.put("article", jsonObject);
                    request = this.doPost("/api/v2/help_center/sections/"+jsonObject.get("section_id").toString()+"/articles", requestParam);
                    document.put("newId", request.getJSONObject("article").get("id"));
                }
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
    public void deleteArticleInfo(){
        ModuleRecord moduleRecord = beginModuleRecord("deleteArticleInfo");
        log.info("开始执行删除 Article_info 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/help_center/articles", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("articles");
        List<String> articleIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            articleIds.add(temps.getLong("id").toString());
        }
        try{
            for (String articleId : articleIds) {
                doDelete("/api/v2/help_center/articles/",articleId);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 article_info 成功，一共删除{}条记录\n", articleIds.size());
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
//        recursion(parentSectionArray);
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
                } else if(mongoTemplate.findOne(new Query(new Criteria("id").is(jsonObject.getLong("parent_section_id"))), Document.class, ExportEnum.ARTICLE.getValue() + "_section").get("newId")!=null){
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
                //更新edit中userSegment的Id
                JSONArray editIds = jsonObject.getJSONArray("edit");
                JSONArray editNewIds = new JSONArray();
                if(editIds.size()!=0){
                    for (Object editIdObject : editIds) {
                        Long groupId = (Long) editIdObject;
                        Document userSegmentDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(groupId)), Document.class, ExportEnum.USER_SEGMENT.getValue() + "_info");
                        editNewIds.add(userSegmentDoc.get("newId"));
                    }
                    jsonObject.put("edit", editNewIds);
                }
                requestParam.put("permission_group", jsonObject);
                if(jsonObject.getBoolean("built_in")==true){
                    JSONObject temp = doGetTarget("/api/v2/guide/permission_groups.json",new HashMap<>());
                    JSONArray tempArray = temp.getJSONArray("permission_groups");
                    for (Object tempObject : tempArray) {
                        JSONObject permissionGroup = (JSONObject) tempObject;
                        if(permissionGroup.getBoolean("built_in")==true){
                            //request = this.doUpdate("/api/v2/help_center/user_segments/",requestParam, permissionGroup.get("id").toString());
                            document.put("newId", permissionGroup.get("id"));
                        }
                    }
                }else {
                    request = this.doPost("/api/v2/guide/permission_groups.json", requestParam);
                    document.put("newId", request.getJSONObject("permission_group").get("id"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importPermissionGroupInfo 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            mongoTemplate.save(document, ExportEnum.PERMISSION_GROUPS.getValue() + "_info");
            saveImportInfo("importPermissionGroupInfo", request);
        }
        log.info("导入 PermissionGroup_info 成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }

    @Override
    public void deletePermissionGroupInfo(){
        ModuleRecord moduleRecord = beginModuleRecord("deletePermissionGroupInfo");
        log.info("开始执行删除 permission_group 任务");
        long startTime = System.currentTimeMillis();
        JSONObject temp = doGetTarget("/api/v2/guide/permission_groups.json", new HashMap<>());
        JSONArray tempArray = temp.getJSONArray("permission_groups");
        List<String> permissionGroupIds = new ArrayList<>();
        for (Object tempObj : tempArray) {
            JSONObject temps = (JSONObject) tempObj;
            permissionGroupIds.add(temps.getLong("id").toString());
        }
        try{
            for (String permissionGroupId : permissionGroupIds) {
                doDelete("/api/v2/guide/permission_groups/",permissionGroupId+".json");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("删除 permission_groups 成功，一共删除{}条记录\n", permissionGroupIds.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    @Override
    public void exportArticleTranslation(){
        ModuleRecord moduleRecord = beginModuleRecord("exportArticleTranslation");
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ARTICLE.getValue() + "_section");

        //List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ARTICLE.getValue() + "_category");
        for (Document document : list) {
            JSONObject jsonObject = JSONObject.parseObject(document.toJson());
            Long useTime = doExport("/api/v2/help_center/sections/"+jsonObject.get("id").toString()+"/translations", "translations", ExportEnum.ARTICLE.getValue() + "_translation");

//            Long useTime = doExport("/api/v2/help_center/categories/"+jsonObject.get("id").toString()+"/translations", "translations", ExportEnum.ARTICLE.getValue() + "_translation");
            endModuleRecord(moduleRecord, useTime);
        }

    }




    @Override
    public void importArticleTranslation(){
        ModuleRecord moduleRecord = beginModuleRecord("importArticleTranslation");
        long startTime = System.currentTimeMillis();
        JSONObject requestParam = new JSONObject();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.ARTICLE.getValue() + "_translation");
        JSONObject request = null;
        JSONObject nestedParam = new JSONObject();
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                //更新category
                Long categoryId = jsonObject.getLong("source_id");
                Document categoryDoc = mongoTemplate.findOne(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain)).and("id").is(categoryId)), Document.class, ExportEnum.ARTICLE.getValue() + "_sections");
                if(categoryDoc!=null) {
                    jsonObject.put("source_id", categoryDoc.get("newId"));
                    requestParam.put("translation", jsonObject);
//                request = this.doPost("/api/v2/help_center/categories/"+jsonObject.get("source_id").toString()+"/translations", requestParam);

                    request = this.doPost("/api/v2/help_center/sections/" + jsonObject.get("source_id").toString() + "/translations", requestParam);
                    document.put("newId", request.getJSONObject("translation").get("id"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("importArticleTranslation 执行完毕,请求参数 {},执行结果 {}", requestParam, request);
            saveImportInfo("ArticleTranslation", request);
            mongoTemplate.save(document, ExportEnum.ARTICLE.getValue() + "_translation");
        }
        log.info("导入Article_Translation成功，一共导入{}条记录", list.size());
        endModuleRecord(moduleRecord, System.currentTimeMillis() - startTime);
    }


    @Override
    public void exportLocale() {
        ModuleRecord moduleRecord = beginModuleRecord("exportLocaleInfo");
        Long useTime = doExport("/api/v2/locales", "locales", ExportEnum.LOCALE.getValue() + "_info");
        endModuleRecord(moduleRecord, useTime);
    }

    @Override
    public void importLocale() {
        ModuleRecord moduleRecord = beginModuleRecord("importLocaleInfo");
        long startTime = System.currentTimeMillis();
        JSONObject requestParam = new JSONObject();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.LOCALE.getValue() + "_info");
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



}
