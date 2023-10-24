package com.whaleal.zendesk.service.content.impl;

import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IDeleteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Locale;


/**
 * @author lyz
 * @desc
 * @create: 2023-10-24 13:19
 **/
@Slf4j
@Service
public class DeleteServiceImpl extends BaseExportService implements IDeleteService {


    @Override
    public void delete(String filedName) {
        log.info("开始清空已导出的记录");
        String table = filedName.toLowerCase(Locale.ROOT);

        if("all".equals(table)){
            ExportEnum[] values = ExportEnum.values();
            for (ExportEnum exportEnum : values){
                String value = exportEnum.getValue();
                mongoTemplate.remove(new Query(),value + "_info");
                log.info("清空了导出的{}记录",exportEnum.getModule());
            }
        }else {
            mongoTemplate.remove(new Query(), table + "_info");
            log.info("清空了导出的{}记录",ExportEnum.parse(table));
        }
        log.info("完成清空");
    }
}
