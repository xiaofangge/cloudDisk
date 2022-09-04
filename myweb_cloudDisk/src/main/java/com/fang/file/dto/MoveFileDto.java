package com.fang.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 川川
 * @date 2022-02-21 15:51
 */
@Data
@Schema(description = "移动文件dto", required = true)
public class MoveFileDto {

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "旧文件名")
    private String oldFilePath;

    @Schema(description = "扩展名")
    private String extendName;

}
