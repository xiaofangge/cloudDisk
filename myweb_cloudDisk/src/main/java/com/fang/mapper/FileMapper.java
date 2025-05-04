package com.fang.mapper;

import com.fang.pojo.File;

import java.util.List;

/**
 * 
 * @author 川川
 * @date 2022-02-15 15:00
 */
public interface FileMapper {
    /**
     * delete by primary key
     * @param fileId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long fileId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(File record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(File record);

    /**
     * select by primary key
     * @param fileId primary key
     * @return object by primary key
     */
    File selectByPrimaryKey(Long fileId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(File record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(File record);

    // 根据md5查询文件列表
    List<File> listByIdentfer(String identifier);
}