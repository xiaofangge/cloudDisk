package com.fang.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author 川川
 * @date 2022-02-15 15:00
 */


/**
    * 管理员表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {
    /**
    * 管理员id
    */
    private Integer id;

    /**
    * 管理员账号
    */
    private String admin;

    /**
    * 管理员密码
    */
    private String password;

    private static final long serialVersionUID = 1L;
}