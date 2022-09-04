package com.fang.file.operation.upload.product;

import com.fang.common.exception.file.NotSameFileException;
import com.fang.common.exception.file.UploadGeneralException;
import com.fang.file.operation.pojo.UploadFile;
import com.fang.file.operation.upload.Uploader;
import com.fang.file.util.FileUtil;
import com.fang.file.util.PathUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 * 本地上传实现类
 * @author 川川
 * @date 2022-02-20 15:45
 */
@Component
public class LocalStorageUploader extends Uploader {

    @Resource(name = "fileUtil")
    private FileUtil fileUtil;

    @Override
    public List<UploadFile> upload(HttpServletRequest request, UploadFile uploadFile) throws UploadGeneralException {
        List<UploadFile> saveUploadFileList = new ArrayList<>();
        StandardMultipartHttpServletRequest standardMultipartHttpServletRequest = (StandardMultipartHttpServletRequest) request;
        boolean isMultipart = ServletFileUpload.isMultipartContent(standardMultipartHttpServletRequest);
        if (!isMultipart) {
            throw new UploadGeneralException("未包含文件上传域");
        }

        String savePath = getSaveFilePath();

        try {
            Iterator<String> iter = standardMultipartHttpServletRequest.getFileNames();
            while (iter.hasNext()) {
                saveUploadFileList = doUpload(standardMultipartHttpServletRequest, savePath, iter, uploadFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new UploadGeneralException("未包含文件上传域");
        }
        return saveUploadFileList;
    }

    /**
     * 执行上传动作
     * @author xinchao
     * @date 2022/2/20 15:55
     * @param standardMultipartHttpServletRequest standard multipart request
     * @param savePath      保存路径
     * @param iter          迭代器
     * @param uploadFile    上传文件信息
     * @return List<UploadFile>
     */
    private List<UploadFile> doUpload(StandardMultipartHttpServletRequest standardMultipartHttpServletRequest, String savePath,
                                      Iterator<String> iter, UploadFile uploadFile) throws IOException, NotSameFileException {
        List<UploadFile> saveUploadFileList = new ArrayList<>();
        // 获得文件
        MultipartFile multipartFile = standardMultipartHttpServletRequest.getFile(iter.next());

        String timeStampName = uploadFile.getIdentifier();
        String originalName = Objects.requireNonNull(multipartFile).getOriginalFilename();

        // 文件名称
        String fileName = getFileName(Objects.requireNonNull(originalName));
        // 文件类型(即文件扩展名)
        String fileType = fileUtil.getFileExtendName(originalName);

        uploadFile.setFileName(fileName);
        uploadFile.setFileType(fileType);
        uploadFile.setTimestampName(timeStampName);

        String saveFilePath = savePath + FILE_SEPARATOR + timeStampName + "." + fileType;
        String tempFilePath = savePath + FILE_SEPARATOR + timeStampName + "." + fileType + "_tmp";
        String minFilePath = savePath + FILE_SEPARATOR + timeStampName + "_min" + "." + fileType;
        String confFilePath = savePath + FILE_SEPARATOR + timeStampName + ".conf";

        File file = new File(PathUtil.getStaticPath() + FILE_SEPARATOR + saveFilePath);
        File tempFile = new File(PathUtil.getStaticPath() + FILE_SEPARATOR + tempFilePath);
        File minFile = new File(PathUtil.getStaticPath() + FILE_SEPARATOR + minFilePath);
        File confFile = new File(PathUtil.getStaticPath() + FILE_SEPARATOR + confFilePath);

        uploadFile.setUrl(saveFilePath);
        if (StringUtils.isEmpty(uploadFile.getTaskId())) {
            uploadFile.setTaskId(UUID.randomUUID().toString());
        }

        // 第一步：打开将要写入的文件
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        // 第二步：打开通道
        FileChannel channel = raf.getChannel();
        // 第三步：计算偏移量
        long position = (uploadFile.getChunkNumber() - 1) * uploadFile.getChunkSize();
        // 第四步：获取分片数据
        byte[] fileData = multipartFile.getBytes();
        // 第五步：写入数据
        channel.position(position);
        channel.write(ByteBuffer.wrap(fileData));
        channel.force(true);
        channel.close();
        // 判断是否完成文件的传输并进行校验与重命名
        boolean isComplete = checkUploadStatus(uploadFile, confFile);
        if (isComplete) {
            FileInputStream fis = new FileInputStream(tempFile.getPath());
            String md5 = DigestUtils.md5DigestAsHex(fis);
            fis.close();
            if (StringUtils.isNoneBlank(md5) && !StringUtils.equals(md5, uploadFile.getIdentifier())) {
                throw new NotSameFileException();
            }
            tempFile.renameTo(file);
            if (fileUtil.isImageFile(uploadFile.getFileType())) {
                Thumbnails.of(file).size(300, 300).toFile(minFile);
            }

            uploadFile.setSuccess(1);
            uploadFile.setMessage("上传成功");
        } else {
            uploadFile.setSuccess(0);
            uploadFile.setMessage("未完成");
        }

        uploadFile.setFileSize(uploadFile.getTotalSize());

        saveUploadFileList.add(uploadFile);
        return saveUploadFileList;
    }


    public static void main(String[] args) {
        System.out.println(PathUtil.getStaticPath());
    }
}
