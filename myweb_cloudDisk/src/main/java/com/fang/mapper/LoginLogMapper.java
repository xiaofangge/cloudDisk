package com.fang.mapper;

import com.fang.pojo.LoginLog;

/**
 * 
 * @author 川川
 * @date 2022-02-15 15:00
 */


public interface LoginLogMapper {
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
    int insert(LoginLog record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(LoginLog record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    LoginLog selectByPrimaryKey(Long id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(LoginLog record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(LoginLog record);
}