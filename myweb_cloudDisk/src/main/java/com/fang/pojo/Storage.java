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
    * 存储信息表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storage implements Serializable {
    /**
    * 存储id
    */
    private Long storageId;

    /**
    * 用户id
    */
    private Long userId;


    /**
    * 总存储大小
    */
    private Long totalSize;

    /**
    * 修改时间
    */
    private Date modifyTime;

    /**
    * 修改用户id
    */
    private Long modifyUserId;

    private static final long serialVersionUID = 1L;
}