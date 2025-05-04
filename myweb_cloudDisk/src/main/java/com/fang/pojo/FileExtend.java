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
    * 文件扩展名表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileExtend implements Serializable {
    /**
    * 扩展名Id
    */
    private Integer extendId;

    /**
    * 扩展名
    */
    private String extendName;

    /**
    * 扩展名描述
    */
    private String extendDesc;

    /**
    * 扩展名预览图
    */
    private String extendImgUrl;

    private static final long serialVersionUID = 1L;
}