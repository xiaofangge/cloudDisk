package com.fang.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 川川
 * @date 2022-02-21 15:54
 */
@Data
@Schema(description = "批量移动文件dto", required = true)
public class BatchMoveFileDto {

    @Schema(description = "文件集合")
    private String files;

    @Schema(description = "文件路径")
    private String filePath;

}
