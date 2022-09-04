package com.fang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fang.mapper")
public class MywebCloudDiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(MywebCloudDiskApplication.class, args);
    }

}
