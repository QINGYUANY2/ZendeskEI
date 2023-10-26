package com.whaleal.zendesk.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author lyz
 * @desc  导入导出记录
 * @create: 2023-10-25 10:31
 **/
@Document("task_log")
@Data
public class ExportTask {

    @Id
    private String id;

    private String name;

    /**
     * 绑定的子域名
     */
    @Field("sub_domain")
    private String subDomain;

    /**
     * 1: 导出
     * 2： 导入
     * 3：清空导出记录
     */
    private Integer type;


    private Integer status;

    private Date createTime;

}
