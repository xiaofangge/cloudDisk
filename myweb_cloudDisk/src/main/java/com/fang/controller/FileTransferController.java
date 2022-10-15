package com.fang.controller;

import com.alibaba.fastjson.JSON;
import com.fang.common.cache.RedisCacheEnum;
import com.fang.common.enums.DeletedEnum;
import com.fang.common.exception.file.UploadGeneralException;
import com.fang.common.vo.R;
import com.fang.common.vo.StorageVo;
import com.fang.file.dto.DownloadFileDto;
import com.fang.file.dto.UploadFileDto;
import com.fang.file.util.FileUtil;
import com.fang.file.vo.UploadFileVo;
import com.fang.pojo.File;
import com.fang.pojo.Storage;
import com.fang.pojo.User;
import com.fang.pojo.UserFile;
import com.fang.service.*;
import com.fang.utils.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 川川
 * @date 2022-02-21 10:21
 */
@Slf4j
@RequestMapping("/filetransfer")
@RestController
@Tag(name = "filetransfer", description = "该接口为文件传输接口，主要用来做文件的上传和下载")
public class FileTransferController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "fileServiceImpl")
    private FileService fileService;

    @Resource(name = "userFileServiceImpl")
    private UserFileService userFileService;

    @Resource(name = "fileTransferServiceImpl")
    private FileTransferService fileTransferService;

    @Resource(name = "storageServiceImpl")
    private StorageService storageService;

    @Resource(name = "fileUtil")
    private FileUtil fileUtil;


    @GetMapping("/uploadfile")
    @Operation(summary = "极速上传", description = "校验文件MD5判断文件是否存在，如果存在直接上传成功并返回" +
            "skipUpload=true，如果不存在返回skipUpload=false，需要再次调用该接口的POST方法",
            tags = {"filetransfer"})
    public R uploadFileSpeed(@RequestHeader("token") String token, UploadFileDto uploadFileDto) {
        User userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            return R.fail().message("token未认证");
        }

        UploadFileVo uploadFileVo = new UploadFileVo();

        log.info("uploadFileDto: {}", uploadFileDto);


        synchronized (FileTransferController.class) {
            List<File> list = fileService.listByIdentifer(uploadFileDto.getIdentifier());
            if (list != null && !list.isEmpty()) {
                File file = list.get(0);
                UserFile userFile = new UserFile();
                userFile.setFileId(file.getFileId());
                userFile.setUserId(userByToken.getUserId());
                userFile.setFilePath(uploadFileDto.getFilePath());

                String fileName = uploadFileDto.getFileName();
                userFile.setFileName(fileUtil.getFileNameNotExtend(fileName));
                userFile.setExtendName(fileUtil.getFileExtendName(fileName));
                userFile.setIsDir(false);
                userFile.setUploadTime(new Date());
                userFile.setDeleteFlag(DeletedEnum.NON_DELETED.getDeleteBool());

                userFileService.save(userFile);

                uploadFileVo.setSkipUpload(true);
            } else {
                uploadFileVo.setSkipUpload(false);
            }
        }
        return R.success().data(uploadFileVo);
    }

    @Operation(summary = "文件切片上传", description = "真正的文件上传接口", tags = {"filetransfer"})
    @PostMapping("/uploadfile")
    public R uploadFile(@RequestHeader("token") String token, HttpServletRequest request,
                        UploadFileDto uploadFileDto) throws UploadGeneralException {
        User userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            return R.fail().message("token未认证");
        }
        log.info("uploadFileDto: {}", uploadFileDto);
        fileTransferService.uploadFile(request, uploadFileDto, userByToken.getUserId());
        UploadFileVo uploadFileVo = new UploadFileVo();
        return R.success().data(uploadFileVo);
    }

    @Operation(summary = "文件下载", description = "文件下载接口", tags = {"filetransfer"})
    @GetMapping("/downloadfile")
    public void downloadFile(HttpServletResponse response,
                          DownloadFileDto downloadFileDto) throws IOException {
        fileTransferService.downloadFile(response, downloadFileDto);
    }


    @Operation(summary = "获取存储信息", description = "获取存储信息", tags = {"filetransfer"})
    @GetMapping("/getstorage")
    public R getStorage(@RequestHeader("token") String token) {
        User userByToken = userService.getUserByToken(token);
        if (userByToken == null) {
            return R.fail().message("token未认证");
        }
        String storageSize;
        if (RedisUtil.KeyOps.hasKey(RedisCacheEnum.USER_STORAGE_SIZE.getValue() + ":" + userByToken.getUserId())) {
            storageSize = RedisUtil.StringOps.get(RedisCacheEnum.USER_STORAGE_SIZE.getValue() + ":" + userByToken.getUserId());
        } else {
            storageSize = String.valueOf(fileTransferService.selectStorageSizeByUserId(userByToken.getUserId()));
            RedisUtil.StringOps.setEx(RedisCacheEnum.USER_STORAGE_SIZE.getValue() + ":" + userByToken.getUserId(),
                    storageSize, RedisCacheEnum.USER_STORAGE_SIZE.getTime(), TimeUnit.SECONDS);
        }


        Storage storageInfo = null;
        if (RedisUtil.KeyOps.hasKey(RedisCacheEnum.USER_STORAGE_INFO.getValue() + ":" + userByToken.getUserId())) {
            String storageInfoString = RedisUtil.StringOps.get(RedisCacheEnum.USER_STORAGE_INFO.getValue() + ":" + userByToken.getUserId());
            storageInfo = JSON.parseObject(storageInfoString, Storage.class);
        } else {
            storageInfo = storageService.getStorageInfo(userByToken.getUserId());
            RedisUtil.StringOps.setEx(RedisCacheEnum.USER_STORAGE_INFO.getValue() + ":" + userByToken.getUserId(),
                    JSON.toJSONString(storageInfo), RedisCacheEnum.USER_STORAGE_INFO.getTime(), TimeUnit.SECONDS);
        }

        StorageVo storageVo = new StorageVo();
        storageVo.setUserId(String.valueOf(userByToken.getUserId()));
        storageVo.setStorageSize(storageSize == null ? String.valueOf(0L) : storageSize);
        storageVo.setTotalStorageSize(String.valueOf(Objects.requireNonNull(storageInfo).getTotalSize()));
        return R.success().data(storageVo);
    }

}
