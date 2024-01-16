package com.whaleal.zendesk.common;

/**
 * @author lyz
 * @desc 模块常量
 * @create: 2023-10-24 13:33
 **/
public enum ExportEnum {

    DYNAMIC_CONTENT("动态内容", "content", 1),

    USER("人员", "user", 2),

    ORGANIZATIONS("组织", "organizations", 3),

    GROUP("群组", "group", 4),

    FIELD("字段", "field", 5),

    SHEET("表格", "sheet", 6),

    SYSTEM_SETTING("系统设置", "setting", 7),

    BUSINESS_RULES("业务规则", "rule", 8),


    TICKET("工单", "ticket", 9),

    ASSIST_CONVERSATION("协助对话", "assistConversation", 10),

    BRAND("品牌", "brand", 11),

    VIEW("视图", "view", 12),

    MACRO("宏", "macro", 13),

    ITEM("项目", "item", 14),

    SATISFACTION("满意度", "satisfaction", 15),

    ARTICLE("文章", "article", 16),

    PERMISSION_GROUPS("权限组", "permission_groups", 17),

    SUPPORT_ADDRESS("支持地址", "support_address", 18),

    TRIGGER("触发器", "trigger", 19),

    TRIGGER_CATEGORIES("触发器类别", "trigger_categories", 20),

    AUTOMATIONS("自动化", "automations", 21),

    SLA_POLICIES("sla等级", "SLAPolicies", 22),

    GROUP_SLA_POLICIES("sla等级组", "groupSLAPolicies", 23),

    SHARING_AGREEMENT("共享协议", "sharing_agreement", 24),

    SCHEDULES("时间安排表", "Schedules", 25),

    ACCOUNT_ATTRIBUTES("账户属性", "account_attributes", 26),

    RESOURCE_COLLECTIONS("资源集合", "resource_collections", 27),

    SIDE_CONVERSATION("协助对话", "side_conversation", 28),

    EVENT("协助对话活动", "side_conversation_event", 29),
    /**
     * 帮助中心
     */
    GUIDE("知识库", "guide", 1),
    GATHER("社区", "gather", 2),
    UNION_SEARCH("联合搜索", "search", 3),
    RECORD("记录", "record", 4),
    TOPIC("话题", "topic", 5),
    THEMES("题目", "themes", 6),
    POSTS("文章", "posts", 7),

    /**
     * voice
     */
    VOICE_RELATE("线路相关", "voice", 1),
    GREETING_CATEGORIES("问候语类别", "GreetingCategories", 3),
    GREETING("问候语类别", "Greeting", 4),
    ADDRESS("地址导入", "Address", 6),
    IVRS("交互式语音应答", "ivrs", 5),
    PHONE("电话配置", "phone", 2);


    private final String module;

    private final String value;

    private final int brand;

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
