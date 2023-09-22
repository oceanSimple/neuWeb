package com.ocean.service;

import com.ocean.commonPackage.common.R;
import com.ocean.commonPackage.entity.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(value = "service-user-8001")
public interface UserService {
    @GetMapping("/user/getUserByCode")
    R<User> getUserByCode(@RequestParam("code") String code);
}
