package com.fang.service;

import com.fang.common.exception.file.UploadGeneralException;
import com.fang.file.dto.DownloadFileDto;
import com.fang.file.dto.UploadFileDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 川川
 * @date 2022-02-20 19:57
 */
public interface FileTransferService {

    void uploadFile(HttpServletRequest request, UploadFileDto uploadFileDto, Long userId) throws UploadGeneralException;

    void downloadFile(HttpServletResponse response, DownloadFileDto downloadFileDto) throws IOException;

    // 查询用户已经存储的大小
    Long selectStorageSizeByUserId(Long userId);

}
