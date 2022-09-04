package com.fang.file.operation.upload;

import com.fang.common.exception.file.UploadGeneralException;
import com.fang.file.operation.pojo.UploadFile;
import com.fang.file.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 前端上传文件时如果文件很大，上传时会出现各种问题，比如连接超时了，网断了，都会导致上传失败。
 * 为了避免上传大文件时上传超时，就需要用到切片上传，工作原理是：
 * 我们将大文件切割为小文件，然后将切割的若干小文件上传到服务器端，
 * 服务器端接收到被切割的小文件，然后按照一定的顺序将小文件拼接合并成一个大文件。
 * @author 川川
 * @date 2022-02-20 14:36
 */
@Slf4j
public abstract class Uploader {
    public static final String ROOT_PATH = "upload";
    public static final String FILE_SEPARATOR = "/";
    // 文件大小限制，单位KB
    public final int maxSize = 10000000;

    public abstract List<UploadFile> upload(HttpServletRequest request, UploadFile uploadFile) throws UploadGeneralException;


    /**
     * 根据字符串创建本地目录，并按照日期建立子目录返回
     * @author xinchao
     * @date 2022/2/20 14:49
     * @return String
     */
    protected String getSaveFilePath() {
        String path = ROOT_PATH;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        path = FILE_SEPARATOR + path + FILE_SEPARATOR + sdf.format(new Date());
        String staticPath = PathUtil.getStaticPath();

        File dir = new File(staticPath + path);
        if (!dir.exists()) {
            try {
                boolean isSuccessMkdir = dir.mkdirs();
                if (!isSuccessMkdir) {
                    log.error("目录创建失败：{}", PathUtil.getStaticPath() + path);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("目录创建失败：{}", PathUtil.getStaticPath() + path);
                return "";
            }
        }
        return path;
    }

    /**
     * 依据原始文件名生成新文件名
     * @author xinchao
     * @date 2022/2/20 15:04
     * @return String
     */
    protected String getTimeStampName() {
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            return "" + number.nextInt(10000) + System.currentTimeMillis();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("生成安全随机数失败：{}", e.getMessage());
        }
        return "" + System.currentTimeMillis();
    }


    public synchronized boolean checkUploadStatus(UploadFile param, File confFile) throws IOException {
        RandomAccessFile confAccessFile = new RandomAccessFile(confFile, "rw");

        // 设置文件长度
        confAccessFile.setLength(param.getTotalChunks());
        // 设置起始偏移量
        confAccessFile.seek(param.getChunkNumber() - 1);
        // 将指定的一个字节写入文件中127
        confAccessFile.write(Byte.MAX_VALUE);
        byte[] completeStatusList = FileUtils.readFileToByteArray(confFile);
        confAccessFile.close();

        // 创建conf文件：文件长度为总分片数，每上传一个分块即向conf文件中写入一个127，
        // 那么没上传的位置就是默认的0，已上传的就是127
        for (int i = 0; i < completeStatusList.length; i++) {
            if (completeStatusList[i] != Byte.MAX_VALUE) {
                return false;
            }
        }
        confFile.delete();
        return true;
    }

    /**
     * 如果是文件夹，返回文件夹名称；否则，返回文件名称（不包含文件扩展名）
     * @author xinchao
     * @date 2022/2/20 15:26
     * @param fileName 文件名称
     * @return String
     */
    protected String getFileName(String fileName) {
        if (!fileName.contains(".")) {
            return fileName;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

}
