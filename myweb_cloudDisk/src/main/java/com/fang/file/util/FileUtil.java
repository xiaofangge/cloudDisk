package com.fang.file.util;

import com.fang.mapper.FileClassifyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文件工具类
 * @author 川川
 * @date 2022-02-20 11:25
 */
@Component
@Slf4j
public class FileUtil {

    @Resource
    private FileClassifyMapper fileClassifyMapper;


    /**
     * 判断是否为音乐
     * @author xinchao
     * @date 2022/2/20 11:38
     * @param extendName    文件扩展名
     * @return boolean
     */
    public boolean isMusic(String extendName) {
        // 音乐
        List<String> MUSIC_FILE = fileClassifyMapper.getFileExtends(4);
        for (String s : MUSIC_FILE) {
            if (s.equalsIgnoreCase(extendName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否为图片文件
     * @author xinchao
     * @date 2022/2/20 11:38
     * @param extendName    文件扩展名
     * @return boolean
     */
    public  boolean isImageFile(String extendName) {
        // 图片
        List<String> IMG_FILE = fileClassifyMapper.getFileExtends(1);
        for (String s : IMG_FILE) {
            if (s.equalsIgnoreCase(extendName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为文档
     * @author xinchao
     * @date 2022/2/20 11:38
     * @param extendName    文件扩展名
     * @return boolean
     */
    public  boolean isDocxFile(String extendName) {
        // 文档
        List<String> DOC_FILE = fileClassifyMapper.getFileExtends(2);
        for (String s : DOC_FILE) {
            if (s.equalsIgnoreCase(extendName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为视频
     * @author xinchao
     * @date 2022/2/20 11:38
     * @param extendName    文件扩展名
     * @return boolean
     */
    public  boolean isVideo(String extendName) {
        // 视频
        List<String> VIDEO_FILE = fileClassifyMapper.getFileExtends(3);
        for (String s : VIDEO_FILE) {
            if (s.equalsIgnoreCase(extendName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件扩展名
     * @author xinchao
     * @date 2022/2/20 11:40
     * @param fileName      文件名
     * @return String       文件扩展名
     */
    public String getFileExtendName(String fileName) {
        if (fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取不包含扩展名的文件名
     * @author xinchao
     * @date 2022/2/20 11:47
     * @param fileName      文件名
     * @return String
     */
    public String getFileNameNotExtend(String fileName) {
        if (fileName == null) {
            fileName = "";
        }
        String fileExtendName = getFileExtendName(fileName);
        return fileName.replace("." + fileExtendName, "");
    }
}
