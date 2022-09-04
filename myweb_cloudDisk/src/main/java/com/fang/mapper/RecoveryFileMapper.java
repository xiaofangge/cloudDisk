package com.fang.mapper;

import com.fang.pojo.RecoveryFile;

/**
 * 
 * @author 川川
 * @date 2022-02-21 13:52
 */


public interface RecoveryFileMapper {
    /**
     * delete by primary key
     * @param recoveryFileId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long recoveryFileId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(RecoveryFile record);

    /**
     * insert record to table selective
     * @param record the record
     */
    void insertSelective(RecoveryFile record);

    /**
     * select by primary key
     * @param recoveryFileId primary key
     * @return object by primary key
     */
    RecoveryFile selectByPrimaryKey(Long recoveryFileId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(RecoveryFile record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(RecoveryFile record);
}