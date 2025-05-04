package com.fang.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author 川川
 * @date 2022-02-20 19:33
 */
@Data
@Schema(description = "上传文件dto", required = true)
public class UploadFileDto {

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "长传时间")
    private Date uploadTime;

    @Schema(description = "扩展名")
    private String extendName;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件大小")
    private Long fileSize;

    @Schema(description = "切片数量")
    private int chunkNumber;

    @Schema(description = "切片大小")
    private long chunkSize;

    @Schema(description = "所有切片")
    private int totalChunks;

    @Schema(description = "总大小")
    private long totalSize;

    @Schema(description = "当前切片大小")
    private long currentChunkSize;

    @Schema(description = "md5码")
    private String identifier;

}
