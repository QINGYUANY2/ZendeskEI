package com.whaleal.zendesk.execute;


import com.whaleal.zendesk.model.TaskRecord;
import com.whaleal.zendesk.service.content.*;
import com.whaleal.zendesk.service.helpCenter.IExportContentService;
import com.whaleal.zendesk.service.helpCenter.IExportGatherService;
import com.whaleal.zendesk.service.helpCenter.IExportGuideService;
import com.whaleal.zendesk.service.voice.IExportPhoneService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static com.whaleal.zendesk.common.Constants.*;

@Slf4j
@Component
public class Execute {

    @Value("${zendesk.source.domain}")
    private String sourceDomain;

    @Value("${zendesk.target.url}")
    private String targetDomain;


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

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 导出源端记录到数据库中
     */
    public void doExport() {
        log.info("开始执行导出任务");

        /**
         * 判断是否执行过导出操作
         * todo 需要去判断全量与增量
         */
        Query query = Query.query(Criteria.where("sub_domain").is(sourceDomain).and("type").is(EXPORT_TASK));

        TaskRecord task = mongoTemplate.findOne(query, TaskRecord.class);
        if(!ObjectUtils.isEmpty(task)){
            if(2 == task.getStatus()){
                log.info("重复执行，如果需要执行，请先清空导出记录");
                return;
            }else if(1 == task.getStatus()){
                log.info("导出任务正在执行中");
                return;
            }else if(3 == task.getStatus()){
                deleteService.delete("all");
            }
        }

        /**
         * 记录导出任务
         */
        TaskRecord taskRecord = new TaskRecord();
        String objectId = new ObjectId().toString();

        taskRecord.setId(objectId);
        taskRecord.setStatus(1);
        taskRecord.setSourceUrl(sourceDomain);
        taskRecord.setTargetUrl(targetDomain);
        taskRecord.setStartTime(new Date());
        mongoTemplate.save(taskRecord);

        int status = 0;
        try {
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
            status = 2;
        }catch (Exception e){
            log.error("导出过程中出现了异常，{}",e.getMessage());
            status = 5;
        }

        //导出完成 更新任务状态
        Query id = Query.query(Criteria.where("_id").is(objectId));

        Update update = new Update().set("status", status);
        mongoTemplate.updateFirst(id,update,TaskRecord.class);
    }

    /**
     * 把数据库中记录导入到目标端
     */
    public void doImport() {
        log.info("执行导入任务");
        Criteria criteria = Criteria.where("source_url").is(sourceDomain).and("target_url").is(targetDomain);

        Query query = Query.query(criteria);

        TaskRecord taskRecord = mongoTemplate.findOne(query, TaskRecord.class);

        String id1 = taskRecord.getId();
        Query id = Query.query(Criteria.where("_id").is(id1));

        if(ObjectUtils.isEmpty(taskRecord)){
            log.error("未找到导出记录");
            return;
        }

        if(EXPORTED == taskRecord.getStatus()){
            //更新任务状态为导入中
            Update status = new Update().set("status", IMPORTING);

            mongoTemplate.updateFirst(id,status,TaskRecord.class);

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



        Update status = new Update().set("status", IMPORTED);

        mongoTemplate.updateFirst(id,status,TaskRecord.class);

    }

    /**
     * 删除
     *
     * @param fileName
     */
    public void doDelete(String fileName) {
        log.info("开始执行删除任务");
        deleteService.delete(fileName);
        log.info("删除任务执行完成");
    }

}
