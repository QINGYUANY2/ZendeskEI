package com.whaleal.zendesk.service.content;

public interface IExportBusinessService {

    void exportViewInfo();

    void importViewInfo();

    void exportMacroInfo();

    void importMacroInfo();

    void exportTriggerInfo();

    void importTriggerInfo();

    void exportTriggerCategoriesInfo();

    void importTriggerCategoriesInfo();

    void exportAutomationsInfo();

    void importAutomationsInfo();

    void exportSLAPoliciesInfo();

    void importSLAPoliciesInfo();

    void exportGroupSLAPoliciesInfo();

    void importGroupSLAPoliciesInfo();


}
