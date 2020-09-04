package com.mylife.service.user;

import com.mylife.bean.qo.user.UserQO;
import com.mylife.bean.vo.user.UserVO;
import com.mylife.entity.user.TUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mylife.util.Result;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wyh
 * @since 2020-08-20
 */
public interface ITUserService extends IService<TUser> {

    /**
     * @description : 查询 用户
     * @author : wyh
     * @date : 2020/8/21 18:14
     * @params : [qo]
     * @return : java.util.List<com.mylife.bean.vo.user.UserVO>
     **/
    List<UserVO> listUser(UserQO qo);

    /**
     * @description : 计数 用户
     * @author : wyh
     * @date : 2020/9/2 11:35
     * @params : [qo]
     * @return : java.lang.Integer
     **/
    Integer countUser(UserQO qo);

    /**
     * @description : 使用手机号登录
     * @author : wyh
     * @date : 2020/9/2 10:19
     * @params : [session, mobilePhone, verificationCode]
     * @return : com.mylife.util.Result
     **/
    Result loginByMobilePhone(HttpSession session, String mobilePhone, String verificationCode);

}
