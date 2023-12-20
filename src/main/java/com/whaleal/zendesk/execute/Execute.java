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

    @Value("${zendesk.target.domain}")
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

//            //1.组织
//            iExportOrgService.exportOrgInfo();
//            //2.群组
//            iExportGroupService.exportGroupInfo();
//            //3.品牌
//            iExportSysService.exportBrandInfo();
//            //4.人员字段
//            iExportUserService.exportUserField();
//            //5.人员
//            iExportUserService.exportUserInfo();
//            //6.视图
//            iExportBusinessService.exportViewInfo();
//            //7.宏
//            iExportBusinessService.exportMacroInfo();
//            //8.工单request
//            iExportTicketService.exportTicketRequest();
//            //9.工单form
//            iExportFormsService.exportTicketForms();
//            //10.工单field
//            iExportTicketService.exportTicketFields();
//            //11.工单
//            iExportTicketService.exportTicketInfo();

            //-------------------------------------------------

//            iExportOrgService.exportOrgMembershipInfo();  // ok
//
//            iExportOrgService.exportOrgSubscriptionsInfo();  //无数据

//            iExportUserService.exportRoleInfo();   // 此版本无角色
//
//            iExportGroupService.exportGroupMembershipInfo(); // ok
//
//            iExportItemService.exportItemInfo();    //  ok

//            iExportTicketService.exportSatisfactionRatingInfo();   //ticket 中已传
//
//            iExportTicketService.exportTicketAudit();    // 加ticket
//
//            iExportContentService.exportExternalContentRecordInfo();   //无数据
//
//            iExportGatherService.exportTopicInfo();    //ok

//            iExportGatherService.exportPostsInfo();    //  没有权限  未开通
//
//            iExportGuideService.exportArticleInfo();   // 无数据
//
//            iExportGuideService.exportThemeInfo();   // 导入不进去,没有创建主题
//
//            iExportGuideService.exportPermissionGroupInfo();   //  ok
//
//            iExportPhoneService.exportGreetingInfo();     // ok

//            iExportPhoneService.exportPhoneNumberInfo();   // 无可用phone 数据

//            iExportPhoneService.exportIVRsInfo();    // ok

//            iExportSysService.exportSupportAddressInfo();   //ok

//            iExportBusinessService.exportTriggerInfo();    // ok

//            iExportBusinessService.exportTriggerCategoriesInfo();   //ok

//            iExportBusinessService.exportAutomationsInfo();   //ok

//            iExportBusinessService.exportSLAPoliciesInfo();   //ok

//            iExportBusinessService.exportGroupSLAPoliciesInfo();   // 无数据

//            iExportTicketService.exportCustomTicketStatus();  // 版本不包含new 且标签等已占用问题

//            iExportTicketService.exportSharingAgreement();  //  无数据

//            iExportTicketService.exportSchedules();  //  ok

//            iExportTicketService.exportAccountAttributes();  //  ok

//            iExportTicketService.exportResourceCollections();   // 文档不清楚
















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
//
//        String id1 = taskRecord.getId();
//        Query id = Query.query(Criteria.where("_id").is(id1));
//
//        if(ObjectUtils.isEmpty(taskRecord)){
//            log.error("未找到导出记录");
//            return;
//        }

//        if(EXPORTED == taskRecord.getStatus()){
            //更新任务状态为导入中
            Update status = new Update().set("status", IMPORTING);

        //1.组织
//        iExportOrgService.importOrgInfo();

//        iExportOrgService.importOrgMembershipInfo();

        //2.群组
//        iExportGroupService.importGroupInfo();

//        iExportGroupService.importGroupMembershipInfo();

//        //3.品牌
//        iExportSysService.importBrandInfo();
//        //4.人员字段
//        iExportUserService.importUserField();
//        //5.人员
//        iExportUserService.importUserInfo();
//        //6.视图
//        iExportBusinessService.importViewInfo();
//        //7.宏
//        iExportBusinessService.importMacroInfo();
//        //8.工单form  暂时无数据
//        iExportFormsService.importTicketForms();
//        //9.工单field
//        iExportTicketService.importTicketFields();
//        //10.工单
//        iExportTicketService.importTicketInfo();


//        iExportItemService.importItemInfo();

//        iExportGatherService.importTopicInfo();

//        iExportGuideService.importPermissionGroupInfo();

//        iExportPhoneService.importGreetingInfo();

//        iExportPhoneService.importIVRsInfo();

//        iExportSysService.importSupportAddressInfo();

//        iExportBusinessService.importTriggerCategoriesInfo();

//        iExportBusinessService.importTriggerInfo();

//        iExportBusinessService.importAutomationsInfo();

//        iExportBusinessService.importSLAPoliciesInfo();

//        iExportTicketService.importSchedules();

//        iExportTicketService.importAccountAttributes();


//            mongoTemplate.updateFirst(id,status,TaskRecord.class);

            log.info("导入任务执行完成");
//        }


//
//        Update status = new Update().set("status", IMPORTED);
//
//        mongoTemplate.updateFirst(id,status,TaskRecord.class);

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
