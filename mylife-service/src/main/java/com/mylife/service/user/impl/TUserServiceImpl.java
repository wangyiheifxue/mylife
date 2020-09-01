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
    public Map<String, Object> loginByUserName(HttpSession session, String userName, String password, String verificationCode) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("success",false);

        // 参数检测
        if(StringUtils.isBlank(userName)){
            result.put("message","用户名不能为空");
            return result;
        }
        if(StringUtils.isBlank(password)){
            result.put("message","密码不能为空");
            return result;
        }
        if(StringUtils.isBlank(verificationCode)){
            result.put("message","验证码不能为空");
            return result;
        }

        // 数据有效性检测
        QueryWrapper<TUser> qw = new QueryWrapper<>();
        qw.eq("user_name",userName);
        qw.eq("password",password);
        TUser user = this.getOne(qw);
        if(user == null){
            result.put("message","用户不存在");
            return result;
        }else{
            session.removeAttribute(Const.SESSION_USER);
            session.setAttribute(Const.SESSION_USER,user);
        }

        result.put("success",true);
        result.put("message","登录成功");
        return result;
    }

}
