package com.fang.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 川川
 * @date 2022-02-16 10:09
 */
@Getter
@Setter
@Schema(description = "登录Dto")
public class LoginDto {
    @Schema(description = "手机号")
    private String telephone;
    @Schema(description = "密码")
    private String password;
}
