package com.fang.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 川川
 * @date 2022-02-18 16:08
 */
@Data
@Schema(description = "创建文件dto")
public class CreateFileDto {
    @Schema(description = "文件名")
    private String fileName;
    @Schema(description = "文件路径")
    private String filePath;
    @Schema(description = "是否是文件夹（false:不是 true:是）")
    private Boolean isDir;
}
