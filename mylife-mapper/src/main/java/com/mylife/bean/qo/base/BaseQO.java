package com.mylife.bean.qo.base;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseQO {
    /**
     * 用户id
     */
    protected Long userId;
    /**
     * 分页查询-页码数
     */
    protected Integer page;
    /**
     * 分页查询-每页条数
     */
    protected Integer pageSize;
    /**
     * 分页查询-起始行数
     */
    protected Integer startRow;
    /**
     * 关键词
     */
    protected String keywords;
    /**
     * 数据逻辑状态：0：逻辑删除；1：有效数据；默认1；
     */
    protected Integer status;
}
