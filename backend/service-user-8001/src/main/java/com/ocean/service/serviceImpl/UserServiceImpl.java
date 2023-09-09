package com.ocean.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import com.ocean.commonPackage.frontParamEntity.user.CheckEmailVerificationCode;
import com.ocean.commonPackage.frontParamEntity.user.CheckTokenParam;
import com.ocean.commonPackage.frontParamEntity.user.LoginUserParam;
import com.ocean.commonPackage.frontParamEntity.user.UpdateUserParam;
import com.ocean.mapper.UserMapper;
import com.ocean.service.UserService;
import com.ocean.mpPackage.sqlEntity.User;
import com.ocean.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final StringRedisTemplate stringRedisTemplate; // redis操作模板

    // 构造器注入
    @Autowired
    public UserServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    // 登录
    @Override
    public R<User> login(LoginUserParam param) {
        // 将用户的密码进行md5加密
        param.setPassword(Util.md5Encryption(param.getPassword()));
        // 数据库查找，通过账号获取数据
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", param.getCode());
        User result = getOne(queryWrapper);
        // 判断账号是否存在
        if (result == null) {
            return R.error(RCodeEnum.NO_DATA.getCode(), "账号不存在");
        }
        // 判断密码是否正确
        if (!result.getPassword().equals(param.getPassword())) {
            return R.error(RCodeEnum.IDENTIFY_ERROR.getCode(), "密码错误");
        }
        // 判断账号是否被禁用
        if (result.getIsDeleted() == 1) {
            return R.error(RCodeEnum.IS_DELETED.getCode(), "账号已被禁用");
        }
        // 如果通过上面的检查，说明账号密码正确，且账号未被禁用，生成token，存入redis
        result.setToken(Util.getUUID()); // 生成token
        stringRedisTemplate.opsForHash().put("userToken", result.getCode(), result.getToken()); // 存入redis
        // 返回结果
        return R.success(RCodeEnum.SUCCESS.getCode(), "登录成功", result);
    }

    // token登录
    @Override
    public R<String> checkToken(CheckTokenParam param) {
        // 判断参数是否为空
        if (param.getToken() == null || param.getCode() == null) {
            return R.error(RCodeEnum.PARAM_ERROR.getCode(), "token或code不能为空");
        }
        // 从redis中获取token
        String token = (String) stringRedisTemplate.opsForHash().get("userToken", param.getCode());
        if (token == null) {
            return R.error(RCodeEnum.IDENTIFY_ERROR.getCode(), "token不存在，请重新登录");
        }
        // 判断token是否正确
        if (!token.equals(param.getToken())) {
            return R.error(RCodeEnum.IDENTIFY_ERROR.getCode(), "token错误，请重新登录");
        }
        // 通过上面的检查，说明token正确，返回用户信息
        return R.success(RCodeEnum.SUCCESS.getCode(), RCodeEnum.SUCCESS.getMsg(), "token验证成功,自动登录成功");
    }

    // 注册
    @Override
    public R<User> create(User user) {
        // 将密码进行md5加密
        user.setPassword(Util.md5Encryption(user.getPassword()));
        // 这一步很神奇地帮忙把id设置赋值为数据库自增的id，暂时不知道原理
        boolean save = save(user);
        // 判断是否注册成功
        if (save) {
            // 通过code查找用户,若注册成功，则返回用户信息
            R<User> result = getUserByCode(user.getCode());
            if (result.getCode() != 200) {
                return R.error(RCodeEnum.ERROR.getCode(), "注册失败");
            } else {
                return R.success(RCodeEnum.SUCCESS.getCode(), "注册成功", result.getData());
            }
        } else {
            return R.error(RCodeEnum.ERROR.getCode(), "注册失败");
        }
    }

    // 检查邮箱验证码
    @Override
    public R<String> checkEmailVerificationCode(CheckEmailVerificationCode param) {
        // 判断参数是否为空
        if (param.getEmail() == null || param.getCode() == null) {
            return R.error(RCodeEnum.PARAM_ERROR.getCode(), "邮箱或验证码不能为空");
        }
        // 从redis中获取验证码
        String verificationCode = (String) stringRedisTemplate.opsForHash().get("emailVerificationCode", param.getEmail());
        if (verificationCode == null) {
            return R.error(RCodeEnum.IDENTIFY_ERROR.getCode(), "验证码不存在，请重新获取");
        }
        // 判断验证码是否正确
        if (!verificationCode.equals(param.getCode())) {
            return R.error(RCodeEnum.IDENTIFY_ERROR.getCode(), "验证码错误，请重新输入");
        }
        // 通过上面的检查，说明验证码正确，返回结果
        return R.success(RCodeEnum.SUCCESS.getCode(), RCodeEnum.SUCCESS.getMsg(), "验证码正确");
    }

    // 根据code获取用户信息
    @Override
    public R<User> getUserByCode(String code) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        User user = getOne(queryWrapper);
        if (user == null) {
            return R.error(RCodeEnum.NO_DATA.getCode(), "查询失败");
        }
        return R.success(RCodeEnum.SUCCESS.getCode(), "查询成功", user);
    }

    // 修改个人信息
    @Override
    public R<User> update(UpdateUserParam param) {
        // 判断参数是否为空
        if (param.getCode() == null || param.getTarget() == null || param.getData() == null) {
            return R.error(RCodeEnum.PARAM_ERROR.getCode(), "参数不能为空");
        }
        // 如果修改的是密码，则需要将密码进行md5加密
        if (param.getTarget().equals("password")) {
            param.setData(Util.md5Encryption(param.getData()));
        }
        // 修改用户信息
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", param.getCode()).set(param.getTarget(), param.getData());
        boolean update = update(new User(), updateWrapper);
        // 判断是否修改成功
        if (update) {
            // 通过code查找用户,若修改成功，则返回用户信息
            R<User> result = getUserByCode(param.getCode());
            if (result.getCode() != 200) {
                return R.error(RCodeEnum.ERROR.getCode(), "修改失败");
            } else {
                return R.success(RCodeEnum.SUCCESS.getCode(), "修改成功", result.getData());
            }
        } else {
            return R.error(RCodeEnum.ERROR.getCode(), "修改失败");
        }
    }
}
