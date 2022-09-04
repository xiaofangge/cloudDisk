package com.fang.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 川川
 * @date 2022-02-24 15:29
 */
@Data
@Schema(description = "用户存储信息vo")
public class StorageVo {

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "已用存储大小")
    private Long storageSize;

    @Schema(description = "总存储大小")
    private Long totalStorageSize;
}
