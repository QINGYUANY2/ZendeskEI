package com.whaleal.zendesk.service.content;

/**
 * @author lyz
 * @desc
 * @create: 2023-10-24 13:19
 **/
public interface IDeleteService {

    /**
     * 清空已导出的记录
     * @param filedName 要清空的模块 all 代表清空所有
     */
    void delete(String filedName);

}
