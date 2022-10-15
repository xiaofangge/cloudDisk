package com.fang.service.impl;

import com.fang.common.enums.DeletedEnum;
import com.fang.common.vo.UserFileListVo;
import com.fang.mapper.RecoveryFileMapper;
import com.fang.mapper.UserFileMapper;
import com.fang.pojo.RecoveryFile;
import com.fang.pojo.UserFile;
import com.fang.service.UserFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author 川川
 * @date 2022-02-18 16:01
 */
@Service
@Slf4j
public class UserFileServiceImpl implements UserFileService {


    private static Executor executor = Executors.newFixedThreadPool(20);

    @Resource(name = "userFileMapper")
    private UserFileMapper userFileMapper;

    @Resource(name = "recoveryFileMapper")
    private RecoveryFileMapper recoveryFileMapper;

    @Override
    public void updateByPrimaryKeySelective(UserFile record) {
        userFileMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public UserFile selectByPrimaryKey(Long userFileId) {
        return userFileMapper.selectByPrimaryKey(userFileId);
    }

    /**
     * 判断同一目录下文件名是否存在
     * @author xinchao
     * @date 2022/2/18 16:36
     * @param userId    用户id
     * @param fileName  文件名称
     * @param filePath  文件路径
     * @return boolean
     */
    @Override
    public boolean isDirExist(Long userId, String fileName, String filePath) {
        return userFileMapper.isDirExist(userId, fileName, filePath) != null;
    }

    /**
     * 创建用户文件
     * @author xinchao
     * @date 2022/2/18 16:51
     * @param userFile 用户文件
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(UserFile userFile) {
        userFileMapper.insertSelective(userFile);
    }


    /**
     * 根据文件路径查询文件列表
     * @author xinchao
     * @date 2022/2/18 19:06
     * @param filePath      文件路径
     * @param userId        用户id
     * @param currentPage   当前页
     * @param pageCount     每页显示条数
     * @return List<UserFileListVo>
     */
    @Override
    public List<UserFileListVo> userFileByFilePath(String filePath, Long userId, Long currentPage, Long pageCount) {
        Long beginCount = (currentPage - 1) * pageCount;
        UserFile userFile = new UserFile();
        userFile.setUserId(userId);
        userFile.setFilePath(filePath);

        return userFileMapper.userFileList(userFile, beginCount, pageCount);
    }

    @Override
    public Long userFileCountByFilePath(String filePath, Long userId) {
        return userFileMapper.userFileListCount(filePath, userId);
    }

    /**
     * 通过类型获取文件列表
     * @author xinchao
     * @date 2022/2/19 20:52
     * @param userId        用户id
     * @param fileType      文件类型
     * @param currentPage   当前页
     * @param pageCount     页面数量
     * @return Map<Object>
     */
    @Override
    public Map<String, Object> getUserFileByType(Long userId, int fileType, long currentPage, long pageCount) {
        long beginCount = (currentPage - 1) * pageCount;
        UserFile userFile = new UserFile();
        userFile.setUserId(userId);
        List<UserFileListVo> fileList = userFileMapper.userFileListByType(userFile, fileType, beginCount, pageCount);

        Map<String, Object> map = new HashMap<>();
        map.put("list", fileList);
        map.put("total", fileList.size());
        return map;
    }

    /**
     * 删除用户文件
     * @author xinchao
     * @date 2022/2/21 14:04
     * @param userFileId    用户文件id
     * @param tokenUserId   token用户id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserFile(Long userFileId, Long tokenUserId) {
        Date currentDateTime = new Date();

        UserFile userFileDB = userFileMapper.selectByPrimaryKey(userFileId);
        String uuid = UUID.randomUUID().toString();

        if (userFileDB.getIsDir()) { // 如果是目录
            userFileDB.setDeleteFlag(DeletedEnum.DELETED.getDeleteBool());
            userFileDB.setDeleteTime(currentDateTime);
            userFileDB.setDeleteNum(uuid);
            userFileMapper.updateByPrimaryKeySelective(userFileDB);

            String filePath = userFileDB.getFilePath() + userFileDB.getFileName() + "/";
            updateFileDeleteStateByFilePath(tokenUserId, currentDateTime, filePath, uuid);
        } else { // 如果是文件
            userFileDB.setDeleteFlag(DeletedEnum.DELETED.getDeleteBool());
            userFileDB.setDeleteTime(currentDateTime);
            userFileDB.setDeleteNum(uuid);
            userFileMapper.updateByPrimaryKeySelective(userFileDB);
        }
        // 插入文件删除记录
        RecoveryFile recoveryFile = new RecoveryFile();
        recoveryFile.setUserFileId(userFileId);
        recoveryFile.setDeleteTime(currentDateTime);
        recoveryFile.setDeleteBatchNum(uuid);
        recoveryFileMapper.insertSelective(recoveryFile);
    }

    /**
     * 删除在该文件路径下的所有文件
     * @author xinchao
     * @date 2022/2/21 14:30
     * @param tokenUserId       token用户id
     * @param currentDateTime   删除时间
     * @param filePath          文件路径
     * @param uuid              删除批次号
     */
    private void updateFileDeleteStateByFilePath(Long tokenUserId, Date currentDateTime, String filePath, String uuid) {
        new Thread(() -> {
            List<UserFile> userFiles = selectFileTreeListLikeFilePath(tokenUserId, filePath);
            for (UserFile userFile : userFiles) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        userFile.setDeleteFlag(DeletedEnum.DELETED.getDeleteBool());
                        userFile.setDeleteTime(currentDateTime);
                        userFile.setDeleteNum(uuid);
                        userFileMapper.updateByPrimaryKey(userFile);

                        RecoveryFile recoveryFile = new RecoveryFile();
                        recoveryFile.setUserFileId(userFile.getUserFileId());
                        recoveryFile.setDeleteTime(currentDateTime);
                        recoveryFile.setDeleteBatchNum(uuid);
                        recoveryFileMapper.insertSelective(recoveryFile);
                    }
                });
            }
        }).start();
    }

    /**
     * 查找出同一目录下的所有文件集合
     * @author xinchao
     * @date 2022/2/21 14:05
     * @param tokenUserId       token用户id
     * @param filePath          文件路径
     * @return List<UserFile>
     */
    @Override
    public List<UserFile> selectFileTreeListLikeFilePath(Long tokenUserId, String filePath) {

        log.info("查询文件路径：{}", filePath);

        return userFileMapper.selectFileListLikeFilePath(tokenUserId, filePath);
    }

    /**
     * 查询用户所有目录
     * @author xinchao
     * @date 2022/2/24 10:04
     * @param userId    用户id
     * @return List<UserFile>
     */
    @Override
    public List<UserFile> selectFilePathTreeByUserId(Long userId) {
        return userFileMapper.selectFileTreeByUserId(userId);
    }

    /**
     * 文件移动
     * @author xinchao
     * @date 2022/2/24 10:08
     * @param oldFilePath   旧文件路径
     * @param newFilePath   新文件路径
     * @param fileName      文件名称
     * @param extendName    扩展名
     * @param userId        用户id
     */
    @Override
    public void updateFilePathByFilePath(String oldFilePath, String newFilePath, String fileName, String extendName, Long userId) {
        if ("null".equals(extendName)) {
            extendName = null;
        }
        if (StringUtils.isNotEmpty(extendName)) {
            userFileMapper.updateFile(newFilePath, oldFilePath, fileName, extendName,  userId);
        } else {
            userFileMapper.updateDir(newFilePath, oldFilePath, fileName, userId);
        }

        // 移动子目录
        oldFilePath = oldFilePath + fileName + "/";
        newFilePath = newFilePath + fileName + "/";

        oldFilePath = oldFilePath.replace("\\", "\\\\\\\\");
        oldFilePath = oldFilePath.replace("'", "\\'");
        oldFilePath = oldFilePath.replace("%", "\\%");
        oldFilePath = oldFilePath.replace("_", "_%");

        if (extendName == null) {
            // 为null则说明是目录，则需要移动子目录
            userFileMapper.updateFilePathByFilePath(oldFilePath, newFilePath, userId);
        }
    }

    /**
     * 根据文件名和文件路径查询用户存储的文件
     * @author xinchao
     * @date 2022/2/24 14:00
     * @param fileName          文件名
     * @param filePath          文件路径
     * @param userId            用户id
     * @return List<UserFile>
     */
    @Override
    public List<UserFile> selectUserFileByNameAndPath(String fileName, String filePath, Long userId) {
        return userFileMapper.selectUserFileByNameAndPath(fileName, filePath, userId);
    }

    /**
     * 替换文件路径
     * @author xinchao
     * @date 2022/2/24 14:01
     * @param newFilePath       新文件路径
     * @param oldFilePath       旧文件路径
     * @param userId            用户id
     */
    @Override
    public void replaceUserFilePath(String newFilePath, String oldFilePath, Long userId) {
        userFileMapper.replaceFilePath(newFilePath, oldFilePath, userId);
    }
}
