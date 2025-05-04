package com.fang.service.impl;

import com.fang.common.enums.DeletedEnum;
import com.fang.common.enums.StatusEnum;
import com.fang.common.exception.file.UploadGeneralException;
import com.fang.file.dto.DownloadFileDto;
import com.fang.file.dto.UploadFileDto;
import com.fang.file.operation.FileOperationFactory;
import com.fang.file.operation.download.Downloader;
import com.fang.file.operation.pojo.DownloadFile;
import com.fang.file.operation.pojo.UploadFile;
import com.fang.file.operation.upload.Uploader;
import com.fang.file.util.PropertiesUtil;
import com.fang.mapper.FileMapper;
import com.fang.mapper.UserFileMapper;
import com.fang.pojo.File;
import com.fang.pojo.UserFile;
import com.fang.service.FileTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 川川
 * @date 2022-02-20 20:03
 */
@Service
@Slf4j
public class FileTransferServiceImpl implements FileTransferService {

    @Resource
    private FileMapper fileMapper;

    @Resource
    private UserFileMapper userFileMapper;

    @Resource(name = "localStorageOperationFactory")
    private FileOperationFactory localStorageOperationFactory;

    /**
     * 上传文件
     * @author xinchao
     * @date 2022/2/20 20:42
     * @param request           request
     * @param uploadFileDto     uploadFileDto
     * @param userId            userId
     */
    @Override
    public void uploadFile(HttpServletRequest request, UploadFileDto uploadFileDto, Long userId) throws UploadGeneralException {
        Date currentDate = new Date();

        Uploader uploader = null;
        UploadFile uploadFile = new UploadFile();
        uploadFile.setChunkNumber(uploadFileDto.getChunkNumber());
        uploadFile.setChunkSize(uploadFileDto.getChunkSize());
        uploadFile.setTotalChunks(uploadFileDto.getTotalChunks());
        uploadFile.setIdentifier(uploadFileDto.getIdentifier());
        uploadFile.setTotalSize(uploadFileDto.getTotalSize());
        uploadFile.setCurrentChunkSize(uploadFileDto.getCurrentChunkSize());
        String storageType = PropertiesUtil.getProperty("file.storage-type");

        synchronized (FileTransferService.class) {
            if ("0".equals(storageType)) {
                uploader = localStorageOperationFactory.getUploader();
            }
        }

        List<UploadFile> uploadFileList = Objects.requireNonNull(uploader).upload(request, uploadFile);
        for (UploadFile value : uploadFileList) {
            uploadFile = value;
            File file = new File();
            file.setIdentifier(uploadFileDto.getIdentifier());
            file.setStorageType(Integer.parseInt(storageType));
            file.setTimestampName(uploadFile.getTimestampName());

            if (uploadFile.getSuccess() == 1) {

                file.setFileSize(uploadFile.getFileSize());
                file.setFileUrl(uploadFile.getUrl());
                file.setPointCount(1);
                file.setFileStatus(StatusEnum.STATUS.getStatusBool());
                file.setCreateTime(currentDate);
                file.setCreateUserId(userId);
                fileMapper.insertSelective(file);

                UserFile userFile = new UserFile();
                userFile.setUserId(userId);
                userFile.setFileId(file.getFileId());
                userFile.setExtendName(uploadFile.getFileType());
                userFile.setFileName(uploadFile.getFileName());
                userFile.setFilePath(uploadFileDto.getFilePath());
                userFile.setIsDir(false);
                userFile.setUploadTime(currentDate);
                userFile.setDeleteFlag(DeletedEnum.NON_DELETED.getDeleteBool());
                userFileMapper.insertSelective(userFile);
            }
        }
    }

    /**
     * 下载文件
     * @author xinchao
     * @date 2022/2/20 20:44
     * @param response          response
     * @param downloadFileDto   文件下载dto
     */
    @Override
    public void downloadFile(HttpServletResponse response, DownloadFileDto downloadFileDto) throws IOException {
        UserFile userFile = userFileMapper.selectByPrimaryKey(downloadFileDto.getUserFileId());

        String fileName = userFile.getFileName() +
                "." +
                userFile.getExtendName();

        log.info("时间：{}, 下载文件：{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), fileName);

        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8));

        //设置Content-Type头(设置强制下载不打开)
        response.setContentType("application/force-download");
        // 设置文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);

        File file = fileMapper.selectByPrimaryKey(userFile.getFileId());

        Downloader downloader = null;
        if (file.getStorageType() == 0) {
            downloader = localStorageOperationFactory.getDownloader();
        }
        DownloadFile downloadFile = new DownloadFile();
        downloadFile.setFileUrl(file.getFileUrl());
        downloadFile.setTimestampName(file.getTimestampName());

        Objects.requireNonNull(downloader).download(response, downloadFile);
    }

    @Override
    public Long selectStorageSizeByUserId(Long userId) {
        return userFileMapper.selectStorageSizeByUserId(userId);
    }
}
