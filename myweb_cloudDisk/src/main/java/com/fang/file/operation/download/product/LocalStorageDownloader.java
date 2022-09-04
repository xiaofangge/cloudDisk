package com.fang.file.operation.download.product;

import com.fang.file.operation.download.Downloader;
import com.fang.file.operation.pojo.DownloadFile;
import com.fang.file.util.PathUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件下载实现类
 * @author 川川
 * @date 2022-02-20 19:04
 */
@Component
public class LocalStorageDownloader extends Downloader {

    @Override
    public void download(HttpServletResponse response, DownloadFile downloadFile) throws IOException {
        BufferedInputStream bis = null;
        byte[] buffer = new byte[1024];

        // 设置文件路径
        File file = new File(PathUtil.getStaticPath() + downloadFile.getFileUrl());
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    bis.close();
                }
            }
        }
    }

}
