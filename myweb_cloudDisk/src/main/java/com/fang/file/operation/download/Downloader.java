package com.fang.file.operation.download;

import com.fang.file.operation.pojo.DownloadFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件下载抽象类
 * @author 川川
 * @date 2022-02-20 19:02
 */
public abstract class Downloader {

    public abstract void download(HttpServletResponse response, DownloadFile downloadFile) throws IOException;

}
