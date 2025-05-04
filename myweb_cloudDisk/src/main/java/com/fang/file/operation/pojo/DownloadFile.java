package com.fang.file.operation.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 川川
 * @date 2022-02-20 17:07
 */
@Data
@Schema(description = "本地下载文件实体类")
public class DownloadFile {

    @Schema(description = "文件url")
    private String fileUrl;

    @Schema(description = "时间戳")
    private String timestampName;

}
