package com.whaleal.zendesk.common;


public enum TaskEnum {


    /**
     * status
     */
//    EXPORTING("exporting",1),
//    EXPORTED("exported",2),
//    IMPORTING("importing",3),
//    IMPORTED("imported",4),
//    EXCEPTION("exception",5),


    NOT_STARTED("not_start",1),
    IN_PROGRESS("in_progress",2),
    COMPLETED("completed",3),


    /**
     * type
     */

    EXPORT("export",1),
    IMPORT("import",2);






    private String name;

    private Integer value;


    TaskEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
