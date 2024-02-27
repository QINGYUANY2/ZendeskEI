package com.whaleal.zendesk.service.helpCenter;

public interface IExportGuideService {

    void exportThemeInfo();

    void importThemeInfo();

    void deleteArticleCategory();

    void exportArticleCategory();

    void importArticleCategory();

    void exportArticleInfo();

    void importArticleInfo();

    void deleteArticleInfo();

    void exportArticleSection();

    void importArticleSection();

    void exportPermissionGroupInfo();

    void importPermissionGroupInfo();


    void deletePermissionGroupInfo();

    void exportArticleTranslation();

    void importArticleTranslation();

    void exportLocale();

    void importLocale();
}
