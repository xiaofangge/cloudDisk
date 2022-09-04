package com.fang.mapper;

import com.fang.pojo.FileClassify;

import java.util.List;

/**
 * 
 * @author 川川
 * @date 2022-02-15 15:00
 */
public interface FileClassifyMapper {
    /**
     * delete by primary key
     * @param classifyId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long classifyId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(FileClassify record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(FileClassify record);

    /**
     * select by primary key
     * @param classifyId primary key
     * @return object by primary key
     */
    FileClassify selectByPrimaryKey(Long classifyId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(FileClassify record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(FileClassify record);

    // 根据文件类型获取文件扩展名列表
    List<String> getFileExtends(int fileType);
}