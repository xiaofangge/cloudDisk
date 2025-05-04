package com.fang.mapper;

import com.fang.pojo.OperationLog;

/**
 * 
 * @author 川川
 * @date 2022-02-15 15:00
 */


public interface OperationLogMapper {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(OperationLog record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(OperationLog record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    OperationLog selectByPrimaryKey(Long id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OperationLog record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OperationLog record);
}