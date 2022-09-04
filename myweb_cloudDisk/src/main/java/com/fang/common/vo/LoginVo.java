package com.fang.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 存放用户登录接口响应参数
 * @author 川川
 * @date 2022-02-16 10:27
 */
@Getter
@Setter
@Schema(description = "登录Vo")
public class LoginVo {
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "token")
    private String token;
}
