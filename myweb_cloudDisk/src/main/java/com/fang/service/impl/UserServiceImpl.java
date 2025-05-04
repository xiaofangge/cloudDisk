package com.fang.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.fang.common.vo.R;
import com.fang.mapper.StorageMapper;
import com.fang.mapper.UserMapper;
import com.fang.pojo.Storage;
import com.fang.pojo.User;
import com.fang.service.UserService;
import com.fang.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author 川川
 * @date 2022-02-15 21:33
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private StorageMapper storageMapper;

    /**
     * 用户注册
     * @author xinchao
     * @date 2022/2/15 22:24
     * @param user user
     * @return R
     */
    @Override
    public R registerUser(User user) {
        // 验证手机号是否存在
        if (isTelephoneExit(user.getTelephone())) {
            return R.fail().message("手机号已存在");
        }

        // 生成盐
        String salt = UUID.randomUUID().toString().replace("-", "").substring(15);
        String passwordAndSalt = user.getPassword() + salt;
        // md5加密
        String newPassword = DigestUtils.md5DigestAsHex(passwordAndSalt.getBytes());

        user.setSalt(salt);
        user.setPassword(newPassword);
        user.setRegisterTime(new Date());

        int insert = userMapper.insertSelective(user);
        if (insert <= 0) {
            return R.fail().message("注册用户失败，请检查输入信息");
        }

        // 初始化存储信息
        Storage storage = new Storage();
        storage.setUserId(user.getUserId());
        storage.setTotalSize((long)(Math.pow(1024, 3) * 10));
        storage.setModifyTime(new Date());

        storageMapper.insertSelective(storage);

        return R.success().message("注册成功");
    }


    /**
     * 用户登录
     * @author xinchao
     * @date 2022/2/15 22:25
     * @param user 用户
     * @return R<User>
     */
    @Override
    public R login(User user) {
        User userDB = userMapper.getUserByPhone(user.getTelephone());
        if (userDB == null) {
            return R.fail().message("当前手机号未注册，请先注册");
        }

        String salt = userDB.getSalt();
        String passwordAndSalt = user.getPassword() + salt;

        String encodedPassword = DigestUtils.md5DigestAsHex(passwordAndSalt.getBytes());
        if (!StringUtils.equals(encodedPassword, userDB.getPassword())) {
            return R.fail().message("手机号或密码错误");
        }

        userDB.setPassword(null);
        userDB.setSalt(null);
        return R.success().data(userDB);
    }

    /**
     * 通过token获取用户信息
     * @author xinchao
     * @date 2022/2/18 16:04
     * @param token token
     * @return User
     */
    @Override
    public User getUserByToken(String token) {
        User tokenUserInfo = null;
        try {
            Claims claims = JWTUtil.parseJWT(token);
            // getSubject() 获取用户信息
            String subject = claims.getSubject();
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON转对象
            tokenUserInfo = objectMapper.readValue(subject, User.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("解码异常：{}", e.getMessage());
            return null;
        }
        return tokenUserInfo;
    }


    /**
     * 判断手机号是否存在
     * @author xinchao
     * @date 2022/2/15 21:56
     * @param telephone 手机号
     * @return boolean
     */
    private boolean isTelephoneExit(String telephone) {
        return userMapper.existsByPhone(telephone) != null;
    }
}
