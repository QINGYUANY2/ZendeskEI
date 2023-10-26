package com.whaleal.zendesk.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtil {


    public static String getTime(){

        // 获取中国时区
        ZoneId chinaZone = ZoneId.of("Asia/Shanghai");

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now(chinaZone);

        // 格式化时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }


}
