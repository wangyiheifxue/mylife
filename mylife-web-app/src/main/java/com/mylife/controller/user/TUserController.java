package com.mylife.controller.user;

import com.mylife.bean.qo.user.UserQO;
import com.mylife.constant.Const;
import com.mylife.entity.user.TUser;
import com.mylife.redis.RedisService;
import com.mylife.service.user.ITUserService;
import com.mylife.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return Result.data(session.getAttribute(Const.SESSION_USER));
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
        return Result.data(userService.getById(id));
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
        return Result.data(userService.listUser(qo));
    }

    /**
     * @description : 计数 用户
     * @author : wyh
     * @date : 2020/9/2 11:36
     * @params : [session, qo]
     * @return : com.mylife.util.Result
     **/
    @RequestMapping("/count")
    public Result countUser(HttpSession session, UserQO qo){
        return Result.data(userService.countUser(qo));
    }

    /**
     * @description : 更新 用户
     * @author : wyh
     * @date : 2020/9/4 15:15
     * @params : [session, user]
     * @return : com.mylife.util.Result
     **/
    @PutMapping("/update")
    public Result countUser(HttpSession session, TUser user){
        TUser operator = (TUser) session.getAttribute(Const.SESSION_USER);
        return userService.updateUser(operator,user);
    }
}

