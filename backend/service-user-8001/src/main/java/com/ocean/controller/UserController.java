package com.ocean.controller;

import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.frontParamEntity.user.CheckEmailVerificationCode;
import com.ocean.commonPackage.frontParamEntity.user.CheckTokenParam;
import com.ocean.commonPackage.frontParamEntity.user.LoginUserParam;
import com.ocean.commonPackage.frontParamEntity.user.UpdateUserParam;
import com.ocean.service.UserService;
import com.ocean.mpPackage.sqlEntity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    private final UserService userService; // 用于处理用户相关的业务

    // 通过构造器注入UserService
    @Autowired
    private UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录(账号密码)
     *
     * @param param 用户登录参数，包含code和password
     * @return 返回查询的用户信息
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody LoginUserParam param) {
        return userService.login(param);
    }

    /**
     * 登录(token)
     *
     * @param param 包含code和token
     * @return 返回token是否正确
     */
    @PostMapping("/checkToken")
    public R<String> checkToken(@RequestBody CheckTokenParam param) {
        return userService.checkToken(param);
    }

    /**
     * 注册
     *
     * @param user 用户信息
     * @return 返回注册结果
     */
    @PostMapping("/register")
    public R<User> register(@RequestBody User user) {
        return userService.create(user);
    }

    /**
     * 检查邮箱验证码
     *
     * @param param 包含email和code
     * @return 返回验证码是否正确
     */
    @PostMapping("/checkEmailVerificationCode")
    public R<String> checkEmailVerificationCode(@RequestBody CheckEmailVerificationCode param) {
        return userService.checkEmailVerificationCode(param);
    }

    /**
     * 更新用户信息
     *
     * @param param 包含code,target(修改字段)和data(数据)
     * @return 返回更新结果
     */
    @PostMapping("/update")
    public R<User> update(@RequestBody UpdateUserParam param) {
        return userService.update(param);
    }
}
