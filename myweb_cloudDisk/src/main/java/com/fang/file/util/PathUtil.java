package com.fang.file.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 路径工具类
 * @author 川川
 * @date 2022-02-20 13:51
 */
@Slf4j
public class PathUtil {

    public static String getFilePath() {
        String path = "upload";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // File.separator 路径分隔符（跨平台）
        path = File.separator + path + File.separator + sdf.format(new Date());

        String staticPath = getStaticPath();
        File dir = new File(staticPath + path);
        if (!dir.exists()) {
            try {
                boolean isSuccessMkdirs = dir.mkdirs();
                if (!isSuccessMkdirs) {
                    log.error("目录创建失败：{}", getStaticPath() + path);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("目录创建失败：{}", getStaticPath() + path);
                return "";
            }
        }
        return path;
    }

    public static String getStaticPath() {
        String localStoragePath = PropertiesUtil.getProperty("file.local-storage-path");
        if (StringUtils.isNotEmpty(localStoragePath)) {
            return localStoragePath;
        } else {
            String projectRootAbsolutePath = getProjectRootPath();
            int index = Objects.requireNonNull(projectRootAbsolutePath).indexOf("file:");
            if (index != -1) {
                projectRootAbsolutePath = projectRootAbsolutePath.substring(0, index);
            }
            return projectRootAbsolutePath + "static" + File.separator;
        }
    }

    public static void main(String[] args) {
        String localStoragePath = PropertiesUtil.getProperty("file.local-storage-path");
    }

    /**
     * 获取项目所在的根目录路径resources路径
     * @author xinchao
     * @date 2022/2/20 14:12
     * @return String
     */
    public static String getProjectRootPath() {
        String absolutePath = null;
        try {
            String url = ResourceUtils.getURL("classpath:").getPath();
            absolutePath = urlDecode(new File(url).getAbsolutePath()) + File.separator;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("获取项目所在根目录路径异常：{}", e.getMessage());
        }
        return absolutePath;
    }


    /**
     * 路径解码
     * @author xinchao
     * @date 2022/2/20 14:21
     * @param url   路径
     * @return String
     */
    public static String urlDecode(String url) {
        String decodedUrl = null;
        try {
            decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("路径解码失败：{}", e.getMessage());
        }
        return decodedUrl;
    }
}
