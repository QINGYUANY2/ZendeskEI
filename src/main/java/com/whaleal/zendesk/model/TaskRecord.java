package com.whaleal.zendesk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author lyz
 * @desc  导入导出任务记录
 * @create: 2023-10-25 10:31
 **/
@Document("task_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRecord {

    @Id
    private String id;

    private String name;

    /**
     * 源端url
     */
    @Field("sub_domain")
    private String sourceUrl;

    /**
     * 目标端URL
     */
    private String targetUrl;

    /**
     * 1：导出中
     * 2：已导出
     * 3：导入中
     * 4：已导入
     * 5：异常
     */
    private Integer status;

    /**
     * 开始时间
     */
    private Date startTime;

    private Date endTime;

}
