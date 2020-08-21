package com.mylife.service.user.impl;

import com.mylife.bean.qo.user.UserQO;
import com.mylife.bean.vo.user.UserVO;
import com.mylife.entity.user.TUser;
import com.mylife.mapper.user.TUserMapper;
import com.mylife.service.user.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
