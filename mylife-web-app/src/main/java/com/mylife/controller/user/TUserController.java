package com.mylife.controller.user;

import com.mylife.entity.user.TUser;
import com.mylife.service.user.ITUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wyh
 * @since 2020-08-20
 */
@RestController
@RequestMapping("/user")
public class TUserController {

    @Autowired
    private ITUserService userService;

    @RequestMapping("/test")
    public void test(){
        TUser user = userService.getById(1);
        System.out.println(user);
    }

}

