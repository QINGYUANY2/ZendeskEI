package com.whaleal.zendesk.service.content;

import org.springframework.stereotype.Service;

/**
 * @author lyz
 * @desc
 * @create: 2023-10-11 14:24
 **/
public interface IExportUserService {

    void exportUserInfo();

    void importUserInfo();

    void deleteUserInfo();

    void exportRoleInfo();

    void importRoleInfo();

    void exportUserField();

    void importUserField();

    void deleteUserField();

}
