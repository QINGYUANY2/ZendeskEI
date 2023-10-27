package com.whaleal.zendesk.service.helpCenter.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.TaskInfo;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.helpCenter.IExportContentService;
import com.whaleal.zendesk.util.StringSub;
import com.whaleal.zendesk.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class IExportContentServiceImpl extends BaseExportService implements IExportContentService {

    @Override
    public void exportExternalContentRecordInfo() {

        TaskInfo exportUserInfo = saveTaskInfo("exportExternalContentRecordInfo");
        Long useTime = doExport("/api/v2/guide/external_content/records", "records", ExportEnum.RECORD.getValue() + "_info");
        exportUserInfo.setEndTime(TimeUtil.getTime());
        exportUserInfo.setUseTime(useTime);
        exportUserInfo.setStatus(2);
        mongoTemplate.save(exportUserInfo);

    }

    @Override
    public void importExternalContentRecordInfo() {
        TaskInfo saveTask = saveTaskInfo("importExternalContentRecordInfo");
        long startTime = System.currentTimeMillis();
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.RECORD.getValue() + "_info");
        JSONObject requestParam = new JSONObject();
        JSONObject request = null;
        for (Document document : list) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                requestParam.put("record", jsonObject);
                request = this.doPost("/api/v2/guide/external_content/records", requestParam);
               document.put("newId",request.getJSONObject("record").get("id"));
            }catch (Exception e){
                e.printStackTrace();
            }
            mongoTemplate.save(document,ExportEnum.RECORD.getValue() + "_info");
            log.info("importExternalContentRecordInfo 执行完毕,请求参数：{},执行结果{}", requestParam, request);
            saveImportInfo("importExternalContentRecordInfo", request);
        }
        log.info("导入 ContentRecordInfo 成功，一共导出{}条记录",list.size());
        saveTask.setEndTime(TimeUtil.getTime());
        saveTask.setUseTime(System.currentTimeMillis() - startTime);
        saveTask.setStatus(2);
        mongoTemplate.save(saveTask);
    }
}
