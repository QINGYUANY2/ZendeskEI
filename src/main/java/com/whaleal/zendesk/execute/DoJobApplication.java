package com.whaleal.zendesk.execute;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.whaleal.zendesk.common.Constants.*;


/**
 * @author lyz
 * @desc
 * @create: 2023-10-26 17:13
 **/
@Slf4j
@Component
public class DoJobApplication implements ApplicationRunner {

    @Autowired
    Execute execute;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> modes = args.getOptionValues("mode");
        if(modes.isEmpty()){
            log.error("缺少执行模式");
        }
        //获取执行模式
        String mode = modes.get(0);

        if(doExport.equalsIgnoreCase(mode)){
            execute.doExport();
        }else if(doImport.equalsIgnoreCase(mode)){
            execute.doImport();
        }else if(doDelete.equalsIgnoreCase(mode)){
            String moduleName = args.getOptionValues("moduleName").get(0);
            execute.doDelete(moduleName);
        }else {
            log.error("执行模式不合法");
        }
    }
}
