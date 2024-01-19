package com.whaleal.zendesk.service.content;

public interface IExportOrgService {

    void exportOrgInfo();

    void importOrgInfo();

    void deleteOrgInfo();

    void exportOrgMembershipInfo();

    void importOrgMembershipInfo();

    void exportOrgSubscriptionsInfo();

    void importOrgSubscriptionsInfo();

    void deleteOrgSubscriptionsInfo();

    void exportOrgField();

    void importOrgField();

    void deleteOrgField();


}
