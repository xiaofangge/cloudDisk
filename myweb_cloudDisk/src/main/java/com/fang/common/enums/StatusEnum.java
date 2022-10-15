package com.fang.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Fang Ruichuan
 * @date 2022-09-08 20:53
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    NON_STATUS(false, "失效"),
    STATUS(true, "生效");

    private Boolean statusBool;
    private String statusName;

}
