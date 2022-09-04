package com.fang.file.operation;

import com.fang.file.operation.delete.Deleter;
import com.fang.file.operation.download.Downloader;
import com.fang.file.operation.upload.Uploader;

/**
 * 文件操作工厂
 * @author 川川
 * @date 2022-02-20 19:25
 */
public interface FileOperationFactory {

    Uploader getUploader();

    Downloader getDownloader();

    Deleter getDeleter();

}
