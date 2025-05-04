package com.fang.common.exception.file;

/**
 * 上传异常
 * @author 川川
 * @date 2022-02-20 10:36
 */
public abstract class UploadException extends Exception {

    // 使用指定的详细消息构造一个新异常
    protected UploadException(String message) {
        super(message);
    }

    // 使用指定的详细消息和原因构造一个新异常
    protected UploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
