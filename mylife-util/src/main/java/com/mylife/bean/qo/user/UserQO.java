package com.mylife.bean.qo.user;

import com.mylife.bean.qo.base.BaseQO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQO extends BaseQO {
    private String nickname;
    private Integer enable;
}
