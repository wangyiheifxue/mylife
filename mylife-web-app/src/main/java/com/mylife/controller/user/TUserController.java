package com.mylife.controller.user;

import com.mylife.bean.qo.user.UserQO;
import com.mylife.constant.Const;
import com.mylife.redis.RedisService;
import com.mylife.service.user.ITUserService;
import com.mylife.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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

    @Autowired
    private RedisService redisService;

    /**
     * @description : 从session中获取我的用户信息
     * @author : wyh
     * @date : 2020/9/2 10:39
     * @params : [session]
     * @return : com.mylife.util.Result
     **/
    @GetMapping("/get")
    public Result get(HttpSession session){
        return Result.success(session.getAttribute(Const.SESSION_USER));
    }

    /**
     * @description : 根据id获取用户信息
     * @author : wyh
     * @date : 2020/9/2 10:44
     * @params : [session, id]
     * @return : com.mylife.util.Result
     **/
    @GetMapping("/get/{id}")
    public Result getById(HttpSession session,@PathVariable("id") Long id){
        return Result.success(userService.getById(id));
    }

    /**
     * @description : 查询 用户
     * @author : wyh
     * @date : 2020/8/21 18:30
     * @params : [session, qo]
     * @return : java.lang.Object
     **/
    @RequestMapping("/list")
    public Result listUser(HttpSession session, UserQO qo){
        return Result.success(userService.listUser(qo));
    }

}

