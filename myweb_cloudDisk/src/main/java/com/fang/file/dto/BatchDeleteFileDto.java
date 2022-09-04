package com.fang.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 川川
 * @date 2022-02-21 13:59
 */
@Data
@Schema(description = "批量删除文件dto")
public class BatchDeleteFileDto {

    @Schema(description = "文件集合")
    private String files;

}
