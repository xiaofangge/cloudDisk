package com.fang.mapper;

import com.fang.pojo.Storage;

/**
 * 
 * @author 川川
 * @date 2022-02-15 15:00
 */


public interface StorageMapper {
    /**
     * delete by primary key
     * @param storageId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long storageId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(Storage record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(Storage record);

    /**
     * select by primary key
     * @param storageId primary key
     * @return object by primary key
     */
    Storage selectByPrimaryKey(Long storageId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Storage record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Storage record);

    // 根据用户id获取用户存储信息
    Storage getStorageInfo(Long userId);
}