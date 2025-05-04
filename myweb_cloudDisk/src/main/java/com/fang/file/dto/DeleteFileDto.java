package com.fang.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 川川
 * @date 2022-02-21 13:55
 */
@Data
@Schema(description = "删除文件dto", required = true)
public class DeleteFileDto {

    @Schema(description = "用户文件id")
    private Long userFileId;

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "是否是目录")
    private Boolean isDir;
}
