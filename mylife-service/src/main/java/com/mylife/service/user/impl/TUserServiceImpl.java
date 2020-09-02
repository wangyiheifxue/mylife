package com.mylife.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.mylife.bean.qo.user.UserQO;
import com.mylife.bean.vo.user.UserVO;
import com.mylife.constant.Const;
import com.mylife.entity.user.TUser;
import com.mylife.mapper.user.TUserMapper;
import com.mylife.service.user.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mylife.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wyh
 * @since 2020-08-20
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

    public List<UserVO> listUser(UserQO qo) {
        return baseMapper.listUser(qo);
    }

    @Override
    public Result loginByMobilePhone(HttpSession session, String mobilePhone, String verificationCode) {

        //-----参数检测
        if(StringUtils.isBlank(mobilePhone)){
            return Result.fail("手机号不能为空");
        }
        if(StringUtils.isBlank(verificationCode)){
            return Result.fail("验证码不能为空");
        }

        //-----数据有效性检测
        QueryWrapper<TUser> qw = new QueryWrapper<>();
        qw.eq("mobile_phone",mobilePhone);
        TUser user = this.getOne(qw);

        if(user == null){
            return Result.fail("用户不存在");
        }else{
            //重置session
            session.removeAttribute(Const.SESSION_USER);
            session.setAttribute(Const.SESSION_USER,user);
        }

        return Result.success("登录成功");
    }

}
