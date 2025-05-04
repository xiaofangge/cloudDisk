package com.fang.service;

import com.fang.common.vo.UserFileListVo;
import com.fang.pojo.UserFile;

import java.util.List;
import java.util.Map;

/**
 * @author 川川
 * @date 2022-02-18 16:00
 */
public interface UserFileService {

    // 根据主键修改选择用户文件信息
    void updateByPrimaryKeySelective(UserFile record);

    // 根据主键获取文件信息
    UserFile selectByPrimaryKey(Long userFileId);

    // 判断同目录下文件名是否重复
    boolean isDirExist(Long userId, String fileName, String filePath);

    // 创建文件夹
    void save(UserFile userFile);

    // 根据文件路径查询文件列表
    List<UserFileListVo> userFileByFilePath(String filePath, Long userId, Long currentPage, Long pageCount);

    // 根据文件路径查询用户文件列表数量
    Long userFileCountByFilePath(String filePath, Long userId);

    // 通过类型获取用户文件列表
    Map<String, Object> getUserFileByType(Long userId, int fileType, long currentPage, long pageCount);

    // 删除用户文件
    void deleteUserFile(Long userFileId, Long tokenUserId);

    // 查找出同一目录下的所有用户文件集合
    List<UserFile> selectFileTreeListLikeFilePath(Long tokenUserId, String filePath);

    // 查找用户文件树
    List<UserFile> selectFilePathTreeByUserId(Long userId);

    // 文件移动
    void updateFilePathByFilePath(String oldFilePath, String newFilePath, String fileName, String extendName, Long userId);

    // 根据文件名和文件路径查询每个用户存储的文件
    List<UserFile> selectUserFileByNameAndPath(String fileName, String filePath, Long userId);

    // 替换文件路径
    void replaceUserFilePath(String newFilePath, String oldFilePath, Long userId);
}
