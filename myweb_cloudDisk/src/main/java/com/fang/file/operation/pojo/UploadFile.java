package com.fang.file.operation.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 本地上传文件实体类
 * @author 川川
 * @date 2022-02-20 14:43
 */
@Data
@Schema(description = "本地上传文件实体类")
public class UploadFile {

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "文件大小")
    private long fileSize;

    @Schema(description = "时间戳")
    private String timestampName;

    @Schema(description = "成功与否")
    private int success;

    @Schema(description = "消息")
    private String message;

    @Schema(description = "文件url")
    private String url;

    // 切片上传相关参数
    @Schema(description = "上传任务id")
    private String taskId;

    @Schema(description = "当前分片数")
    private int chunkNumber;

    @Schema(description = "当前分片大小")
    private long chunkSize;

    @Schema(description = "文件总分片数")
    private int totalChunks;

    @Schema(description = "md5唯一标识")
    private String identifier;

    @Schema(description = "文件总大小")
    private long totalSize;
    private long currentChunkSize;
}
