package com.fang.mapper;

import com.fang.pojo.User;

/**
 * 
 * @author 川川
 * @date 2022-02-15 15:11
 */
public interface UserMapper {
    /**
     * delete by primary key
     * @param userId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(User record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(User record);

    /**
     * select by primary key
     * @param userId primary key
     * @return object by primary key
     */
    User selectByPrimaryKey(Long userId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(User record);

    /**
     * 查询手机号是否已存在
     * @param telephone 手机号
     * @return Integer
     */
    Integer existsByPhone(String telephone);

    /**
     * 根据手机号查找用户
     * @param telephone 手机号
     * @return User
     */
    User getUserByPhone(String telephone);
}