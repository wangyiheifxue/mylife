package com.mylife.service.user;

import com.mylife.bean.qo.user.UserQO;
import com.mylife.bean.user.SUser;

import java.util.List;

public interface SUserService {

    long save(SUser user);

    void delete(long id);

    List<SUser> list(UserQO qo);

    long count(UserQO qo);
}
