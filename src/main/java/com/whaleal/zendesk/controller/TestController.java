package com.whaleal.zendesk.controller;

import com.whaleal.zendesk.service.IExportUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lyz
 * @desc
 * @create: 2023-10-11 15:43
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private IExportUserService exportUserService;

    @RequestMapping("/export/userinfo")
    public ResponseEntity test(){
        exportUserService.exportUserInfo();
        return new ResponseEntity(HttpStatus.OK);
    }
}
