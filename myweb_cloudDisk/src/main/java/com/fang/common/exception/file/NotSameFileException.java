package com.fang.common.exception.file;

/**
 * 文件md5校验失败异常
 * @author 川川
 * @date 2022-02-20 10:34
 */
public class NotSameFileException extends Exception {

    public NotSameFileException() {
        super("文件md5不同");
    }
}
