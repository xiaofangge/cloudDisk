package com.fang.mapper;

import com.fang.common.vo.UserFileListVo;
import com.fang.pojo.UserFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @author 川川
 * @date 2022-02-15 15:00
 */


public interface UserFileMapper {
    /**
     * delete by primary key
     * @param userFileId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long userFileId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(UserFile record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(UserFile record);

    /**
     * select by primary key
     * @param userFileId primary key
     * @return object by primary key
     */
    UserFile selectByPrimaryKey(Long userFileId);

    /**
     * update record selective
     * @param record the updated record
     */
    void updateByPrimaryKeySelective(UserFile record);

    /**
     * update record
     * @param record the updated record
     */
    void updateByPrimaryKey(UserFile record);

    // 查询同一目录下是否存在同一名称文件
    Integer isDirExist(@Param("userId") Long userId, @Param("fileName") String fileName,
                       @Param("filePath") String filePath);


    // 文件列表查询
    List<UserFileListVo> userFileList(@Param("userFile") UserFile userFile, @Param("beginCount") Long beginCount,
                                      @Param("pageCount") Long pageCount);

    // 文件列表数量查询
    Long userFileListCount(@Param("filePath") String filePath, @Param("userId") Long userId);

    // 根据文件类型分页查询文件列表
    List<UserFileListVo> userFileListByType(@Param("userFile") UserFile userFile, @Param("fileType") int fileType,
                                            @Param("beginCount") long beginCount, @Param("pageCount") long pageCount);

    // 查询同一文件路径下的所有文件
    List<UserFile> selectFileListLikeFilePath(@Param("tokenUserId") Long tokenUserId,
                                              @Param("filePath") String filePath);

    // 移动文件路径
    void updateFilePathByFilePath(@Param("oldFilePath") String oldFilePath, @Param("newFilePath") String newFilePath,
                                  @Param("userId") Long userId);

    // 查询用户所有目录
    List<UserFile> selectFileTreeByUserId(Long userId);

    // 更新文件
    void updateFile(@Param("newFilePath") String newFilePath, @Param("oldFilePath") String oldFilePath,
                    @Param("fileName") String fileName, @Param("extendName") String extendName,
                    @Param("userId") Long userId);

    // 更新目录
    void updateDir(@Param("newFilePath") String newFilePath, @Param("oldFilePath") String oldFilePath,
                   @Param("fileName") String fileName, @Param("userId") Long userId);

    // 替换文件路径
    void replaceFilePath(@Param("newFilePath") String newFilePath, @Param("oldFilePath") String oldFilePath,
                         @Param("userId") Long userId);

    // 查询用户存储大小
    Long selectStorageSizeByUserId(Long userId);

    // 根据文件名和文件路径查询用户存储的文件集
    List<UserFile> selectUserFileByNameAndPath(@Param("fileName") String fileName, @Param("filePath") String filePath,
                                               @Param("userId") Long userId);
}