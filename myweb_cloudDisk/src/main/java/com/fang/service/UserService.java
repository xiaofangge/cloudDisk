package com.fang.service;

import com.fang.common.vo.R;
import com.fang.pojo.User;

/**
 * @author 川川
 * @date 2022-02-15 21:31
 */
public interface UserService {

    // 用户注册
    R registerUser(User user);

    // 用户登录
    R<User> login(User user);

    // 通过token获取到用户信息
    User getUserByToken(String token);
}
