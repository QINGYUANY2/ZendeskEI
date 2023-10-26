package com.whaleal.zendesk.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document
@Data
public class ImportInfo {

    @Id
    private String id;

    private String name;

    @Field("sub_domain")
    private String subDomain;

    private String type;

    private String creatTime;

    private JSONObject request;

}
