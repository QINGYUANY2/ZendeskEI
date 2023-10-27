package com.whaleal.zendesk.common;

/**
 * @author lyz
 * @desc
 * @create: 2023-10-25 10:38
 **/
public class Constants {

    /**
     * 执行mode
     */
    public final static String doExport = "export";

    public final static String doImport = "import";

    public final static String doDelete = "delete";


    /**
     * 任务类型
     */
    public final static int EXPORT_TASK = 1;
    public final static int IMPORT_TASK = 2;

    /**
     * 任务状态
     */
    public final static int EXPORTING = 1;
    public final static int EXPORTED = 2;
    public final static int IMPORTING = 3;
    public final static int IMPORTED = 4;
    public final static int EXCEPTION = 5;


}
