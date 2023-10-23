package com.whaleal.zendesk.execute;


import com.whaleal.zendesk.service.content.*;
import com.whaleal.zendesk.service.helpCenter.IExportContentService;
import com.whaleal.zendesk.service.helpCenter.IExportGatherService;
import com.whaleal.zendesk.service.helpCenter.IExportGuideService;
import com.whaleal.zendesk.service.voice.IExportPhoneService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class Execute {

    @Resource
    private IExportUserService iExportUserService;
    @Resource
    private IExportBusinessService iExportBusinessService;
    @Resource
    private IExportFieldService iExportFieldService;
    @Resource
    private IExportFormsService iExportFormsService;
    @Resource
    private IExportGroupService iExportGroupService;
    @Resource
    private IExportItemService iExportItemService;
    @Resource
    private IExportOrgService iExportOrgService;
    @Resource
    private IExportSysService iExportSysService;
    @Resource
    private IExportTicketService iExportTicketService;

    @Resource
    private IExportContentService iExportContentService;
    @Resource
    private IExportGatherService iExportGatherService;
    @Resource
    private IExportGuideService iExportGuideService;

    @Resource
    private IExportPhoneService iExportPhoneService;


    @PostConstruct
    void init(){

        //ok
//        iExportOrgService.exportOrgMembershipInfo();
//        iExportOrgService.importOrgMembershipInfo();

        // 无数据
//        iExportOrgService.exportOrgSubscriptionsInfo();
//        iExportOrgService.importOrgSubscriptionsInfo();

        //ok
//        iExportOrgService.exportOrgInfo();
//        iExportOrgService.importOrgInfo();

        //role  有点问题，没有role
//        iExportUserService.exportRoleInfo();
//        iExportUserService.importRoleInfo();

        //ok
//        iExportUserService.exportUserField();
//        iExportUserService.importUserField();

        //ok
//        iExportUserService.exportUserInfo();
//        iExportUserService.importUserInfo();

        //ok
//        iExportBusinessService.exportViewInfo();
//        iExportBusinessService.importViewInfo();

        //ok
//        iExportBusinessService.exportMacroInfo();
//        iExportBusinessService.importMacroInfo();

        //ok
//        iExportFieldService.exportFieldInfo();
//        iExportFieldService.importFieldInfo();

        //ok
//        iExportGroupService.exportGroupInfo();
//        iExportGroupService.importGroupInfo();

        //ok
//        iExportGroupService.exportGroupMembershipInfo();
//        iExportGroupService.importGroupMembershipInfo();

        //ok
//        iExportItemService.exportItemInfo();
//        iExportItemService.importItemInfo();

        //ok
//        iExportSysService.exportBrandInfo();
//        iExportSysService.importBrandInfo();

        // 关联太多
//        iExportTicketService.exportTicketInfo();
        iExportTicketService.importTicketInfo();

//        iExportTicketService.exportSatisfactionRatingInfo();

        //无数据
//        iExportContentService.exportExternalContentRecordInfo();
//        iExportContentService.importExternalContentRecordInfo();

        //ok
//        iExportGatherService.exportTopicInfo();
//        iExportGatherService.importTopicInfo();

        //导入部分没有示例java代码，一招curl编写 导入时有问题
//        iExportGuideService.exportThemeInfo();
//        iExportGuideService.importThemeInfo();

        //无数据
//        iExportGuideService.exportArticleInfo();
//        iExportGuideService.exportArticleInfo();

        //ok
//        iExportGuideService.exportPermissionGroupInfo();
//        iExportGuideService.importPermissionGroupInfo();

        //导入有问题
//        iExportPhoneService.exportPhoneNumberInfo();
//        iExportPhoneService.importPhoneNumberInfo();

        //导出的数据用作参数时缺少Category
//        iExportPhoneService.exportGreetingCategoriesInfo();
//        iExportPhoneService.importGreetingCategoriesInfo();

        // 导入时会有权限问题
//        iExportPhoneService.exportIVRsInfo();
//        iExportPhoneService.importIVRsInfo();

    }
}
