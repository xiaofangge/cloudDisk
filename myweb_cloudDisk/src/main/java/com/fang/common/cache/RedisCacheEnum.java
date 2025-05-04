package com.fang.common.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Fang Ruichuan
 * @date 2022-09-04 16:28
 */
@Getter
@AllArgsConstructor
public enum RedisCacheEnum {

    FILE_TREE("文件数", "FILE_TREE", 60 * 60),
    USER_STORAGE_SIZE("用户存储大小", "USER_STORAGE_SIZE", 60 * 60),
    USER_STORAGE_INFO("用户总存储大小", "USER_STORAGE_INFO", 60 * 60);


    private String type;
    private String value;
    // 单位秒
    private long time;

}
