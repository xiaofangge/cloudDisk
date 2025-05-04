package com.fang.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author 川川
 * @date 2022-02-18 17:06
 */
@Data
@Schema(description = "用户文件列表查询返回信息vo")
public class UserFileListVo {

    @Schema(description = "文件id")
    private Long fileId;

    @Schema(description = "文件url")
    private String fileUrl;

    @Schema(description = "文件大小")
    private Long fileSize;

    @Schema(description = "时间戳")
    private String timestampName;

    @Schema(description = "引用数量")
    private Integer pointCount;

    @Schema(description = "md5")
    private String identifier;

    @Schema(description = "存储类型")
    private Integer storageType;

    @Schema(description = "用户文件id")
    private Long userFileId;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "扩展名")
    private String extendName;

    @Schema(description = "是否是目录")
    private Boolean isDir;

    @Schema(description = "上传时间")
    private Date uploadTime;

    @Schema(description = "是否删除")
    private Boolean deleteFlag;

    @Schema(description = "删除时间")
    private Date deleteTime;

    @Schema(description = "删除批次号")
    private Date deleteNum;

    @Schema(description = "图片宽度")
    private Integer imageWidth;

    @Schema(description = "图片高度")
    private Integer imageHeight;
}
