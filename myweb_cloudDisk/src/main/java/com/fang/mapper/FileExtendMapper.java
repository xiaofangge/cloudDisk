package com.fang.mapper;

import com.fang.pojo.FileExtend;

/**
 * 
 * @author 川川
 * @date 2022-02-15 15:00
 */
public interface FileExtendMapper {
    /**
     * delete by primary key
     * @param extendId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer extendId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(FileExtend record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(FileExtend record);

    /**
     * select by primary key
     * @param extendId primary key
     * @return object by primary key
     */
    FileExtend selectByPrimaryKey(Integer extendId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(FileExtend record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(FileExtend record);
}