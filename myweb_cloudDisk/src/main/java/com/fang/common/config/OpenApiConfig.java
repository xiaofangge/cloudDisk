package com.fang.common.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Swagger3配置类
 * @author 川川
 * @date 2022-02-16 13:23
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI myWebCloudDisk() {
        return new OpenAPI()
                .info(new Info()
                        .title("个人云网盘 API")
                        .description("浙江中医药大学-我的毕业设计")
                        .version("v1.0")
                        .license(new License().name("MIT").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("gitee地址")
                        .url("https://www.baidu.com"));
    }

}
