package com.fang.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Fang Ruichuan
 * @date 2022-09-08 20:53
 */
@Getter
@AllArgsConstructor
public enum DeletedEnum {

    NON_DELETED(false, "未删除"),
    DELETED(true, "已删除");

    private Boolean deleteBool;
    private String deleteName;

}
