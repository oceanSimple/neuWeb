package com.ocean;

import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import com.ocean.commonPackage.frontParamEntity.user.CheckEmailVerificationCode;
import com.ocean.commonPackage.frontParamEntity.user.CheckTokenParam;
import com.ocean.commonPackage.frontParamEntity.user.LoginUserParam;
import com.ocean.commonPackage.frontParamEntity.user.UpdateUserParam;
import com.ocean.mpPackage.sqlEntity.User;
import com.ocean.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerTest {
    private final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
    @Autowired
    private UserServiceImpl service;

    @Test
    public void test() {
        // 测试注册
        R<User> user = create();
        if (user.getCode().equals(RCodeEnum.SUCCESS.getCode())) log.info("注册成功");
        else log.info("注册失败");

        // 测试login登录
        LoginUserParam loginUserParam = new LoginUserParam(user.getData().getCode(), "123456");
        R<User> login = this.login(loginUserParam);
        if (login.getCode().equals(RCodeEnum.SUCCESS.getCode())) log.info("登录成功");
        else log.info("登录失败");

        // 测试checkToken
        CheckTokenParam checkTokenParam = new CheckTokenParam(login.getData().getCode(), login.getData().getToken());
        R<String> checkToken = this.checkToken(checkTokenParam);
        if (checkToken.getCode().equals(RCodeEnum.SUCCESS.getCode())) log.info("token验证成功");
        else log.info("token验证失败");

        // 测试checkEmailVerificationCode
        this.checkEmailVerificationCode();

        // 测试getUserByCode
        this.getUserByCode();

        // 测试update
        this.update();

        // 测试setUserIsDeleted
        this.setUserIsDeleted(user.getData().getCode());

        // 测试delete
        this.delete(user.getData().getCode());
    }

    //@Test
    public R<User> login(LoginUserParam loginUserParam) {
        return service.login(loginUserParam);
    }

    //@Test
    public R<String> checkToken(CheckTokenParam checkTokenParam) {
        return service.checkToken(checkTokenParam);
    }

    //@Test
    public R<User> create() {
        User user = new User();
        user.setCode("99999999");
        user.setPassword("123456");
        user.setNickname("测试用户");
        user.setCampus("南校区");
        user.setDormitory("南校区1号楼");
        user.setEmail("123");
        user.setPhone("123");
        return service.create(user);
    }

    //@Test
    public void checkEmailVerificationCode() {
        R<String> stringR = service.checkEmailVerificationCode(new CheckEmailVerificationCode("1", "123456"));
        if (stringR.getCode() .equals(RCodeEnum.SUCCESS.getCode())) log.info("验证成功");
        else log.info("验证失败");
    }

    //@Test
    public void getUserByCode() {
        R<User> userByCode = service.getUserByCode("00000000");
        if (userByCode.getCode().equals(RCodeEnum.SUCCESS.getCode())) log.info("查询成功");
        else log.info("查询失败");
    }

    //@Test
    public void update() {
        UpdateUserParam updateUserParam = new UpdateUserParam();
        updateUserParam.setCode("00000000");
        updateUserParam.setTarget("nickname");
        updateUserParam.setData("test");
        System.out.println(service.update(updateUserParam));
    }

    //@Test
    public void setUserIsDeleted(String code) {
        R<User> userR = service.setUserIsDeleted(code);
        if (userR.getCode() .equals(RCodeEnum.SUCCESS.getCode())) log.info("封号成功");
        else log.info("封号失败");
    }

    //@Test
    public void delete(String code) {
        R<String> delete = service.delete(code);
        if (delete.getCode().equals(RCodeEnum.SUCCESS.getCode())) log.info("删除成功");
        else log.info("删除失败");
    }
}