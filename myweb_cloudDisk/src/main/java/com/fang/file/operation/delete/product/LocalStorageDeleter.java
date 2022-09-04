package com.fang.file.operation.delete.product;

import com.fang.file.operation.delete.Deleter;
import com.fang.file.operation.pojo.DeleteFile;
import com.fang.file.util.FileUtil;
import com.fang.file.util.PathUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

/**
 * 本地文件删除实现类
 * @author 川川
 * @date 2022-02-20 19:18
 */
@Component
public class LocalStorageDeleter extends Deleter {

    @Resource(name = "fileUtil")
    private FileUtil fileUtil;

    @Override
    public void delete(DeleteFile deleteFile) {
        File file = new File(PathUtil.getStaticPath() + deleteFile.getFileUrl());
        if (file.exists()) {
            file.delete();
        }
        if (fileUtil.isImageFile(fileUtil.getFileExtendName(deleteFile.getFileUrl()))) {
            File minFile = new File(PathUtil.getStaticPath() + deleteFile.getFileUrl()
            .replace(deleteFile.getTimestampName(), deleteFile.getTimestampName() + "_min"));
            if (minFile.exists()) {
                minFile.delete();
            }
        }
    }
}
