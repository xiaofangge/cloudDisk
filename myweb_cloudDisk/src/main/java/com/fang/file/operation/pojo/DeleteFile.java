package com.fang.file.operation.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 川川
 * @date 2022-02-20 19:15
 */
@Data
@Schema(description = "文件删除实体类")
public class DeleteFile {

    @Schema(description = "文件url")
    private String fileUrl;

    @Schema(description = "时间戳")
    private String timestampName;

}
