package com.fang.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
    * 文件表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File implements Serializable {
    /**
    * 文件Id
    */
    private Long fileId;

    /**
    * 文件大小
    */
    private Long fileSize;

    /**
    * 文件url
    */
    private String fileUrl;

    /**
    * 时间戳
    */
    private String timestampName;

    /**
    * md5唯一标识
    */
    private String identifier;

    /**
     * 引用数量
     */
    private Integer pointCount;

    /**
    * 文件状态(0-失效，1-生效)
    */
    private Boolean fileStatus;

    /**
    * 存储类型(0-本地物理磁盘存储，1-阿里云OOS存储)
    */
    private Integer storageType;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 创建用户id
    */
    private Long createUserId;

    private static final long serialVersionUID = 1L;
}