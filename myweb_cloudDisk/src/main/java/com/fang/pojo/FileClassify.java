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
    * 文件分类表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileClassify implements Serializable {
    /**
    * 文件分类id
    */
    private Long classifyId;

    /**
    * 文件类型id
    */
    private Integer typeId;

    /**
    * 文件扩展名
    */
    private String extendName;

    private static final long serialVersionUID = 1L;
}