//package com.whaleal.zendesk.controller;
//
//import com.whaleal.zendesk.service.content.*;
//import com.whaleal.zendesk.service.helpCenter.IExportContentService;
//import com.whaleal.zendesk.service.helpCenter.IExportGatherService;
//import com.whaleal.zendesk.service.helpCenter.IExportGuideService;
//import com.whaleal.zendesk.service.voice.IExportPhoneService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * @author lyz
// * @desc
// * @create: 2023-10-11 15:43
// **/
//@RestController
//@RequestMapping("/doJob")
//public class DoJobController {
//
//    @Autowired
//    private IExportUserService exportUserService;
//
//    @Resource
//    private IExportUserService iExportUserService;
//    @Resource
//    private IExportBusinessService iExportBusinessService;
//    @Resource
//    private IExportFormsService iExportFormsService;
//    @Resource
//    private IExportGroupService iExportGroupService;
//    @Resource
//    private IExportItemService iExportItemService;
//    @Resource
//    private IExportOrgService iExportOrgService;
//    @Resource
//    private IExportSysService iExportSysService;
//    @Resource
//    private IExportTicketService iExportTicketService;
//
//    @Resource
//    private IExportContentService iExportContentService;
//    @Resource
//    private IExportGatherService iExportGatherService;
//    @Resource
//    private IExportGuideService iExportGuideService;
//
//    @Resource
//    private IExportPhoneService iExportPhoneService;
//
//    @GetMapping("export")
//    public ResponseEntity doExport(){
//        return null;
//    }
//
//}
