package com.whaleal.zendesk.service.content.impl;

import com.alibaba.fastjson.JSONObject;
import com.whaleal.zendesk.common.ExportEnum;
import com.whaleal.zendesk.model.TaskInfo;
import com.whaleal.zendesk.service.BaseExportService;
import com.whaleal.zendesk.service.content.IExportSysService;
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
public class IExportSysServiceImpl extends BaseExportService implements IExportSysService {
    @Override
    public void exportBrandInfo() {

        TaskInfo exportUserInfo = saveTaskInfo("exportBrandInfo");
        Long useTime = doExport("/api/v2/brands", "brands", ExportEnum.BRAND.getValue() + "_info");
        exportUserInfo.setEndTime(TimeUtil.getTime());
        exportUserInfo.setUseTime(useTime);
        exportUserInfo.setStatus(2);
        mongoTemplate.save(exportUserInfo);
    }

    @Override
    public void importBrandInfo() {
        // todo  后期添加分页 以防过大
        List<Document> list = mongoTemplate.find(new Query(new Criteria("domain").is(StringSub.getDomain(this.sourceDomain))), Document.class, ExportEnum.BRAND.getValue() + "_info");
        JSONObject request = null;
        for (Document document : list) {
            try {
                // host_mapping 关联问题 请求时移除即可
                document.remove("host_mapping");
                // todo  临时加 111 防止重复
                document.put("subdomain",document.get("subdomain")+"111");
                JSONObject jsonObject = JSONObject.parseObject(document.toJson());
                JSONObject requestParam = new JSONObject();
                requestParam.put("brand", jsonObject);
                request = this.doPost("/api/v2/brands", requestParam);
                log.info("请求结果{}", request);
                document.put("status",1);
                document.put("newId",request.getJSONObject("brand").get("id"));
            }catch (Exception e){
                e.printStackTrace();
                document.put("status",2);
            }
            document.put("request",request);
            mongoTemplate.save(document,ExportEnum.BRAND.getValue() + "_info");
        }
    }
}
