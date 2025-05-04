package com.fang.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 川川
 * @date 2022-02-20 19:42
 */
@Data
@Schema(description = "下载文件dto", required = true)
public class DownloadFileDto {

    @Schema(description = "用户文件id")
    private Long userFileId;

}
