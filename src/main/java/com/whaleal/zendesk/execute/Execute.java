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
        //todo 记录导入批次 导入成功之后下次就不再对这批次进行导入

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

        // 关联太多  ok
//        iExportTicketService.exportSatisfactionRatingInfo();
        iExportTicketService.exportTicketAudit();
//        iExportTicketService.exportTicketRequest();
//        iExportTicketService.exportTicketInfo();
//        iExportTicketService.importTicketInfo();


        //ok
//        iExportTicketService.exportTicketFields();
//        iExportTicketService.importTicketFields();

        //ok
//        iExportFormsService.exportTicketForms();
//        iExportFormsService.importTicketForms();


        //无数据
//        iExportContentService.exportExternalContentRecordInfo();
//        iExportContentService.importExternalContentRecordInfo();

        //ok
//        iExportGatherService.exportTopicInfo();
//        iExportGatherService.importTopicInfo();

        //无数据
//        iExportGuideService.exportArticleInfo();
//        iExportGuideService.exportArticleInfo();
        //导入部分没有示例java代码，依照curl编写 导入时有问题
//        iExportGuideService.exportThemeInfo();
//        iExportGuideService.importThemeInfo();

        //ok
//        iExportGuideService.exportPermissionGroupInfo();
//        iExportGuideService.importPermissionGroupInfo();

        //ok
//        iExportPhoneService.exportGreetingCategoriesInfo();
//        iExportPhoneService.exportGreetingInfo();
//        iExportPhoneService.importGreetingInfo();

        //导入有问题  开通即可
//        iExportPhoneService.exportPhoneNumberInfo();
//        iExportPhoneService.importPhoneNumberInfo();

        // 导入时会有权限问题
//        iExportPhoneService.exportIVRsInfo();
//        iExportPhoneService.importIVRsInfo();

    }
}
