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
    * 文件类型表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileType implements Serializable {
    /**
    * 类型id
    */
    private Integer typeId;

    /**
    * 文件类型名
    */
    private String typeName;

    /**
    * 次序
    */
    private Integer orderNum;

    private static final long serialVersionUID = 1L;
}