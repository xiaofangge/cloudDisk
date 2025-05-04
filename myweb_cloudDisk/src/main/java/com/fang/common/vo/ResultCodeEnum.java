package com.fang.common.vo;

import lombok.Getter;

/**
 * 结果类枚举
 * @author 川川
 * @date 2022-02-15 15:20
 */
@Getter
public enum ResultCodeEnum {

    SUCESS(true, 200, "成功"),
    UNKNOWN_ERROR(false, 20001, "未知错误"),
    PARAM_ERROR(false, 20002, "参数错误"),
    NULL_POINT(false, 20003, "空指针异常"),
    INDEX_OUT_OF_BOUNDS(false, 20004, "数组下标越界");

    /**
     * 响应是否成功
     */
    private Boolean success;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    ResultCodeEnum(Boolean success, Integer code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }
}
