package com.mylife.mapper.user;

import com.mylife.bean.qo.user.UserQO;
import com.mylife.bean.vo.user.UserVO;
import com.mylife.config.mysqlInjector.base.MyBaseMapper;
import com.mylife.entity.user.TUser;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2020-08-20
 */
public interface TUserMapper extends MyBaseMapper<TUser> {

    /**
     * @description : 查询 用户
     * @author : wyh
     * @date : 2020/8/21 18:14
     * @params : [qo]
     * @return : java.util.List<com.mylife.bean.vo.user.UserVO>
     **/
    List<UserVO> listUser(UserQO qo);

}
