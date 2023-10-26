package com.whaleal.zendesk.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author lyz
 * @desc  整体记录
 * @create: 2023-10-25 10:31
 **/
@Document("module_record")
@Data
public class ProcessInfo {

    @Id
    private String id;

    /**
     * 导出子域名
     */
    @Field("sub_domain")
    private String sourceUrl;

    /**
     *  导入子域名
     */
    private String targetUrl;

    /**
     * 1: 导出
     * 2： 导入
     * 3：清空导出记录
     */
    private Integer type;


    private Integer status;

    private Date createTime;

}
