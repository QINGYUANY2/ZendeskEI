package com.whaleal.zendesk.service.content;

public interface IExportTicketService {

    void exportTicketRequest();

    void importTicketRequest();

    void exportTicketAudit();

    void exportTicketInfo();

    void importTicketInfo();

    void deleteTicketInfo();

    void exportTicketFields();

    void importTicketFields();

    void deleteTicketFields();

    void exportSatisfactionRatingInfo();

    void importSatisfactionRatingInfo();

    void exportCustomTicketStatus();

    void importCustomTicketStatus();

    void exportSharingAgreement();

    void importSharingAgreement();

    void deleteSchedules();

    void exportSchedules();

    void importSchedules();


    void exportAttributeValue();

    void importAttributeValue();

    void deleteAccountAttributes();

    void exportAccountAttributes();

    void importAccountAttributes();

    void exportResourceCollections();

    void importResourceCollections();

    void deleteResourceCollections();




}
