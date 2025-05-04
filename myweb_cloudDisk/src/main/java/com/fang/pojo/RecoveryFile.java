package com.fang.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author 川川
 * @date 2022-02-21 13:52
 */


/**
    * 文件删除记录表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecoveryFile implements Serializable {
    /**
    * 文件删除记录id
    */
    private Long recoveryFileId;

    /**
    * 用户文件id
    */
    private Long userFileId;

    /**
    * 删除时间
    */
    private Date deleteTime;

    /**
    * 删除批次号
    */
    private String deleteBatchNum;

    private static final long serialVersionUID = 1L;

}