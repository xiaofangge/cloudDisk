package com.fang.controller;

import com.fang.common.dto.LoginDto;
import com.fang.common.dto.RegisterDto;
import com.fang.common.vo.LoginVo;
import com.fang.common.vo.R;
import com.fang.pojo.User;
import com.fang.service.UserService;
import com.fang.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 川川
 * @date 2022-02-16 10:10
 */
@Tag(name = "user", description = "该接口为用户接口，主要做用户登录、注册和校验token")  // @Tag标签用来描述一组操作的信息
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    /**
     * 用户注册
     * @author xinchao
     * @date 2022/2/16 10:13
     * @param registerDto 注册dto
     * @return R
     */
    @Operation(summary = "用户注册", description = "注册账号", tags = {"users"}) // 用来描述接口信息
    @PostMapping(value = "/register")
    public R register(@RequestBody RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setTelephone(registerDto.getTelephone());
        user.setPassword(registerDto.getPassword());
        return userService.registerUser(user);
    }

    /**
     * 用户登录
     * @author xinchao
     * @date 2022/2/16 10:23
     * @param loginDto 登录dto
     * @return R
     */
    @Operation(summary = "用户登录", description = "用户登录认证后才能进入系统", tags = {"users"})
    @PostMapping(value = "/login")
    public R login(@RequestBody LoginDto loginDto) {
        User user = new User();
        user.setTelephone(loginDto.getTelephone());
        user.setPassword(loginDto.getPassword());
        R<User> serviceResult = userService.login(user);

        // 登录失败
        if (!serviceResult.getSuccess()) {
            return serviceResult;
        }

        // 登录成功
        LoginVo loginVo = new LoginVo();
        loginVo.setUsername(serviceResult.getData().getUsername());
        String jwt = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jwt = JWTUtil.createJWT("xinchaoShare", "xinchao", objectMapper.writeValueAsString(serviceResult.getData()));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建jwt失败");
            return R.fail().message("登录失败");
        }
        loginVo.setToken(jwt);
        return R.success().data(loginVo);
    }

    /**
     * 用户登录成功后，可以调用该接口来获取登录状态，判断token是否失效，保证前后台登录状态一致
     * 如果 token 不正确，或者 token 过期，就会导致解码失败，返回认证失败，如果能够正确解析，那么就会返回成功
     * @author xinchao
     * @date 2022/2/16 10:49
     * @param token token
     * @return R<User>
     */
    @Operation(summary = "检查用户登录信息", description = "验证token有效性", tags = {"users"})
    @GetMapping("/checkUserLoginInfo")
    public R<User> checkToken(@RequestHeader(value = "token") String token) {
        R<User> result = new R<>();

        User tokenUserInfo = null;
        try {
            Claims claims = JWTUtil.parseJWT(token);
            String subject = claims.getSubject();
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON转对象
            tokenUserInfo = objectMapper.readValue(subject, User.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("解码异常：{}", e.getMessage());
            return R.fail().message("认证失败");
        }

        if (tokenUserInfo == null) {
            return R.fail().message("用户未登录");
        }
        return R.success().data(tokenUserInfo);
    }
}
