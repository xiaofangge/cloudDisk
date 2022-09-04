package com.fang.file.config;

import com.fang.file.util.PropertiesUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 获取配置文件类
 * @author 川川
 * @date 2022-02-20 10:53
 */
@Configuration
public class PropertiesConfig {

    @Resource(name = "environment")
    private Environment environment;

    @PostConstruct
    public void setProperties() {
        PropertiesUtil.setEnvironment(environment);
    }
}
