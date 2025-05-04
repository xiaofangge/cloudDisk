package com.fang.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 川川
 * @date 2022-02-16 10:08
 */
@Getter
@Setter
@Schema(description = "注册Dto")  // 用来定义模型类和模型属性
public class RegisterDto {
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "手机号")
    private String telephone;
    @Schema(description = "密码")
    private String password;
}
