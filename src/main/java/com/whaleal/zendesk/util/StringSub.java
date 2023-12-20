package com.whaleal.zendesk.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSub {


    public static String getDomain(String input) {

        // 使用正则表达式匹配 {} 内的内容
        Pattern pattern = Pattern.compile("https://(.+?)\\.zendesk\\.com");
        Matcher matcher = pattern.matcher(input);
        String extractedContent=null;
        if (matcher.find()) {
            extractedContent= matcher.group(1);
        }else{
            System.out.println("2");
        }
        return extractedContent;
    }

}
