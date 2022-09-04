package com.fang.file.util;

import org.springframework.core.env.Environment;


/**
 * @author 川川
 * @date 2022-02-20 11:00
 */
public class PropertiesUtil {
    private static Environment env;

    public static void setEnvironment(Environment env) {
        PropertiesUtil.env = env;
    }

    // 获取application.yml中的配置
    public static String getProperty(String key) {
        return PropertiesUtil.env.getProperty(key);
    }

}
