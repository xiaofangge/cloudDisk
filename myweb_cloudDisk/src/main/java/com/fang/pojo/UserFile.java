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
    * 用户文件表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFile implements Serializable {
    /**
    * 用户文件Id
    */
    private Long userFileId;

    /**
    * 用户Id
    */
    private Long userId;

    /**
    * 文件Id
    */
    private Long fileId;

    /**
    * 文件名称
    */
    private String fileName;

    /**
    * 文件路径
    */
    private String filePath;

    /**
    * 扩展名
    */
    private String extendName;

    /**
    * 是否是目录(0-不是,1-是)
    */
    private Boolean isDir;

    /**
    * 上传时间
    */
    private Date uploadTime;

    /**
    * 删除标识（0-未删除，1-已删除）
    */
    private Boolean deleteFlag;

    /**
    * 删除时间
    */
    private Date deleteTime;

    /**
    * 删除批次号
    */
    private String deleteNum;

    private static final long serialVersionUID = 1L;
}