package com.fang.common.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 统一返回结果
 * @author 川川
 * @date 2022-02-15 15:28
 */
@Getter
@Setter
public class R<T> {
    private Boolean success;

    private Integer code;

    private String msg;

    private T data;

    /**
     * 通用返回成功
     * @author xinchao
     * @date 2022/2/15 15:31
     * @return R
     */
    public static R success() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.SUCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCESS.getCode());
        r.setMsg(ResultCodeEnum.SUCESS.getMsg());
        return r;
    }


    /**
     * 通用返回失败，未知错误
     * @author xinchao
     * @date 2022/2/15 15:34
     * @return R
     */
    public static R fail() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        r.setMsg(ResultCodeEnum.UNKNOWN_ERROR.getMsg());
        return r;
    }

    /**
     * 设置结果返回
     * @author xinchao
     * @date 2022/2/15 15:46
     * @param result 结果枚举
     * @return R
     */
    public static R setResult(ResultCodeEnum result) {
        R r = new R();
        r.setSuccess(result.getSuccess());
        r.setCode(result.getCode());
        r.setMsg(result.getMsg());
        return r;
    }

    /**
     * 自定义返回数据
     * @author xinchao
     * @date 2022/2/15 15:41
     * @param data 数据
     * @return R
     */
    public R data(T data) {
        this.setData(data);
        return this;
    }

    /**
     * 自定义返回消息
     * @author xinchao
     * @date 2022/2/15 15:43
     * @param msg 自定义消息
     * @return R
     */
    public R message(String msg) {
        this.setMsg(msg);
        return this;
    }

    /**
     * 自定义返回状态码
     * @author xinchao
     * @date 2022/2/15 15:44
     * @param code 状态码
     * @return R
     */
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

}
