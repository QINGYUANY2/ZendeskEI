package com.whaleal.zendesk.common;

/**
 * @author lyz
 * @desc 模块常量
 * @create: 2023-10-24 13:33
 **/
public enum ExportEnum {

    DYNAMIC_CONTENT("动态内容","content",1),

    USER("人员","user",2),

    ORGANIZATIONS("组织","organizations",3),

    GROUP("群组","group",4),

    FIELD("字段","field",5),

    SHEET("表格","sheet",6),

    SYSTEM_SETTING("系统设置","setting",7),

    BUSINESS_RULES("业务规则","rule",8),


    TICKET("工单","ticket",9),

    ASSIST_CONVERSATION("协助对话","assistConversation",10),

    BRAND("品牌","brand",11),

    VIEW("视图","view",12),

    MACRO("宏","macro",13),

    ITEM("项目","item",14),

    SATISFACTION("满意度","satisfaction",15),



    /**
     * 帮助中心
     */
    GUIDE("知识库","guide",1),
    GATHER("社区","gather",2),
    UNION_SEARCH("联合搜索","search",3),
    RECORD("记录","record",4),
    TOPIC("话题","topic",5),
    THEMES("题目","themes",6),

    /**
     * voice
     */
    VOICE_RELATE("线路相关","voice",1),
    PHONE_SYSTEM("电话配置","phone",2);


    private String module;

    private String value;

    private int brand;

    ExportEnum(String module, String value, int brand) {
        this.module = module;
        this.value = value;
        this.brand = brand;
    }

//    public static String parse(String value){
//        ExportEnum[] values = ExportEnum.values();
//        for (ExportEnum exportEnum : values){
//            if(value.equals(exportEnum.getValue())){
//                return exportEnum.getModule();
//            }
//        }
//        return "";
//    }

    public String getValue() {
        return value;
    }

    public String getModule() {
        return module;
    }
}
