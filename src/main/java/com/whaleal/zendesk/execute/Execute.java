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

        iExportOrgService.exportOrgMembershipInfo();
        iExportOrgService.importOrgMembershipInfo();

//        iExportOrgService.exportOrgSubscriptionsInfo();
//        iExportOrgService.importOrgSubscriptionsInfo();

        //ok
//        iExportOrgService.exportOrgInfo();
//        iExportOrgService.importOrgInfo();

        //role  有点问题
//        iExportUserService.exportRoleInfo();
//        iExportUserService.importRoleInfo();

        //ok
//        iExportUserService.exportUserField();
//        iExportUserService.importUserField();

        //ok
//        iExportUserService.exportUserInfo();
//        iExportUserService.importUserInfo();

//        iExportBusinessService.exportViewInfo();

//        iExportBusinessService.exportMacroInfo();

//        iExportFieldService.exportFieldInfo();

        //ok
//        iExportGroupService.exportGroupInfo();
//        iExportGroupService.importGroupInfo();

//        iExportGroupService.exportMembershipInfo();
//        iExportGroupService.importMembershipInfo();

//        iExportItemService.exportItemInfo();

//        iExportItemService.exportDynamicContent();
//        iExportSysService.exportBrandInfo();
//        iExportTicketService.exportTicketInfo();
//        iExportTicketService.importTicketInfo();
//        iExportTicketService.exportSatisfactionRatingInfo();
//        iExportContentService.exportExternalContentRecordInfo();
//        iExportGatherService.exportTopicInfo();
//        iExportGatherService.exportUserSegmentInfo();
//        iExportGuideService.exportThemeInfo();
//        iExportGuideService.exportArticleInfo();
//        iExportGuideService.exportPermissionGroupInfo();
//        iExportPhoneService.exportPhoneNumberInfo();
//        iExportPhoneService.exportGreetingCategoriesInfo();
//        iExportPhoneService.exportIVRsInfo();

    }

//
//    @PostConstruct
//    void init(){
////        iExportUserService.exportUserInfo();
////        iExportUserService.importUserInfo();
////        iExportBusinessService.exportViewInfo();
////        iExportBusinessService.exportMacroInfo();
////        iExportFieldService.exportFieldInfo();
////        iExportGroupService.exportGroupInfo();
////        iExportGroupService.exportMembershipInfo();
////        iExportItemService.exportItemInfo();
////        iExportItemService.exportDynamicContent();
////        iExportSysService.exportBrandInfo();
////        iExportTicketService.exportTicketInfo();
////        iExportTicketService.exportSatisfactionRatingInfo();
////        iExportContentService.exportExternalContentRecordInfo();
////        iExportGatherService.exportTopicInfo();
////        iExportGatherService.exportUserSegmentInfo();
////        iExportGuideService.exportThemeInfo();
////        iExportGuideService.exportArticleInfo();
////        iExportGuideService.exportPermissionGroupInfo();
////        iExportPhoneService.exportPhoneNumberInfo();
////        iExportPhoneService.exportGreetingCategoriesInfo();
////        iExportPhoneService.exportIVRsInfo();
//
//    }
//


}
