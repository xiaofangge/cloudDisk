package com.fang.file.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 川川
 * @date 2022-02-20 19:47
 */
@Data
@Schema(description = "文件上传vo", required = true)
public class UploadFileVo {

    @Schema(description = "时间戳")
    private String timestampName;

    @Schema(description = "跳过上传")
    private boolean skipUpload;

    @Schema(description = "是否需要合并分片")
    private boolean needMerge;

    @Schema(description = "已经上传的分片")
    private List<Integer> uploaded;
}
