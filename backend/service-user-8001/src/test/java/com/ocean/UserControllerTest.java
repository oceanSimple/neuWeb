package com.ocean;

import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.common.RCode.RCodeEnum;
import com.ocean.commonPackage.frontParamEntity.user.CheckEmailVerificationCode;
import com.ocean.commonPackage.frontParamEntity.user.CheckTokenParam;
import com.ocean.commonPackage.frontParamEntity.user.LoginUserParam;
import com.ocean.commonPackage.frontParamEntity.user.UpdateUserParam;
import com.ocean.mpPackage.sqlEntity.User;
import com.ocean.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
    private final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
    @Autowired
    private UserServiceImpl service;

    @Test
    @Order(1)
    public void create() {
        User user = new User();
        user.setCode("99999999");
        user.setPassword("123456");
        user.setNickname("测试用户");
        user.setCampus("南校区");
        user.setDormitory("南校区1号楼");
        user.setEmail("123");
        user.setPhone("123");
        R<User> result = service.create(user);
        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode()))
            log.info("创建成功");
        else
            log.info("创建失败");
    }

    @Test
    @Order(2)
    public void login() {
        LoginUserParam loginUserParam = new LoginUserParam("99999999", "123456");
        R<User> result = service.login(loginUserParam);
        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode()))
            log.info("登录成功");
        else
            log.info("登录失败");
    }

    @Test
    @Order(3)
    public void checkToken() {
        CheckTokenParam checkTokenParam = new CheckTokenParam("20210000", "123456");
        R<String> result = service.checkToken(checkTokenParam);
        if (result.getCode().equals(RCodeEnum.SUCCESS.getCode()))
            log.info("token验证成功");
        else
            log.info("token验证失败");
    }


    @Test
    @Order(4)
    public void checkEmailVerificationCode() {
        R<String> stringR = service.checkEmailVerificationCode(new CheckEmailVerificationCode("1", "123456"));
        if (stringR.getCode().equals(RCodeEnum.SUCCESS.getCode()))
            log.info("邮箱验证码，验证成功");
        else
            log.info("邮箱验证码，验证失败");
    }

    @Test
    @Order(5)
    public void getUserByCode() {
        R<User> userByCode = service.getUserByCode("00000000");
        if (userByCode.getCode().equals(RCodeEnum.SUCCESS.getCode()))
            log.info("查询成功");
        else
            log.info("查询失败");
    }

    @Test
    @Order(6)
    public void update() {
        UpdateUserParam updateUserParam = new UpdateUserParam();
        updateUserParam.setCode("00000000");
        updateUserParam.setTarget("nickname");
        updateUserParam.setData("test");
        R<User> update = service.update(updateUserParam);
        if (update.getCode().equals(RCodeEnum.SUCCESS.getCode()))
            log.info("更新成功");
        else
            log.info("更新失败");
    }

    @Test
    @Order(7)
    public void setUserIsDeleted() {
        R<User> userR = service.setUserIsDeleted("99999999");
        if (userR.getCode().equals(RCodeEnum.SUCCESS.getCode()))
            log.info("封号成功");
        else
            log.info("封号失败");
    }

    @Test
    @Order(8)
    public void delete() {
        R<String> delete = service.delete("99999999");
        if (delete.getCode().equals(RCodeEnum.SUCCESS.getCode()))
            log.info("删除成功");
        else
            log.info("删除失败");
    }
}