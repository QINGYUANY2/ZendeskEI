package com.whaleal.zendesk.service.content;

public interface IExportBusinessService {

    void exportViewInfo();

    void importViewInfo();

    void deleteViewInfo();

    void exportMacroInfo();

    void importMacroInfo();

    void deleteMacroInfo();

    void exportTriggerInfo();

    void importTriggerInfo();

    void deleteTriggerInfo();

    void exportTriggerCategoriesInfo();

    void importTriggerCategoriesInfo();

    void deleteTriggerCategoriesInfo();

    void exportAutomationsInfo();

    void importAutomationsInfo();


    void exportSLAPoliciesInfo();

    void importSLAPoliciesInfo();

    void exportGroupSLAPoliciesInfo();

    void importGroupSLAPoliciesInfo();



}
