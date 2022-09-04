package com.fang.file.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 川川
 * @date 2022-02-21 15:42
 */
@Data
@Schema(description = "树节点vo", required = true)
public class TreeNodeVo {

    @Schema(description = "节点id")
    private Long id;

    @Schema(description = "节点名")
    private String label;

    @Schema(description = "深度")
    private Long depth;

    @Schema(description = "是否被关闭")
    private String state = "closed";

    @Schema(description = "属性集合")
    private Map<String, String> attributes = new HashMap<>();

    @Schema(description = "子节点列表")
    private List<TreeNodeVo> children = new ArrayList<>();

}
