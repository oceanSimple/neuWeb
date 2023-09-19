package com.ocean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.frontParamEntity.user.CheckEmailVerificationCode;
import com.ocean.commonPackage.frontParamEntity.user.CheckTokenParam;
import com.ocean.commonPackage.frontParamEntity.user.LoginUserParam;
import com.ocean.commonPackage.frontParamEntity.user.UpdateUserParam;
import com.ocean.mpPackage.sqlEntity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {
    R<User> login(LoginUserParam param); // 账号密码登录

    R<String> checkToken(CheckTokenParam param); // token登录

    R<User> create(User user); // 注册

    R<String> checkEmailVerificationCode(CheckEmailVerificationCode param); // 验证码校验

    R<User> getUserByCode(String code); // 根据code获取用户信息

    R<User> update(UpdateUserParam param); // 修改个人信息

    R<String> delete(String code); // 删除账号

    R<User> setUserIsDeleted(String code); // 设置账号是否被禁用
}
