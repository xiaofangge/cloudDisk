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
    * 登录日志表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginLog implements Serializable {
    /**
    * 登录日志id
    */
    private Long id;

    /**
    * 登录用户id
    */
    private Long userId;

    /**
    * 最后登录时间
    */
    private Date loginTime;

    /**
    * 最后登出时间
    */
    private Date logoutTime;

    /**
    * 登录ip
    */
    private String loginIp;

    /**
    * 登录地址
    */
    private String loginLocation;

    /**
    * 浏览器类型
    */
    private String browser;

    /**
    * 操作系统
    */
    private String os;

    /**
    * 登录状态（0-成功,1-失败）
    */
    private Boolean status;

    private static final long serialVersionUID = 1L;
}