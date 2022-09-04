package com.fang.controller;

import com.alibaba.fastjson.JSON;
import com.fang.common.dto.CreateFileDto;
import com.fang.common.dto.UserFileListDto;
import com.fang.common.vo.R;
import com.fang.common.vo.UserFileListVo;
import com.fang.file.dto.*;
import com.fang.file.vo.TreeNodeVo;
import com.fang.pojo.User;
import com.fang.pojo.UserFile;
import com.fang.service.UserFileService;
import com.fang.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 川川
 * @date 2022-02-18 16:11
 */
@RestController
@Slf4j
@Tag(name = "file", description = "该接口为文件接口，主要用来做一些文件的基本操作，如创建目录，删除，移动，复制等")
@RequestMapping("/file")
public class FileController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "userFileServiceImpl")
    private UserFileService userFileService;

    @Operation(summary = "创建文件/文件夹", description = "目录（文件夹）的创建", tags = {"file"})
    @PostMapping("/createfile")
    public R createFile(@RequestHeader("token") String token,
                        @RequestBody CreateFileDto createFileDto) {
        User userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            return R.fail().message("token认证失败");
        }

        boolean isDirExist = userFileService.isDirExist(userByToken.getUserId(), createFileDto.getFileName(), createFileDto.getFilePath());
        if (isDirExist) {
            return R.fail().message("同目录下文件名重复");
        }

        UserFile userFile = new UserFile();
        userFile.setUserId(userByToken.getUserId());
        userFile.setFileName(createFileDto.getFileName());
        userFile.setFilePath(createFileDto.getFilePath());
        userFile.setIsDir(createFileDto.getIsDir());
        userFile.setDeleteFlag(false);
        userFile.setUploadTime(new Date());

        userFileService.save(userFile);
        return R.success().message("文件夹创建成功");
    }

    @Operation(summary = "获取文件列表", description = "用来做前台文件列表展示", tags = {"file"})
    @GetMapping("/getlist")
    public R userFileList(@RequestHeader("token") String token,
                          UserFileListDto userFileListDto) {
        User userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            return R.fail().message("token认证失败");
        }
        List<UserFileListVo> userFileList = userFileService.userFileByFilePath(userFileListDto.getFilePath(), userByToken.getUserId(), userFileListDto.getCurrentPage(), userFileListDto.getPageCount());
        Long total = userFileService.userFileCountByFilePath(userFileListDto.getFilePath(), userByToken.getUserId());

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", userFileList);
        return R.success().data(map);
    }

    @Operation(summary = "通过文件类型选择文件", description = "实现文件格式分类查看", tags = {"file"})
    @GetMapping("/selectfilebyfiletype")
    public R selectFilesByFileType(@RequestHeader("token") String token,
                                   @Parameter(description = "文件类型", required = true) @RequestParam("fileType") int fileType,
                                   @Parameter(description = "当前页", required = true) @RequestParam(defaultValue = "1") long currentPage,
                                   @Parameter(description = "页面数量", required = true) @RequestParam(defaultValue = "10") long pageCount) {
        User userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            return R.fail().message("token认证失败");
        }

        Long userId = userByToken.getUserId();

        Map<String, Object> map = userFileService.getUserFileByType(userId, fileType, currentPage, pageCount);
        return R.success().data(map);
    }

    @Operation(summary = "删除单个文件", description = "可以删除文件或目录", tags = {"file"})
    @PostMapping("/deletefile")
    public R deleteFile(@RequestHeader("token") String token, @RequestBody DeleteFileDto deleteFileDto) {
        User userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            return R.fail().message("token认证失败");
        }

        userFileService.deleteUserFile(deleteFileDto.getUserFileId(), userByToken.getUserId());
        return R.success().message("删除成功");
    }

    @Operation(summary = "批量删除文件", description = "批量删除文件或目录", tags = {"file"})
    @PostMapping("/batchdeletefile")
    public R deleteBatchFile(@RequestHeader("token") String token, @RequestBody BatchDeleteFileDto batchDeleteFileDto) throws JsonProcessingException {
        User userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            return R.fail().message("token认证失败");
        }
        List<UserFile> userFiles = JSON.parseArray(batchDeleteFileDto.getFiles(), UserFile.class);
        for (UserFile userFile : userFiles) {
            userFileService.deleteUserFile(userFile.getUserFileId(), userByToken.getUserId());
        }
        return R.success().message("批量删除文件成功");
    }


    @Operation(summary = "获取文件树", description = "文件移动的时候需要用到该接口，用来展示目录树", tags = {"file"})
    @GetMapping("/getfiletree")
    public R getFileTree(@RequestHeader("token") String token) {
        R<TreeNodeVo> result = new R<>();
        UserFile userFile = new UserFile();

        User userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            return R.fail().message("token认证失败");
        }
        userFile.setUserId(userByToken.getUserId());
        List<UserFile> filePathList = userFileService.selectFilePathTreeByUserId(userByToken.getUserId());

        TreeNodeVo resultTreeNode = new TreeNodeVo();
        resultTreeNode.setLabel("/");
        for (UserFile file : filePathList) {
            String filePath = file.getFilePath() + file.getFileName() + "/";

            Queue<String> queue = new LinkedList<>();
            String[] strArr = filePath.split("/");
            for (int i = 0; i < strArr.length; i++) {
                if (!StringUtils.equals("", strArr[i]) && StringUtils.isNotEmpty(strArr[i])) {
                    queue.add(strArr[i]);
                }
            }
            if (queue.isEmpty()) {
                continue;
            }
            resultTreeNode = insertTreeNode(resultTreeNode, "/", queue);
        }
        result.setSuccess(true);
        result.setData(resultTreeNode);
        return result;
    }

    @Operation(summary = "文件移动", description = "可以移动文件或目录，移动文件接口的本质其实就是" +
            "将保存到数据库中的虚拟路径做一个修改即可，真实的保存在磁盘上的文件是不需要做任何变动的", tags = {"file"})
    @PostMapping("/movefile")
    public R moveFile(@RequestHeader("token") String token,
                      @RequestBody MoveFileDto moveFileDto) {
        User userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            return R.fail().message("token认证失败");
        }
        String oldFilePath = moveFileDto.getOldFilePath();
        String newFilePath = moveFileDto.getFilePath();
        String fileName = moveFileDto.getFileName();
        String extendName = moveFileDto.getExtendName();

        userFileService.updateFilePathByFilePath(oldFilePath, newFilePath, fileName, extendName, userByToken.getUserId());
        return R.success().message("移动文件成功");
    }


    @Operation(summary = "批量移动文件", description = "可以同时选择移动多个文件或目录", tags = {"file"})
    @PostMapping("/batchmovefile")
    public R batchMoveFile(@RequestHeader("token") String token,
                           @RequestBody BatchMoveFileDto batchMoveFileDto) throws JsonProcessingException {
        User userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            return R.fail().message("token认证失败");
        }

        String newFilePath = batchMoveFileDto.getFilePath();

        List<UserFile> userFiles = JSON.parseArray(batchMoveFileDto.getFiles(), UserFile.class);
        for (UserFile userFile : userFiles) {
            userFileService.updateFilePathByFilePath(userFile.getFilePath(), newFilePath, userFile.getFileName(),
                    userFile.getExtendName(), userByToken.getUserId());
        }
        return R.success().message("批量移动文件成功");
    }


    @Operation(summary = "文件重命名", description = "文件重命名", tags = {"file"})
    @PostMapping("/renamefile")
    public R renameFile(@RequestHeader("token") String token,
                        @RequestBody RenameFileDto renameFileDto) {
        User userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            return R.fail().message("token认证失败");
        }
        UserFile userFile = userFileService.selectByPrimaryKey(renameFileDto.getUserFileId());

        List<UserFile> userFileList = userFileService.selectUserFileByNameAndPath(renameFileDto.getFileName(), userFile.getFilePath(), userByToken.getUserId());
        if (userFileList != null && !userFileList.isEmpty()) {
            return R.fail().message("同名文件已存在");
        }

        UserFile tempFile = new UserFile();
        tempFile.setUserFileId(renameFileDto.getUserFileId());
        tempFile.setFileName(renameFileDto.getFileName());
        tempFile.setUploadTime(new Date());
        userFileService.updateByPrimaryKeySelective(tempFile);

        if (userFile.getIsDir()) { // 如果是目录
            userFileService.replaceUserFilePath(userFile.getFilePath() + renameFileDto.getFileName() + "/",
                    userFile.getFilePath() + userFile.getFileName() + "/",
                    userByToken.getUserId());
        }

        return R.success().message("文件重命名成功");
    }



    /**
     * 将查询出来的文件路径组装成树形结构
     * @author xinchao
     * @date 2022/2/24 10:53
     * @param treeNode    树节点
     * @param filePath    根路径
     * @param queue       队列
     * @return TreeNodeVo
     */
    private TreeNodeVo insertTreeNode(TreeNodeVo treeNode, String filePath, Queue<String> queue) {
        List<TreeNodeVo> childrenTreeNodes = treeNode.getChildren();
        String currentNodeName = queue.peek();
        if (currentNodeName == null) {
            return treeNode;
        }
        Map<String, String> map = new HashMap<>();
        filePath = filePath + currentNodeName + "/";
        map.put("filePath", filePath);

        if (!isExistPath(childrenTreeNodes, currentNodeName)) {
            // 判断有没有该子节点，如果没有则插入
            TreeNodeVo resultTreeNode = new TreeNodeVo();
            resultTreeNode.setAttributes(map);
            resultTreeNode.setLabel(queue.poll());
            childrenTreeNodes.add(resultTreeNode);
        } else {
            // 如果有，则跳过
            queue.poll();
        }

        if (queue.size() != 0) {
            for (int i = 0; i < childrenTreeNodes.size(); i++) {
                TreeNodeVo childTreeNode = childrenTreeNodes.get(i);
                if (StringUtils.equals(currentNodeName, childTreeNode.getLabel())) {
                    childTreeNode = insertTreeNode(childTreeNode, filePath, queue);
                    childrenTreeNodes.remove(i);
                    childrenTreeNodes.add(childTreeNode);
                    treeNode.setChildren(childrenTreeNodes);
                }
            }
        } else {
            treeNode.setChildren(childrenTreeNodes);
        }
        return treeNode;
    }

    /**
     * 判断有没有该子节点
     * @author xinchao
     * @date 2022/2/24 11:15
     * @param childrenTreeNodes     树节点
     * @param currentNodeName       当前节点名称
     * @return boolean
     */
    private boolean isExistPath(List<TreeNodeVo> childrenTreeNodes, String currentNodeName) {
        boolean isExist = false;
        try {
            for (TreeNodeVo childrenTreeNode : childrenTreeNodes) {
                if (StringUtils.equals(currentNodeName, childrenTreeNode.getLabel())) {
                    isExist = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExist;
    }

}
