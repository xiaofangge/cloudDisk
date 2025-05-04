package com.fang.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author 川川
 * @date 2022-02-15 15:00
 */


/**
    * 操作日志表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationLog implements Serializable {
    /**
    * 操作日志id
    */
    private Long id;

    /**
    * 操作用户id
    */
    private Long userId;

    /**
    * 消息
    */
    private String message;

    /**
    * 日志类型
    */
    private String operation;

    /**
    * 请求方法
    */
    private String method;

    /**
    * 请求参数
    */
    private String params;

    /**
    * 请求ip
    */
    private String ip;

    /**
    * 请求地址
    */
    private String location;

    /**
    * 请求路由
    */
    private String requestUrl;

    /**
    * 请求时间
    */
    private Date requestTime;

    /**
    * 请求方式
    */
    private String requestWay;

    /**
    * 操作状态（0-成功，1-失败）
    */
    private Boolean requestStatus;

    private static final long serialVersionUID = 1L;
}