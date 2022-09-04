package com.fang.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 川川
 * @date 2022-02-18 16:53
 */
@Data
@Schema(description = "文件列表查询接收参数dto")
public class UserFileListDto {

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "当前页")
    private Long currentPage;

    @Schema(description = "一页显示")
    private Long pageCount;
}
