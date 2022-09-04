package com.fang.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author 川川
 * @date 2022-02-15 15:11
 */


/**
    * 普通用户表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
    * 用户Id
    */
    private Long userId;

    /**
    * 用户名
    */
    private String username;

    /**
    * 手机号
    */
    private String telephone;

    /**
    * 登录密码
    */
    private String password;

    /**
    * 盐
    */
    private String salt;

    /**
    * 用户头像地址
    */
    private String avatar;

    /**
    * 注册时间
    */
    private Date registerTime;

    private static final long serialVersionUID = 1L;
}