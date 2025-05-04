package com.fang.file.operation;

import com.fang.file.operation.delete.Deleter;
import com.fang.file.operation.delete.product.LocalStorageDeleter;
import com.fang.file.operation.download.Downloader;
import com.fang.file.operation.download.product.LocalStorageDownloader;
import com.fang.file.operation.upload.Uploader;
import com.fang.file.operation.upload.product.LocalStorageUploader;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 文件操作具体工厂
 * @author 川川
 * @date 2022-02-20 19:28
 */
@Component
public class LocalStorageOperationFactory implements FileOperationFactory {

    @Resource(name = "localStorageUploader")
    private LocalStorageUploader localStorageUploader;

    @Resource(name = "localStorageDownloader")
    private LocalStorageDownloader localStorageDownloader;

    @Resource(name = "localStorageDeleter")
    private LocalStorageDeleter localStorageDeleter;

    @Override
    public Uploader getUploader() {
        return localStorageUploader;
    }

    @Override
    public Downloader getDownloader() {
        return localStorageDownloader;
    }

    @Override
    public Deleter getDeleter() {
        return localStorageDeleter;
    }
}
