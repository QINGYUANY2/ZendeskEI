package com.whaleal.zendesk.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author lyz
 * @desc  模块导入导出记录
 * @create: 2023-10-25 10:31
 **/
@Document("module_record")
@Data
public class ModuleRecord {

    @Id
    private String id;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 子域名
     */
    @Field("sub_domain")
    private String sourceUrl;

    /**
     * 1: 导出
     * 2： 导入
     * 3：清空导出记录
     */
    private Integer type;


    /**
     *
     * 1: 未开始
     * 2: 进行中
     * 3: 已结束
     */
    private Integer status;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    private Long duration;

}
