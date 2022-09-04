package com.fang.mapper;

import com.fang.pojo.FileType;

/**
 * 
 * @author 川川
 * @date 2022-02-15 15:00
 */


public interface FileTypeMapper {
    /**
     * delete by primary key
     * @param typeId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer typeId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(FileType record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(FileType record);

    /**
     * select by primary key
     * @param typeId primary key
     * @return object by primary key
     */
    FileType selectByPrimaryKey(Integer typeId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(FileType record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(FileType record);
}