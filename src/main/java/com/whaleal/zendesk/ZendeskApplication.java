package com.whaleal.zendesk;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ZendeskApplication {


    public static void main(String[] args) {
        new SpringApplicationBuilder(ZendeskApplication.class)
                .web(WebApplicationType.NONE).run(args);
    }

}
