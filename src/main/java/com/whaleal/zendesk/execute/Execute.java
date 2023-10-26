package com.whaleal.zendesk.execute;


import com.whaleal.zendesk.service.content.*;
import com.whaleal.zendesk.service.helpCenter.IExportContentService;
import com.whaleal.zendesk.service.helpCenter.IExportGatherService;
import com.whaleal.zendesk.service.helpCenter.IExportGuideService;
import com.whaleal.zendesk.service.voice.IExportPhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
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

    @Resource
    private IDeleteService deleteService;

    /**
     * 导出源端记录到数据库中
     */
    public void doExport(){
        log.info("开始执行导出任务");
        iExportOrgService.exportOrgMembershipInfo();

        iExportOrgService.exportOrgSubscriptionsInfo();

        iExportOrgService.exportOrgInfo();

        iExportUserService.exportRoleInfo();

        iExportUserService.exportUserField();
        iExportUserService.exportUserInfo();

        iExportBusinessService.exportViewInfo();

        iExportBusinessService.exportMacroInfo();


        iExportGroupService.exportGroupInfo();

        iExportGroupService.exportGroupMembershipInfo();

        iExportItemService.exportItemInfo();

        iExportSysService.exportBrandInfo();

        iExportTicketService.exportSatisfactionRatingInfo();
        iExportTicketService.exportTicketAudit();
        iExportTicketService.exportTicketRequest();
        iExportTicketService.exportTicketInfo();


        iExportTicketService.exportTicketFields();

        iExportFormsService.exportTicketForms();


        iExportContentService.exportExternalContentRecordInfo();

        iExportGatherService.exportTopicInfo();

        iExportGuideService.exportArticleInfo();
        iExportGuideService.exportArticleInfo();
        iExportGuideService.exportThemeInfo();

        iExportGuideService.exportPermissionGroupInfo();

        iExportPhoneService.exportGreetingCategoriesInfo();
        iExportPhoneService.exportGreetingInfo();

        iExportPhoneService.exportPhoneNumberInfo();

        iExportPhoneService.exportIVRsInfo();
        log.info("执行导出完成");
    }

    /**
     * 把数据库中记录导入到目标端
     */
    public void doImport(){
        log.info("执行导入任务");
            iExportOrgService.importOrgMembershipInfo();

            iExportOrgService.importOrgSubscriptionsInfo();

            iExportOrgService.importOrgInfo();

            iExportUserService.importRoleInfo();

            iExportUserService.importUserField();

            iExportUserService.importUserInfo();

            iExportBusinessService.importViewInfo();

            iExportBusinessService.importMacroInfo();

            iExportGroupService.importGroupInfo();

            iExportGroupService.importGroupMembershipInfo();

            iExportItemService.importItemInfo();

            iExportSysService.importBrandInfo();

            iExportTicketService.importTicketInfo();

            iExportTicketService.importTicketFields();

            iExportFormsService.importTicketForms();

            iExportContentService.importExternalContentRecordInfo();

            iExportGatherService.importTopicInfo();

            iExportGuideService.importThemeInfo();

            iExportGuideService.importPermissionGroupInfo();

            iExportPhoneService.importGreetingInfo();

            iExportPhoneService.importPhoneNumberInfo();

            iExportPhoneService.importIVRsInfo();
            log.info("导入任务执行完成");
    }

    /**
     * 删除
     * @param fileName
     */
    public void doDelete(String fileName){
        log.info("开始执行删除任务");
        deleteService.delete(fileName);
        log.info("删除任务执行完成");
    }

}
