package com.fang.common.exception.file;

/**
 * 文件上传通用异常
 * @author 川川
 * @date 2022-02-20 10:42
 */
public class UploadGeneralException extends UploadException{

    public UploadGeneralException(Throwable cause) {
        super("上传出现了异常", cause);
    }

    public UploadGeneralException(String message) {
        super(message);
    }

    public UploadGeneralException(String message, Throwable cause) {
        super(message, cause);
    }

}
