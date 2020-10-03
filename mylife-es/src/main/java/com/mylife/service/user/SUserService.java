package com.mylife.service.user;

import com.mylife.bean.user.SUser;
import com.mylife.bean.user.SUserQO;

import java.util.List;

public interface SUserService {

    long save(SUser user);

    void delete(long id);

    List<SUser> list(SUserQO qo);

    int count(SUserQO qo);
}
